package dev.mitra;

import dev.mitra.filter.SpamFilter;
import net.fabricmc.api.ClientModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class QuietAuthClient implements ClientModInitializer {

    private static final Logger LOGGER = LoggerFactory.getLogger("QuietAuth");
    private static volatile boolean initialized = false;

    @Override
    public void onInitializeClient() {
        if (initialized) {
            return;
        }
        initialized = true;

        try {
            installLogFilter();
            LOGGER.info("QuietAuth initialized.");
        } catch (Exception e) {
            LOGGER.warn("QuietAuth unable to initialize", e);
        }
    }

    private void installLogFilter() {
        LoggerContext ctx = (LoggerContext) LogManager.getContext(false);

        Configuration config = ctx.getConfiguration();

        SpamFilter filter = SpamFilter.create();
        config.addFilter(filter);

        ctx.updateLoggers();
    }
}
