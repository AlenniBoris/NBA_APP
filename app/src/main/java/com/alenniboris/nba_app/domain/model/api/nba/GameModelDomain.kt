package com.alenniboris.nba_app.domain.model.api.nba

import com.alenniboris.nba_app.domain.utils.GameStatus
import java.util.Calendar
import java.util.Date

data class GameModelDomain(
    override val id: Int = 0,
    override val isFollowed: Boolean = false,
    val dateOfTheGame: Date = Calendar.getInstance().time,
    val gameStage: String? = null,
    val weekOfTheGame: String? = null,
    val venueOfTheGame: String? = null,
    val gameStatus: GameStatus = GameStatus.NotDefined,
    val leagueId: Int = 0,
    val leagueName: String = "",
    val leagueType: String? = null,
    val leagueSeason: String? = null,
    val leagueLogoUrl: String? = null,
    val countryId: Int = 0,
    val countryName: String = "",
    val countryCode: String? = null,
    val countryFlagUrl: String? = null,
    val homeTeam: TeamModelDomain = TeamModelDomain(),
    val homeScores: ScoresModelDomain? = null,
    val visitorsTeam: TeamModelDomain = TeamModelDomain(),
    val visitorsScores: ScoresModelDomain? = null,
) : IStateModel