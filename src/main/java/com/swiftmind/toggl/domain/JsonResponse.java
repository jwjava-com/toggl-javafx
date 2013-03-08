package com.swiftmind.toggl.domain;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.annotate.JsonTypeInfo.As;
import org.codehaus.jackson.annotate.JsonTypeInfo.Id;

/**
 * Wrapping the json-response. The payload is in the {@link #data} property.
 *
 * @author Odilo Oehmichen, <a href="http://www.swiftmind.com">Swiftmind GmbH</a>
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class JsonResponse<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	T[] data;
	
	@JsonTypeInfo(use=Id.CLASS, include=As.PROPERTY, property="class")
	public Class<T> clazz;

	public T[] getData() {
		return data;
	}

	public void setData(T[] data) {
		this.data = data;
	}
	
}
