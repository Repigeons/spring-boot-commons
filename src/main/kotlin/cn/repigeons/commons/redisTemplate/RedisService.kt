package cn.repigeons.commons.redisTemplate

interface RedisService {
    operator fun get(key: String): Any?
    operator fun set(key: String, value: Any)
    fun set(key: String, value: Any, time: Long)
    fun del(vararg keys: String): Long
    fun hasKey(key: String): Boolean
    fun getExpire(key: String): Long?
    fun expire(key: String, time: Long): Boolean
    fun increment(key: String): Long?
    fun increment(key: String, delta: Long): Long?
    fun decrement(key: String): Long?
    fun decrement(key: String, delta: Long): Long?

    // Hash
    fun hGet(key: String, hashKey: String): Any?
    fun hSet(key: String, hashKey: String, value: Any)
    fun hGetAll(key: String): Map<String, Any>
    fun hSetAll(key: String, map: Map<String, Any>)
    fun hDel(key: String, vararg hashKey: String): Long
    fun hHasKey(key: String, hashKey: String): Boolean
    fun hKeys(key: String): Set<String>
    fun hIncrement(key: String, hashKey: String, delta: Long): Long?

    // List
    fun lPush(key: String, vararg value: Any): Long?
    fun rPush(key: String, vararg value: Any): Long?
    fun lPop(key: String): Any?
    fun rPop(key: String): Any?
    fun lSize(key: String): Long?
    fun lIndex(key: String, index: Long): Any?
    fun lRange(key: String, start: Long, end: Long): List<Any>?
    fun lRemove(key: String, count: Long, value: Any): Long?

    // Set
    fun sAdd(key: String, vararg value: Any): Long?
    fun sRemove(key: String, vararg value: Any): Long?
    fun sSize(key: String): Long?
    fun sMembers(key: String): Set<Any>?
}
