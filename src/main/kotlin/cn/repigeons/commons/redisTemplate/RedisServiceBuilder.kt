package cn.repigeons.commons.redisTemplate

import org.springframework.data.redis.core.RedisTemplate

class RedisServiceBuilder {
    lateinit var redisTemplate: RedisTemplate<String, Any>
        private set

    fun redisTemplate(redisTemplate: RedisTemplate<String, Any>) =
        apply { this.redisTemplate = redisTemplate }

    fun build(): RedisService = RedisServiceImpl(redisTemplate)
}
