package org.test.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

//@Component
public class MessageListener implements javax.jms.MessageListener {

	private static final Logger logger = LoggerFactory.getLogger(MessageListener.class);

	@Autowired
	private TestMessageSender sendMessage;

	@Override
	public void onMessage(Message message) {
		logger.debug("Received message from queue [" + message + "]");
		if (message instanceof TextMessage) {
			try {
				String msgText = ((TextMessage) message).getText();
				sendMessage.sendMessage(msgText);
			} catch (JMSException jmsEx_p) {
				String errMsg = "An error occurred extracting message";
				logger.error(errMsg, jmsEx_p);
			}
		} else {
			String errMsg = "Message is not of expected type TextMessage";
			logger.error(errMsg);
			throw new RuntimeException(errMsg);
		}
	}
}
