package cn.repigeons.commons.api

import org.springframework.http.HttpStatus

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
        @JvmStatic
        fun success() = CommonResponse<Nothing>(HttpStatus.OK)

        @JvmStatic
        fun <T : Any> success(data: T?) = CommonResponse(HttpStatus.OK, data)

        @JvmStatic
        fun <T : Any> success(data: T?, message: String) = CommonResponse(HttpStatus.OK, message, data)

        // unauthorized =401
        @JvmStatic
        fun unauthorized() = CommonResponse<Nothing>(HttpStatus.UNAUTHORIZED)

        @JvmStatic
        fun unauthorized(message: String) = CommonResponse<Nothing>(HttpStatus.UNAUTHORIZED, message)

        // forbidden =403
        @JvmStatic
        fun forbidden() = CommonResponse<Nothing>(HttpStatus.FORBIDDEN)

        @JvmStatic
        fun forbidden(message: String) = CommonResponse<Nothing>(HttpStatus.FORBIDDEN, message)

        // failed =500
        @JvmStatic
        fun failed() = CommonResponse<Nothing>(HttpStatus.INTERNAL_SERVER_ERROR)

        @JvmStatic
        fun failed(message: String) = CommonResponse<Nothing>(HttpStatus.INTERNAL_SERVER_ERROR, message)
    }
}