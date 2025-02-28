package com.alenniboris.nba_app.data.source.remote.api.nba.model.response

open class NbaApiResponse<T>(
    open val getHeader: String? = null,
    open val queryParameters: Any? = null,
    open val responseErrors: Any? = null,
    open val resultsCount: String? = null,
    open val responseList: List<T?>? = null
) {
    open val isSomePropertyNotReceived: Boolean =
        false
}