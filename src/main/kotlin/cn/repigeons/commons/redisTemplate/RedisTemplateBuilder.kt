package cn.repigeons.commons.redisTemplate

import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.RedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer

@Suppress("unused")
class RedisTemplateBuilder<T : Any> {
    private lateinit var redisConnectionFactory: RedisConnectionFactory

    @Suppress("unused")
    fun redisConnectionFactory(redisConnectionFactory: RedisConnectionFactory) =
        apply { this.redisConnectionFactory = redisConnectionFactory }

    private var keySerializer: RedisSerializer<String> = StringRedisSerializer()

    @Suppress("unused")
    fun keySerializer(keySerializer: RedisSerializer<String>) =
        apply { this.keySerializer = keySerializer }

    private var valueSerializer: RedisSerializer<T> = Repigeons2JsonRedisSerializer()

    @Suppress("unused")
    fun valueSerializer(valueSerializer: RedisSerializer<T>) =
        apply { this.valueSerializer = valueSerializer }

    @Suppress("unused")
    fun build(): RedisTemplate<String, T> {
        val redisTemplate = RedisTemplate<String, T>()
        redisTemplate.connectionFactory = redisConnectionFactory
        redisTemplate.keySerializer = keySerializer
        redisTemplate.valueSerializer = valueSerializer
        redisTemplate.hashKeySerializer = keySerializer
        redisTemplate.hashValueSerializer = valueSerializer
        redisTemplate.afterPropertiesSet()
        return redisTemplate
    }
}
