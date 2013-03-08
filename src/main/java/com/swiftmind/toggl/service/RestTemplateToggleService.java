package com.swiftmind.toggl.service;

import java.io.IOException;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.codehaus.jackson.map.ObjectMapper;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.swiftmind.toggl.domain.JsonRequest;
import com.swiftmind.toggl.domain.JsonResponse;
import com.swiftmind.toggl.domain.Project;
import com.swiftmind.toggl.domain.TimeEntry;

/**
 * Implementation of the {@link ToggleService} using Spring's RestTemplate.
 * 
 * @author Odilo Oehmichen, <a href="http://www.swiftmind.com">Swiftmind GmbH</a>
 */
public class RestTemplateToggleService implements ToggleService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RestTemplateToggleService.class);

	String toggleBaseUrl = "https://www.toggl.com/api/v6/";

	RestTemplate template;

	HttpHeaders httpHeaders;

	public RestTemplateToggleService(String apiKey) {
		SimpleClientHttpRequestFactory s = createSimpleClient(apiKey);
		template = new RestTemplate(s);

		httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
	}

	@Override
	public Duration sumDurationForDay(LocalDate date) {
		LOGGER.info("Reading total duration already booked for day {}", date);
		
		String url = toggleBaseUrl + "time_entries.json?start_date={startdate}&end_date={enddate}";

		ParameterizedTypeReference<JsonResponse<TimeEntry>> responseType = new ParameterizedTypeReference<JsonResponse<TimeEntry>>() {
		};
		ResponseEntity<JsonResponse<TimeEntry>> response = template.exchange(url, HttpMethod.GET, null, responseType,
				getStringFromLocalDate(date), getStringFromLocalDate(date.plusDays(1)));

		JsonResponse<TimeEntry> result = response.getBody();
		Duration duration = Duration.ZERO;
		if (result != null && result.getData() != null && result.getData().length > 0) {
			for (TimeEntry t : result.getData()) {
				duration = duration.plus(Duration.standardSeconds(t.getDurationInSeconds()));
			}
		} else {
			LOGGER.info("No entry found for date {}", date);
		}

		return duration;
	}

	@Override
	public List<Project> getAllProjects() {
		LOGGER.info("Reading all projects from toggle.");
		
		String url = toggleBaseUrl + "projects.json";

		ParameterizedTypeReference<JsonResponse<Project>> responseType = new ParameterizedTypeReference<JsonResponse<Project>>() {};
		ResponseEntity<JsonResponse<Project>> response = template.exchange(url, HttpMethod.GET, null, responseType);

		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug("Found projects:");
			for (Project p : response.getBody().getData()) {
				LOGGER.debug("{}", p);
			}
		}

		return Arrays.asList(response.getBody().getData());
	}

	@Override
	public String createEntry(String description, LocalDate startDate, Duration duration, Integer projectId) {
		String url = toggleBaseUrl + "time_entries.json";

		TimeEntry entry = new TimeEntry();
		entry.setDescription(description);
		entry.setJodaDuration(duration);

		DateTime startDateTime = startDate.toDateTime(new LocalTime(8, 30));
		entry.setStartDateTime(startDateTime);
		entry.setEndDateTime(startDateTime.plus(duration.toPeriod()));
		entry.setProjectId(projectId);

		JsonRequest request = new JsonRequest(entry);

		HttpEntity<JsonRequest> requestEntity = new HttpEntity<JsonRequest>(request, httpHeaders);

		logJson(request);

		ResponseEntity<String> response = template.exchange(url, HttpMethod.POST, requestEntity, String.class);
		return response.getBody();
	}

	void logJson(Object obj) {
		if(LOGGER.isDebugEnabled()) {
			try {
				ObjectMapper mapper = new ObjectMapper();
				StringWriter writer = new StringWriter();
				mapper.writeValue(writer, obj);
				LOGGER.debug("Sending JSON: {}", writer.toString());
			} catch (Exception e) {
				LOGGER.warn("Error logging JSON request.", e);
			}
		}
	}

	static String getStringFromLocalDate(LocalDate date) {
		return date.toString();
	}

	/** need to send the API token with Basic Authorization */
	private SimpleClientHttpRequestFactory createSimpleClient(final String apiKey) {
		SimpleClientHttpRequestFactory simpleClient = new SimpleClientHttpRequestFactory() {
			@Override
			protected void prepareConnection(HttpURLConnection connection, String httpMethod) throws IOException {
				super.prepareConnection(connection, httpMethod);

				// Basic Authentication for Police API
				String authorisation = apiKey + ":" + "api_token";
				byte[] encodedAuthorisation = Base64.encodeBase64(authorisation.getBytes());
				connection.setRequestProperty("Authorization", "Basic " + new String(encodedAuthorisation));
			}
		};
		return simpleClient;
	}
}
