package se.nrm.bio.mediaserver.rs;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.log4j.Logger;
import org.apache.tika.Tika;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.json.simple.JSONObject;
import se.nrm.bio.mediaserver.business.MediaserviceBean;
import se.nrm.bio.mediaserver.business.StartupBean;
import se.nrm.bio.mediaserver.domain.Lic;
import se.nrm.bio.mediaserver.domain.Media;
import se.nrm.bio.mediaserver.domain.MediaText;
import se.nrm.mediaserver.resteasy.util.AggregateTags;
import se.nrm.mediaserver.resteasy.util.CheckSumFactory;
import se.nrm.mediaserver.resteasy.util.ExifExtraction;
import se.nrm.mediaserver.resteasy.util.FileSystemWriter;
import se.nrm.mediaserver.resteasy.util.FileUploadForm;
import se.nrm.mediaserver.resteasy.util.MediaFactory;
import se.nrm.mediaserver.resteasy.util.MediaURL;
import se.nrm.mediaserver.resteasy.util.PathHelper;
import se.nrm.mediaserver.resteasy.util.TagHelper;
import se.nrm.mediaserver.resteasy.util.Writeable;


/**
 * 19 feb, sök på  // ie:temp, tagit bort MediaText från Media-Ojbektet
 * @author ingimar
 */
@Path("/")
public class MediaResourceForm {
    
    private final static Logger logger = Logger.getLogger(MediaResourceForm.class);
    
    @EJB
    private MediaserviceBean bean;
    
    @EJB 
    private StartupBean envBean;
    ConcurrentHashMap envMap = null;
    

    private int dynamic_status = Response.Status.OK.getStatusCode();

    private final int STATUS_CONFLICT = Response.Status.CONFLICT.getStatusCode();

    private final int STATUS_INTERNAL_SERVER_ERROR = Response.Status.INTERNAL_SERVER_ERROR.getStatusCode();

    public MediaResourceForm() {
    }
    
