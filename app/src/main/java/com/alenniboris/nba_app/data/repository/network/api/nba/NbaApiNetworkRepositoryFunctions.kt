package com.alenniboris.nba_app.data.repository.network.api.nba

import android.util.Log
import com.alenniboris.nba_app.data.source.remote.api.nba.model.response.NbaApiResponse
import com.alenniboris.nba_app.domain.model.CustomResultModelDomain
import com.alenniboris.nba_app.domain.model.exception.NbaApiExceptionModelDomain
import com.alenniboris.nba_app.domain.utils.GsonUtil.toJson
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

object NbaApiNetworkRepositoryFunctions {

    suspend fun <T, E, V> getDataFromApi(
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
                Log.e("NbaApiRepositoryImpl", exception.stackTraceToString())
                CustomResultModelDomain.Error(NbaApiExceptionModelDomain.SomeUnknownExceptionOccurred)
            }
        }

}