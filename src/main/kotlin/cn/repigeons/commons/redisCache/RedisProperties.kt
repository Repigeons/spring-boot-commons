package cn.repigeons.commons.redisCache

import org.springframework.beans.factory.InitializingBean
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component
import java.time.Duration

@Component
@ConfigurationProperties(prefix = "redis")
class RedisProperties : InitializingBean {
    var prefix: String? = null
    val key = mutableMapOf<String, String>()
    val expire = mutableMapOf<String, Duration>()

    companion object {
        var prefix: String? = null
            private set
        val key = mutableMapOf<String, String>()
        val expire = mutableMapOf<String, Duration>()
    }

    override fun afterPropertiesSet() {
        Companion.prefix = this.prefix
        Companion.key.putAll(this.key)
        Companion.expire.putAll(this.expire)
    }
}