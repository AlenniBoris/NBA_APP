package com.alenniboris.nba_app.presentation.model

import com.alenniboris.nba_app.domain.utils.EnumValues.GameStatus

data class GameUiModel(
    override val id: Int = 0,
    override val isFollowed: Boolean = false,
    val date: String? = "",
    val time: String? = "",
    val timezone: String? = "",
    val status: GameStatus = GameStatus.In_Play,
    val leagueId: Int = 0,
    val leagueName: String? = "",
    val leagueSeason: String? = "",
    val leagueLogoUrl: String? = "",
    val countryId: Int = 0,
    val countryName: String? = "",
    val countryFlagUrl: String? = "",
    val homeTeam: TeamUiModel = TeamUiModel(),
    val homeScores: ScoresUiModel = ScoresUiModel(),
    val visitorsTeam: TeamUiModel = TeamUiModel(),
    val visitorsScores: ScoresUiModel = ScoresUiModel(),
) : IStateUiModel