package com.alenniboris.nba_app.domain.model.params.api.nba


enum class NbaApiGameRequestType : INbaApiRequestType {
    GAMES_DATE,
    GAMES_SEASON_LEAGUE,
    GAMES_DATE_SEASON_LEAGUE
}

enum class NbaApiTeamRequestType : INbaApiRequestType {
    TEAMS_SEARCH,
    TEAMS_COUNTRY,
    TEAMS_SEASON_LEAGUE,
    TEAMS_SEARCH_SEASON_LEAGUE,
    TEAMS_SEARCH_SEASON_LEAGUE_COUNTRY
}

enum class NbaApiPlayerRequestType : INbaApiRequestType {
    PLAYER_SEARCH,
    PLAYER_SEASON_TEAM,
    PLAYER_SEASON_TEAM_SEARCH
}
