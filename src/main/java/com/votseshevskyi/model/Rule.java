package com.votseshevskyi.model;

import java.util.Arrays;
import java.util.Optional;

public enum Rule {
	AVERAGE_RESPONSE_TIME("avg-rt"),
	SUCCESS_RATE("succ");

	private final String ruleName;
	Rule(String name) {
		this.ruleName = name;
	}

	public static Optional<Rule> defineRule(String ruleName) {
		return Arrays.stream(Rule.values()).filter(rule -> rule.getRuleName().equals(ruleName)).findFirst();
	}
	public String getRuleName() {
		return ruleName;
	}
}
