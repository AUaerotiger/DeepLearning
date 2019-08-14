package com.gdms.messaging;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

/**
 * Primary configuration for the JMS messaging for the Spirit application.
 *
 */
@Configuration
public class MessagingConfiguration {

    static final Logger LOG = LoggerFactory.getLogger(MessagingConfiguration.class);
    
    public static final String MY_LOCAL_FACTORY = "myLocalFactory";

    @Value("${messaging.local-url}")
    private String localUrl;

    /**
     * Gets the Local Connection Factory
     *
     * @return The connection factory
     */
    @Bean
    @Primary
    public ConnectionFactory localJmsConnectionFactory() {
        LOG.info("Creating Connection Factory from local url: " + this.localUrl);
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(this.localUrl);
        return connectionFactory;
    }

    /**
     * Gets a Local JMS Template
     *
     * @return The JMS Template
     */
    @Bean
    @Primary
    public JmsTemplate localJmsTemplate() {
        LOG.info("Creating Local JMS Template");
        JmsTemplate jmsTemplate = new JmsTemplate();
        jmsTemplate.setConnectionFactory(this.localJmsConnectionFactory());
        jmsTemplate.setMessageConverter(this.jacksonJmsMessageConverter());
        return jmsTemplate;
    }

    /**
     * Creates a JMS listener container factory using the local connection factory
     *
     * @param connectionFactory
     *            - provides appropriate connection for the container factory.
     * @param configurer
     *            - used to generate the container factory.
     * @return The JMS listener container factory
     *
     */
    @Bean
    @Primary
    public JmsListenerContainerFactory<?> myLocalFactory(ConnectionFactory connectionFactory,
            DefaultJmsListenerContainerFactoryConfigurer configurer) {
        LOG.info("Creating Local Listener Factory");
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        return factory;
    }

    /**
     * Serialize message content to json using TextMessage
     *
     * @return The message converter
     */
    @Bean
    @Primary
    public MessageConverter jacksonJmsMessageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName(MY_LOCAL_FACTORY);
        return converter;
    }

}
