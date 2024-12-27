package com.alenniboris.nba_app.presentation.utils

import com.alenniboris.nba_app.domain.utils.EnumValues
import com.alenniboris.nba_app.presentation.model.GameUiModel
import com.alenniboris.nba_app.presentation.model.PlayerUiModel
import com.alenniboris.nba_app.presentation.model.ScoresUiModel
import com.alenniboris.nba_app.presentation.model.TeamUiModel
import com.alenniboris.nba_app.presentation.model.filter.CountryFilterUiModel
import com.alenniboris.nba_app.presentation.model.filter.LeagueFilterUiModel

object PresentationValues {


    val PossibleLeaguesStub = listOf(
        LeagueFilterUiModel(1, "first"),
        LeagueFilterUiModel(2, "second"),
        LeagueFilterUiModel(3, "third"),
        LeagueFilterUiModel(4, "fourth"),
        LeagueFilterUiModel(5, "fifth"),
        LeagueFilterUiModel(6, "sixth"),
        LeagueFilterUiModel(7, "seventh"),
        LeagueFilterUiModel(8, "eighth"),
    )


    val PossibleCountriesStub = listOf(
        CountryFilterUiModel(1, "first"),
        CountryFilterUiModel(2, "second"),
        CountryFilterUiModel(3, "third"),
        CountryFilterUiModel(4, "fourth"),
        CountryFilterUiModel(5, "fifth"),
        CountryFilterUiModel(6, "sixth"),
        CountryFilterUiModel(7, "seventh"),
        CountryFilterUiModel(8, "eighth"),
    )

    val PossibleTeamsStub = listOf(
        TeamUiModel(
            id = 0,
            name = "Boris",
            logoUrl = "",
            national = false,
            country = CountryFilterUiModel(
                name = "boris",
                flag = "https://media.api-sports.io/basketball/teams/144.png"
            )
        ),
        TeamUiModel(
            id = 1,
            name = "Boris",
            logoUrl = "",
            national = false,
            country = CountryFilterUiModel(
                name = "boris",
                flag = "https://media.api-sports.io/basketball/teams/144.png"
            )
        ),
        TeamUiModel(
            id = 2,
            name = "Boris",
            logoUrl = "",
            national = false,
            country = CountryFilterUiModel(
                name = "boris",
                flag = "https://media.api-sports.io/basketball/teams/144.png"
            )
        ),
        TeamUiModel(
            id = 3,
            name = "Boris",
            logoUrl = "",
            national = false,
            country = CountryFilterUiModel(
                name = "boris",
                flag = "https://media.api-sports.io/basketball/teams/144.png"
            )
        ),
        TeamUiModel(
            id = 4,
            name = "Boris",
            logoUrl = "",
            national = false,
            country = CountryFilterUiModel(
                name = "boris",
            )
        ),
        TeamUiModel(
            id = 5,
            name = "Boris",
            logoUrl = "",
            national = false,
            country = CountryFilterUiModel(
                name = "boris",
            )
        ),
        TeamUiModel(
            id = 6,
            name = "Boris",
            logoUrl = "",
            national = false,
            country = CountryFilterUiModel(
                name = "boris",
            )
        ),
        TeamUiModel(
            id = 7,
            name = "Boris",
            logoUrl = "",
            national = false,
            country = CountryFilterUiModel(
                name = "boris",
            )
        ),
        TeamUiModel(
            id = 8,
            name = "Boris",
            logoUrl = "",
            national = false,
            country = CountryFilterUiModel(
                name = "boris",
            )
        ),
        TeamUiModel(
            id = 9,
            name = "Boris",
            logoUrl = "",
            national = false,
            country = CountryFilterUiModel(
                name = "boris",
            )
        ),

        )

