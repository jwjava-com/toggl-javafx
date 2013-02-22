package com.swiftmind.toggle.service;

import java.util.List;

import org.joda.time.Duration;
import org.joda.time.LocalDate;

import com.swiftmind.toggle.domain.Project;

/**
 * TODO(OOE) add some comments
 *
 * @author Odilo Oehmichen, <a href="http://www.swiftmind.com">Swiftmind GmbH</a>
 */
public interface ToggleService {
	
	Duration sumDurationForDay(LocalDate date);
	
	List<Project> getAllProjects();
	
	String createEntry(String description, LocalDate date, Duration duration, Integer projectId);
	
}
