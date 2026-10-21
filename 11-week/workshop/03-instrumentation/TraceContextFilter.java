import java.util.UUID;
import java.util.regex.Pattern;

public final class TraceContextFilter {
    private static final Pattern TRACE_ID =
            Pattern.compile("[0-9a-fA-F]{32}");

    private TraceContextFilter() {
    }

    public static String getOrCreateTraceId(String incomingTraceId) {
        if (incomingTraceId != null
                && TRACE_ID.matcher(incomingTraceId).matches()
                && !incomingTraceId.matches("0{32}")) {
            return incomingTraceId.toLowerCase();
        }

        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String createSpanId() {
        String spanId;
        do {
            spanId = UUID.randomUUID().toString()
                    .replace("-", "").substring(0, 16);
        } while (spanId.matches("0{16}"));

        return spanId;
    }
}