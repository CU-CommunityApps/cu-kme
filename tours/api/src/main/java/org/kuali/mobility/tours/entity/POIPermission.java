package org.kuali.mobility.tours.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity(name="POIPermission")
@Table(name="TOUR_POI_PRMSSN_T")
public class POIPermission {

	@Id
    //@SequenceGenerator(name="tour_poi_permission_sequence", sequenceName="SEQ_TOUR_POI_PRMSSN_T", initialValue=1000, allocationSize=1)
    //@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="tour_poi_permission_sequence")
	@GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name="PRMSSN_ID")
	private Long permissionId;
	
	@Basic
    @Column(name="POI_ID", insertable=false, updatable=false)
	private Long poiId;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="POI_ID", nullable=true)
	private POI poi;
    
    @Column(name="PRMSSN_TYPE")
	private String type;
    
    @Column(name="GRP_NM")
	private String groupName;

	public POIPermission copy(boolean includeIds) {
		POIPermission copy = new POIPermission();
		if (includeIds) {
			copy.setPermissionId(new Long(permissionId));
		}
		copy.setPoiId(new Long(poiId));
		copy.setType(new String(type));
		copy.setGroupName(new String(groupName));
		return copy;
	}

	public Long getPoiId() {
		return poiId;
	}

	public void setPoiId(Long poiId) {
		this.poiId = poiId;
	}

	public POI getPoi() {
		return poi;
	}

	public void setPoi(POI poi) {
		this.poi = poi;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	public Long getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(Long permissionId) {
		this.permissionId = permissionId;
	}
}
