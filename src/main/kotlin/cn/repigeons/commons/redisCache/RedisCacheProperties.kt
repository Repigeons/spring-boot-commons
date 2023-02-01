package cn.repigeons.commons.redisCache

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import java.time.Duration

@Configuration
@ConfigurationProperties(prefix = "redis")
open class RedisCacheProperties {
    var prefix: String = "spring-cache"
    val key: Map<String, String> = mutableMapOf()
    val expire: Map<String, Duration> = mutableMapOf()
}