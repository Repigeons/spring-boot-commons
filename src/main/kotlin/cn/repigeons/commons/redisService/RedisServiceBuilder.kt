package cn.repigeons.commons.redisService

import org.springframework.data.redis.core.RedisTemplate

@Suppress("unused")
class RedisServiceBuilder {
    private lateinit var redisTemplate: RedisTemplate<String, Any>

    @Suppress("unused")
    fun redisTemplate(redisTemplate: RedisTemplate<String, Any>) =
        apply { this.redisTemplate = redisTemplate }

    @Suppress("unused")
    fun build(): RedisService = RedisServiceImpl(redisTemplate)
}
