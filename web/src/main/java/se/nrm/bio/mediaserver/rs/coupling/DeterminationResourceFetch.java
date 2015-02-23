package se.nrm.bio.mediaserver.rs.coupling;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import se.nrm.bio.mediaserver.business.MediaCouplingBean;
import se.nrm.bio.mediaserver.business.MediaserviceBean;
import se.nrm.bio.mediaserver.domain.Determination;
import se.nrm.bio.mediaserver.domain.Media;

/**
 *
 * @author ingimar
 */
@Path("")
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
public class DeterminationResourceFetch {

    @EJB
    private MediaCouplingBean bean;

    /**
     *
     * @param extUUID
     * @return List of Media-metadata
     */
    @GET
    @Path("/determination/metadata/{extuuid}")
    public List<Media> getMediaMetadata(@PathParam("extuuid") String extUUID) {
        List<Media> mediaList = bean.getMetaDataForMedia(extUUID);
        return mediaList;
    }

    @GET
    @Path("/determination/metadata/{extuuid}/{lang}")
    public List<Media> getMediaMetadataByLang(@PathParam("extuuid") String extUUID,
            @PathParam("lang") String locale) {
        List<Media> mediaList = bean.getMetadataByLanguage(extUUID, locale);
        return mediaList;
    }

    @GET
    @Path("/determination/metadata/{extuuid}/{lang}/{tags}")
    public List<Media> getMediaMetadataByLangAndTags(@PathParam("extuuid") String extUUID,
            @PathParam("lang") String lang, @PathParam("tags") String tags) {
        List<Media> medLiaList = bean.getMetadataByLanguageAndTags(extUUID, lang, tags);
        return medLiaList;
    }

    /**
     *
     * @param extUUID -tagValue (TAG_VALUE) stored in DETERMINATION-table.
     * @return
     */
    @GET
    @Path("/determination/present/{extuuid}")
    public String isTagPresent(@PathParam("extuuid") String extUUID) {
        Boolean hasTagvalue = bean.isTagPresentInDetermination(extUUID);
        return hasTagvalue.toString();
    }

    @GET
    @Path("/determination/sorting/{extuuid}/media/{mediauuid}/sortorder/{inSortorder}")
    public List<Determination> changeSortOrder(@PathParam("extuuid") String extuuid,
            @PathParam("mediauuid") String mediauuid,
            @PathParam("inSortorder") int inSortorder) {

        List<Determination> currentList = bean.getDeterminationsByTagValue(extuuid);
        List<Determination> modifiedList = modifyList(currentList, mediauuid, inSortorder);

        for (Determination d : modifiedList) {
            bean.save(d);
        }

        return modifiedList;
    }

    private List<Determination> modifyList(List<Determination> currentList, String mediaUUID, int inSortOrder) {
        List<Determination> modifiedList = new ArrayList<>();

        for (Determination d : currentList) {
            int thisSortOrder = d.getSortOrder();
            if (d.getMedia().getUuid().equals(mediaUUID)) {
                d.setSortOrder(inSortOrder);
            } else if (inSortOrder <= thisSortOrder) {
                d.setSortOrder(thisSortOrder + 1);
            }

            modifiedList.add(d);
        }

        return modifiedList;
    }

}
