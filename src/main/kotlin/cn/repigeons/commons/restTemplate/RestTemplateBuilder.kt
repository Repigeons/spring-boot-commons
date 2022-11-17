package cn.repigeons.commons.restTemplate

import org.springframework.http.client.ClientHttpRequestInterceptor
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter
import org.springframework.web.client.ResponseErrorHandler
import java.time.Duration

class RestTemplateBuilder {
    var connectTimeout: Duration = Duration.ofMillis(-1)
        private set

    fun connectTimeout(connectTimeout: Duration) =
        apply { this.connectTimeout = connectTimeout }

    var readTimeout: Duration = Duration.ofMillis(-1)
        private set

    fun readTimeout(readTimeout: Duration) =
        apply { this.readTimeout = readTimeout }

    var errorHandler: ResponseErrorHandler = RestTemplateThrowErrorHandler()
        private set

    fun errorHandler(errorHandler: ResponseErrorHandler) =
        apply { this.errorHandler = errorHandler }

    var interceptors: MutableList<ClientHttpRequestInterceptor> = mutableListOf(
        RestTemplateRequestInterceptor()
    )
        private set

    fun addInterceptor(interceptor: ClientHttpRequestInterceptor) =
        apply { interceptors.add(interceptor) }

    fun interceptors(interceptors: MutableList<ClientHttpRequestInterceptor>) =
        apply { this.interceptors = interceptors }

    var messageConverter: AbstractJackson2HttpMessageConverter = RestTemplateMessageConverter()
        private set

    fun messageConverter(messageConverter: AbstractJackson2HttpMessageConverter) =
        apply { this.messageConverter = messageConverter }
}
