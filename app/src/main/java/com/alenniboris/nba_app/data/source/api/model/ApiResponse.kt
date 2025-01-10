package com.alenniboris.nba_app.data.source.api.model

open class ApiResponse<T>(
    open val getHeader: String? = null,
    open val queryParameters: Any? = null,
    open val responseErrors: Any? = null,
    open val resultsCount: String? = null,
    open val responseList: List<T?>? = null
) {
    open val isSomePropertyNotReceived: Boolean =
        false
}