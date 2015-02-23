package se.nrm.bio.mediaserver.domain.dummy;

import java.util.UUID;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import se.nrm.bio.mediaserver.domain.Media;

/**
 *
 * @author ingimar
 */
public class NewClass {
    
    public static void main(String[] args) {
//     NewClass.save();
        
        Vehicle v = NewClass.getVehicle("5805c250-4321-4097-ac9d-9629635ad4ef"); 
        System.out.println("START: Vechicle *******************");
        System.out.println("    UUID "+v.getUuid());
        System.out.println("    Wheels "+v.getWheels());
        System.out.println("    Speed "+v.getSpeed());
        System.out.println("END: Vechicle *******************");
        
        Media m = NewClass.getImage("00036f71-465c-4443-88e3-370f71fe1d84");
        System.out.println("  m "+m.getHash());
        
    }
    static void save(){
          /* Create EntityManagerFactory */
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TEST_PU");

        /* Create EntityManager */
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        
        UUID randomUUID = UUID.randomUUID();
        Vehicle car = new Car(randomUUID.toString(), 3, 150);
        try {
        em.persist(car);
        } catch ( Exception ex){
            System.out.println("ex "+ex);
        }
        transaction.commit();
    }
    static Vehicle getVehicle(String uuid){
           /* Create EntityManagerFactory */
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TEST_PU");

        /* Create EntityManager */
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        
        Query namedQuery = em.createNamedQuery("Vehicle.findByUuid");
        namedQuery.setParameter("uuid", uuid);
        Vehicle v = (Vehicle)namedQuery.getSingleResult();
        
        return v;
    }
    static Media getImage(String uuid){
           /* Create EntityManagerFactory */
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("TEST_PU");

        /* Create EntityManager */
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        
        Query namedQuery = em.createNamedQuery("Media.FindByUuid");
        namedQuery.setParameter("uuid", uuid);
        Media v = (Media)namedQuery.getSingleResult();
        
        return v;
    }
}
