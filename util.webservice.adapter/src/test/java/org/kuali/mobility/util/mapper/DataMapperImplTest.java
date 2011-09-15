package org.kuali.mobility.util.mapper;

import static org.junit.Assert.fail;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.kuali.mobility.util.mapper.domain.Seat;

public class DataMapperImplTest {

	public static final String sourceUrl = "http://ulib.iupui.edu/utility/seats.php?show=locations&type=data";

	public static final String mappingFile = "/Users/joseswan/Documents/kuali/workspace/kme.xstream.mapper/src/test/resources/seatMapping.xml";
	
	@Test
	public void testMapData() {
		
		List<Seat> seats = new ArrayList<Seat>();
		try
		{
			URL url = new URL( sourceUrl );
			DataMapperImpl mapper = new DataMapperImpl();
			seats = mapper.mapData( seats, url, mappingFile);
			
			if( seats == null || seats.isEmpty() )
			{
				System.out.println( "No seats parsed." );
			}
			else
			{
				for( Seat s : seats )
				{
					System.out.println( s.getLab() );
					System.out.println( "\t"+ s.getFloor() );
					System.out.println( "\t"+ s.getBuildingCode() );
					System.out.println( "\t"+ s.getAvailability() );
					System.out.println( "\t"+ s.getWindowsAvailability() );
					System.out.println( "\t"+ s.getMacAvailability() );
				}
			}
		}
		catch( MalformedURLException mue )
		{
			
		}
		catch( ClassNotFoundException cnfe )
		{
			
		}
		fail("Not yet implemented");
	}

}
