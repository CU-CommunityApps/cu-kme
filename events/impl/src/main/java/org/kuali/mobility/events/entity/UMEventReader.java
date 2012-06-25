package org.kuali.mobility.events.entity;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("events")
public class UMEventReader {
	@XStreamImplicit
	private List<UMEvent> events;

	public List<UMEvent> getEvents() {
		return events;
	}

	public void setEvents(List<UMEvent> events) {
		this.events = events;
	}

}
