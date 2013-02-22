package com.swiftmind.toggle.domain;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * 
 * TODO(OOE) add some comments
 *
 * @author Odilo Oehmichen, <a href="http://www.swiftmind.com">Swiftmind GmbH</a>
 */
public class JsonRequest {

	@JsonProperty("time_entry")
	private TimeEntry timeEntry;

	
	
	public JsonRequest(TimeEntry timeEntry) {
		super();
		this.timeEntry = timeEntry;
	}

	public TimeEntry getTimeEntry() {
		return timeEntry;
	}

	public void setTimeEntry(TimeEntry timeEntry) {
		this.timeEntry = timeEntry;
	}
}
