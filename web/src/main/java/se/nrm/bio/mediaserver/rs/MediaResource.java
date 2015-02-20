package se.nrm.bio.mediaserver.rs;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import se.nrm.bio.mediaserver.business.MediaserviceBean;
import se.nrm.bio.mediaserver.domain.Media;

import org.apache.log4j.Logger;

/**
 * 
 * @author ingimar
 */
@Path("/")
public class MediaResource {
    
    private final static Logger logger = Logger.getLogger(MediaResource.class);

    @EJB
    private MediaserviceBean service;

    /**
     * http://localhost:8080/wildfly-ejb-in-ear/media/meta/00036f71-465c-4443-88e3-370f71fe1d84
     * http://localhost:8080/wildfly-ejb-in-ear/rest/meta/00036f71-465c-4443-88e3-370f71fe1d84
     * @param mediaUUID
     * @return 
     */
    @GET
    @Path("/meta/{uuid}")
    @Produces({MediaType.APPLICATION_JSON})
    public Media getMetadata(@PathParam("uuid") String mediaUUID) {
        Media media = (Media) service.get(mediaUUID);
        return media;
    }
}