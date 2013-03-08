package com.swiftmind.toggl.domain;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.joda.time.DateTime;
import org.joda.time.Duration;

import com.swiftmind.toggl.jackson.JodaDateTimeSerializer;

/**
 * Bean holding the information of a toggl time-entry.
 *
 * @author Odilo Oehmichen, <a href="http://www.swiftmind.com">Swiftmind GmbH</a>
 */
@JsonSerialize(include=Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
public class TimeEntry implements Serializable {

	private static final long serialVersionUID = 1L;

	Integer id;
	
	String description;
	
	@JsonSerialize(using = JodaDateTimeSerializer.class)
	@JsonProperty("start")
	DateTime startDateTime;
	
	@JsonSerialize(using = JodaDateTimeSerializer.class)
	@JsonProperty("stop")
	DateTime endDateTime;
	
	@JsonProperty("duration")
	long durationInSeconds;
	
	 @JsonProperty("user_id")
	Integer userId;
	 
	@JsonProperty("created_with")
	String createdWith = "Script";
	
	@JsonProperty("project")
	Project project;
	
	Boolean billable = Boolean.TRUE;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getDurationInSeconds() {
		return durationInSeconds;
	}
	
	public void setJodaDuration(Duration duration) {
		this.durationInSeconds = duration.getStandardSeconds();
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer user_id) {
		this.userId = user_id;
	}


	public void setDurationInSeconds(long durationInSeconds) {
		this.durationInSeconds = durationInSeconds;
	}

	@JsonIgnore
	public int getProjectId() {
		return project != null ? project.getId() : null;
	}

	public void setProjectId(int pId) {
		if(this.project == null) {
			this.project = new Project();
		}
		this.project.setId(pId);
	}

	public DateTime getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(DateTime startDateTime) {
		this.startDateTime = startDateTime;
	}

	public DateTime getEndDateTime() {
		return endDateTime;
	}

	public void setEndDateTime(DateTime endDateTime) {
		this.endDateTime = endDateTime;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TimeEntry [id=").append(id).append(", description=").append(description).append(", startDateTime=")
				.append(startDateTime).append(", endDateTime=").append(endDateTime).append(", durationInSeconds=")
				.append(durationInSeconds).append(", userId=").append(userId).append(", project=").append(project).append("]");
		return builder.toString();
	}

	public Boolean getBillable() {
		return billable;
	}

	public void setBillable(Boolean billable) {
		this.billable = billable;
	}
	
}
