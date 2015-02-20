package se.nrm.bio.mediaserver.business;

import java.io.Serializable;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import se.nrm.bio.mediaserver.domain.Lic;
import se.nrm.bio.mediaserver.domain.Media;
import org.apache.log4j.Logger;

/**
 *
 * @author ingimar
 */
@Stateless
public class MediaserviceBean<T> implements Serializable {

    private final static Logger logger = Logger.getLogger(MediaserviceBean.class);

    private static final long serialVersionUID = 1L;

    @PersistenceContext(unitName = "MysqlPU")
    private EntityManager em;

    public T save(T entity) {
        T saved = em.merge(entity);
        return saved;
    }

    public T get(String uuid) {
        Query namedQuery = em.createNamedQuery(Media.FIND_BY_UUID);
        namedQuery.setParameter("uuid", uuid);

        T media = null;
        try {
            media = (T) namedQuery.getSingleResult();
        } catch (Exception ex) {

        }
        return media;
    }

    public T delete(T t) {
        T media = null;

        return media;
    }

    public T getLicenseByAbbr(String abbrevation) {
        Query namedQuery = em.createNamedQuery(Lic.FIND_BY_ABBREV);
        namedQuery.setParameter("abbrev", abbrevation);
        T licence = null;
        try {
            licence = (T) namedQuery.getSingleResult();
        } catch (Exception ex) {
            logger.info("no license linked to '"+abbrevation+"' : \n"+ex);
            return null;
        }
        return licence;
    }

    public T getVechicle(String uuid) {
        Query namedQuery = em.createNamedQuery("Vehicle.findByUuid");
        namedQuery.setParameter("uuid", uuid);

        T media = null;
        try {
            media = (T) namedQuery.getSingleResult();
        } catch (Exception ex) {

        }
        return media;
    }

    public T getTitle(String title) {
        Query namedQuery = em.createNamedQuery("Dummy.FindByTitle");
        namedQuery.setParameter("title", title);

        T y = null;
        try {
            y = (T) namedQuery.getSingleResult();
        } catch (Exception ex) {

        }
        return y;
    }
}