package org.kuali.mobility.tours.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity(name="POIPhoneNumber")
@Table(name="TOUR_POI_PHN_NUM_T")
public class POIPhoneNumber {

	@Id
    //@SequenceGenerator(name="tour_poi_phn_sequence", sequenceName="SEQ_TOUR_POI_PHN_NUM_T", initialValue=1000, allocationSize=1)
    //@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="tour_poi_phn_sequence")
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Column(name="PHN_NUM_ID")
	private Long id;
	
	@Basic
    @Column(name="POI_ID", insertable=false, updatable=false)
	private Long poiId;

	@ManyToOne
	@JoinColumn(name="POI_ID", nullable=false)
	private POI poi;
    
    @Column(name="NM")
	private String name;
    
    @Column(name="NMBR")
	private String number;
    
    @Transient
    private String formattedNumber;

	public POIPhoneNumber copy(boolean includeIds) {
		POIPhoneNumber copy = new POIPhoneNumber();
		if (includeIds) {
			copy.setId(new Long(id));
		}
		copy.setPoiId(new Long(poiId));
		copy.setName(new String(name));
		copy.setNumber(new String(number));
		return copy;
	}

	public POI getPoi() {
		return poi;
	}

	public void setPoi(POI poi) {
		this.poi = poi;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPoiId() {
		return poiId;
	}

	public void setPoiId(Long poiId) {
		this.poiId = poiId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getFormattedNumber() {
		return formattedNumber;
	}

	public void setFormattedNumber(String formattedNumber) {
		this.formattedNumber = formattedNumber;
	}
}
