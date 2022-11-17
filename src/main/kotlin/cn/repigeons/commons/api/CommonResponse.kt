package cn.repigeons.commons.api

import org.springframework.http.HttpStatus

/**
 * Success with null-data
 */
fun success() = CommonResponse.success()

/**
 * Success with data
 */
fun <T : Any> success(data: T?) = CommonResponse.success(data)

/**
 * Success with data and custom-message
 */
fun <T : Any> success(data: T?, message: String) = CommonResponse.success(data, message)

/**
 * failed with default-message for unauthorized request
 */
fun unauthorized() = CommonResponse.unauthorized()

/**
 * failed with custom-message for unauthorized request
 */
fun unauthorized(message: String) = CommonResponse.unauthorized(message)

/**
 * failed with default-message for forbidden request
 */
fun forbidden() = CommonResponse.forbidden()

/**
 * failed with custom-message for forbidden request
 */
fun forbidden(message: String) = CommonResponse.forbidden(message)

/**
 * failed when handling the request
 */
fun failed() = CommonResponse.failed()

/**
 * failed with custom-message when handling the request
 */
fun failed(message: String) = CommonResponse.failed(message)

/**
 * Common response class for restful API
 */
data class CommonResponse<T : Any>
internal constructor(
    val status: Int,
    val message: String,
    val data: T?,
) {
    private constructor(status: HttpStatus) : this(status.value(), status.reasonPhrase, null)
    private constructor(status: HttpStatus, message: String) : this(status.value(), message, null)
    private constructor(status: HttpStatus, message: String, data: T?) : this(status.value(), message, data)
    private constructor(status: HttpStatus, data: T?) : this(status.value(), status.reasonPhrase, data)

    companion object {
        // success =200
        fun success() = CommonResponse<Any>(HttpStatus.OK)
        fun <T : Any> success(data: T?) = CommonResponse(HttpStatus.OK, data)
        fun <T : Any> success(data: T?, message: String) = CommonResponse(HttpStatus.OK, message, data)

        // unauthorized =401
        fun unauthorized() = CommonResponse<Any>(HttpStatus.UNAUTHORIZED)
        fun unauthorized(message: String) = CommonResponse<Any>(HttpStatus.UNAUTHORIZED, message)

        // forbidden =403
        fun forbidden() = CommonResponse<Any>(HttpStatus.FORBIDDEN)
        fun forbidden(message: String) = CommonResponse<Any>(HttpStatus.FORBIDDEN, message)

        // failed =500
        fun failed() = CommonResponse<Any>(HttpStatus.INTERNAL_SERVER_ERROR)
        fun failed(message: String) = CommonResponse<Any>(HttpStatus.INTERNAL_SERVER_ERROR, message)
    }
}
