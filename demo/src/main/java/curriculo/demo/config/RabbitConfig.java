package curriculo.demo.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;

@Configuration
public class RabbitConfig {

    @Value("${rabbitmq.exchange}")     String exchangeName;
    @Value("${rabbitmq.queue}")        String queueName;
    @Value("${rabbitmq.routing-key}")  String routingKey;

    @Bean
    public CachingConnectionFactory rabbitConnectionFactory(
            @Value("${rabbitmq.host}") String host,
            @Value("${rabbitmq.port}") int port,
            @Value("${rabbitmq.username}") String user,
            @Value("${rabbitmq.password}") String pass) {

        var cf = new CachingConnectionFactory(host, port);
        cf.setUsername(user);
        cf.setPassword(pass);
        return cf;
    }

    @Bean
    public TopicExchange candidaturasExchange() {
        return ExchangeBuilder.topicExchange(exchangeName).durable(true).build();
    }

    @Bean
    public Queue candidaturasQueue() {
        return QueueBuilder.durable(queueName).build();
    }

    @Bean
    public Binding candidaturasBinding(Queue q, TopicExchange ex) {
        return BindingBuilder.bind(q).to(ex).with(routingKey);
    }

    // Opcional: facilita enviar mensagens como JSON
    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    // Opcional: template para publicar mensagens
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory cf, Jackson2JsonMessageConverter converter) {
        RabbitTemplate template = new RabbitTemplate(cf);
        template.setMessageConverter(converter);
        return template;
    }
}
