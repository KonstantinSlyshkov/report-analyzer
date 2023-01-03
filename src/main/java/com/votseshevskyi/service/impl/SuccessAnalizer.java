package com.votseshevskyi.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.votseshevskyi.model.Request;
import com.votseshevskyi.model.Rule;
import com.votseshevskyi.service.Analyzer;

@Component
public class SuccessAnalizer extends Analyzer {
	private static final String THRESHOLD_PARAM = "threshold";

	@Override
	public Optional<String> checkIfConditionPassed(List<String> parameters, List<Request> requests) {
		Long threshold = Long.valueOf(getParam(parameters, THRESHOLD_PARAM).replace("%", ""));
		long countSuccess = requests.stream()
				.filter(req -> req.success.equals("true"))
				.count();
		int totalSize = requests.size();
		Double percentagePassed = (double) countSuccess / totalSize * 100;
		if (percentagePassed < threshold) {
			return Optional.of("Actual success rate " + percentagePassed + " breaks threshold " + threshold);
		}
		return Optional.empty();
	}

	@Override
	public Rule getRuleName() {
		return Rule.SUCCESS_RATE;
	}
}
