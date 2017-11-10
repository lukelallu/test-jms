package org.test.jms;

//@Component
public class OldSchoolListener {/*implements MessageListener {

	private static final Logger logger_c = LoggerFactory.getLogger(OldSchoolListener.class);

	@Autowired
	private TestMessageSender messageSender_i;

	@Override
	public void onMessage(Message message) {
		logger_c.debug("Received message from queue [" + message + "]");

		//* The message must be of type TextMessage *//*
		if (message instanceof TextMessage) {
			try {
				String msgText = ((TextMessage) message).getText();
				logger_c.debug("About to process message: " + msgText);

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
	}*/
}
