## 1. Purpose

This document explains how to compile and verify the Java observability utilities developed for the Futbolix Week 11 workshop.

The current implementation is a standalone Java demonstration of trace identifiers and structured JSON logging. It is not yet integrated into a running Futbolix API service.

## 2. Prerequisites

- Java Development Kit (JDK) installed.
- java and javac available in the terminal.
- Visual Studio Code or another text editor.
- A terminal opened in the instrumentation directory.

## 3. Instrumentation Components

The implementation contains the following Java files:

- TraceContextFilter.java: validates an incoming trace identifier or generates a new one and creates span identifiers.
- StructuredLogger.java: generates JSON log entries with a timestamp, severity level, service name, trace identifier, span identifier, event, and message.

These utilities use the Java standard library and do not require Spring Boot to compile.

## 4. Compile the Java Files

Open a terminal in this directory:

11-week/workshop/03-instrumentation/

Check the installed Java version:

```powershell
java -version
javac -version
Compile both files:
javac TraceContextFilter.java StructuredLogger.java
If compilation succeeds, Java creates the corresponding .class files.
5. Verify the Compilation
Check that the compiled files exist:
Test-Path .\TraceContextFilter.class
Test-Path .\StructuredLogger.class
Both commands should return True after successful compilation.
6. Trace Context Behavior
TraceContextFilter provides methods to:
Reuse a valid incoming 32-character hexadecimal trace identifier.
Generate a new trace identifier when the incoming value is missing or invalid.
Generate a 16-character hexadecimal span identifier.
These methods are utility functions. They do not yet read HTTP headers or propagate trace context through HTTP requests or RabbitMQ messages.
7. Structured Logging Behavior
StructuredLogger provides info and error methods that print JSON log entries.
Each entry includes:
timestamp
level
service
trace_id
span_id
event
message
The logger escapes common special characters in string values. Sensitive information, including passwords, tokens, and payment credentials, must never be included in log messages.
8. Automated Tests and Metrics
Automated tests verifying trace identifier behavior and structured log output still need to be added and executed.
Request counters and latency histograms also remain to be implemented and verified.
No test results or metrics are claimed in this document until they have been produced by an actual execution.
9. Current Limitations
The current implementation is a standalone Java demonstration. It does not yet include:
An HTTP middleware or Spring Boot filter.
Propagation of trace context to downstream services.
RabbitMQ message-header propagation.
An HTTP response header containing the trace identifier.
Request counters or latency histograms.
Automated tests.
These capabilities must be implemented and tested before the instrumentation can be considered complete.
10. Verification Evidence
After running the compilation commands, record the actual results here.
Java version: To be recorded after execution.
Compilation result: To be recorded after execution.
Automated test result: Pending implementation.
Metrics verification: Pending implementation.
Service integration: Pending implementation.