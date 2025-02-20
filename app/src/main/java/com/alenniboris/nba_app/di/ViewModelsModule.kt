package com.alenniboris.nba_app.di

import com.alenniboris.nba_app.domain.usecase.authentication.IGetCurrentUserUseCase
import com.alenniboris.nba_app.domain.usecase.authentication.ILoginUserUseCase
import com.alenniboris.nba_app.domain.usecase.authentication.IRegisterUserUseCase
import com.alenniboris.nba_app.domain.usecase.authentication.ISignOutUserUseCase
import com.alenniboris.nba_app.domain.usecase.countries.IGetCountriesUseCase
import com.alenniboris.nba_app.domain.usecase.game.IGetFollowedGamesUseCase
import com.alenniboris.nba_app.domain.usecase.game.IGetGameDataAndStatisticsByIdUseCase
import com.alenniboris.nba_app.domain.usecase.game.IGetGamesByDateUseCase
import com.alenniboris.nba_app.domain.usecase.game.IGetGamesBySeasonAndLeagueUseCase
import com.alenniboris.nba_app.domain.usecase.game.IUpdateGameIsFollowedUseCase
import com.alenniboris.nba_app.domain.usecase.leagues.IGetLeaguesByCountryUseCase
import com.alenniboris.nba_app.domain.usecase.player.IGetFollowedPlayersUseCase
import com.alenniboris.nba_app.domain.usecase.player.IGetPlayerDataByIdUseCase
import com.alenniboris.nba_app.domain.usecase.player.IGetPlayerStatisticsInSeasonUseCase
import com.alenniboris.nba_app.domain.usecase.player.IGetPlayersByQuerySeasonTeamUseCase
import com.alenniboris.nba_app.domain.usecase.player.IGetPlayersByQueryUseCase
import com.alenniboris.nba_app.domain.usecase.player.IGetPlayersBySeasonTeamUseCase
import com.alenniboris.nba_app.domain.usecase.player.IGetPlayersForTeamInSeasonUseCase
import com.alenniboris.nba_app.domain.usecase.player.IUpdatePlayerIsFollowedUseCase
import com.alenniboris.nba_app.domain.usecase.seasons.IGetSeasonsUseCase
import com.alenniboris.nba_app.domain.usecase.team.IGetFollowedTeamsUseCase
import com.alenniboris.nba_app.domain.usecase.team.IGetTeamStatisticsUseCase
import com.alenniboris.nba_app.domain.usecase.team.IGetTeamsByCountryUseCase
import com.alenniboris.nba_app.domain.usecase.team.IGetTeamsByQuerySeasonLeagueCountryUseCase
import com.alenniboris.nba_app.domain.usecase.team.IGetTeamsByQuerySeasonLeagueUseCase
import com.alenniboris.nba_app.domain.usecase.team.IGetTeamsByQueryUseCase
import com.alenniboris.nba_app.domain.usecase.team.IGetTeamsBySeasonAndLeagueUseCase
import com.alenniboris.nba_app.domain.usecase.team.IReloadDataForTeamAndLoadLeaguesUseCase
import com.alenniboris.nba_app.domain.usecase.team.IUpdateTeamIsFollowedUseCase
import com.alenniboris.nba_app.presentation.activity.MainActivityVM
import com.alenniboris.nba_app.presentation.screens.details.game.GameDetailsScreenVM
import com.alenniboris.nba_app.presentation.screens.details.player.PlayerDetailsScreenVM
import com.alenniboris.nba_app.presentation.screens.details.team.TeamDetailsScreenVM
import com.alenniboris.nba_app.presentation.screens.enter.EnterScreenVM
import com.alenniboris.nba_app.presentation.screens.followed.FollowedScreenVM
import com.alenniboris.nba_app.presentation.screens.showing.ShowingScreenVM
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val ViewModels = module {
    viewModel<EnterScreenVM> {
        EnterScreenVM(
            loginUserUseCase = get<ILoginUserUseCase>(),
            registerUserUseCase = get<IRegisterUserUseCase>()
        )
    }

    viewModel<MainActivityVM> {
        MainActivityVM(
            getCurrentUserUseCase = get<IGetCurrentUserUseCase>()
        )
    }

    viewModel<ShowingScreenVM> {
        ShowingScreenVM(
            signOutUserUseCase = get<ISignOutUserUseCase>(),
            getFollowedGamesUseCase = get<IGetFollowedGamesUseCase>(),
            getFollowedPlayersUseCase = get<IGetFollowedPlayersUseCase>(),
            getFollowedTeamsUseCase = get<IGetFollowedTeamsUseCase>(),
            updateGameIsFollowedUseCase = get<IUpdateGameIsFollowedUseCase>(),
            updateTeamIsFollowedUseCase = get<IUpdateTeamIsFollowedUseCase>(),
            updatePlayerIsFollowedUseCase = get<IUpdatePlayerIsFollowedUseCase>(),
            getSeasonsUseCase = get<IGetSeasonsUseCase>(),
            getCountriesUseCase = get<IGetCountriesUseCase>(),
            getGamesByDateUseCase = get<IGetGamesByDateUseCase>(),
            getGamesBySeasonAndLeague = get<IGetGamesBySeasonAndLeagueUseCase>(),
            getPlayersByQueryUseCase = get<IGetPlayersByQueryUseCase>(),
            getPlayersBySeasonAndTeamUseCase = get<IGetPlayersBySeasonTeamUseCase>(),
            getPlayersBySeasonAndTeamAndQueryUseCase = get<IGetPlayersByQuerySeasonTeamUseCase>(),
            getTeamsByCountryUseCase = get<IGetTeamsByCountryUseCase>(),
            getTeamsByQueryAndSeasonAndLeagueAndCountryUseCase = get<IGetTeamsByQuerySeasonLeagueCountryUseCase>(),
            getTeamsByQueryAndSeasonAndLeagueUseCase = get<IGetTeamsByQuerySeasonLeagueUseCase>(),
            getTeamsByQueryUseCase = get<IGetTeamsByQueryUseCase>(),
            getTeamsBySeasonAndLeagueUseCase = get<IGetTeamsBySeasonAndLeagueUseCase>(),
            getLeaguesByCountryUseCase = get<IGetLeaguesByCountryUseCase>()
        )
    }

    viewModel<FollowedScreenVM> {
        FollowedScreenVM(
            getFollowedGamesUseCase = get<IGetFollowedGamesUseCase>(),
            getFollowedPlayersUseCase = get<IGetFollowedPlayersUseCase>(),
            getFollowedTeamsUseCase = get<IGetFollowedTeamsUseCase>(),
            updateGameIsFollowedUseCase = get<IUpdateGameIsFollowedUseCase>(),
            updateTeamIsFollowedUseCase = get<IUpdateTeamIsFollowedUseCase>(),
            updatePlayerIsFollowedUseCase = get<IUpdatePlayerIsFollowedUseCase>()
        )
    }

    viewModel<GameDetailsScreenVM> { (gameId: Int) ->
        GameDetailsScreenVM(
            getFollowedGamesUseCase = get<IGetFollowedGamesUseCase>(),
            getGameDataAndStatisticsByIdUseCase = get<IGetGameDataAndStatisticsByIdUseCase>(),
            updateGameIsFollowedUseCase = get<IUpdateGameIsFollowedUseCase>(),
            gameId = gameId
        )
    }

    viewModel<PlayerDetailsScreenVM> { (playerId: Int) ->
        PlayerDetailsScreenVM(
            getFollowedPlayersUseCase = get<IGetFollowedPlayersUseCase>(),
            getPlayerDataByIdUseCase = get<IGetPlayerDataByIdUseCase>(),
            getSeasonsUseCase = get<IGetSeasonsUseCase>(),
            updatePlayerIsFollowedUseCase = get<IUpdatePlayerIsFollowedUseCase>(),
            getPlayerStatisticsInSeasonUseCase = get<IGetPlayerStatisticsInSeasonUseCase>(),
            playerId = playerId
        )
    }

    viewModel<TeamDetailsScreenVM> { (teamId: Int) ->
        TeamDetailsScreenVM(
            teamId = teamId,
            getFollowedTeamsUseCase = get<IGetFollowedTeamsUseCase>(),
            getSeasonsUseCase = get<IGetSeasonsUseCase>(),
            getPlayersForTeamInSeasonUseCase = get<IGetPlayersForTeamInSeasonUseCase>(),
            reloadDataForTeamAndLoadLeaguesUseCase = get<IReloadDataForTeamAndLoadLeaguesUseCase>(),
            updateTeamIsFollowedUseCase = get<IUpdateTeamIsFollowedUseCase>(),
            getTeamStatisticsUseCase = get<IGetTeamStatisticsUseCase>()
        )
    }
}