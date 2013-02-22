package com.swiftmind.toggle.jackson;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.joda.time.DateTime;

/**
 * TODO(OOE) add some comments
 *
 * @author Odilo Oehmichen, <a href="http://www.swiftmind.com">Swiftmind GmbH</a>
 */
public class JodaDateTimeSerializer extends JsonSerializer<DateTime> {

	/** {@inheritDoc} */
	@Override
	public void serialize(DateTime value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
		// using ISO8601
		jgen.writeString(value.toString());
	}

}
