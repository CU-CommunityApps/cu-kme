package org.kuali.mobility.util.mapper;

import java.net.URL;

public interface DataMapper {
	
	public <B extends Object> B mapData( B responseObject, final URL source, final String mappingFile ) throws ClassNotFoundException;

	public <B extends Object> B mapData( B responseObject, final String dataFile, final String mappingFile ) throws ClassNotFoundException;

}
