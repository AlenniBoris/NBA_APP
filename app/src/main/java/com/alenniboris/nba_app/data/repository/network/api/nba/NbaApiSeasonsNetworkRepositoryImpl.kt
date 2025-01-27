package com.alenniboris.nba_app.data.repository.network.api.nba

import com.alenniboris.nba_app.data.source.remote.api.nba.INbaApiService
import com.alenniboris.nba_app.data.source.remote.api.nba.model.response.game.GamesResponseErrorsModelData
import com.alenniboris.nba_app.domain.model.CustomResultModelDomain
import com.alenniboris.nba_app.domain.model.IAppDispatchers
import com.alenniboris.nba_app.domain.model.exception.NbaApiExceptionModelDomain
import com.alenniboris.nba_app.domain.model.filters.SeasonModelDomain
import com.alenniboris.nba_app.domain.repository.network.api.nba.INbaApiSeasonsNetworkRepository
import com.alenniboris.nba_app.domain.utils.GsonUtil.fromJson

class NbaApiSeasonsNetworkRepositoryImpl(
    private val apiService: INbaApiService,
    private val dispatchers: IAppDispatchers
) : INbaApiSeasonsNetworkRepository {

    override suspend fun getAllSeasons()
            : CustomResultModelDomain<List<SeasonModelDomain>, NbaApiExceptionModelDomain> =
        NbaApiNetworkRepositoryFunctions.getDataFromApi(
            apiCall = {
                apiService.getAllSeasons()
            },
            dispatcher = dispatchers.IO,
            transform = { responseList ->
                responseList?.mapIndexedNotNull { index, value ->
                    SeasonModelDomain(
                        id = index,
                        name = value!!
                    )
                }
            },
            errorsParser = { json ->
                json?.fromJson<GamesResponseErrorsModelData>()
            }
        )

}