package se.nrm.mediaserver.resteasy.util;


import se.nrm.bio.mediaserver.util.AdminProperties;
import java.util.Properties;

/**
 *
 * @author ingimar
 */
public class MediaURL {

    public String getPathToStream() {
//        String path = this.getPathByKey("relative_stream_url");
//        return path;
        return "media/stream/";
    }

    private String getPathByKey(String key) {
        String path = "";
//        Properties prop = AdminProperties.getAdminConfigProperties();
//        String base_url = prop.getProperty("base_url");
//        String base_url = "/wildfly-ejb-in-ear/";
//        String relative_url = prop.getProperty(key);
//        path = path.concat(base_url).concat(relative_url);
//
//        return path;
        return "";
    }
}
