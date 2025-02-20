package com.alenniboris.nba_app.domain.usecase.authentication

import com.alenniboris.nba_app.domain.model.UserModelDomain
import kotlinx.coroutines.flow.StateFlow

interface IGetCurrentUserUseCase {

    val userFlow: StateFlow<UserModelDomain?>

}