package com.swiftmind.toggl.domain;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Wrapping a JsonRequest for a new "time_entry".
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
