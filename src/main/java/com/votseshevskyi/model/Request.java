package com.votseshevskyi.model;

import com.opencsv.bean.CsvBindByPosition;

public class Request {
	@CsvBindByPosition(position = 0)
	public String timeStamp;
	@CsvBindByPosition(position = 1)
	public String elapsed;
	@CsvBindByPosition(position = 2)
	public String label;
	@CsvBindByPosition(position = 3)
	public String responseCode;
	@CsvBindByPosition(position = 4)
	public String responseMessage;
	@CsvBindByPosition(position = 5)
	public String threadName;
	@CsvBindByPosition(position = 6)
	public String dataType;
	@CsvBindByPosition(position = 7)
	public String success;
	@CsvBindByPosition(position = 8)
	public String failureMessage;
	@CsvBindByPosition(position = 9)
	public String bytes;
	@CsvBindByPosition(position = 10)
	public String sentBytes;
	@CsvBindByPosition(position = 11)
	public String grpThreads;
	@CsvBindByPosition(position = 12)
	public String allThreads;
	@CsvBindByPosition(position = 13)
	public String URL;
	@CsvBindByPosition(position = 14)
	public String Latency;
	@CsvBindByPosition(position = 15)
	public String IdleTime;
	@CsvBindByPosition(position = 16)
	public String Connect;
}
