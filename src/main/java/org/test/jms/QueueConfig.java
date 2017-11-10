package org.test.jms;


import org.slf4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.destination.JndiDestinationResolver;
import org.springframework.jndi.JndiTemplate;
import org.springframework.util.ErrorHandler;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;
import javax.naming.NamingException;

import static org.slf4j.LoggerFactory.getLogger;

@Configuration
@EnableJms
@PropertySource("classpath:config.properties")
public class QueueConfig {

	public static final String CONNECTION_FACTORY = "java:comp/env/jms/ConnectionFactory";
	public static final String ON_DEBIT_NETWORK_TRANSACTION = "java:comp/env/jms/OnDebitNetworkTransaction";
	public static final String ON_NEW_PAYMENT = "java:comp/env/jms/OnNewPayment";
	public static final String P2P_PYMT_REQUEST = "java:comp/env/jms/P2PPYMTREQUEST";

	private static final Logger logger = getLogger(QueueConfig.class);


	@Bean
	public ConnectionFactory connectionFactory(JndiTemplate jndiTemplate) throws NamingException {
		return jndiTemplate.lookup(CONNECTION_FACTORY, ConnectionFactory.class);
	}

	//DefaultMessageListenerContainer

	@Bean
	public DefaultJmsListenerContainerFactory jmsListenerContainerFactory(ConnectionFactory connectionFactory) {

		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		factory.setConnectionFactory(connectionFactory);
		factory.setErrorHandler(errorHandler());
		factory.setConcurrency("3");

		JndiDestinationResolver resolver = new JndiDestinationResolver();
		//resolver.setDynamicDestinationResolver(new DynamicDestinationResolver());
		//resolver.setFallbackToDynamicDestination(true);

		factory.setDestinationResolver(resolver);

		return factory;
	}

	@Bean
	public ErrorHandler errorHandler() {
		return new JmsErrorHandler();
	}

	@Bean
	public JmsTemplate jmsTemplate(ConnectionFactory connectionFactory) {
		JmsTemplate jmsTemplate = new JmsTemplate();
		jmsTemplate.setConnectionFactory(connectionFactory);
		return jmsTemplate;
	}

	@Bean
	public Queue publishQueue(JndiTemplate jndiTemplate) throws NamingException {
		return jndiTemplate.lookup(ON_DEBIT_NETWORK_TRANSACTION, Queue.class);
	}

	@Bean
	public Queue listenQueue(JndiTemplate jndiTemplate) throws NamingException {
		return jndiTemplate.lookup(ON_NEW_PAYMENT, Queue.class);
	}

	@Bean
	public JndiTemplate jndiTemplate() {
		return new JndiTemplate();
	}

	//To resolve ${} in @Value
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
		return new PropertySourcesPlaceholderConfigurer();
	}

/*	@Bean
	public DefaultMessageListenerContainer oldSchoolListenerContainer(
			ConnectionFactory connectionFactory,
			JndiTemplate jndiTemplate,
			OldSchoolListener listener) throws NamingException {

		DefaultMessageListenerContainer container = new DefaultMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setDestination(jndiTemplate.lookup(ON_NEW_PAYMENT, Queue.class));
		container.setMessageListener(listener);
		return container;
	}*/


}
