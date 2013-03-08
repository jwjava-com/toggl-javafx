package com.swiftmind.toggl.domain;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

/**
 * Bean holding toggl-project information.
 *
 * @author Odilo Oehmichen, <a href="http://www.swiftmind.com">Swiftmind GmbH</a>
 */
@JsonSerialize(include=Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
public class Project implements Serializable{
	
	private static final long serialVersionUID = 1L;

	int id;
	
	String name;
	
	@JsonProperty("client_project_name")
	String clientProjectName;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClientProjectName() {
		return clientProjectName;
	}

	public void setClientProjectName(String clientProjectName) {
		this.clientProjectName = clientProjectName;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Project [id=").append(id).append(", name=").append(name).append(", clientProjectName=").append(clientProjectName)
				.append("]");
		return builder.toString();
	}

}
