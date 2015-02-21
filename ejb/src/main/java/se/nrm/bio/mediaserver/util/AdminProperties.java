package se.nrm.bio.mediaserver.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.log4j.Logger;

/**
 * @author ingimar
 */
public class AdminProperties {

    private final static Logger logger = Logger.getLogger(AdminProperties.class);

//    public static final String PROPERTY_FILE = "mediaserver.properties";

    private AdminProperties() {
    }

//    public static String getImagesFilePath() {
////        Properties prop = getAdminConfigProperties();
////        final String path = prop.getProperty("path_to_files");
////        return path;
//        return "/opt/data/mediaserver/newmedia";
//    }
//
//    public static boolean getIsExif() {
////        Properties prop = getAdminConfigProperties();
////        final String exif = prop.getProperty("is_exif");
////        boolean is_exif = Boolean.parseBoolean(exif);
////        return is_exif;
//        return true;
//    }
//     public static boolean getIsSuffix() {
////        Properties prop = getAdminConfigProperties();
////        final String exif = prop.getProperty("is_suffix");
////        boolean is_exif = Boolean.parseBoolean(exif);
////        return is_exif;
//         return false;
//    }

}
