package org.kuali.mobility.bus.dao.helper;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("stopnames")
public class UMBusStopNames {

	@XStreamImplicit
	private List<UMBusStopName> stopnames;

	/**
	 * @return the stopnames
	 */
	public List<UMBusStopName> getStopnames() {
		return stopnames;
	}

	/**
	 * @param stopnames the stopnames to set
	 */
	public void setStopnames(List<UMBusStopName> stopnames) {
		this.stopnames = stopnames;
	}

	
	
}
