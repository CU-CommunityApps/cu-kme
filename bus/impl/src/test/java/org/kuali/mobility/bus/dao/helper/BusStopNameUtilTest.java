package org.kuali.mobility.bus.dao.helper;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import org.kuali.mobility.bus.dao.helper.BusStopNameUtil;


public class BusStopNameUtilTest
{
   
	@Test
	public void testloadStopNames() {
		BusStopNameUtil.loadStopNames();
		List<UMBusStopName> busStopNames = BusStopNameUtil.getBusStopNames();
		assertTrue( "Failed to find nearby bus stops.", busStopNames != null );
	}
	
	@Test
	public void testupdateBusStopName() {
		//String me = BusStopNameUtil.updateBusStopName("Commuter Southbound", "Bonisteel and Beal (Cooley) W","Bonisteel","Beal (Cooley) W");
		//String me = BusStopNameUtil.updateBusStopName("Commuter Southbound", "Bonisteel and Beal (Cooley) W");
		String me = BusStopNameUtil.updateBusStopName("Commuter Southbound","Biomedical Science Research");
		assertTrue( "Failed to find nearby bus stops.", me != null );
		String me1 = BusStopNameUtil.updateBusStopName("Commuter Southbound","Zina Pitcher Place (at BSR)");
		assertTrue( "Failed to find nearby bus stops.", me1 != null );

	}

}
