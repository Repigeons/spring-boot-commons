package cn.repigeons.web.httpTrace

import cn.repigeons.web.extension.toMultiValueMap
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.util.ContentCachingRequestWrapper
import org.springframework.web.util.ContentCachingResponseWrapper
import org.springframework.web.util.WebUtils
import java.net.URI
import java.net.URISyntaxException
import java.nio.charset.StandardCharsets
import java.time.LocalDateTime

@Configuration
@ConditionalOnClass(HttpServletRequest::class)
open class ServletTraceLogFilter : OncePerRequestFilter() {
    private val log = LoggerFactory.getLogger(javaClass)
    override fun doFilterInternal(
        httpRequest: HttpServletRequest,
        httpResponse: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        var request = httpRequest
        var response = httpResponse
        val startTime = System.currentTimeMillis()
        if (!isRequestValid(request)) {
            filterChain.doFilter(request, response)
            return
        }
        if (request !is ContentCachingRequestWrapper) {
            request = ContentCachingRequestWrapper(request)
        }
        if (response !is ContentCachingResponseWrapper) {
            response = ContentCachingResponseWrapper(response)
        }
        var status = HttpStatus.INTERNAL_SERVER_ERROR.value()
        try {
            filterChain.doFilter(request, response)
            status = response.getStatus()
        } finally {
            val path = request.getRequestURI()
            if (!filterLog(request.getRequestURI())) {
                val traceLog = HttpTraceLog(
                    path = path,
                    parameterMap = request.parameterMap.toMultiValueMap(),
                    method = request.getMethod(),
                    timeTaken = System.currentTimeMillis() - startTime,
                    time = LocalDateTime.now().toString(),
                    status = status,
                    requestBody = getRequestBody(request).formatLog(),
                    responseBody = getResponseBody(response).formatLog(),
                )
                log.info("{}", traceLog)
            }
            updateResponse(response)
        }
    }

    private fun isRequestValid(request: HttpServletRequest): Boolean {
        return try {
            URI(request.requestURL.toString())
            true
        } catch (ex: URISyntaxException) {
            false
        }
    }

    private fun getRequestBody(request: HttpServletRequest): String {
        if (request.contentType?.let { MediaType.parseMediaType(it) } !in REQUEST_CONTENT_TYPE)
            return ""
        val wrapper = WebUtils.getNativeRequest(
            request,
            ContentCachingRequestWrapper::class.java
        )
        return if (wrapper != null) {
            String(wrapper.contentAsByteArray, StandardCharsets.UTF_8)
        } else ""
    }

    private fun getResponseBody(response: HttpServletResponse): String {
        if (response.contentType?.let { MediaType.parseMediaType(it) } !in RESPONSE_CONTENT_TYPE)
            return ""
        val wrapper = WebUtils.getNativeResponse(
            response,
            ContentCachingResponseWrapper::class.java
        )
        return if (wrapper != null) {
            String(wrapper.contentAsByteArray, StandardCharsets.UTF_8)
        } else ""
    }

    private fun updateResponse(response: HttpServletResponse) {
        val responseWrapper = WebUtils.getNativeResponse(
            response,
            ContentCachingResponseWrapper::class.java
        )
        responseWrapper?.copyBodyToResponse()
    }
}