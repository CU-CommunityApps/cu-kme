package org.kuali.mobility.util.mapper.entity;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

public class MappingElement implements Serializable
{

	private static final long serialVersionUID = -7978581905560378671L;

	@XStreamAsAttribute
	private String mapTo;
	@XStreamAsAttribute
	private String mapFrom;
	@XStreamAsAttribute
	private boolean list;
	@XStreamAsAttribute
	private String type;
	@XStreamAsAttribute
	private boolean isAttribute;

	/**
     * @return the list
     */
    public boolean isList() {
        return list;
    }

    /**
     * @param list the list to set
     */
    public void setList(final boolean list) {
        this.list = list;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type == null ? "java.lang.String" : type;
    }

    /**
     * @param type the type to set
     */
    public void setType(final String type) {
        this.type = type;
    }

	public String getMapTo() {
		return mapTo;
	}

	public void setMapTo(String mapTo) {
		this.mapTo = mapTo;
	}

	public String getMapFrom() {
		return (mapFrom == null || mapFrom.isEmpty() ? getMapTo() : mapFrom);
	}

	public void setMapFrom(String mapFrom) {
		this.mapFrom = mapFrom;
	}

	public boolean isAttribute() {
		return isAttribute;
	}

	public void setAttribute(boolean isAttribute) {
		this.isAttribute = isAttribute;
	}
}