    @POST
    @Path("upload-file")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.TEXT_PLAIN)
    public Response createNewFile(@MultipartForm FileUploadForm form) throws IOException {
        envMap = envBean.getEnvironment();
        String mimeType = "unknown", hashChecksum = "unknown";
        final String NOT_APPLICABLE = "N/A";

        String displayOrder = form.getDisplayOrder();

        final byte[] fileData = form.getFileData();
        if (null == fileData || fileData.length == 0) {
            String msg = "attribute 'fileData' is null or empty \n";
            logger.info(msg);
            return Response.status(STATUS_INTERNAL_SERVER_ERROR).entity(msg).build();
        }

        String fileUUID = generateRandomUUID();
        String uploadedFileLocation = getAbsolutePathToFile(fileUUID);
        if (uploadedFileLocation.isEmpty()) {
            String msg = "could not create a directory for the mediafile \n";
            logger.info(msg);
            return Response.status(STATUS_INTERNAL_SERVER_ERROR).entity(msg).build();
        }
        writeToFile(form, uploadedFileLocation);

        Tika tika = new Tika();
        mimeType = tika.detect(fileData);

        Media media = null;
        switch (mimeType) {
            case "image/tiff":
                logger.info("mediatype - image/tiff");
            case "image/png":
            case "image/jpeg":
            case "image/gif": {
                boolean exportImage = form.isExport();
                String exifJSON = NOT_APPLICABLE;
                String isExif = (String)envMap.get("is_exif");
                if (Boolean.parseBoolean(isExif)) {
                    try {
                        exifJSON = extractExif(uploadedFileLocation, exifJSON);
                    } catch (ImageProcessingException ex) {
                        logger.info(ex);
                    }
                }

                media = MediaFactory.createImage2(exportImage, exifJSON);

                break;
            }
            case "video/quicktime":
            case "video/mp4": {
                String startTime = form.getStartTime(), endTime = form.getEndTime();
                media = MediaFactory.createVideo(checkStartEndTime(startTime), checkStartEndTime(endTime));
                break;
            }
            case "audio/mpeg":
            case "audio/vorbis":
            case "audio/ogg": {
                String startTime = form.getStartTime(), endTime = form.getEndTime();
                media = MediaFactory.createSound(checkStartEndTime(startTime), checkStartEndTime(endTime));
                break;
            }
            case "application/pdf": {
                media = MediaFactory.createAttachement();
                break;
            }
        }

        if (null == media) {
            String msg = String.format("Mimetype [ %s ] is not supported \n", mimeType);
            logger.info("[media is null]: " + msg);
            return Response.status(STATUS_INTERNAL_SERVER_ERROR).entity(msg).build();
        }

        hashChecksum = CheckSumFactory.createMD5ChecksumFromBytestream(fileData);

        MediaURL url = new MediaURL();
//        String pathToMedia = url.getPathToStream();
        String pathToMedia = (String)envMap.get("relative_stream_url");

        media.setUuid(fileUUID);
        media.setOwner(form.getOwner());
        media.setFilename(form.getFileName());
        media.setMimetype(mimeType);
        media.setVisibility(form.getAccess());
        media.setHash(hashChecksum);
        media.setMediaURL(pathToMedia.concat(fileUUID));

        AggregateTags aggr = new AggregateTags();

        String tagsConcatenated = aggr.aggregateTags(form);

        if (tagsConcatenated != null || !tagsConcatenated.isEmpty()) {
            addingTags(media, tagsConcatenated);
        }

        if (form.getLegend() == null || form.getLegend().isEmpty()) {
            form.setLegend(NOT_APPLICABLE);
        }

        if (form.getLegend() != null && !form.getLegend().isEmpty()) {
            MediaText mediaText;
            String comment = form.getComment();
            if (comment != null) {
                mediaText = new MediaText(form.getLegend(), form.getLanguage(), media, comment);
            } else {
                mediaText = new MediaText(form.getLegend(), form.getLanguage(), media);
            }
            // ie:temp
//            media.addMediaText(mediaText);
        }

        final String licenceType = form.licenceType();
        if (licenceType != null) {
            Lic license = fetchFromDB(licenceType);
            media.getLics().add(license);
        }

        writeToDatabase(media);
        String responseOutput = fileUUID;

        Response build = Response.status(dynamic_status).header("mediaUUID", responseOutput).entity(responseOutput).build();

        return build;
    }
    
    private String extractExif(String location, String exifJSON) throws ImageProcessingException, IOException {
        Metadata metadata = ImageMetadataReader.readMetadata(new File(location));
        JSONObject jsonObject = packageEXIF_IN_JSON(metadata, true);
        exifJSON = jsonObject.toJSONString();
        final String prefix = "<![CDATA[";
        final String suffix = "]]>";
        exifJSON = prefix.concat(exifJSON).concat(suffix);
        return exifJSON;
    }

    /**
     * @TODO JUnit gives java.util.ConcurrentModificationException - must use
     * iterator and it.remove(); to handle this.
     *
     * @param updatedMediaText
     * @param media
     * @return
     */
    protected Media updateMediatext(final MediaText updatedMediaText, final Media media) {
//        final String locale = updatedMediaText.getLang();
//        
//         // ie:temp
//        final Set<MediaText> mediatextList = media.getTexts();
//
//        String comment = "";
//        try {
//            Iterator<MediaText> it = mediatextList.iterator();
//            while (it.hasNext()) {
//                MediaText mediaText = it.next();
//                comment = mediaText.getComment().trim();
//                if (mediaText.getLang().equals(locale)) {
//                    bean.delete(mediaText);
//                    it.remove();
//                }
//            }
//            if (!comment.isEmpty()) {
//                if (comment.equals(updatedMediaText.getComment().trim())) {
//                    updatedMediaText.setComment(comment);
//                }
//            }
//            media.addMediaText(updatedMediaText);
//        } catch (ConcurrentModificationException ex) {
//            logger.info(ex);
//            throw new ConcurrentModificationException("probz with MEDIA_TEXT", ex);
//        }
//
//        return media;
        return null;
    }

    /**
     * Only one Licensetype for a media-file. That Constraint is in this method
     *
     * @param licenceType
     * @param media
     * @return
     */
    protected Media updateLicense(final String licenceType, Media media) {
        Lic updateLicense = fetchFromDB(licenceType);
        media.getLics().clear();
        media.getLics().add(updateLicense);
        return media;
    }

    private List<String> addingTags(Media media, String inTags) {
        TagHelper helper = new TagHelper();
        List<String> tags = helper.addingTags(media, inTags);
        return tags;
    }

    /**
     * @TODO Add some regexp later, change String to timedate
     *
     * @param time
     * @return
     */
    private int checkStartEndTime(String time) {
        if (null == time || time.equals("")) {
            time = "0";
        }

        return Integer.parseInt(time);
    }

    private String generateRandomUUID() {
        final String uuIdFilename = UUID.randomUUID().toString();
        return uuIdFilename;
    }

    // "path_to_files"
    private String getAbsolutePathToFile(String uuid) {
        String basePath = (String)envMap.get("path_to_files");
        return PathHelper.getEmptyOrAbsolutePathToFile(uuid, basePath);
    }

//        boolean isSuffix = AdminProperties.getIsSuffix();
    private void writeToFile(FileUploadForm form, String location) {
        Writeable writer = new FileSystemWriter();
        writer.writeBytesTo(form.getFileData(), location);
    }

    private void writeBase64ToFile(byte[] parseBase64Binary, String location) {
        Writeable writer = new FileSystemWriter();
        writer.writeBytesTo(parseBase64Binary, location);
    }

    private <T> void writeToDatabase(T media) {
        bean.save(media);
    }

    private Lic fetchFromDB(String abbrevation) {
        String trimmedAbbrevation = abbrevation.trim();
        Lic license = (Lic) bean.getLicenseByAbbr(trimmedAbbrevation);
        
        return license;
    }

    /**
     * Removes 'tags' with key= 'Unknown'
     *
     * @param metadata
     * @param isFiltered
     * @return
     */
    private JSONObject packageEXIF_IN_JSON(Metadata metadata, boolean isFiltered) {
        ExifExtraction extract = new ExifExtraction();
        return extract.packageEXIF_IN_JSON(metadata, isFiltered);
    }
}
