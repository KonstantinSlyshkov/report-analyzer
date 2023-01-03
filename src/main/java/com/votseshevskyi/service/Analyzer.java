package com.votseshevskyi.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.votseshevskyi.model.Request;
import com.votseshevskyi.model.Rule;

public abstract class Analyzer {
	private static final String REQUEST_PARAM = "requestName";
	private static final String SKIP_PARAM_VALUE = "ALL";

	public Map<String, String> analyzeRequests(List<Request> requests, List<String> conditions) {
		Map<String, String> errors = new HashMap<>();
		for (String condition : conditions) {
			List<String> parameters = Arrays.stream(condition.split(" "))
					.map(String::trim)
					.filter(StringUtils::isNoneEmpty)
					.collect(Collectors.toList());
			List<Request> requestsToServe = filterByLabelIfConditionPresent(requests, parameters);
			checkIfConditionPassed(parameters, requestsToServe).ifPresent(error -> errors.put(condition, error));
		}
		return errors;
	}

	protected abstract Optional<String> checkIfConditionPassed(List<String> parameters, List<Request> requests);

	public abstract Rule getRuleName();


	protected List<Request> filterByLabelIfConditionPresent(List<Request> requestsToServe, List<String> parameters) {
		/*Contains request label restriction*/
		String param = getParam(parameters, REQUEST_PARAM);
		if (!param.equals(SKIP_PARAM_VALUE)) {
			return requestsToServe.stream()
					.filter(request -> request.label.equals(param))
					.collect(Collectors.toList());
		}
		return requestsToServe;
	}

	protected String getParam(List<String> parameters, String name) {
		return parameters.stream()
				.filter(e -> e.contains(name))
				.findAny()
				.map(param -> param.replace(name + "=", ""))
				.orElseThrow(() -> new IllegalStateException("Obligatory param is missing " + name));
	}

}
