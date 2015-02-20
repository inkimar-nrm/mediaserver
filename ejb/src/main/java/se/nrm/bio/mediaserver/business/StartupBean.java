/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.nrm.bio.mediaserver.business;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import org.apache.log4j.Logger;
import se.nrm.bio.mediaserver.domain.AdminConfig;
import se.nrm.bio.mediaserver.util.AdminProperties;

/**
 * http://stackoverflow.com/questions/1149737/on-using-enum-based-singleton-to-cache-large-objects-java 
 * @author ingimar
 */
@Singleton
@Startup
public class StartupBean {

    private final static Logger logger = Logger.getLogger(StartupBean.class);
    
    @EJB
    AdminBean bean;

    private static final String ENVIRONMENT = "development";
    
    private ConcurrentHashMap map = null;

    /**
     * // skriver till %WILDFLY_HOME%/bin
     *
     * @throws IOException
     */
    @PostConstruct
    public void a() throws IOException {
        this.map = new ConcurrentHashMap();
        List<AdminConfig> config = bean.getAdminConfig(ENVIRONMENT);

        Properties prop = new Properties();

        try (OutputStream output = new FileOutputStream(AdminProperties.PROPERTY_FILE)) {

            for (AdminConfig conf : config) {
                map.put(conf.getAdminKey(), conf.getAdminValue());
                prop.setProperty(conf.getAdminKey(), conf.getAdminValue());
            }
            // save properties to project root folder
            prop.store(output, null);
            

        }
    }

    // funkar !
    public ConcurrentHashMap getEnvironment()  {
        return map;
    }

    @PreDestroy
    private void shutdown() {
    }
/**
 * http://www.adam-bien.com/roller/abien/entry/singleton_the_perfect_cache_facade
 *  
 * istället för property-fil
 * 
 * @Singleton
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class Hits {

    private ConcurrentHashMap hits = null;
	
	@PostConstruct
    public void initialize() {
        this.hits = new ConcurrentHashMap();
	}
//cache accessors omitted
}
* 
* 
 * 
 */
}


