#Prerequisites
Java 8

#Build project
mvn clean package

#Run analysis demo
cd ./target
java -jar jmeter-report-parser-1.0-SNAPSHOT.jar full_path_to_rules full_path_to_report