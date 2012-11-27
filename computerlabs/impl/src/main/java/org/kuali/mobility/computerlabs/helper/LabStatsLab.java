package org.kuali.mobility.computerlabs.helper ;

/**
 * This class is used to inhale JSON, which comes back from doing a 
 * WS call to the Cornell Information Information Technologies site that provides  
 * "Lab Stats".  Flexjson is used to deserialize.
 * 
 * An array of these objects will get created, and having the compareTo() method 
 * comes in handy, as we can easily sort that array before it gets "mapped" to an array 
 * of KME's Lab objects.
 */
public class LabStatsLab implements Comparable<LabStatsLab> {

private String __type ;
private int groupId ;
private String groupName ;
private int offCount ;
private int availableCount ;
private int unavailableCount ;
private int inUseCount ;
private boolean hasPublishedSchedule ;
private int totalCount ;
private String groupDescription ;
private boolean reservable ;
private int percentInUse ;

// Compare based on groupName field
public int compareTo(LabStatsLab lsl) {

int iReturn = -1 ;
if (groupName != null)
	{
	String sGroupNameToCompare = lsl.getGroupName() ; 
	if (sGroupNameToCompare != null)
		{
		iReturn = groupName.compareTo(sGroupNameToCompare) ;
		}
	}

return (iReturn) ;
}

public String get__type() {

return __type;
}

public void set__type(String __type) {

this.__type = __type;
}

public int getGroupId() {

return groupId;
}

public void setGroupId(int groupId) {

this.groupId = groupId;
}

public String getGroupName() {

return groupName;
}

public void setGroupName(String groupName) {

this.groupName = groupName;
}

public int getOffCount() {

return offCount;
}

public void setOffCount(int offCount) {

this.offCount = offCount;
}

public int getAvailableCount() {

return availableCount;
}

public void setAvailableCount(int availableCount) {

this.availableCount = availableCount;
}

public int getUnavailableCount() {

return unavailableCount;
}

public void setUnavailableCount(int unavailableCount) {

this.unavailableCount = unavailableCount;
}

public int getInUseCount() {

return inUseCount;
}

public void setInUseCount(int inUseCount) {

this.inUseCount = inUseCount;
}

public boolean isHasPublishedSchedule() {

return hasPublishedSchedule;
}

public void setHasPublishedSchedule(boolean hasPublishedSchedule) {

this.hasPublishedSchedule = hasPublishedSchedule;
}

public int getTotalCount() {

return totalCount;
}

public void setTotalCount(int totalCount) {

this.totalCount = totalCount;
}

public String getGroupDescription() {

return groupDescription;
}

public void setGroupDescription(String groupDescription) {

this.groupDescription = groupDescription;
}

public boolean isReservable() {

return reservable;
}

public void setReservable(boolean reservable) {

this.reservable = reservable;
}

public int getPercentInUse() {

return percentInUse;
}

public void setPercentInUse(int percentInUse) {

this.percentInUse = percentInUse;
}

}