    val PossiblePlayersStub = listOf(
        PlayerUiModel(
            id = 0,
            name = "Boris",
            number = "1",
            country = "Belarus",
            position = "Coach",
            age = 1
        ),
        PlayerUiModel(
            id = 0,
            name = "Boris",
            number = "1",
            country = "Belarus",
            position = "Coach",
            age = 1
        ),
        PlayerUiModel(
            id = 0,
            name = "Boris",
            number = "1",
            country = "Belarus",
            position = "Coach",
            age = 1
        ),
        PlayerUiModel(
            id = 0,
            name = "Boris",
            number = "1",
            country = "Belarus",
            position = "Coach",
            age = 1
        ),
        PlayerUiModel(
            id = 0,
            name = "Boris",
            number = "1",
            country = "Belarus",
            position = "Coach",
            age = 1
        ),
        PlayerUiModel(
            id = 0,
            name = "Boris",
            number = "1",
            country = "Belarus",
            position = "Coach",
            age = 1
        ),
        PlayerUiModel(
            id = 0,
            name = "Boris",
            number = "1",
            country = "Belarus",
            position = "Coach",
            age = 1
        ),
        PlayerUiModel(
            id = 0,
            name = "Boris",
            number = "1",
            country = "Belarus",
            position = "Coach",
            age = 1
        ),
        PlayerUiModel(
            id = 0,
            name = "Boris",
            number = "1",
            country = "Belarus",
            position = "Coach",
            age = 1
        ),
        PlayerUiModel(
            id = 0,
            name = "Boris",
            number = "1",
            country = "Belarus",
            position = "Coach",
            age = 1
        ),
        PlayerUiModel(
            id = 0,
            name = "Boris",
            number = "1",
            country = "Belarus",
            position = "Coach",
            age = 1
        ),
        PlayerUiModel(
            id = 0,
            name = "Boris",
            number = "1",
            country = "Belarus",
            position = "Coach",
            age = 1
        ),
        PlayerUiModel(
            id = 0,
            name = "Boris",
            number = "1",
            country = "Belarus",
            position = "Coach",
            age = 1
        ),
    )

