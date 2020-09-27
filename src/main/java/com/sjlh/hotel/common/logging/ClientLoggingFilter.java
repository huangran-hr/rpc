/**
 * 
 */
package com.sjlh.hotel.common.logging;

import java.io.IOException;
import java.io.OutputStream;

import javax.annotation.Priority;
import javax.ws.rs.ConstrainedTo;
import javax.ws.rs.RuntimeType;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.client.ClientResponseContext;
import javax.ws.rs.client.ClientResponseFilter;
import javax.ws.rs.container.PreMatching;

import org.glassfish.jersey.message.MessageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sjlh.hotel.common.logging.LoggingFeature.Verbosity;

/**
 * @author Administrator
 *
 */
@ConstrainedTo(RuntimeType.CLIENT)
@PreMatching
@Priority(Integer.MAX_VALUE)
@SuppressWarnings("ClassWithMultipleLoggers")
final class ClientLoggingFilter extends LoggingInterceptor implements ClientRequestFilter, ClientResponseFilter {

    /**
     * Create a logging filter with custom logger and custom settings of entity
     * logging.
     *
     * @param logger        the logger to log messages to.
     * @param level         level at which the messages will be logged.
     * @param verbosity     verbosity of the logged messages. See {@link Verbosity}.
     * @param maxEntitySize maximum number of entity bytes to be logged (and buffered) - if the entity is larger,
     *                      logging filter will print (and buffer in memory) only the specified number of bytes
     *                      and print "...more..." string at the end. Negative values are interpreted as zero.
     */
    public ClientLoggingFilter(final Logger logger, final String level, final Verbosity verbosity, final int maxEntitySize) {
        super(logger, level, verbosity, maxEntitySize);
    }

    @Override
    public void filter(final ClientRequestContext context) throws IOException {
        final long id = _id.incrementAndGet();
        context.setProperty(LOGGING_ID_PROPERTY, id);

        final StringBuilder b = new StringBuilder();

        printRequestLine(b, "Sending client request", id, context.getMethod(), context.getUri());
        printPrefixedHeaders(b, id, REQUEST_PREFIX, context.getStringHeaders());

        if (context.hasEntity() && printEntity(verbosity, context.getMediaType())) {
            final OutputStream stream = new LoggingStream(b, context.getEntityStream());
            context.setEntityStream(stream);
            context.setProperty(ENTITY_LOGGER_PROPERTY, stream);
            // not calling log(b) here - it will be called by the interceptor
        } else {
            log(context, b);
        }
    }

	@Override
    public void filter(final ClientRequestContext requestContext, final ClientResponseContext responseContext)
            throws IOException {
        final Object requestId = requestContext.getProperty(LOGGING_ID_PROPERTY);
        final long id = requestId != null ? (Long) requestId : _id.incrementAndGet();

        final StringBuilder b = new StringBuilder();

        printResponseLine(b, "Client response received", id, responseContext.getStatus());
        printPrefixedHeaders(b, id, RESPONSE_PREFIX, responseContext.getHeaders());

        if (responseContext.hasEntity() && printEntity(verbosity, responseContext.getMediaType())) {
            responseContext.setEntityStream(logInboundEntity(b, responseContext.getEntityStream(),
                    MessageUtils.getCharset(responseContext.getMediaType())));
        }

        log(requestContext, b);
    }

	private void log(ClientRequestContext requestContext, StringBuilder b) {
		Logger logger = getLogger(requestContext);
		logger.info(b.toString());
	}
    
    protected Logger getLogger(final ClientRequestContext context) {
    	String loggerName = this.logger.getName();
    	String path = context.getUri().getPath();
    	if(path!=null && !path.trim().equals("")) {
    		if(path.startsWith("/"))path = path.substring(1);
    		if(path.endsWith("/"))path = path.substring(0, path.length()-1);
    		String[] paths = path.split("/");
    		for(String p: paths) {
    			loggerName += "." + p;
    		}
    	}
    	Logger logger = LoggerFactory.getLogger(loggerName);
    	return logger;
    }
}