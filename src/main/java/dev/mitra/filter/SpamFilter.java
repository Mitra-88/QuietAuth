package dev.mitra.filter;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.filter.AbstractFilter;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.message.ParameterizedMessage;

public final class SpamFilter extends AbstractFilter {

    private static final String[] WARN_SPAM_PREFIXES = {
            "Received packet for unknown team",
            "Missing texture references in model",
            "Existing file",
            "Profile contained invalid signature for textures property"
    };

    private static final String[] ERROR_SPAM_PREFIXES = {
            "Malformed signature encoding on property",
            "Failed to verify signature on property",
            "Couldn't parse item model"
    };

    private final char[] warnFirstChars;
    private final char[] errorFirstChars;

    private SpamFilter() {
        super(Result.NEUTRAL, Result.NEUTRAL);
        this.warnFirstChars = computeFirstChars(WARN_SPAM_PREFIXES);
        this.errorFirstChars = computeFirstChars(ERROR_SPAM_PREFIXES);
    }

    public static SpamFilter create() {
        return new SpamFilter();
    }

    @Override
    public Result filter(LogEvent event) {
        if (event == null) {
            return Result.NEUTRAL;
        }

        Message message = event.getMessage();
        if (message == null) {
            return Result.NEUTRAL;
        }

        String text = getMessageText(message);
        if (text == null || text.isEmpty()) {
            return Result.NEUTRAL;
        }

        Level level = event.getLevel();

        if (level == Level.WARN) {
            return checkSpam(text, warnFirstChars, WARN_SPAM_PREFIXES);
        }

        if (level == Level.ERROR) {
            return checkSpam(text, errorFirstChars, ERROR_SPAM_PREFIXES);
        }

        return Result.NEUTRAL;
    }

    @Override
    public Result filter(org.apache.logging.log4j.core.Logger logger, Level level,
                         org.apache.logging.log4j.Marker marker, String message, Object... params) {
        if (message == null || message.isEmpty()) {
            return Result.NEUTRAL;
        }

        if (level == Level.WARN) {
            return checkSpam(message, warnFirstChars, WARN_SPAM_PREFIXES);
        }

        if (level == Level.ERROR) {
            return checkSpam(message, errorFirstChars, ERROR_SPAM_PREFIXES);
        }

        return Result.NEUTRAL;
    }

    @Override
    public Result filter(org.apache.logging.log4j.core.Logger logger, Level level,
                         org.apache.logging.log4j.Marker marker, String message, Object p0) {
        return filter(logger, level, marker, message, new Object[]{p0});
    }

    @Override
    public Result filter(org.apache.logging.log4j.core.Logger logger, Level level,
                         org.apache.logging.log4j.Marker marker, String message, Object p0, Object p1) {
        return filter(logger, level, marker, message, new Object[]{p0, p1});
    }

    @Override
    public Result filter(org.apache.logging.log4j.core.Logger logger, Level level,
                         org.apache.logging.log4j.Marker marker, String message, Object p0, Object p1, Object p2) {
        return filter(logger, level, marker, message, new Object[]{p0, p1, p2});
    }

    @Override
    public Result filter(org.apache.logging.log4j.core.Logger logger, Level level,
                         org.apache.logging.log4j.Marker marker, String message, Object p0, Object p1, Object p2, Object p3) {
        return filter(logger, level, marker, message, new Object[]{p0, p1, p2, p3});
    }

    @Override
    public Result filter(org.apache.logging.log4j.core.Logger logger, Level level,
                         org.apache.logging.log4j.Marker marker, String message, Object p0, Object p1, Object p2, Object p3,
                         Object p4) {
        return filter(logger, level, marker, message, new Object[]{p0, p1, p2, p3, p4});
    }

    @Override
    public Result filter(org.apache.logging.log4j.core.Logger logger, Level level,
                         org.apache.logging.log4j.Marker marker, String message, Object p0, Object p1, Object p2, Object p3,
                         Object p4, Object p5) {
        return filter(logger, level, marker, message, new Object[]{p0, p1, p2, p3, p4, p5});
    }

    @Override
    public Result filter(org.apache.logging.log4j.core.Logger logger, Level level,
                         org.apache.logging.log4j.Marker marker, String message, Object p0, Object p1, Object p2, Object p3,
                         Object p4, Object p5, Object p6) {
        return filter(logger, level, marker, message, new Object[]{p0, p1, p2, p3, p4, p5, p6});
    }

    @Override
    public Result filter(org.apache.logging.log4j.core.Logger logger, Level level,
                         org.apache.logging.log4j.Marker marker, String message, Object p0, Object p1, Object p2, Object p3,
                         Object p4, Object p5, Object p6, Object p7) {
        return filter(logger, level, marker, message, new Object[]{p0, p1, p2, p3, p4, p5, p6, p7});
    }

    @Override
    public Result filter(org.apache.logging.log4j.core.Logger logger, Level level,
                         org.apache.logging.log4j.Marker marker, String message, Object p0, Object p1, Object p2, Object p3,
                         Object p4, Object p5, Object p6, Object p7, Object p8) {
        return filter(logger, level, marker, message, new Object[]{p0, p1, p2, p3, p4, p5, p6, p7, p8});
    }

    @Override
    public Result filter(org.apache.logging.log4j.core.Logger logger, Level level,
                         org.apache.logging.log4j.Marker marker, String message, Object p0, Object p1, Object p2, Object p3,
                         Object p4, Object p5, Object p6, Object p7, Object p8, Object p9) {
        return filter(logger, level, marker, message, new Object[]{p0, p1, p2, p3, p4, p5, p6, p7, p8, p9});
    }

    @Override
    public Result filter(org.apache.logging.log4j.core.Logger logger, Level level,
                         org.apache.logging.log4j.Marker marker, Object msg, Throwable t) {
        if (msg instanceof String) {
            return filter(logger, level, marker, (String) msg);
        }
        return Result.NEUTRAL;
    }

    private static String getMessageText(Message message) {
        if (message instanceof ParameterizedMessage) {
            String format = ((ParameterizedMessage) message).getFormat();
            if (format != null && !format.isEmpty()) {
                return format;
            }
        }
        return message.getFormattedMessage();
    }

    private Result checkSpam(String message, char[] firstChars, String[] patterns) {
        if (message.isEmpty()) {
            return Result.NEUTRAL;
        }

        char firstChar = message.charAt(0);
        boolean matches = false;
        for (char c : firstChars) {
            if (c == firstChar) {
                matches = true;
                break;
            }
        }

        if (!matches) {
            return Result.NEUTRAL;
        }

        for (String pattern : patterns) {
            if (message.startsWith(pattern)) {
                return Result.DENY;
            }
        }

        return Result.NEUTRAL;
    }

    private static char[] computeFirstChars(String[] patterns) {
        char[] chars = new char[patterns.length];
        for (int i = 0; i < patterns.length; i++) {
            chars[i] = patterns[i].charAt(0);
        }
        return chars;
    }
}
