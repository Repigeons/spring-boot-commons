package cn.repigeons.commons.restTemplate

import org.springframework.http.client.BufferingClientHttpRequestFactory
import org.springframework.http.client.SimpleClientHttpRequestFactory
import org.springframework.http.converter.StringHttpMessageConverter
import org.springframework.web.client.RestTemplate
import java.nio.charset.StandardCharsets

fun RestTemplateBuilder.build(): RestTemplate {
    val factory = SimpleClientHttpRequestFactory().apply {
        setConnectTimeout(connectTimeout.toMillis().toInt())
        setReadTimeout(readTimeout.toMillis().toInt())
    }.let { simpleFactory ->
        BufferingClientHttpRequestFactory(simpleFactory)
    }
    val restTemplate = RestTemplate(factory)
    restTemplate.errorHandler = errorHandler
    restTemplate.interceptors = interceptors
    restTemplate.messageConverters[1] = StringHttpMessageConverter(StandardCharsets.UTF_8)
    restTemplate.messageConverters[2] = messageConverter
    return restTemplate
}
