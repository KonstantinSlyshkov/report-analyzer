package com.votseshevskyi;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import com.votseshevskyi.model.Request;
import com.votseshevskyi.model.Rule;
import com.votseshevskyi.service.Analyzer;

@SpringBootApplication
public class Runner implements CommandLineRunner {

	public static void main(String[] args) {
		new SpringApplicationBuilder(Runner.class)
				.web(WebApplicationType.NONE)
				.run(args);
	}
	@Autowired
	private List<Analyzer> analyzers;

	@Override
	public void run(String... args) throws Exception {
		String reportName = args[0];
		String rules = args[1];
		String timeFrame = null;
		if (args.length > 2) {
			timeFrame = args[2];
		}

		List<Request> parsedRequests = Reader.parseRequests(reportName);
		List<Request> requests = RequestPreparationUtils.limitRequestsWithTimeFrame(parsedRequests, timeFrame);
		Map<Rule, List<String>> rulesToConditions = Reader.parseRulesToConditions(rules);

		for (Map.Entry<Rule, List<String>> entry: rulesToConditions.entrySet()) {
			Optional<Analyzer> analyzer = analyzers.stream()
					.filter(e -> e.getRuleName().equals(entry.getKey()))
					.findAny();
			if (!analyzer.isPresent()) {
				System.out.println("[WARN] cannot find implementation for specified rule " + entry.getKey());
				continue;
			}
			Map<String, String> conditionToError = analyzer.get().analyzeRequests(requests, entry.getValue());
			if (!conditionToError.isEmpty()) {
				for (Map.Entry<String, String> entryError: conditionToError.entrySet())
				System.out.println("[ERROR] failed on rule " + entry.getKey().getRuleName() + " Error: " + entryError);
			}
		}
	}
}
