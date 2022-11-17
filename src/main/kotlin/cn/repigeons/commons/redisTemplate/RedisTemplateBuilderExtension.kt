package cn.repigeons.commons.redisTemplate

import org.springframework.data.redis.core.RedisTemplate

fun <T : Any> RedisTemplateBuilder<T>.build(): RedisTemplate<String, T> {
    val redisTemplate = RedisTemplate<String, T>()
    redisTemplate.connectionFactory = redisConnectionFactory
    redisTemplate.keySerializer = keySerializer
    redisTemplate.valueSerializer = valueSerializer
    redisTemplate.hashKeySerializer = keySerializer
    redisTemplate.hashValueSerializer = valueSerializer
    redisTemplate.afterPropertiesSet()
    return redisTemplate
}
