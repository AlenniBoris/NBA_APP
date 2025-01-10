package com.alenniboris.nba_app.domain.model

import com.alenniboris.nba_app.domain.utils.GameStatus

data class GameModelDomain(
    override val id: Int,
    override val isFollowed: Boolean,
    val dateOfTheGame: String,
    val startingTime: String,
    val gameStage: String?,
    val weekOfTheGame: String?,
    val venueOfTheGame: String?,
    val gameStatus: GameStatus,
    val leagueId: Int,
    val leagueName: String,
    val leagueType: String?,
    val leagueSeason: String?,
    val leagueLogoUrl: String?,
    val countryId: Int,
    val countryName: String,
    val countryCode: String?,
    val countryFlagUrl: String?,
    val homeTeam: TeamModelDomain,
    val homeScores: ScoresModelDomain?,
    val visitorsTeam: TeamModelDomain,
    val visitorsScores: ScoresModelDomain?,
) : IStateModel

