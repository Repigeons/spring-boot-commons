package cn.repigeons.cache.redis.redisCache

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import java.time.Duration

@Configuration
@ConfigurationProperties(prefix = "spring.cache.redis")
open class RedisCacheProperties {
    var keyPrefix: String = "\${spring.application.name}"
    val key: Map<String, String> = mutableMapOf()
    val expire: Map<String, Duration> = mutableMapOf()
}