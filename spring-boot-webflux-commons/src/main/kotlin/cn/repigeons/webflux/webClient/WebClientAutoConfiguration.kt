package cn.repigeons.webflux.webClient

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient
import java.time.Duration

@Configuration
open class WebClientAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnClass(HttpClient::class)
    open fun reactorClientHttpConnector(): ReactorClientHttpConnector = HttpClient.create()
        .responseTimeout(Duration.ofSeconds(60))
        .let { ReactorClientHttpConnector(it) }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnClass(WebClient::class)
    open fun restTemplate(): WebClient = WebClient.builder()
        .clientConnector(reactorClientHttpConnector())
        .filter(RequestFilter)
        .build()
}