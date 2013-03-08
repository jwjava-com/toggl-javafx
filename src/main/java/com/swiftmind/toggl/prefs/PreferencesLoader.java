package com.swiftmind.toggl.prefs;

import java.io.StringWriter;
import java.util.List;
import java.util.prefs.Preferences;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.swiftmind.toggl.domain.Project;

/**
 * Loads and stores some program preferences.
 *
 * @author Odilo Oehmichen, <a href="http://www.swiftmind.com">Swiftmind GmbH</a>
 */
public class PreferencesLoader {

	private static final Logger LOGGER = LoggerFactory.getLogger(PreferencesLoader.class);

	private static final String PREF_PROJECTS = "toggle.projects";
	
	private static final String PREF_API_KEY = "toggle.apikey";
	
	public void updateApiKey(String key) {
		Preferences prefs = Preferences.userRoot().node(this.getClass().getName());	
		try {
			prefs.put(PREF_API_KEY, key);
			prefs.flush();
		} catch(Exception e) {
			LOGGER.error("Exception while updating api key preference.", e);
		}
	}
	
	public String getApiKey() {
		Preferences prefs = Preferences.userRoot().node(this.getClass().getName());	
		return prefs.get(PREF_API_KEY, "");
	}
	
	public void updateProjects(List<Project> projects) {
		Preferences prefs = Preferences.userRoot().node(this.getClass().getName());	
		
		ObjectMapper mapper = new ObjectMapper();
		StringWriter writer = new StringWriter();
		try {
			mapper.writeValue(writer, projects);
			prefs.put(PREF_PROJECTS, writer.toString());
			prefs.flush();
		} catch (Exception e) {
			LOGGER.error("Exception while updating projects.", e);
		} 
	}
	
	public List<Project> loadProjectsFromFile() {
		Preferences prefs = Preferences.userRoot().node(this.getClass().getName());	
		String jsonProjects = prefs.get(PREF_PROJECTS, "");
		
		if(StringUtils.hasText(jsonProjects)) {
			ObjectMapper mapper = new ObjectMapper();
			try {
				List<Project> readValue = mapper.readValue(jsonProjects, new TypeReference<List<Project>>() { });
				LOGGER.info("Read value {}", readValue);
				return readValue;
			} catch(Exception e) {
				LOGGER.warn("Exception while loading projects from preferences.", e);
			}
		}
		return null;
	}
}
