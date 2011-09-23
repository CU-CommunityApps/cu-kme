package org.kuali.mobility.util.mapper;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import org.apache.log4j.Logger;
import org.kuali.mobility.util.mapper.entity.DataMapping;
import org.kuali.mobility.util.mapper.entity.MappingElement;

import com.thoughtworks.xstream.XStream;

public class DataMapperImpl implements DataMapper {

	private static final Logger logger = Logger.getLogger(DataMapperImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public <B> B mapData(B responseObject, URL source, String mappingFile)
			throws ClassNotFoundException {
		XStream xstream = loadMapper( mappingFile );
		if( xstream != null )
		{
			responseObject = (B) xstream.fromXML(source);
		}
		return responseObject;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <B> B mapData(B responseObject, String dataFile, String mappingFile)
			throws ClassNotFoundException {
		XStream xstream = loadMapper( mappingFile );
		if( xstream != null )
		{
			responseObject = (B) xstream.fromXML( this.getClass().getClassLoader().getResourceAsStream( dataFile ) );
		}
		return responseObject;
	}

	private XStream loadMapper( final String mappingFile ) throws ClassNotFoundException
	{
		XStream xstream = new XStream();
		
		DataConfig dc = new DataConfig();
		DataMapping mapping = null;
		try {
			mapping = dc.loadConfiguation(mappingFile);
		} catch (IOException ioe) {
			logger.error(ioe);
		}

		if (mapping != null) 
		{
			if( mapping.getRootElement() != null 
					&& !"".equalsIgnoreCase( mapping.getRootElement() ) )
			{
				xstream.alias( mapping.getRootElement(), (mapping.isList() ? List.class : Object.class ) );
			}
			xstream.alias(mapping.getId(),
					Class.forName(mapping.getClassName()));
			for (MappingElement map : mapping.getMappings()) {
				logger.debug( "Processing element: "+map.getMapTo() );
				if (map.isAttribute()) {
					xstream.aliasAttribute(
							Class.forName(mapping.getClassName()),
							map.getMapTo(), map.getMapFrom());
				} else {
					xstream.aliasField(map.getMapFrom(),
							Class.forName(mapping.getClassName()),
							map.getMapTo());
				}
			}
		}
		else
		{
			xstream = null;
		}
		return xstream;
	}
}
