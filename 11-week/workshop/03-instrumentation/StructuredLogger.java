import java.time.Instant;

public final class StructuredLogger {
    private StructuredLogger() {
    }

    public static String createLog(
            String level,
            String service,
            String traceId,
            String spanId,
            String event,
            String message) {

        return "{"
                + "\"timestamp\":\"" + Instant.now() + "\","
                + "\"level\":\"" + escape(level) + "\","
                + "\"service\":\"" + escape(service) + "\","
                + "\"trace_id\":\"" + escape(traceId) + "\","
                + "\"span_id\":\"" + escape(spanId) + "\","
                + "\"event\":\"" + escape(event) + "\","
                + "\"message\":\"" + escape(message) + "\""
                + "}";
    }

    private static String escape(String value) {
        if (value == null) {
            return "";
        }

        return value.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }

    public static void info(
            String service,
            String traceId,
            String spanId,
            String event,
            String message) {
        System.out.println(createLog(
                "INFO", service, traceId, spanId, event, message));
    }

    public static void error(
            String service,
            String traceId,
            String spanId,
            String event,
            String message) {
        System.err.println(createLog(
                "ERROR", service, traceId, spanId, event, message));
    }
}