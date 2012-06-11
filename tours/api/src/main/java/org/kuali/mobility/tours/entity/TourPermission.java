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

@Entity(name="TourPermission")
@Table(name="TOUR_PRMSSN_T")
public class TourPermission {

	@Id
    //@SequenceGenerator(name="tour_permission_sequence", sequenceName="SEQ_TOUR_PRMSSN_T", initialValue=1000, allocationSize=1)
    //@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="tour_permission_sequence")
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Column(name="PRMSSN_ID")
	private Long permissionId;
	
	@Basic
    @Column(name="TOUR_ID", insertable=false, updatable=false)
	private Long tourId;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="TOUR_ID", nullable=true)
	private Tour tour;
    
    @Column(name="PRMSSN_TYPE")
	private String type;
    
    @Column(name="GRP_NM")
	private String groupName;

	public Long getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(Long permissionId) {
		this.permissionId = permissionId;
	}

	public Long getTourId() {
		return tourId;
	}

	public void setTourId(Long tourId) {
		this.tourId = tourId;
	}

	public Tour getTour() {
		return tour;
	}

	public void setTour(Tour tour) {
		this.tour = tour;
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

	public TourPermission copy(boolean includeIds) {
		TourPermission copy = new TourPermission();
		if (includeIds) {
			copy.setPermissionId(new Long(permissionId));
		}
		copy.setTourId(new Long(tourId));
		copy.setType(new String(type));
		copy.setGroupName(new String(groupName));
		return copy;
	}
}
