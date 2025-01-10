package com.alenniboris.nba_app.domain.model.params.api.nba


sealed interface INbaApiRequestParams {
    val requestType: INbaApiRequestType
    val possibleRequestTypes: List<INbaApiRequestType>
}