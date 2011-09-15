package org.kuali.mobility.util.mapper;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import org.apache.log4j.Logger;
import org.kuali.mobility.util.mapper.entity.DataMapping;
import org.kuali.mobility.util.mapper.entity.MappingElement;

import com.thoughtworks.xstream.XStream;

public class DataMapperImpl implements DataMapper {

	private static final Logger logger = Logger.getLogger(DataMapperImpl.class);

	@Override
	public <B> B mapData(B responseObject, URL source, String mappingFile)
			throws ClassNotFoundException {

		DataConfig dc = new DataConfig();
		DataMapping mapping = null;
		try {
			mapping = dc.loadConfiguation(mappingFile);
		} catch (IOException ioe) {
			logger.error(ioe);
		}

		if (mapping != null) {
			XStream xstream = new XStream();
			xstream.alias(mapping.getId(),
					Class.forName(mapping.getClassName()));
			xstream.alias(mapping.getId() + "s", List.class);
			for (MappingElement map : mapping.getMappings()) {
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
			responseObject = (B) xstream.fromXML(source);
		}
		return responseObject;
	}

}
