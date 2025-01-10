package com.alenniboris.nba_app.domain.model.exception

sealed class NbaApiExceptionModelDomain : Throwable() {

    /* Thrown when some unpredicted situation occurred */
    data object SomeUnknownExceptionOccurred : NbaApiExceptionModelDomain()

    /* Thrown when season field of is needed to be filled to make request */
    data object LeagueAndSeasonFieldIsNeededToBeFilledBoth : NbaApiExceptionModelDomain()

    data object CountryIsNotSelectedForThisQuery : NbaApiExceptionModelDomain()

    data object SearchQuerySizeToSmall : NbaApiExceptionModelDomain()

    data object LeagueOrTeamIsNotChosen : NbaApiExceptionModelDomain()

}