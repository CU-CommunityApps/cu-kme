package org.kuali.mobility.util.mapper;

import java.net.URL;

public interface DataMapper {
	
	public <B extends Object> B mapData( B responseObject, final URL source, final String mappingFile ) throws ClassNotFoundException;

}
