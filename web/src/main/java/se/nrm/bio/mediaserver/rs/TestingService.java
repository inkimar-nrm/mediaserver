package se.nrm.bio.mediaserver.rs;

/**
 * A simple CDI service which is able to say hello to someone
 * 
 * @author Pete Muir
 * 
 */
public class TestingService {

    String createHelloMessage(String name) {
        return "Hello " + name + "!";
    }

}
