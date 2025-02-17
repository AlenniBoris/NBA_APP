package com.alenniboris.nba_app.presentation.screens.showing.state

import com.alenniboris.nba_app.domain.model.api.nba.TeamModelDomain
import com.alenniboris.nba_app.domain.model.filters.CountryModelDomain
import com.alenniboris.nba_app.domain.model.filters.LeagueModelDomain
import com.alenniboris.nba_app.domain.model.filters.SeasonModelDomain
import com.alenniboris.nba_app.domain.utils.GameStatus
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

data class ShowingFilter(
    val listOfSeasons: List<SeasonModelDomain> = emptyList(),
    val selectedSeason: SeasonModelDomain? = null,
    val isSeasonsLoading: Boolean = false,
    val listOfLeagues: List<LeagueModelDomain> = emptyList(),
    val selectedLeague: LeagueModelDomain? = null,
    val isLeaguesLoading: Boolean = false,
    val listOfStatuses: List<GameStatus> =
        GameStatus.entries.toList().filter { it != GameStatus.NotDefined },
    val selectedStatus: GameStatus? = GameStatus.In_Play,
    val listOfCountries: List<CountryModelDomain> = emptyList(),
    val selectedCountry: CountryModelDomain? = null,
    val isCountriesLoading: Boolean = false,
    val listOfTeams: List<TeamModelDomain> = emptyList(),
    val selectedTeam: TeamModelDomain? = null,
    val isTeamsLoading: Boolean = false,
    val enteredQuery: String = "",
    val selectedDate: Date = Calendar.getInstance().time
) {

    val selectedDateText: String =
        SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(selectedDate)

}
