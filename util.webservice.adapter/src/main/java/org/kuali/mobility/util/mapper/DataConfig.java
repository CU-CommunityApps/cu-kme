package org.kuali.mobility.util.mapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;
import org.kuali.mobility.util.mapper.entity.DataMapping;
import org.kuali.mobility.util.mapper.entity.MappingElement;

import com.thoughtworks.xstream.XStream;

public class DataConfig {
	private static final Logger logger = Logger.getLogger(DataConfig.class);

	public DataMapping loadConfiguation( final String fileName ) throws IOException
	{
        final String xml = readConfiguration(fileName);
        final XStream xstream = new XStream();
        xstream.alias( "dataMapping", DataMapping.class );
        xstream.addImplicitCollection( DataMapping.class, "mappings", "mapping", MappingElement.class );
        xstream.aliasAttribute(MappingElement.class, "mapTo", "mapTo");
        xstream.aliasAttribute(MappingElement.class, "mapFrom", "mapFrom");
        xstream.aliasAttribute(MappingElement.class, "isAttribute", "attribute");
        xstream.aliasAttribute(MappingElement.class, "list", "list");
        xstream.aliasAttribute(MappingElement.class, "type", "type");
        xstream.processAnnotations(DataMapping.class);

        final DataMapping dm = (DataMapping)xstream.fromXML(xml);
        return dm;
	}
	
	protected String readConfiguration(final String fileName)
			throws IOException {
		String configurationData = null;

		if (fileName == null || fileName.isEmpty()) {
			throw new IOException("File name not provided.");
		} else {
			InputStream inputStream = null;
			InputStreamReader reader = null;
			BufferedReader buffer = null;
			final StringBuilder builder = new StringBuilder();
			try {
				inputStream = this.getClass().getClassLoader().getResourceAsStream( fileName );
				reader = new InputStreamReader( inputStream );
				buffer = new BufferedReader(reader);
				String line;
				while ((line = buffer.readLine()) != null) {
					builder.append(line);
				}
				buffer.close();
				reader.close();
				inputStream.close();
			} catch (Exception e) {
				logger.error(e);
			} finally {
				if (buffer != null) {
					try {
						buffer.close();
					} catch (Exception e) {
						logger.error("Could not close the buffered reader.");
						logger.error(e);
					}
				}
				if (reader != null) {
					try {
						reader.close();
					} catch (Exception e) {
						logger.error("Could not close the input stream reader.");
						logger.error(e);
					}
				}
				if (inputStream != null) {
					try {
						inputStream.close();
					} catch (Exception e) {
						logger.error("Could not close the input stream.");
						logger.error(e);
					}
				}
			}
			configurationData = builder.toString();
		}
		return configurationData;
	}
}
