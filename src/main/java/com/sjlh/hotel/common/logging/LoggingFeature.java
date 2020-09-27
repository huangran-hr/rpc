/**
 * 
 */
package com.sjlh.hotel.common.logging;

import java.util.Map;

import javax.ws.rs.RuntimeType;
import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;

import org.glassfish.jersey.CommonProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Administrator
 *
 */
public class LoggingFeature implements Feature {

    /**
     * Default logger name to log request and response messages.
     */
    public static final String DEFAULT_LOGGER_NAME = LoggingFeature.class.getName();
    /**
     * Default maximum entity bytes to be logged.
     */
    public static final int DEFAULT_MAX_ENTITY_SIZE = 8 * 1024;
    /**
     * Default verbosity for entity logging. See {@link Verbosity}.
     */
    public static final Verbosity DEFAULT_VERBOSITY = Verbosity.PAYLOAD_TEXT;

    private static final String LOGGER_NAME_POSTFIX = ".logger.name";
    private static final String LOGGER_LEVEL_POSTFIX = ".logger.level";
    private static final String VERBOSITY_POSTFIX = ".verbosity";
    private static final String MAX_ENTITY_POSTFIX = ".entity.maxSize";
    private static final String LOGGING_FEATURE_COMMON_PREFIX = "jersey.config.logging";
    /**
     * Common logger name property.
     */
    public static final String LOGGING_FEATURE_LOGGER_NAME = LOGGING_FEATURE_COMMON_PREFIX + LOGGER_NAME_POSTFIX;
    /**
     * Common logger level property.
     */
    public static final String LOGGING_FEATURE_LOGGER_LEVEL = LOGGING_FEATURE_COMMON_PREFIX + LOGGER_LEVEL_POSTFIX;
    /**
     * Common property for configuring a verbosity of entity.
     */
    public static final String LOGGING_FEATURE_VERBOSITY = LOGGING_FEATURE_COMMON_PREFIX + VERBOSITY_POSTFIX;
    /**
     * Common property for configuring a maximum number of bytes of entity to be logged.
     */
    public static final String LOGGING_FEATURE_MAX_ENTITY_SIZE = LOGGING_FEATURE_COMMON_PREFIX + MAX_ENTITY_POSTFIX;

    private static final String LOGGING_FEATURE_SERVER_PREFIX = "jersey.config.server.logging";
    /**
     * Server logger name property.
     */
    public static final String LOGGING_FEATURE_LOGGER_NAME_SERVER = LOGGING_FEATURE_SERVER_PREFIX + LOGGER_NAME_POSTFIX;
    /**
     * Server logger level property.
     */
    public static final String LOGGING_FEATURE_LOGGER_LEVEL_SERVER = LOGGING_FEATURE_SERVER_PREFIX + LOGGER_LEVEL_POSTFIX;
    /**
     * Server property for configuring a verbosity of entity.
     */
    public static final String LOGGING_FEATURE_VERBOSITY_SERVER = LOGGING_FEATURE_SERVER_PREFIX + VERBOSITY_POSTFIX;
    /**
     * Server property for configuring a maximum number of bytes of entity to be logged.
     */
    public static final String LOGGING_FEATURE_MAX_ENTITY_SIZE_SERVER = LOGGING_FEATURE_SERVER_PREFIX + MAX_ENTITY_POSTFIX;

    private static final String LOGGING_FEATURE_CLIENT_PREFIX = "jersey.config.client.logging";
    /**
     * Client logger name property.
     */
    public static final String LOGGING_FEATURE_LOGGER_NAME_CLIENT = LOGGING_FEATURE_CLIENT_PREFIX + LOGGER_NAME_POSTFIX;
    /**
     * Client logger level property.
     */
    public static final String LOGGING_FEATURE_LOGGER_LEVEL_CLIENT = LOGGING_FEATURE_CLIENT_PREFIX + LOGGER_LEVEL_POSTFIX;
    /**
     * Client property for configuring a verbosity of entity.
     */
    public static final String LOGGING_FEATURE_VERBOSITY_CLIENT = LOGGING_FEATURE_CLIENT_PREFIX + VERBOSITY_POSTFIX;
    /**
     * Client property for configuring a maximum number of bytes of entity to be logged.
     */
    public static final String LOGGING_FEATURE_MAX_ENTITY_SIZE_CLIENT = LOGGING_FEATURE_CLIENT_PREFIX + MAX_ENTITY_POSTFIX;

