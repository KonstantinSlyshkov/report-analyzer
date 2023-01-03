package com.votseshevskyi;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.opencsv.bean.CsvToBeanBuilder;
import com.votseshevskyi.model.Request;
import com.votseshevskyi.model.Rule;

public class Reader {

	public static Map<Rule, List<String>> parseRulesToConditions(String filename) throws IOException {
		return Files.readAllLines(Paths.get(filename)).stream()
				.map(e -> e.split(";"))
				.filter(e -> {
					Optional<Rule> rule = Rule.defineRule(e[0]);
					if (!rule.isPresent()) {
						System.out.println("[WARN] Rule is not configured to be served. " + Arrays.toString(e));
					}
					return rule.isPresent();
				})
				.collect(Collectors.toMap((String[] data) -> Rule.defineRule(data[0]).get(),
						data -> {
							List<String> conditions = new ArrayList<>();
							conditions.add(data[1]);
							return conditions;
						},
						(List<String> conditionOne, List<String> conditionTwo) -> {
							conditionOne.addAll(conditionTwo);
							return conditionOne;
						}));
	}

	public static List<Request> parseRequests(String filename) throws IOException {
		List<Request> result = new CsvToBeanBuilder(new FileReader(filename))
				.withType(Request.class)
				.build()
				.parse();
		return result.subList(1, result.size());
	}
}
