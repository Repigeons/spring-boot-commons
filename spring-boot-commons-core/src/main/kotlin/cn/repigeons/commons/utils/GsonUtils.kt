package cn.repigeons.commons.utils

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.io.Reader
import java.lang.reflect.Type

object GsonUtils {
    val gson: Gson = GsonBuilder()
        .serializeNulls()
        .enableComplexMapKeySerialization()
        .disableHtmlEscaping()
        .create()

    /**
     * Generate a json string from an object which is not null.
     */
    fun <T : Any> toJson(obj: T): String = gson.toJson(obj)

    /**
     * Generate an object by parsing a json string.
     */
    inline fun <reified T : Any> fromJson(json: String): T = gson.fromJson(json, object : TypeToken<T>() {}.type)
    fun <T : Any> fromJson(json: String, type: Type): T = gson.fromJson(json, type)

    /**
     * Generate an object by parsing a json string from a reader.
     */
    inline fun <reified T : Any> fromJson(reader: Reader): T = gson.fromJson(reader, object : TypeToken<T>() {}.type)
    fun <T : Any> fromJson(reader: Reader, type: Type): T = gson.fromJson(reader, type)
}