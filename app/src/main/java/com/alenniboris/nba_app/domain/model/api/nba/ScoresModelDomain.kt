package com.alenniboris.nba_app.domain.model.api.nba

data class ScoresModelDomain(
    val firstQuarterScore: String?,
    val secondQuarterScore: String?,
    val thirdQuarterScore: String?,
    val fourthQuarterScore: String?,
    val overtimeScore: String?,
    val totalScore: String?,
)
