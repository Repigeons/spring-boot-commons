package cn.repigeons.commons.api

import com.github.pagehelper.PageHelper
import com.github.pagehelper.PageInfo
import org.springframework.data.domain.Page

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
    /**
     * PageHelper
     */
    constructor(list: List<T>, pageInfo: PageInfo<*>) : this(
        totalPage = pageInfo.pages,
        pageNum = pageInfo.pageNum,
        pageSize = pageInfo.pageSize,
        total = pageInfo.total,
        list = list
    )

    /**
     * SpringData
     */
    constructor(list: List<T>, pageInfo: Page<*>) : this(
        totalPage = pageInfo.totalPages,
        pageNum = pageInfo.number,
        pageSize = pageInfo.size,
        total = pageInfo.totalElements,
        list = list
    )

    companion object {
        /**
         * Construct By PageHelper page info
         */
        @JvmStatic
        fun <T : Any> restPage(pageInfo: PageInfo<T>) = CommonPageable(pageInfo.list, pageInfo)

        /**
         * Construct By SpringData page info
         */
        @JvmStatic
        fun <T : Any> restPage(pageInfo: Page<T>) = CommonPageable(pageInfo.content, pageInfo)

        /**
         * Transform the result to new list after querying, and construct a `CommonPageable` instance by this.
         */
        @JvmStatic
        fun <T : Any, R : Any> queryRestPage(
            page: Int, size: Int,
            transform: (R) -> T,
            query: () -> List<R>,
        ): CommonPageable<T> {
            val pageInfo: PageInfo<R> = PageHelper.startPage<R>(page, size)
                .doSelectPageInfo { query() }
            val list = pageInfo.list.map(transform)
            return CommonPageable(list, pageInfo)
        }

        /**
         * Query for a list, and construct a `CommonPageable` instance by this.
         */
        @JvmStatic
        fun <T : Any> queryRestPage(
            page: Int, size: Int,
            query: () -> List<T>,
        ) = queryRestPage(page, size, { it }, query)
    }
}