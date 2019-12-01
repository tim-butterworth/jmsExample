package com.example.exampleJms.jms.listener

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jms.annotation.EnableJms
import org.springframework.jms.listener.DefaultMessageListenerContainer
import org.springframework.jms.listener.SimpleMessageListenerContainer
import org.springframework.util.backoff.FixedBackOff

@EnableJms
@Configuration
class ListenerConfiguration {

    @Bean
    fun receiverActiveMQConnectionFactory(): ActiveMQConnectionFactory {
        return ActiveMQConnectionFactory("tcp://localhost:61616")
    }

//    @Bean
//    fun jmsListenerContainerFactory(): SimpleJmsListenerContainerFactory {
//        val factory = SimpleJmsListenerContainerFactory()
//        factory.setConnectionFactory(receiverActiveMQConnectionFactory())

//        return factory
//    }

    @Bean
    fun jmsListenerContainerFactory(
            connectionFactory: ActiveMQConnectionFactory,
            consumer: JmsConsumer
    ): DefaultMessageListenerContainer {
        val container = DefaultMessageListenerContainer()
        container.connectionFactory = connectionFactory

        container.destinationName = "exampleQueue"
        container.setConcurrency("3-10")
        container.setReceiveTimeout(5000)
        container.setBackOff(FixedBackOff(3000, FixedBackOff.UNLIMITED_ATTEMPTS))

        container.messageListener = consumer

        return container
    }

    @Bean
    fun listener(): JmsConsumer = JmsConsumer()

}