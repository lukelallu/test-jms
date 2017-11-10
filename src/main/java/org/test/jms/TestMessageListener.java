package org.test.jms;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

/**
 * Class handles incoming messages
 *
 */
@Component
public class TestMessageListener {

	private static final Logger logger_c = LoggerFactory.getLogger(TestMessageListener.class);

	@Autowired
	private TestMessageSender messageSender_i;

	//public static final String ON_NEW_PAYMENT = "jms/OnNewPayment";
	public static final String ON_NEW_PAYMENT = "jms/P2P.NEW.PAYMENT";
	public static final String P2P_PYMT_REQUEST = "jms/P2PPYMTREQUEST";
	/**
	 * Method implements JMS onMessage and acts as the entry
	 * point for messages consumed by Springs DefaultMessageListenerContainer.
	 * When DefaultMessageListenerContainer picks a message from the queue it
	 * invokes this method with the message payload.
	 */
	@JmsListener(destination = "${queue.name.test}", containerFactory = "jmsListenerContainerFactory")
	public void onMessage(Message message) {
		logger_c.debug("Received message from queue [" + message + "]");

            /* The message must be of type TextMessage */
		if (message instanceof TextMessage) {
			try {
				String msgText = ((TextMessage) message).getText();
				logger_c.debug("About to process message: " + msgText);

                      /* call message sender to put message onto second queue */
				messageSender_i.sendMessage(msgText);

			} catch (JMSException jmsEx_p) {
				String errMsg = "An error occurred extracting message";
				logger_c.error(errMsg, jmsEx_p);
			}
		} else {
			String errMsg = "Message is not of expected type TextMessage";
			logger_c.error(errMsg);
			throw new RuntimeException(errMsg);
		}
	}

	/**
	 * Sets the message sender.
	 *
	 * @param messageSender_p the new message sender
	 */
	public void setTestMessageSender(TestMessageSender messageSender_p) {
		this.messageSender_i = messageSender_p;
	}
}