    val PossibleGamesStub = listOf(
        GameUiModel(
            id = 0,
            date = "2023-11-22",
            time = "22:22",
            timezone = null,
            status = EnumValues.GameStatus.Cancelled,
            leagueId = 1,
            leagueName = "",
            leagueSeason = "",
            leagueLogoUrl = "",
            countryId = 0,
            countryName = "",
            countryFlagUrl = "",
            homeTeam = TeamUiModel(
                id = 0,
                name = "Los Angeles Clippers",
                logoUrl = "https://media.api-sports.io/basketball/teams/158.png"
            ),
            visitorsTeam = TeamUiModel(
                id = 0,
                name = "San Antonio Spurs",
                logoUrl = "https://media.api-sports.io/basketball/teams/144.png"
            ),
            homeScores = ScoresUiModel(
                firstQuarterScore = 31,
                secondQuarterScore = 32,
                thirdQuarterScore = 28,
                fourthQuarterScore = 31,
                totalScore = 122
            ),
            visitorsScores = ScoresUiModel(
                firstQuarterScore = 17,
                secondQuarterScore = 26,
                thirdQuarterScore = 20,
                fourthQuarterScore = 23,
                totalScore = 86
            ),
        ),
        GameUiModel(
            id = 0,
            date = "2023-11-22",
            time = "22:22",
            timezone = null,
            status = EnumValues.GameStatus.Cancelled,
            leagueId = 1,
            leagueName = "",
            leagueSeason = "",
            leagueLogoUrl = "",
            countryId = 0,
            countryName = "",
            countryFlagUrl = "",
            homeTeam = TeamUiModel(
                id = 0,
                name = "Los Angeles Clippers",
                logoUrl = "https://media.api-sports.io/basketball/teams/158.png"
            ),
            visitorsTeam = TeamUiModel(
                id = 0,
                name = "San Antonio Spurs",
                logoUrl = "https://media.api-sports.io/basketball/teams/144.png"
            ),
            homeScores = ScoresUiModel(
                firstQuarterScore = 31,
                secondQuarterScore = 32,
                thirdQuarterScore = 28,
                fourthQuarterScore = 31,
                totalScore = 122
            ),
            visitorsScores = ScoresUiModel(
                firstQuarterScore = 17,
                secondQuarterScore = 26,
                thirdQuarterScore = 20,
                fourthQuarterScore = 23,
                totalScore = 86
            ),
        ),
        GameUiModel(
            id = 0,
            date = "2023-11-22",
            time = "22:22",
            timezone = null,
            status = EnumValues.GameStatus.Cancelled,
            leagueId = 1,
            leagueName = "",
            leagueSeason = "",
            leagueLogoUrl = "",
            countryId = 0,
            countryName = "",
            countryFlagUrl = "",
            homeTeam = TeamUiModel(
                id = 0,
                name = "Los Angeles Clippers",
                logoUrl = "https://media.api-sports.io/basketball/teams/158.png"
            ),
            visitorsTeam = TeamUiModel(
                id = 0,
                name = "San Antonio Spurs",
                logoUrl = "https://media.api-sports.io/basketball/teams/144.png"
            ),
            homeScores = ScoresUiModel(
                firstQuarterScore = 31,
                secondQuarterScore = 32,
                thirdQuarterScore = 28,
                fourthQuarterScore = 31,
                totalScore = 122
            ),
            visitorsScores = ScoresUiModel(
                firstQuarterScore = 17,
                secondQuarterScore = 26,
                thirdQuarterScore = 20,
                fourthQuarterScore = 23,
                totalScore = 86
            ),
        ),
        GameUiModel(
            id = 0,
            date = "2023-11-22",
            time = "22:22",
            timezone = null,
            status = EnumValues.GameStatus.Cancelled,
            leagueId = 1,
            leagueName = "",
            leagueSeason = "",
            leagueLogoUrl = "",
            countryId = 0,
            countryName = "",
            countryFlagUrl = "",
            homeTeam = TeamUiModel(
                id = 0,
                name = "Los Angeles Clippers",
                logoUrl = "https://media.api-sports.io/basketball/teams/158.png"
            ),
            visitorsTeam = TeamUiModel(
                id = 0,
                name = "San Antonio Spurs",
                logoUrl = "https://media.api-sports.io/basketball/teams/144.png"
            ),
            homeScores = ScoresUiModel(
                firstQuarterScore = 31,
                secondQuarterScore = 32,
                thirdQuarterScore = 28,
                fourthQuarterScore = 31,
                totalScore = 122
            ),
            visitorsScores = ScoresUiModel(
                firstQuarterScore = 17,
                secondQuarterScore = 26,
                thirdQuarterScore = 20,
                fourthQuarterScore = 23,
                totalScore = 86
            ),
        ),
        GameUiModel(
            id = 0,
            date = "2023-11-22-12121212",
            time = "22:22",
            timezone = null,
            status = EnumValues.GameStatus.Cancelled,
            leagueId = 1,
            leagueName = "",
            leagueSeason = "",
            leagueLogoUrl = "",
            countryId = 0,
            countryName = "",
            countryFlagUrl = "",
            homeTeam = TeamUiModel(
                id = 0,
                name = "Los Angeles Clippers",
                logoUrl = "https://media.api-sports.io/basketball/teams/158.png"
            ),
            visitorsTeam = TeamUiModel(
                id = 0,
                name = "San Antonio Spurs",
                logoUrl = "https://media.api-sports.io/basketball/teams/144.png"
            ),
            homeScores = ScoresUiModel(
                firstQuarterScore = 31,
                secondQuarterScore = 32,
                thirdQuarterScore = 28,
                fourthQuarterScore = 31,
                totalScore = 122
            ),
            visitorsScores = ScoresUiModel(
                firstQuarterScore = 17,
                secondQuarterScore = 26,
                thirdQuarterScore = 20,
                fourthQuarterScore = 23,
                totalScore = 86
            ),
        ),
        GameUiModel(
            id = 0,
            date = null,
            time = "22:22",
            timezone = null,
            status = EnumValues.GameStatus.Cancelled,
            leagueId = 1,
            leagueName = "",
            leagueSeason = "",
            leagueLogoUrl = "",
            countryId = 0,
            countryName = "",
            countryFlagUrl = "",
            homeTeam = TeamUiModel(
                id = 0,
                name = "Los Angeles Clippers",
                logoUrl = "https://media.api-sports.io/basketball/teams/158.png"
            ),
            visitorsTeam = TeamUiModel(
                id = 0,
                name = "San Antonio Spurs",
                logoUrl = "https://media.api-sports.io/basketball/teams/144.png"
            ),
            homeScores = ScoresUiModel(
                firstQuarterScore = 31,
                secondQuarterScore = 32,
                thirdQuarterScore = 28,
                fourthQuarterScore = 31,
                totalScore = 122
            ),
            visitorsScores = ScoresUiModel(
                firstQuarterScore = 17,
                secondQuarterScore = 26,
                thirdQuarterScore = 20,
                fourthQuarterScore = 23,
                totalScore = 86
            ),
        ),
        GameUiModel(
            id = 0,
            date = null,
            time = "22:22",
            timezone = null,
            status = EnumValues.GameStatus.Cancelled,
            leagueId = 1,
            leagueName = "",
            leagueSeason = "",
            leagueLogoUrl = "",
            countryId = 0,
            countryName = "",
            countryFlagUrl = "",
            homeTeam = TeamUiModel(
                id = 0,
                name = "Los Angeles Clippers",
                logoUrl = "https://media.api-sports.io/basketball/teams/158.png"
            ),
            visitorsTeam = TeamUiModel(
                id = 0,
                name = "San Antonio Spurs",
                logoUrl = "https://media.api-sports.io/basketball/teams/144.png"
            ),
            homeScores = ScoresUiModel(
                firstQuarterScore = 31,
                secondQuarterScore = 32,
                thirdQuarterScore = 28,
                fourthQuarterScore = 31,
                totalScore = 122
            ),
            visitorsScores = ScoresUiModel(
                firstQuarterScore = 17,
                secondQuarterScore = 26,
                thirdQuarterScore = 20,
                fourthQuarterScore = 23,
                totalScore = 86
            ),
        )
    )
}