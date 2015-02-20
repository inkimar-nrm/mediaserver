package se.nrm.bio.mediaserver.rs;



import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import java.io.Serializable;
import se.nrm.bio.mediaserver.business.GreeterEJB;

/**
 * A simple managed bean that is used to invoke the GreeterEJB and store the
 * response. The response is obtained by invoking getMessage().
 * 
 * @author paul.robinson@redhat.com, 2011-12-21
 */
@Named("greeter")
@SessionScoped
public class Greeter implements Serializable {

    /**
     * Injected GreeterEJB client
     */
    @EJB
    private GreeterEJB greeterEJB;

    /**
     * Stores the response from the call to greeterEJB.sayHello(...)
     */
    private String message;

    /**
     * Invoke greeterEJB.sayHello(...) and store the message
     * 
     * @param name
     *            The name of the person to be greeted
     */
    public void setName(String name) {
        message = greeterEJB.sayHello(name);
    }

    /**
     * Get the greeting message, customized with the name of the person to be
     * greeted.
     * 
     * @return message. The greeting message.
     */
    public String getMessage() {
        return message;
    }

}
