package com.alenniboris.nba_app.presentation.screens.followed

import com.alenniboris.nba_app.domain.model.GameModelDomain
import com.alenniboris.nba_app.domain.model.PlayerModelDomain
import com.alenniboris.nba_app.domain.model.TeamModelDomain

data class FollowedState(
    val followedGames: List<GameModelDomain> = emptyList(),
    val followedTeams: List<TeamModelDomain> = emptyList(),
    val followedPlayers: List<PlayerModelDomain> = emptyList(),
)