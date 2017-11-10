package org.test.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ErrorHandler;

/**
 * Error handler for all JMS transactions
 */
@Component
public class JmsErrorHandler implements ErrorHandler {

	final static Logger LOG = LoggerFactory.getLogger(JmsErrorHandler.class);

	public void handleError(Throwable throwable) {
		LOG.error("Unhandled JMS exception occurred!", throwable);

	}
}
