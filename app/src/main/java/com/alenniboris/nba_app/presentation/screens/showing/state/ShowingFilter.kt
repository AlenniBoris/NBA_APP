package com.alenniboris.nba_app.presentation.screens.showing.state

import com.alenniboris.nba_app.domain.utils.EnumValues.GameStatus
import com.alenniboris.nba_app.presentation.model.filter.CountryFilterUiModel
import com.alenniboris.nba_app.presentation.model.filter.LeagueFilterUiModel
import com.alenniboris.nba_app.presentation.model.filter.SeasonFilterUiModel
import com.alenniboris.nba_app.presentation.screens.showing.ShowingScreenValues.PossibleSeasons
import com.alenniboris.nba_app.presentation.utils.PresentationValues
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

data class ShowingFilter(
    // emptyList() ?
    val listOfSeasons: List<SeasonFilterUiModel> = PossibleSeasons,
    val selectedSeason: SeasonFilterUiModel = SeasonFilterUiModel(),
    // emptyList()
    val listOfLeagues: List<LeagueFilterUiModel> = PresentationValues.PossibleLeaguesStub,
    val selectedLeague: LeagueFilterUiModel = LeagueFilterUiModel(),
    val listOfStatuses: List<GameStatus> =
        GameStatus.entries.toList(),
    val selectedStatus: GameStatus? = GameStatus.In_Play,
    // emptyList()
    val listOfCountries: List<CountryFilterUiModel> = PresentationValues.PossibleCountriesStub,
    val selectedCountry: CountryFilterUiModel = CountryFilterUiModel(),
    val enteredQuery: String = "",
    val selectedDate: Date = Calendar.getInstance().time
) {
    val selectedDateText: String =
        SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(selectedDate)

}
