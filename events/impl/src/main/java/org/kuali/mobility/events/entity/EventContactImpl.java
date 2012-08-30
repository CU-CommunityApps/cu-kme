package org.kuali.mobility.events.entity;

public class EventContactImpl implements EventContact {
	// create 3 string

	private String name;
	private String email;
	private String url;

	/* (non-Javadoc)
	 * @see org.kuali.mobility.events.entity.EventContact#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	/* (non-Javadoc)
	 * @see org.kuali.mobility.events.entity.EventContact#setName(java.lang.String)
	 */
	@Override
	public void setName(String name) {
		this.name = name;
	}

	/* (non-Javadoc)
	 * @see org.kuali.mobility.events.entity.EventContact#getEmail()
	 */
	@Override
	public String getEmail() {
		return email;
	}

	/* (non-Javadoc)
	 * @see org.kuali.mobility.events.entity.EventContact#setEmail(java.lang.String)
	 */
	@Override
	public void setEmail(String email) {
		this.email = email;
	}

	/* (non-Javadoc)
	 * @see org.kuali.mobility.events.entity.EventContact#getUrl()
	 */
	@Override
	public String getUrl() {
		return url; 
	}

	/* (non-Javadoc)
	 * @see org.kuali.mobility.events.entity.EventContact#setUrl(java.lang.String)
	 */
	@Override
	public void setUrl(String url) {
		this.url = url;
	}

}