    private final Logger filterLogger;
    private final Verbosity verbosity;
    private final Integer maxEntitySize;
    private final String level;

    /**
     * Creates the feature with default values.
     */
    public LoggingFeature() {
        this(null, null, null, null);
    }

    /**
     * Creates the feature with custom logger.
     *
     * @param logger the logger to log requests and responses.
     */
    public LoggingFeature(Logger logger) {
        this(logger, null, null, null);
    }

    /**
     * Creates the feature with custom logger and verbosity.
     *
     * @param logger    the logger to log requests and responses.
     * @param verbosity verbosity of logged messages. See {@link Verbosity}.
     */
    public LoggingFeature(Logger logger, Verbosity verbosity) {
        this(logger, null, verbosity, null);
    }

    /**
     * Creates the feature with custom logger and maximum number of bytes of entity to log.
     *
     * @param logger        the logger to log requests and responses.
     * @param maxEntitySize maximum number of entity bytes to be logged (and buffered) - if the entity is larger,
     *                      logging filter will print (and buffer in memory) only the specified number of bytes
     *                      and print "...more..." string at the end. Negative values are interpreted as zero.
     */
    public LoggingFeature(Logger logger, Integer maxEntitySize) {
        this(logger, null, DEFAULT_VERBOSITY, maxEntitySize);
    }

    /**
     * Creates the feature with custom logger, it's level, message verbosity and maximum number of bytes of entity to log.
     *
     * @param logger        the logger to log requests and responses.
     * @param level         level on which the messages will be logged.
     * @param verbosity     verbosity of logged messages. See {@link Verbosity}.
     * @param maxEntitySize maximum number of entity bytes to be logged (and buffered) - if the entity is larger,
     *                      logging filter will print (and buffer in memory) only the specified number of bytes
     *                      and print "...more..." string at the end. Negative values are interpreted as zero.
     */
    public LoggingFeature(Logger logger, String level, Verbosity verbosity, Integer maxEntitySize) {
        this.filterLogger = logger;
        this.level = level;
        this.verbosity = verbosity;
        this.maxEntitySize = maxEntitySize;
    }

    @Override
    public boolean configure(FeatureContext context) {
        boolean enabled = false;

        if (context.getConfiguration().getRuntimeType() == RuntimeType.CLIENT) {
            ClientLoggingFilter clientLoggingFilter = (ClientLoggingFilter) createLoggingFilter(context, RuntimeType.CLIENT);
            context.register(clientLoggingFilter);
            enabled = true;
        }
        if (context.getConfiguration().getRuntimeType() == RuntimeType.SERVER) {
//            ServerLoggingFilter serverClientFilter = (ServerLoggingFilter) createLoggingFilter(context, RuntimeType.SERVER);
//            context.register(serverClientFilter);
//            enabled = true;
        }
        return enabled;
    }

