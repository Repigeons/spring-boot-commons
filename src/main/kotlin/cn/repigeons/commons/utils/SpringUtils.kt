package cn.repigeons.commons.utils

import org.springframework.context.ApplicationContext

/**
 * A holder of the Spring Boot Web Application Context
 */
object SpringUtils {
    lateinit var context: ApplicationContext

    /**
     * Get Spring Bean through the reified template class
     */
    inline fun <reified T> getBean(): T = context.getBean(T::class.java)

    /**
     * Get Spring Bean through the bean name and the reified template class
     */
    inline fun <reified T> getBean(name: String): T = context.getBean(name, T::class.java)
}
