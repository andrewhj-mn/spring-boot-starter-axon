package org.springframework.boot.starter.axon;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.commandhandling.annotation.AnnotationCommandHandlerBeanPostProcessor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.commandhandling.gateway.CommandGatewayFactoryBean;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.SimpleEventBus;
import org.axonframework.eventhandling.annotation.AnnotationEventListenerBeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(CommandBus.class)
public class SimpleAxonConfiguration {

    @Bean
    public AnnotationEventListenerBeanPostProcessor annotationEventListenerBeanPostProcessor(EventBus eventBus) {
        AnnotationEventListenerBeanPostProcessor processor = new AnnotationEventListenerBeanPostProcessor();
        processor.setEventBus(eventBus);
        return processor;
    }

    @Bean
    public AnnotationCommandHandlerBeanPostProcessor annotationCommandHandlerBeanPostProcessor(CommandBus commandBus) {
        AnnotationCommandHandlerBeanPostProcessor processor = new AnnotationCommandHandlerBeanPostProcessor();
        processor.setCommandBus(commandBus);
        return processor;
    }


    @Bean
    @ConditionalOnMissingBean(CommandBus.class)
    public CommandBus commandBus() {
        SimpleCommandBus commandBus = new SimpleCommandBus();
//        commandBus.setHandlerInterceptors(Arrays.asList(new BeanValidationInterceptor()));

        return commandBus;
    }

    @Bean
    @ConditionalOnMissingBean(EventBus.class)
    public EventBus eventBus() {
        return new SimpleEventBus();
    }

    @Bean
    public CommandGatewayFactoryBean<CommandGateway> commandGatewayFactoryBean(CommandBus commandBus) {
        CommandGatewayFactoryBean<CommandGateway> commandGatewayFactoryBean = new CommandGatewayFactoryBean();
        commandGatewayFactoryBean.setCommandBus(commandBus);
        return commandGatewayFactoryBean;
    }

//    @Bean
//    public CommandGateway commandGateway(CommandBus commandBus) throws Exception {
//        return new DefaultCommandGateway(commandBus);
//    }
}
