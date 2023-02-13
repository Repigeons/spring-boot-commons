package cn.repigeons.commons.utils

import org.springframework.beans.factory.InitializingBean
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Configuration

/**
 * A holder of the Spring Boot Web Application Context
 */
@Configuration
open class SpringUtils(
    private val context: ApplicationContext
) : InitializingBean {
    companion object {
        lateinit var context: ApplicationContext private set

        /**
         * Get Spring Bean through the reified template class
         */
        inline fun <reified T> getBean(): T = context.getBean(T::class.java)

        /**
         * Get Spring Bean through the bean name and the reified template class
         */
        inline fun <reified T> getBean(name: String): T = context.getBean(name, T::class.java)

        /**
         * Get Spring Bean through the class type
         */
        fun <T : Any> getBean(type: Class<T>): T? = context.getBean(type)

        /**
         * Get Spring Bean through the bean name and the class type
         */
        fun <T : Any> getBean(name: String, type: Class<T>): T? = context.getBean(name, type)
    }

    override fun afterPropertiesSet() {
        Companion.context = context
    }
}