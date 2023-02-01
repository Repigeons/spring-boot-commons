package cn.repigeons.commons.redisCache

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.cache.RedisCacheWriter
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.RedisSerializationContext

@Configuration
@EnableConfigurationProperties(RedisCacheProperties::class)
open class RedisCacheAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean(RedisTemplate::class)
    open fun redisCacheManager(
        redisTemplate: RedisTemplate<String, Any>,
        redisConnectionFactory: RedisConnectionFactory,
        redisCacheProperties: RedisCacheProperties,
    ): RedisCacheManager {
        val cacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory)
        val config = RedisCacheConfiguration.defaultCacheConfig()
            .prefixCacheNameWith("${redisCacheProperties.prefix}::")
            .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisTemplate.stringSerializer))
            .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(redisTemplate.valueSerializer))
            .disableCachingNullValues()
        return CustomRedisCacheManager(
            cacheWriter = cacheWriter,
            defaultCacheConfiguration = config,
            keys = redisCacheProperties.key,
            expires = redisCacheProperties.expire,
        )
    }
}