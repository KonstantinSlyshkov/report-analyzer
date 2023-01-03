package com.votseshevskyi.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.votseshevskyi.model.Request;
import com.votseshevskyi.model.Rule;
import com.votseshevskyi.service.Analyzer;

@Component
public class AverageResponseTimeAnalizer extends Analyzer {
	private static final String THRESHOLD_PARAM = "threshold";

	@Override
	public Optional<String> checkIfConditionPassed(List<String> parameters, List<Request> requests) {
		return checkElapsedRestriction(getParam(parameters, THRESHOLD_PARAM), requests);
	}

	private Optional<String> checkElapsedRestriction(String elapsedTimeRestriction, List<Request> requests) {
		Integer thresholdValue = Integer.parseInt(elapsedTimeRestriction.replaceAll("\\D", ""));

		Double average = requests.stream().map(request -> request.elapsed).collect(Collectors.averagingInt(Integer::parseInt));

		String sign = elapsedTimeRestriction.substring(0, 1);
		if (sign.equals(">") && average > thresholdValue) {
			return Optional.of("Average " + average + " response time breaks threshold " + thresholdValue);
		} else if (sign.equals("<") && average < thresholdValue) {
			return Optional.of("Average " + average + " response time breaks threshold " + thresholdValue);
		}
		return Optional.empty();
	}

	@Override
	public Rule getRuleName() {
		return Rule.AVERAGE_RESPONSE_TIME;
	}
}
