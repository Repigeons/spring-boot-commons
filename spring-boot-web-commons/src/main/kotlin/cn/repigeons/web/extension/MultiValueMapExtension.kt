package cn.repigeons.web.extension

import org.springframework.util.LinkedMultiValueMap

fun <K, V> Map<K, Array<V>>.toMultiValueMap() = LinkedMultiValueMap<K, V>().apply {
    this@toMultiValueMap.forEach { (key, value) -> addAll(key!!, value.asList()) }
}