    private LoggingInterceptor createLoggingFilter(FeatureContext context, RuntimeType runtimeType) {
        Map properties = context.getConfiguration().getProperties();
        String loggerName = CommonProperties.getValue(properties, LOGGING_FEATURE_LOGGER_NAME,DEFAULT_LOGGER_NAME);
        String filterLoggerName = CommonProperties.getValue(properties, runtimeType == RuntimeType.SERVER ? LOGGING_FEATURE_LOGGER_NAME_SERVER : LOGGING_FEATURE_LOGGER_NAME_CLIENT, 
               loggerName);
        String filterLevel = CommonProperties.getValue(
                properties,
                runtimeType == RuntimeType.SERVER ? LOGGING_FEATURE_LOGGER_LEVEL_SERVER : LOGGING_FEATURE_LOGGER_LEVEL_CLIENT,
                CommonProperties.getValue(
                        context.getConfiguration().getProperties(),
                        LOGGING_FEATURE_LOGGER_LEVEL,
                        "INFO"));
        Verbosity defaultVerbosity = CommonProperties.getValue(properties, LOGGING_FEATURE_VERBOSITY, DEFAULT_VERBOSITY);
        Verbosity filterVerbosity = CommonProperties.getValue(
                properties,
                runtimeType == RuntimeType.SERVER ? LOGGING_FEATURE_VERBOSITY_SERVER : LOGGING_FEATURE_VERBOSITY_CLIENT,
                defaultVerbosity);
        Integer defaultFilterMaxEntitySize = CommonProperties.getValue(
                properties,
                LOGGING_FEATURE_MAX_ENTITY_SIZE,
                DEFAULT_MAX_ENTITY_SIZE
        );
        Integer filterMaxEntitySize = CommonProperties.getValue(
                properties,
                runtimeType == RuntimeType.SERVER ? LOGGING_FEATURE_MAX_ENTITY_SIZE_SERVER
                        : LOGGING_FEATURE_MAX_ENTITY_SIZE_CLIENT,
                        defaultFilterMaxEntitySize);


        if (runtimeType == RuntimeType.SERVER) {
//            return new ServerLoggingFilter(filterLogger != null ? filterLogger : Logger.getLogger(filterLoggerName),
//                    level != null ? level : loggerLevel,
//                    verbosity != null ? verbosity : filterVerbosity,
//                    maxEntitySize != null ? maxEntitySize : filterMaxEntitySize);
        	return null;
        } else {
            return new ClientLoggingFilter(filterLogger != null ? filterLogger : LoggerFactory.getLogger(filterLoggerName),
                    level != null ? level : "INFO",
                    verbosity != null ? verbosity : filterVerbosity,
                    maxEntitySize != null ? maxEntitySize.intValue() : filterMaxEntitySize.intValue());
        }
    }

    /**
     * {@code Verbosity} determines how detailed message will be logged.
     * <p>
     * <ul>
     * <li>The lowest verbosity ({@link #HEADERS_ONLY}) will log only request/response headers.</li>
     * <li>
     * The medium verbosity will log request/response headers, as well as an entity if considered a readable text. See {@link
     * #PAYLOAD_TEXT}.
     * </li>
     * <li>The highest verbosity will log all types of an entity (besides the request/response headers.</li>
     * </ul>
     * <p>
     * Note that the entity is logged up to the maximum number specified in any of the following constructors {@link
     * LoggingFeature#LoggingFeature(Logger, Integer)}, {@link LoggingFeature#LoggingFeature(Logger, Level, Verbosity, Integer)}
     * or by some of the feature's properties (see {@link #LOGGING_FEATURE_MAX_ENTITY_SIZE}, {@link
     * #LOGGING_FEATURE_MAX_ENTITY_SIZE_CLIENT}, {@link #LOGGING_FEATURE_MAX_ENTITY_SIZE_SERVER}.
     */
    public enum Verbosity {
        /**
         * Only content of HTTP headers is logged. No message payload data are logged.
         */
        HEADERS_ONLY,
        /**
         * Content of HTTP headers as well as entity content of textual media types is logged. Following is the list of media
         * types that are considered textual for the logging purposes:
         * <ul>
         * <li>{@code text/*}</li>
         * <li>{@code application/atom+xml}</li>
         * <li>{@code application/json}</li>
         * <li>{@code application/svg+xml}</li>
         * <li>{@code application/x-www-form-urlencoded}</li>
         * <li>{@code application/xhtml+xml}</li>
         * <li>{@code application/xml}</li>
         * </ul>
         */
        PAYLOAD_TEXT,
        /**
         * Full verbose logging. Content of HTTP headers as well as any message payload content will be logged.
         */
        PAYLOAD_ANY
    }
}