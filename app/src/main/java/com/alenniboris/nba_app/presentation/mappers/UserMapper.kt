package com.alenniboris.nba_app.presentation.mappers

import com.alenniboris.nba_app.domain.model.UserModelDomain
import com.alenniboris.nba_app.presentation.model.UserModelUi

fun UserModelDomain.toUserUiModel(): UserModelUi = UserModelUi()