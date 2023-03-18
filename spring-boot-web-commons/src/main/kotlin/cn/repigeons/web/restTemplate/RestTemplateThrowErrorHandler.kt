package cn.repigeons.web.restTemplate

import org.slf4j.LoggerFactory
import org.springframework.http.client.ClientHttpResponse
import org.springframework.web.client.ResponseErrorHandler

internal object RestTemplateThrowErrorHandler : ResponseErrorHandler {
    private val logger = LoggerFactory.getLogger(javaClass)

    override fun hasError(response: ClientHttpResponse): Boolean {
        logger.debug("hasError:[{}]", response.statusText)
        return false
    }

    override fun handleError(response: ClientHttpResponse) {
        logger.debug("handleError:[{}]", response.statusText)
    }
}