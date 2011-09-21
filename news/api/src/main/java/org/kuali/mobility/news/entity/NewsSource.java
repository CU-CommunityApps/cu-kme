package org.kuali.mobility.news.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name="NEWS_SRC_T")
public class NewsSource {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Column(name="ID")
	private Long id;
	
	@Column(name="NAME")
	private String name;
	
	@Column(name="URL")
	private String url;
	
	@Column(name="ACTIVE")
	private boolean active;
	
	@Column(name="ORDR")
	private int order;
	
	@Version
    @Column(name="VER_NBR")
    private Long versionNumber;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public Long getVersionNumber() {
		return versionNumber;
	}
	
	public void setVersionNumber(Long versionNumber) {
		this.versionNumber = versionNumber;
	}

	/**
	 * @return whether the feed is active or not
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * @param active set this feed active or inactive
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the display order
	 */
	public int getOrder() {
		return order;
	}

	/**
	 * @param order the display order of this source
	 */
	public void setOrder(int order) {
		this.order = order;
	}
}
