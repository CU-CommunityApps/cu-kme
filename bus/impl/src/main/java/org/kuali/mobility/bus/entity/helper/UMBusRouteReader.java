/*
 * Copyright 2011 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.kuali.mobility.bus.entity.helper;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import java.util.List;

/**
 *
 * @author Joe Swanson <joseswan@umich.edu>
 */
@XStreamAlias("livefeed")
public class UMBusRouteReader {

    private List<UMBusRoute> routes;

    @XStreamAlias("routecount")
    private int count;

    /**
     * @return the routes
     */
    public List<UMBusRoute> getRoutes() {
        return routes;
    }

    /**
     * @param routes the routes to set
     */
    public void setRoutes(List<UMBusRoute> routes) {
        this.routes = routes;
    }

    /**
     * @return the count
     */
    public int getCount() {
        return count;
    }

    /**
     * @param count the count to set
     */
    public void setCount(int count) {
        this.count = count;
    }
}
