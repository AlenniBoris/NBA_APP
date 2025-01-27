package com.alenniboris.nba_app.domain.model.params.api.nba


sealed interface INbaApiElementsRequestParams {
    val elementsRequestType: INbaApiElementsRequestType
    val possibleElementsRequestTypes: List<INbaApiElementsRequestType>
}