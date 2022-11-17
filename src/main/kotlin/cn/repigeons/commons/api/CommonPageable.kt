package cn.repigeons.commons.api

import com.github.pagehelper.PageInfo
import org.springframework.data.domain.Page

/**
 * Common pageable data from `PageHelper` page info
 */
fun <T : Any, E> restPage(list: List<T>, pageInfo: PageInfo<E>) = CommonPageable.restPage(list, pageInfo)

/**
 * Common pageable data from `PageHelper` page info
 */
fun <T : Any> restPage(pageInfo: PageInfo<T>) = CommonPageable.restPage(pageInfo.list, pageInfo)

/**
 * Common pageable data from `SpringData` page info
 */
fun <T : Any, E> restPage(list: List<T>, pageInfo: Page<E>) = CommonPageable.restPage(list, pageInfo)

/**
 * Common pageable data from `SpringData` page info
 */
fun <T : Any> restPage(pageInfo: Page<T>) = CommonPageable.restPage(pageInfo.content, pageInfo)

/**
 * Common pageable data class from database query result set
 */
data class CommonPageable<T : Any>
internal constructor(
    val pageNum: Int,
    val pageSize: Int,
    val totalPage: Int,
    val total: Long,
    val list: List<T>
) {
    companion object {
        // PageHelper
        fun <T : Any, E> restPage(list: List<T>, pageInfo: PageInfo<E>) =
            CommonPageable(
                totalPage = pageInfo.pages,
                pageNum = pageInfo.pageNum,
                pageSize = pageInfo.pageSize,
                total = pageInfo.total,
                list = list
            )

        // SpringData
        fun <T : Any, E> restPage(list: List<T>, pageInfo: Page<E>) =
            CommonPageable(
                totalPage = pageInfo.totalPages,
                pageNum = pageInfo.number,
                pageSize = pageInfo.size,
                total = pageInfo.totalElements,
                list = list
            )
    }
}
