package cn.repigeons.web.restTemplate

import org.springframework.http.client.BufferingClientHttpRequestFactory
import org.springframework.http.client.ClientHttpRequestInterceptor
import org.springframework.http.client.SimpleClientHttpRequestFactory
import org.springframework.http.converter.StringHttpMessageConverter
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter
import org.springframework.web.client.ResponseErrorHandler
import org.springframework.web.client.RestTemplate
import java.nio.charset.StandardCharsets
import java.time.Duration

class RestTemplateBuilder {
    private var connectTimeout: Duration = Duration.ofMillis(-1)
    fun connectTimeout(connectTimeout: Duration) =
        apply { this.connectTimeout = connectTimeout }

    private var readTimeout: Duration = Duration.ofMillis(-1)
    fun readTimeout(readTimeout: Duration) =
        apply { this.readTimeout = readTimeout }

    private var errorHandler: ResponseErrorHandler = RestTemplateThrowErrorHandler
    fun errorHandler(errorHandler: ResponseErrorHandler) =
        apply { this.errorHandler = errorHandler }

    private var interceptors: MutableList<ClientHttpRequestInterceptor> =
        mutableListOf(RestTemplateRequestInterceptor)

    fun addInterceptor(interceptor: ClientHttpRequestInterceptor) =
        apply { interceptors.add(interceptor) }

    fun interceptors(interceptors: MutableList<ClientHttpRequestInterceptor>) =
        apply { this.interceptors = interceptors }

    private var messageConverter: AbstractJackson2HttpMessageConverter = RestTemplateMessageConverter
    fun messageConverter(messageConverter: AbstractJackson2HttpMessageConverter) =
        apply { this.messageConverter = messageConverter }

    fun build(): RestTemplate {
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
}