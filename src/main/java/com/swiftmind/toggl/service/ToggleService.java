package com.swiftmind.toggl.service;

import java.util.List;

import org.joda.time.Duration;
import org.joda.time.LocalDate;

import com.swiftmind.toggl.domain.Project;

/**
 * Service-Definition for the communication with Toggl REST-Api.
 *
 * @author Odilo Oehmichen, <a href="http://www.swiftmind.com">Swiftmind GmbH</a>
 */
public interface ToggleService {
	
	/**
	 * Returns the amount of time that has been booked for the given day.
	 * 
	 * @param date
	 * @return Duration
	 */
	Duration sumDurationForDay(LocalDate date);
	
	/**
	 * Returns a list of all projects in the user's toggl.
	 * @return
	 */
	List<Project> getAllProjects();
	
	/**
	 * Creates a new toggl time-entry with the given properties.
	 * @param description - description to use for the entry.
	 * @param date - date of the toggl entry.
	 * @param duration - duration.
	 * @param projectId - the id of the project for which the entry should be created.
	 * @return
	 */
	String createEntry(String description, LocalDate date, Duration duration, Integer projectId);
	
}
