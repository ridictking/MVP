package com.ng.emts.morecreditreceiver.config;

import com.ng.emts.morecreditreceiver.model.request.FormRequest;
import com.ng.emts.morecreditreceiver.service.processor.Processor;
import com.ng.emts.morecreditreceiver.service.rabbit.QueueConsumer;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    @Value("${com.ng.emts.receiver.queue.host}")
    private String hostname;
    @Value("${com.ng.emts.receiver.queue.username}")
    private String username;
    @Value("${com.ng.emts.receiver.queue.password}")
    private String password;
    @Value("${com.ng.emts.receiver.queue.name}")
    private static String queueName;
    @Value("${com.ng.emts.receiver.exchange.name}")
    private String exchange;

    @Bean
    public RabbitTemplate rabbitTemplate(){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        return  rabbitTemplate;
    }
    @Bean
    Queue queue(){
        return new Queue("INBOUND_RECEIVER_QUEUE", true);
    }

//    @Bean
//    TopicExchange topicExchange(){
//        return  new TopicExchange(exchange);
//    }
//
//    @Bean
//    Binding binding(){
//        return BindingBuilder.bind(queue()).to(topicExchange()).with(ROUTING_KEY)
//    }

    @Bean
    ConnectionFactory connectionFactory(){
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory(hostname);
        cachingConnectionFactory.setUsername(username);
        cachingConnectionFactory.setPassword(password);
        return cachingConnectionFactory;
    }
    @Bean
    SimpleMessageListenerContainer container(Processor request) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory());
        container.setQueues(queue());
        container.setMessageListener(new QueueConsumer(request));
        return container;
    }

//    @Bean
//    MessageListenerAdapter listenerAdapter(RabbitMqClient receiver) {
//        return new MessageListenerAdapter(receiver, "receiveMessage");
//    }
}
