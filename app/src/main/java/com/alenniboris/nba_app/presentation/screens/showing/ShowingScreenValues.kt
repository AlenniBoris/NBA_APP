package com.alenniboris.nba_app.presentation.screens.showing

import com.alenniboris.nba_app.presentation.model.filter.SeasonFilterUiModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object ShowingScreenValues {

    enum class Category {
        Games,
        Teams,
        Players
    }

    enum class PersonalBtnAction {
        Details,
        Exit
    }

    private val currentYear = Calendar.getInstance()[Calendar.YEAR]

    val PossibleSeasons =
        (2008..currentYear)
            .mapIndexed { index, value ->
                SeasonFilterUiModel(index, value.toString())
            }.toList()

}