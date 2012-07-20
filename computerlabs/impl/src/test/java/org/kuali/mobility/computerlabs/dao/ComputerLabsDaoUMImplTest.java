package org.kuali.mobility.computerlabs.dao;

import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kuali.mobility.computerlabs.entity.Lab;
import org.kuali.mobility.computerlabs.entity.Location;
import org.kuali.mobility.computerlabs.service.ComputerLabsService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class ComputerLabsDaoUMImplTest {
	
private static final Logger LOG = Logger.getLogger( ComputerLabsDaoUMImplTest.class );
	
	private static ApplicationContext applicationContext;
	
    @BeforeClass
    public static void createApplicationContext() {
    	ComputerLabsDaoUMImplTest.setApplicationContext(new FileSystemXmlApplicationContext(getConfigLocations()));
    }

    private static String[] getConfigLocations() {
        return new String[] { "classpath:/ComputerLabsSpringBeans.xml" };
    }
    
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public static void setApplicationContext(ApplicationContext applicationContext) {
		ComputerLabsDaoUMImplTest.applicationContext = applicationContext;
	}
	
	
	@Test
	public void testloadBuildings(){
		ComputerLabsService service = (ComputerLabsService) getApplicationContext().getBean("computerlabService");		
		//Collection<Location> locs = new ArrayList();
		Collection<Location>  locs = null;
		
		locs = (Collection<Location>) service.findAllLabsByCampus("ALL");
		for (Location curl : locs)
		{
			LOG.debug("Building " + curl.getName());
			for (Lab lab : curl.getLabs())
			{
				LOG.debug("Lab name " + lab.getLab() + ", seats available : " + lab.getAvailability());
			}
			
		}
		assertTrue("Failed to load buildings and labs ", locs!=null );
		Collection<Location>  locs2 = null;
		locs2 = (Collection<Location>) service.findAllLabsByCampus("ALL");
		for (Location curl : locs2)
		{
			LOG.debug("Building " + curl.getName());
			for (Lab lab : curl.getLabs())
			{
				LOG.debug("Lab name " + lab.getLab() + ", seats available : " + lab.getAvailability());
			}
			
		}
		//Collection<Location>  locs2 = (Collection<Location>) service.findAllLabsByCampus("ALL");
		assertTrue("Failed to load buildings and labs ", locs2!=null );	
		
	}

	
	
}
