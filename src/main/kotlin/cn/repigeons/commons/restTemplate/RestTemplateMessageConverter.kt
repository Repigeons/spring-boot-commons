package cn.repigeons.commons.restTemplate

import org.springframework.http.MediaType
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter

internal class RestTemplateMessageConverter : MappingJackson2HttpMessageConverter() {
    init {
        supportedMediaTypes = listOf(
            MediaType.APPLICATION_JSON,
            MediaType.TEXT_PLAIN
        )
    }
}
