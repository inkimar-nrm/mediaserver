package se.nrm.bio.mediaserver.rs;

import java.util.UUID;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import se.nrm.bio.mediaserver.business.GreeterEJB;
import se.nrm.bio.mediaserver.business.MediaserviceBean;
import se.nrm.bio.mediaserver.domain.dummy.Car;
import se.nrm.bio.mediaserver.domain.dummy.Dummy;
import se.nrm.bio.mediaserver.domain.dummy.Vehicle;

/**
 * curl http://localhost:8080/wildfly-ejb-in-ear/media/json
 *
 * @author ingimar
 */
@Path("/")
public class TestResource {

    @Inject
    TestingService helloService;

    @EJB
    private GreeterEJB greet;

    @EJB
    private MediaserviceBean service;

    @GET
    @Path("json")
    @Produces({"application/json"})
    public JsonObject getHelloWorldJSON() {
        String sayHello = greet.sayHello("Ingimar");
        return Json.createObjectBuilder()
                .add("result", sayHello)
                .build();
    }

    @GET
    @Path("xml")
    @Produces({"application/xml"})
    public String getHelloWorldXML() {
        return "<xml><result>" + helloService.createHelloMessage("World") + "</result></xml>";
    }

    @GET
    @Path("saving-dummy")
    @Produces({"application/xml"})
    public String testSave() {

        Dummy d = new Dummy("1989", 250, "about the", "12345", "190", "4");

        Dummy save = (Dummy) service.save(d);
        return "<xml><result>" + save + "</result></xml>";
    }

    /**
     * curl http://localhost:8080/wildfly-ejb-in-ear/media/metadata/title/2015
     *
     * @param title
     * @return
     */
    @GET
    @Path("/metadata/title/{title}")
    @Produces({"application/json"})
    public Dummy getDummy(@PathParam("title") String title) {
        Dummy d = (Dummy) service.getTitle(title);
        return d;
    }

    @GET
    @Path("saving-vehicle")
    @Produces({"application/xml"})
    public String testVechicle() {

        UUID randomUUID = UUID.randomUUID();

        Car car = new Car(randomUUID.toString(), 3, 150);
        Vehicle save = (Vehicle) service.save(car);
        return "<xml><result>" + save + "</result></xml>";
    }

    @GET
    @Path("/vehicle/{uuid}")
    @Produces({"application/json"})
    public Vehicle getVechicle(@PathParam("uuid") String uuid) {
        Vehicle media = (Vehicle) service.getVechicle(uuid);
        return media;
    }
}
