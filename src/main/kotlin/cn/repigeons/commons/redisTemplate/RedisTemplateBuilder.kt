package cn.repigeons.commons.redisTemplate

import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.serializer.RedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer

class RedisTemplateBuilder<T : Any> {
    lateinit var redisConnectionFactory: RedisConnectionFactory
        private set

    fun redisConnectionFactory(redisConnectionFactory: RedisConnectionFactory) =
        apply { this.redisConnectionFactory = redisConnectionFactory }

    var keySerializer: RedisSerializer<String> = StringRedisSerializer()
        private set

    fun keySerializer(keySerializer: RedisSerializer<String>) =
        apply { this.keySerializer = keySerializer }

    var valueSerializer: RedisSerializer<T> = Repigeons2JsonRedisSerializer()
        private set

    fun valueSerializer(valueSerializer: RedisSerializer<T>) =
        apply { this.valueSerializer = valueSerializer }
}
