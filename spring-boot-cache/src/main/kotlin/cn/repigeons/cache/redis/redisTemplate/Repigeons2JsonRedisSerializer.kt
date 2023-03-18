package cn.repigeons.cache.redis.redisTemplate

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.PropertyAccessor
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.google.gson.annotations.SerializedName
import org.springframework.data.redis.serializer.RedisSerializer

class Repigeons2JsonRedisSerializer<T : Any> : RedisSerializer<T> {
    override fun serialize(t: T?): ByteArray {
        if (t == null) return ByteArray(0)
        val typeData = serializeObject(t)
        return objectMapper.writeValueAsBytes(typeData)
    }

    override fun deserialize(bytes: ByteArray?): T? {
        if (bytes == null || bytes.isEmpty()) return null
        val typeData = objectMapper.readValue<TypeData<*>>(bytes)
        @Suppress("UNCHECKED_CAST")
        return deserializeObject(typeData) as T
    }

    private val objectMapper: ObjectMapper = ObjectMapper().registerKotlinModule()
        .setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY)

    private data class TypeData<V : Any>(
        @JsonProperty("@type")
        @SerializedName("@type")
        val type: String,
        @JsonProperty("@data")
        @SerializedName("@data")
        val data: V?,
    )

    private fun serializeObject(obj: Any): TypeData<*> {
        val clazz = obj.javaClass
        if (clazz.isArray) {
            return TypeData(
                type = clazz.typeName,
                data = obj,
            )
        }
        if (obj is Collection<*>) {
            val data = obj.map {
                it?.let { serializeObject(it) }
            }
            return TypeData(
                type = clazz.typeName,
                data = data,
            )
        }
        if (obj is Map<*, *>) {
            val data = obj.map { (k, v) ->
                k.toString() to v?.let { serializeObject(v) }
            }
            return TypeData(
                type = clazz.typeName,
                data = mapOf(*data.toTypedArray())
            )
        }
        return TypeData(
            type = clazz.typeName,
            data = obj,
        )
    }

    private fun deserializeObject(obj: Any?) = when (obj) {
        is TypeData<*> -> deserializeTypeData(obj)
        is Map<*, *> -> {
            if (obj.keys == setOf("@type", "@data")) {
                deserializeTypeData(transfer(obj, TypeData::class.java))
            } else {
                obj
            }
        }

        else -> obj
    }

    private fun deserializeTypeData(typeData: TypeData<*>): Any? {
        val (type, data) = typeData
        if (data == null) return null
        when (type) {
            "char[]" -> (data as String).toCharArray()
            "byte[]" -> (data as List<*>).let { list -> ByteArray(list.size) { list[it].toString().toByte() } }
            "short[]" -> (data as List<*>).let { list -> ShortArray(list.size) { list[it].toString().toShort() } }
            "int[]" -> (data as List<*>).let { list -> IntArray(list.size) { list[it].toString().toInt() } }
            "long[]" -> (data as List<*>).let { list -> LongArray(list.size) { list[it].toString().toLong() } }
            "float[]" -> (data as List<*>).let { list -> FloatArray(list.size) { list[it].toString().toFloat() } }
            "double[]" -> (data as List<*>).let { list -> DoubleArray(list.size) { list[it].toString().toDouble() } }
            "boolean[]" -> (data as List<*>).let { list -> BooleanArray(list.size) { list[it].toString().toBoolean() } }
            else -> null
        }?.run { return this }
        if (type.endsWith("[]")) {
            val clazz = Class.forName(type.substringBeforeLast("[]"))
            return (data as List<*>).map { item ->
                item?.let { transfer(item, clazz) }
            }.toTypedArray()
        }
        val clazz = Class.forName(type)
        if (List::class.java.isAssignableFrom(clazz)) {
            data as Collection<*>
            return data.map { deserializeObject(it) }.toMutableList()
        }
        if (Set::class.java.isAssignableFrom(clazz)) {
            data as Collection<*>
            return data.map { deserializeObject(it) }.toMutableSet()
        }
        if (Map::class.java.isAssignableFrom(clazz)) {
            data as Map<*, *>
            val pairs = data.map { (key, value) ->
                key to deserializeObject(value)
            }
            return mutableMapOf(*pairs.toTypedArray())
        }
        return transfer(data, clazz)
    }

    private fun <T : Any> transfer(src: Any, type: Class<T>): T {
        val string = objectMapper.writeValueAsString(src)
        return objectMapper.readValue(string, type)
    }
}
