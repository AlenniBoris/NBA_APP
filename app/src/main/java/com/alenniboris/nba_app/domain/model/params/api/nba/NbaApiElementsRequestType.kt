package com.alenniboris.nba_app.domain.model.params.api.nba

import com.alenniboris.nba_app.R

sealed interface INbaApiElementsRequestType

enum class NbaApiGameTypeElementsRequest : INbaApiElementsRequestType {
    GAMES_DATE,
    GAMES_SEASON_LEAGUE,
}

enum class NbaApiTeamTypeElementsRequest : INbaApiElementsRequestType {
    TEAMS_SEARCH,
    TEAMS_COUNTRY,
    TEAMS_SEASON_LEAGUE,
    TEAMS_SEARCH_SEASON_LEAGUE,
    TEAMS_SEARCH_SEASON_LEAGUE_COUNTRY
}

enum class NbaApiPlayerTypeElementsRequest : INbaApiElementsRequestType {
    PLAYER_SEARCH,
    PLAYER_SEASON_TEAM,
    PLAYER_SEASON_TEAM_SEARCH
}

fun INbaApiElementsRequestType.toRequestString(): Int = when (this) {
    NbaApiGameTypeElementsRequest.GAMES_DATE -> R.string.game_request_date
    NbaApiGameTypeElementsRequest.GAMES_SEASON_LEAGUE -> R.string.game_request_season_league
    NbaApiTeamTypeElementsRequest.TEAMS_SEARCH -> R.string.team_request_search
    NbaApiTeamTypeElementsRequest.TEAMS_COUNTRY -> R.string.team_request_country
    NbaApiTeamTypeElementsRequest.TEAMS_SEASON_LEAGUE -> R.string.team_request_season_league
    NbaApiTeamTypeElementsRequest.TEAMS_SEARCH_SEASON_LEAGUE -> R.string.team_request_search_season_league
    NbaApiTeamTypeElementsRequest.TEAMS_SEARCH_SEASON_LEAGUE_COUNTRY -> R.string.team_request_search_season_league_country
    NbaApiPlayerTypeElementsRequest.PLAYER_SEARCH -> R.string.player_request_search
    NbaApiPlayerTypeElementsRequest.PLAYER_SEASON_TEAM -> R.string.player_request_season_team
    NbaApiPlayerTypeElementsRequest.PLAYER_SEASON_TEAM_SEARCH -> R.string.player_request_season_team_search
}