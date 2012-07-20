/**
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

package org.kuali.mobility.computerlabs.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.kuali.mobility.computerlabs.entity.Lab;
import org.kuali.mobility.computerlabs.entity.Location;

public interface ComputerLabsDao{
	public Collection<Location> findAllLabsByCampus(String campus);
    public Map<String, List<String>> getLabUrls();
	public void setLabUrls(Map<String, List<String>> labUrls);
	public List<Lab> getLabs();
	public void setLabs(List<Lab> labs);
}

