package gameengine.util;

public final class Logger {
    public static final String DEFAULT_CASE = "default in switch-case";

    private Logger() {
    }

    public static void debug(final String source, final String message) {
        android.util.Log.d("" + source, "" + message);
    }
}
