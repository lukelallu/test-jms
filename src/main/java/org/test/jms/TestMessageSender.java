package org.test.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Queue;

/**
 * The TestMessageSender class uses the injected JMSTemplate to send a message
 * to a specified Queue. In our case we're sending messages to 'TestQueueTwo'
 */
@Service
public class TestMessageSender {
	private static final Logger logger_c = LoggerFactory.getLogger(TestMessageSender.class);

	@Autowired
	private JmsTemplate jmsTemplate_i;

	@Autowired
	@Qualifier("publishQueue")
	private Queue testQueue_i;

	/**
	 * Sends message using JMS Template.
	 *
	 * @param message_p the message_p
	 * @throws JMSException the jMS exception
	 */
	public void sendMessage(String message_p) throws JMSException {
		logger_c.debug("About to put message on queue. Queue[" + testQueue_i.toString() + "] Message[" + message_p + "]");
		jmsTemplate_i.convertAndSend(testQueue_i, message_p);
	}

	/**
	 * Sets the jms template.
	 *
	 * @param tmpl the jms template
	 */
	public void setJmsTemplate(JmsTemplate tmpl) {
		this.jmsTemplate_i = tmpl;
	}

	/**
	 * Sets the test queue.
	 *
	 * @param queue the new test queue
	 */
	public void setTestQueue(Queue queue) {
		this.testQueue_i = queue;
	}
}
