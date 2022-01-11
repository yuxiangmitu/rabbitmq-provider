package com.example.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description 主题交换机 topic exchange
 * @Author cxl
 * @Date 2022-01-10
 */
@Configuration
public class TopicRabbitConfig {

    /**
     * 绑定键
     */
    public final static String MAN = "topic.man";
    public final static String WOMAN = "topic.woman";

    @Bean
    public Queue manQueue() {
        return new Queue(TopicRabbitConfig.MAN);
    }

    @Bean
    public Queue womanQueue() {
        return new Queue(TopicRabbitConfig.WOMAN);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange("topicExchange");
    }

    /**
     * 将firstQueue和topicExchange绑定,绑定的键值为topic.man
     * 只有消息携带的路由键是topic.man时,才会分发到该队列
     */
    @Bean
    Binding bindingExchangeMessage() {
        return BindingBuilder.bind(manQueue()).to(exchange()).with(MAN);
    }

    /**
     * 将secondQueue和topicExchange绑定,绑定的键值为用上通配路由键规则的topic.#
     * 只有消息携带的路由键是以topic.开头时,才会分发到该队列
     */
    @Bean
    Binding bindingExchangeMessage2() {
        return BindingBuilder.bind(womanQueue()).to(exchange()).with("topic.#");
    }

}
