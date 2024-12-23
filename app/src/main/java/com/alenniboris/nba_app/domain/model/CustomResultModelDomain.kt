package com.alenniboris.nba_app.domain.model

sealed class CustomResultModelDomain<out T>(
    open val result: T? = null,
    open val exception: CustomExceptionModelDomain? = null
) {

    data class Success<out T>(override val result: T) : CustomResultModelDomain<T>()

    data class Error<out T>(override val exception: CustomExceptionModelDomain) :
        CustomResultModelDomain<T>()

}