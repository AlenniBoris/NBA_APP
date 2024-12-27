package com.alenniboris.nba_app.domain.utils

object EnumValues {

    enum class GameStatus {
        In_Play,
        Not_Started,
        Finished,
        Postponed,
        Cancelled,
        Suspended,
        Awarded,
        Abandoned
    }

}