package com.alenniboris.nba_app.domain.model.params.api.nba

import com.alenniboris.nba_app.R

sealed interface INbaApiRequestType

fun INbaApiRequestType.toRequestString() : Int = when(this){
    NbaApiGameRequestType.GAMES_DATE -> R.string.game_request_date
    NbaApiGameRequestType.GAMES_SEASON_LEAGUE -> R.string.game_request_season_league
    NbaApiGameRequestType.GAMES_DATE_SEASON_LEAGUE -> R.string.game_request_date_season_league
    NbaApiTeamRequestType.TEAMS_SEARCH -> R.string.team_request_search
    NbaApiTeamRequestType.TEAMS_COUNTRY -> R.string.team_request_country
    NbaApiTeamRequestType.TEAMS_SEASON_LEAGUE -> R.string.team_request_season_league
    NbaApiTeamRequestType.TEAMS_SEARCH_SEASON_LEAGUE -> R.string.team_request_search_season_league
    NbaApiTeamRequestType.TEAMS_SEARCH_SEASON_LEAGUE_COUNTRY -> R.string.team_request_search_season_league_country
    NbaApiPlayerRequestType.PLAYER_SEARCH -> R.string.player_request_search
    NbaApiPlayerRequestType.PLAYER_SEASON_TEAM -> R.string.player_request_season_team
    NbaApiPlayerRequestType.PLAYER_SEASON_TEAM_SEARCH -> R.string.player_request_season_team_search
}