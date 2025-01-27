package com.alenniboris.nba_app.presentation.screens.followed

import com.alenniboris.nba_app.domain.model.api.nba.GameModelDomain
import com.alenniboris.nba_app.domain.model.api.nba.PlayerModelDomain
import com.alenniboris.nba_app.domain.model.api.nba.TeamModelDomain

data class FollowedState(
    val followedGames: List<GameModelDomain> = emptyList(),
    val followedTeams: List<TeamModelDomain> = emptyList(),
    val followedPlayers: List<PlayerModelDomain> = emptyList(),
)