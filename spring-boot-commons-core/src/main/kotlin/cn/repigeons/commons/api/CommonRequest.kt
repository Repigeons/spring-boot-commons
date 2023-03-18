package cn.repigeons.commons.api

typealias CommonRequest<T> = Map<String, T>

fun <T : Any> CommonRequest<T>.getNotNull(key: String) =
    requireNotNull(get(key)) { "Required body field '$key' is not present." }
