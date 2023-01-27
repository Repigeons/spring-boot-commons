package cn.repigeons.commons.redisCache

import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.cache.RedisCacheWriter
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.RedisSerializationContext

@Suppress("unused")
class RedisCacheManagerBuilder {
    private lateinit var redisConnectionFactory: RedisConnectionFactory

    @Suppress("unused")
    fun redisConnectionFactory(redisConnectionFactory: RedisConnectionFactory) =
        apply { this.redisConnectionFactory = redisConnectionFactory }

    private lateinit var redisTemplate: RedisTemplate<String, Any>

    @Suppress("unused")
    fun redisTemplate(redisTemplate: RedisTemplate<String, Any>) =
        apply { this.redisTemplate = redisTemplate }

    @Suppress("unused")
    fun build(): RedisCacheManager {
        val cacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory)
        val config = RedisCacheConfiguration.defaultCacheConfig()
            .prefixCacheNameWith("${RedisProperties.prefix ?: "cache"}::")
            .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisTemplate.stringSerializer))
            .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(redisTemplate.valueSerializer))
            .disableCachingNullValues()
        return CustomRedisCacheManager(
            cacheWriter = cacheWriter,
            defaultCacheConfiguration = config,
            keys = RedisProperties.key,
            expires = RedisProperties.expire,
        )
    }
}