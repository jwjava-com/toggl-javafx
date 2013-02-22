package com.swiftmind.toggle.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeSet;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import org.joda.time.Duration;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.swiftmind.toggle.domain.Project;
import com.swiftmind.toggle.prefs.PreferencesLoader;
import com.swiftmind.toggle.service.RestTemplateToggleService;
import com.swiftmind.toggle.service.ToggleService;

/**
 * Controller handling the UI events and initialization.
 * 
 * @author Odilo Oehmichen, <a href="http://www.swiftmind.com">Swiftmind GmbH</a>
 */
public class FxmlController implements Initializable {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FxmlController.class);


	private static final List<String> DAYS = Arrays.asList("Mo", "Di", "Mi", "Do", "Fr", "Sa", "So" );
	
	private String apiToken = "";
	
	private ToggleService service;
	
	private PreferencesLoader prefLoader = new PreferencesLoader();
	
	@FXML TextField apiTokenTextfield;
	
	@FXML
	private ComboBox<Project> projectsCombobox;
	
	@FXML
	private CheckBox moCheckbox, diCheckbox, miCheckbox, doCheckbox, frCheckbox;
	
	@FXML
	private TextField descriptionTextfield;
	
	@FXML
	private TextArea messageTextArea;

	
	@Override
	public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
		apiToken = prefLoader.getApiKey();
		init();
	}
	
	private void init() {
		appendMessage("Intializing...");
		if(StringUtils.hasText(apiToken)) {
			apiTokenTextfield.setText(apiToken);
			service = new RestTemplateToggleService(apiToken);
			readProjectsFromToggle();
			appendMessage("...finished");
		} else {
			appendMessage("...please enter your Toggle API_TOKEN");
		}
	}
	
	@FXML
	protected void handleRefreshAction(ActionEvent event) {
		this.apiToken = apiTokenTextfield.getText();
		prefLoader.updateApiKey(apiToken);
		init();
	}
	
	@FXML
	protected void handleSubmitButtonAction(ActionEvent event) {
		String description = descriptionTextfield.getText();
		Duration duration = getDefaultDuration();
		Project project = projectsCombobox.getSelectionModel().getSelectedItem();
		
		if(project == null) {
			appendMessage("Please select a project!");
			return; 
		}
		Integer projectId = project.getId();
		
		for (CheckBox c : getCheckedDays()) {
				LocalDate date = getDateForDayInCurrentWeek(c.getText());
				Duration alreadyBooked = service.sumDurationForDay(date);
				if (alreadyBooked.isShorterThan(duration)) {
					appendMessage("Created entry for Project ", project.getClientProjectName(), " on ", date);
					service.createEntry(description, date, duration, projectId);
				} else {
					appendMessage("Too many hours booked for Day ", date);
				}
		}
	}
	
	void appendMessage(Object... messages) {
		StringBuilder sb = new StringBuilder("UI-Message: ");
		for(Object message : messages) {
			messageTextArea.appendText(message.toString());
			sb.append(message.toString());
		}
		LOGGER.info(sb.toString());
		messageTextArea.appendText("\n");
	}

	LocalDate getDateForDayInCurrentWeek(String day) {
		LocalDate today = new LocalDate();
		LocalDate monday = today.minusDays(today.getDayOfWeek() - 1);
	
		int index = DAYS.indexOf(day);
		
		if(index < 0 ) {
			throw new IllegalArgumentException("Argument '" + day + "' is not a valid value.");
		}
		return monday.plusDays(index);
	}
	
	
	List<CheckBox> getCheckedDays() {
		List<CheckBox> checkedDays = new ArrayList<CheckBox>();
		
		for(CheckBox cb : new CheckBox[]{moCheckbox, diCheckbox, miCheckbox, doCheckbox, frCheckbox}) {
			if(cb.isSelected()) {
				checkedDays.add(cb);
			}
		}
		return checkedDays;
	}
	
	private Duration getDefaultDuration() {
		return Duration.standardHours(8).plus(Duration.standardMinutes(40));
	}

	private void loadProjectsFromFile() {
		setProjects(prefLoader.loadProjectsFromFile());
	}

	private void readProjectsFromToggle() {
		List<Project> allProjects = service.getAllProjects();
		prefLoader.updateProjects(allProjects);
		setProjects(allProjects);
		appendMessage("Reading projects from toggle finished");
	}
	
	private void setProjects(Collection<Project> newProjects) {
		Set<Project> projects = new TreeSet<Project>(new Comparator<Project>() {

			@Override
			public int compare(Project o1, Project o2) {
				return o1.getClientProjectName().compareTo(o2.getClientProjectName());
			}
		});
		projects.addAll(newProjects);
		
		projectsCombobox.getItems().setAll(projects);
	}
}
