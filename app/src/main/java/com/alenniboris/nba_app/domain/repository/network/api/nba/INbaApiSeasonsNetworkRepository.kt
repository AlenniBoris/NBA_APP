package com.alenniboris.nba_app.domain.repository.network.api.nba

import com.alenniboris.nba_app.domain.model.CustomResultModelDomain
import com.alenniboris.nba_app.domain.model.exception.NbaApiExceptionModelDomain
import com.alenniboris.nba_app.domain.model.filters.SeasonModelDomain

interface INbaApiSeasonsNetworkRepository {

    suspend fun getAllSeasons()
            : CustomResultModelDomain<List<SeasonModelDomain>, NbaApiExceptionModelDomain>

}