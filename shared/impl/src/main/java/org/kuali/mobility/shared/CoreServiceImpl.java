package org.kuali.mobility.shared;

public class CoreServiceImpl implements CoreService {

	private String googleAnalyticsProfileId;
	
	@Override
	public String findGoogleAnalyticsProfileId() {
		return googleAnalyticsProfileId;
	}

	@Override
	public void setGoogleAnalyticsProfileId(String id) {
		googleAnalyticsProfileId = id;
	}

}
