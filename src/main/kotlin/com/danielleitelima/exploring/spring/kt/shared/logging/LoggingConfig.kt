package com.danielleitelima.exploring.spring.kt.shared.logging

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.InjectionPoint
import org.springframework.beans.factory.config.BeanDefinition
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope

@Configuration
class LoggingConfig {

    @Bean
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    fun logger(injectionPoint: InjectionPoint): Logger {
        val clazz = injectionPoint.methodParameter?.containingClass
            ?: injectionPoint.field?.declaringClass
            ?: Logger::class.java
        return LoggerFactory.getLogger(clazz)
    }
}
