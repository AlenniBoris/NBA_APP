package com.alenniboris.nba_app.data.model.api.nba.game

import android.util.Log
import com.alenniboris.nba_app.domain.model.api.nba.ScoresModelDomain
import com.google.gson.annotations.SerializedName

data class ScoresModelData(
    @SerializedName("quarter_1")
    val firstQuarterScore: String?,
    @SerializedName("quarter_2")
    val secondQuarterScore: String?,
    @SerializedName("quarter_3")
    val thirdQuarterScore: String?,
    @SerializedName("quarter_4")
    val fourthQuarterScore: String?,
    @SerializedName("over_time")
    val overtimeScore: String?,
    @SerializedName("total")
    val totalScore: String?,
)

fun ScoresModelData.toModelDomain(): ScoresModelDomain? = runCatching {
    ScoresModelDomain(
        firstQuarterScore = this.firstQuarterScore,
        secondQuarterScore = this.secondQuarterScore,
        thirdQuarterScore = this.thirdQuarterScore,
        fourthQuarterScore = this.fourthQuarterScore,
        overtimeScore = this.overtimeScore,
        totalScore = this.totalScore,
    )
}.getOrElse {
    Log.e("MappingError", "ScoresModelData error: \n ${it.stackTraceToString()}")
    null
}
