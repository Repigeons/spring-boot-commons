package cn.repigeons.web.restTemplate

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate
import java.time.Duration

@Configuration
open class RestTemplateAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnClass(RestTemplate::class)
    open fun restTemplate(): RestTemplate =
        RestTemplateBuilder()
            .connectTimeout(Duration.ofSeconds(15))
            .readTimeout(Duration.ofSeconds(120))
            .build()
}