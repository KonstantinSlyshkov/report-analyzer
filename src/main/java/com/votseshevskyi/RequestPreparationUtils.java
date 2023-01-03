package com.votseshevskyi;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.votseshevskyi.model.Request;

public class RequestPreparationUtils {

	public static List<Request> limitRequestsWithTimeFrame(List<Request> requests, String timeFrame) {
		if (timeFrame == null) {
			return requests;
		}
		String[] frames = timeFrame.split("-");
		Integer start = Integer.valueOf(frames[0].replace("s", ""));
		Integer end = Integer.valueOf(frames[1].replace("s", ""));
		Map<Request, LocalDateTime> requestToDate = requests.stream().collect(Collectors.toMap(Function.identity(), request -> {
			Long timeStamp = Long.valueOf(request.timeStamp);
			return LocalDateTime.ofInstant(Instant.ofEpochMilli(timeStamp), ZoneId.systemDefault());
		}));

		LocalDateTime testStart = requestToDate.entrySet().stream()
				.min(Comparator.comparing(Map.Entry::getValue)).get().getValue();
		LocalDateTime searchStart = testStart.plusSeconds(start);
		LocalDateTime searchEnd = testStart.plusSeconds(end);


		return requestToDate.entrySet().stream()
				.filter(request -> request.getValue().isAfter(searchStart) && request.getValue().isBefore(searchEnd))
				.map(Map.Entry::getKey)
				.collect(Collectors.toList());
	}
}
