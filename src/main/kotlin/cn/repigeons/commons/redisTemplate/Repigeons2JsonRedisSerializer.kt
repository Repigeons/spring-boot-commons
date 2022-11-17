package cn.repigeons.commons.redisTemplate

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.PropertyAccessor
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.springframework.data.redis.serializer.RedisSerializer

class Repigeons2JsonRedisSerializer<T : Any> : RedisSerializer<T> {
    override fun serialize(t: T?): ByteArray {
        if (t == null) return ByteArray(0)
        val typeData = TypeData(t::class.java.typeName ?: Object::class.java.typeName, t)
        return objectMapper.writeValueAsBytes(typeData)
    }

    override fun deserialize(bytes: ByteArray?): T? {
        if (bytes == null || bytes.isEmpty()) return null
        val typeData = objectMapper.readValue<TypeData<T>>(bytes)
        val string = objectMapper.writeValueAsString(typeData.`@data`)
        if (typeData.`@type`.endsWith("[]")) {
            // Array
            val result = objectMapper.readValue(string, List::class.java)
            @Suppress("UNCHECKED_CAST")
            return result.toTypedArray() as T
        }
        val javaType = Class.forName(typeData.`@type`)
        @Suppress("UNCHECKED_CAST")
        return objectMapper.readValue(string, javaType) as T
    }

    private val objectMapper: ObjectMapper = ObjectMapper().registerKotlinModule()
        .setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY)

    private data class TypeData<T : Any>(
        val `@type`: String,
        val `@data`: T,
    )
}
