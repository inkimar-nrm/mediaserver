package se.nrm.mediaserver.resteasy.util;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.log4j.Logger;
import org.apache.tika.io.IOUtils;

/**
 * @TODO, look at nio
 * @author ingimar
 */
public class FileSystemWriter implements Writeable {

    private final static Logger logger = Logger.getLogger(FileSystemWriter.class);

    @Override
    public void writeBytesTo(byte[] data, String location) {
        try {
            File file = new File(location);

            if (!file.exists()) {
                file.createNewFile();
            }

            try (FileOutputStream fos = new FileOutputStream(file)) {
                fos.write(data);
                fos.flush();
            }
        } catch (IOException ex) {
            logger.info(ex);
        }
    }

    @Override
    public void writeStreamToFS(InputStream uploadedInputStream, String location) {
        byte[] bytes = null;
        try {
            bytes = IOUtils.toByteArray(uploadedInputStream);
        } catch (Exception ex) {
            logger.info(ex);
        }
        this.writeBytesTo(bytes, location);
    }
}
