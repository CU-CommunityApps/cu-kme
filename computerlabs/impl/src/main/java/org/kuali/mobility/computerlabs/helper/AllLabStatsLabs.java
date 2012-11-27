package org.kuali.mobility.computerlabs.helper ;

import java.util.List;

/**
 * This make it easy in inhale the JSON, in its entirety, that is returned 
 * from the Lab Stats WS call.  All the important info is the List of 
 * LabStatsLab objects. 
 */
public class AllLabStatsLabs {

private List<LabStatsLab> d ;

public List<LabStatsLab> getD() {

return d;
}

public void setD(List<LabStatsLab> d) {

this.d = d;
}

}
