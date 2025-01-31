package com.alenniboris.nba_app.data.repository.network.api.nba

import com.alenniboris.nba_app.data.mappers.toNbaApiExceptionModelDomain
import com.alenniboris.nba_app.data.source.remote.api.nba.model.response.NbaApiResponse
import com.alenniboris.nba_app.domain.model.CustomResultModelDomain
import com.alenniboris.nba_app.domain.model.exception.NbaApiExceptionModelDomain
import com.alenniboris.nba_app.domain.utils.GsonUtil.toJson
import com.alenniboris.nba_app.domain.utils.LogPrinter
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

object NbaApiNetworkRepositoryFunctions {

    suspend fun <T, E, V> getElementsFromApi(
        apiCall: suspend () -> NbaApiResponse<T>,
        dispatcher: CoroutineDispatcher,
        transform: (List<T?>?) -> List<E>?,
        errorsParser: (String?) -> V?,
    ): CustomResultModelDomain<List<E>, NbaApiExceptionModelDomain> =
        withContext(dispatcher) {
            runCatching {
                val response = apiCall()

                if (response.isSomePropertyNotReceived) {
                    return@runCatching CustomResultModelDomain.Error(
                        NbaApiExceptionModelDomain.SomeUnknownExceptionOccurred
                    )
                }

                runCatching {
                    response.responseErrors?.toJson()?.let {
                        errorsParser(it)
                    }
                }.getOrNull()?.let {
                    return@runCatching CustomResultModelDomain.Error(
                        NbaApiExceptionModelDomain.SomeUnknownExceptionOccurred
                    )
                }

                val resultList = transform(response.responseList)
                    ?: return@runCatching CustomResultModelDomain.Error(
                        NbaApiExceptionModelDomain.SomeUnknownExceptionOccurred
                    )

                CustomResultModelDomain.Success<List<E>, NbaApiExceptionModelDomain>(resultList)

            }.getOrElse { exception ->
                LogPrinter.printLog("NbaApiRepositoryImpl", exception.stackTraceToString())
                CustomResultModelDomain.Error(exception.toNbaApiExceptionModelDomain())
            }
        }

    suspend fun <T, E, V> getElementFromApi(
        apiCall: suspend () -> NbaApiResponse<T>,
        dispatcher: CoroutineDispatcher,
        transform: (List<T?>?) -> E?,
        errorsParser: (String?) -> V?,
    ): CustomResultModelDomain<E, NbaApiExceptionModelDomain> =
        withContext(dispatcher) {
            runCatching {
                val response = apiCall()

                if (response.isSomePropertyNotReceived) {
                    return@runCatching CustomResultModelDomain.Error(
                        NbaApiExceptionModelDomain.SomeUnknownExceptionOccurred
                    )
                }

                runCatching {
                    response.responseErrors?.toJson()?.let {
                        errorsParser(it)
                    }
                }.getOrNull()?.let {
                    return@runCatching CustomResultModelDomain.Error(
                        NbaApiExceptionModelDomain.SomeUnknownExceptionOccurred
                    )
                }

                val element = transform(response.responseList)
                    ?: return@runCatching CustomResultModelDomain.Error(
                        NbaApiExceptionModelDomain.SomeUnknownExceptionOccurred
                    )

                CustomResultModelDomain.Success<E, NbaApiExceptionModelDomain>(element)

            }.getOrElse { exception ->
                LogPrinter.printLog("NbaApiRepositoryImpl", exception.stackTraceToString())
                CustomResultModelDomain.Error(exception.toNbaApiExceptionModelDomain())
            }
        }

}