package se.nrm.bio.mediaserver.business;

import javax.ejb.Stateless;

@Stateless
public class GreeterEJB {
    
    public String sayHello(String name) {
        return "HelloHEJ  - " + name;
    }
}
