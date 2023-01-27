package cn.repigeons.commons.redisService

import org.springframework.data.redis.core.RedisTemplate
import java.util.concurrent.TimeUnit

class RedisServiceImpl(
    private val redisTemplate: RedisTemplate<String, Any>
) : RedisService {
    override fun get(key: String): Any? =
        redisTemplate.opsForValue().get(key)

    override fun set(key: String, value: Any) =
        redisTemplate.opsForValue().set(key, value)

    override fun set(key: String, value: Any, time: Long) =
        redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS)

    override fun del(vararg keys: String): Long =
        redisTemplate.delete(keys.asList())

    override fun hasKey(key: String): Boolean =
        redisTemplate.hasKey(key)

    override fun getExpire(key: String): Long? =
        redisTemplate.getExpire(key, TimeUnit.SECONDS)

    override fun expire(key: String, time: Long): Boolean =
        redisTemplate.expire(key, time, TimeUnit.SECONDS)

    override fun increment(key: String): Long? =
        redisTemplate.opsForValue().increment(key)

    override fun increment(key: String, delta: Long): Long? =
        redisTemplate.opsForValue().increment(key, delta)

    override fun decrement(key: String): Long? =
        redisTemplate.opsForValue().decrement(key)

    override fun decrement(key: String, delta: Long): Long? =
        redisTemplate.opsForValue().decrement(key, delta)

    override fun hGet(key: String, hashKey: String): Any? =
        redisTemplate.opsForHash<String, Any>().get(key, hashKey)

    override fun hSet(key: String, hashKey: String, value: Any) =
        redisTemplate.opsForHash<String, Any>().put(key, hashKey, value)

    override fun hGetAll(key: String): Map<String, Any> =
        redisTemplate.opsForHash<String, Any>().entries(key)

    override fun hSetAll(key: String, map: Map<String, Any>) =
        redisTemplate.opsForHash<String, Any>().putAll(key, map)

    override fun hDel(key: String, vararg hashKey: String): Long =
        redisTemplate.opsForHash<String, Any>().delete(key, *hashKey)

    override fun hHasKey(key: String, hashKey: String): Boolean =
        redisTemplate.opsForHash<String, Any>().hasKey(key, hashKey)

    override fun hKeys(key: String): Set<String> =
        redisTemplate.opsForHash<String, Any>().keys(key)

    override fun hIncrement(key: String, hashKey: String, delta: Long): Long? =
        redisTemplate.opsForHash<String, Any>().increment(key, hashKey, delta)

    override fun lPush(key: String, vararg value: Any): Long? =
        redisTemplate.opsForList().leftPushAll(key, *value)

    override fun rPush(key: String, vararg value: Any): Long? =
        redisTemplate.opsForList().rightPushAll(key, *value)

    override fun lPop(key: String): Any? =
        redisTemplate.opsForList().leftPop(key)

    override fun rPop(key: String): Any? =
        redisTemplate.opsForList().rightPop(key)

    override fun lSize(key: String): Long? =
        redisTemplate.opsForList().size(key)

    override fun lIndex(key: String, index: Long): Any? =
        redisTemplate.opsForList().index(key, index)

    override fun lRange(key: String, start: Long, end: Long): List<Any>? =
        redisTemplate.opsForList().range(key, start, end)

    override fun lRemove(key: String, count: Long, value: Any): Long? =
        redisTemplate.opsForList().remove(key, count, value)

    override fun sAdd(key: String, vararg value: Any): Long? =
        redisTemplate.opsForSet().add(key, *value)

    override fun sRemove(key: String, vararg value: Any): Long? =
        redisTemplate.opsForSet().remove(key, *value)

    override fun sSize(key: String): Long? =
        redisTemplate.opsForSet().size(key)

    override fun sMembers(key: String): Set<Any>? =
        redisTemplate.opsForSet().members(key)
}
