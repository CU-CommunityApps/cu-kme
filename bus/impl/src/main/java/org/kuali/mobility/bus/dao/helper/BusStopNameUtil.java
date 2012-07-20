package org.kuali.mobility.bus.dao.helper;

import java.io.InputStream;
import java.util.List;
import org.apache.log4j.Logger;

import com.thoughtworks.xstream.XStream;

public class BusStopNameUtil {
	
	public static Logger LOG = Logger.getLogger(BusStopNameUtil.class);
	
	private static List<UMBusStopName> busStopNames;
	public BusStopNameUtil()
	{
		if (busStopNames == null)
		{
			loadStopNames();
		}
		
	}
	@SuppressWarnings("unchecked")
	public static void loadStopNames() {
		 	XStream xstream = new XStream();
			xstream.processAnnotations( UMBusStopNames.class);
			xstream.processAnnotations( UMBusStopName.class);	
			InputStream stream = BusStopNameUtil.class.getClassLoader()
		            .getResourceAsStream("busStopNames.xml");
			UMBusStopNames stopnames = null;
			try {
				stopnames = (UMBusStopNames) xstream.fromXML(stream);	 			     
			     setBusStopNames( stopnames.getStopnames() );
				//
				LOG.debug( "Loaded "+getBusStopNames().size()+" bus stopnames " );
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		   //   
	}
	
	
	public static String updateBusStopName(String routename, String name)
	{
		String stopName = new String();
		 for (UMBusStopName sn : getBusStopNames())
	     {
			 if  ((sn.getName().equalsIgnoreCase(routename)) &&  (sn.getName1().equalsIgnoreCase(name))  )
			 {
				  //LOG.debug("Match found for  routename: " +  routename + "and busstopname: " + name );
				 //set new display name 
				 stopName = sn.getName2();
				  break;
			 }
			 else
			 {
				// LOG.error("No matching routename : "+ routename + "and busstopname found:" + name );
				 //set to original name
				 stopName = name;
			 }
			
	     }
		return stopName;
		
	}
	
	
	public static List<UMBusStopName> getBusStopNames() {
		if (busStopNames == null)
		{
			loadStopNames();
		}
		return busStopNames;
	}


	public static void setBusStopNames(List<UMBusStopName> busStopNames) {
	     BusStopNameUtil.busStopNames = busStopNames;
	}
	

}
