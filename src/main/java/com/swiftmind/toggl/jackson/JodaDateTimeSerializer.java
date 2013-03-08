package com.swiftmind.toggl.jackson;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.joda.time.DateTime;

/**
 * Implementation of a {@link JsonSerializer} to deal with Joda's {@link DateTime} objects.
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
