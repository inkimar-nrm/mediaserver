/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.nrm.bio.mediaserver.rs.info;

import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import se.nrm.bio.mediaserver.business.AdminBean;
import se.nrm.bio.mediaserver.domain.AdminConfig;
import se.nrm.bio.mediaserver.domain.Lic;

/**
 *
 * @author ingimar
 */
@Path("admin")
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
public class Administration {

    @EJB
    AdminBean bean;
    

    @GET
    @Path("/licenses")
    public List<Lic> getLicenses() {
        List<Lic> mediaList = bean.getLicenses();
        return mediaList;
    }

    @GET
    @Path("/config/{environment}")
    public List<AdminConfig> getAdminConfig(@PathParam("environment") String environment) {
        List<AdminConfig> list = bean.getAdminConfig(environment);
        return list;
    }
}
