package org.kuali.mobility.util.mapper.entity;

import java.io.Serializable;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("dataMapping")
public class DataMapping implements Serializable 
{
	private static final long serialVersionUID = 4848897251857351688L;

	private String id;
	private String className;
	
	private List<MappingElement> mappings;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public List<MappingElement> getMappings() {
		return mappings;
	}

	public void setMappings(List<MappingElement> mappings) {
		this.mappings = mappings;
	}
}
