package com.alenniboris.nba_app.domain.usecase.impl.authentication

import com.alenniboris.nba_app.domain.model.UserModelDomain
import com.alenniboris.nba_app.domain.repository.authentication.IAuthenticationRepository
import com.alenniboris.nba_app.domain.usecase.authentication.IGetCurrentUserUseCase
import kotlinx.coroutines.flow.StateFlow

class GetCurrentUserUseCaseImpl(
    private val authenticationRepository: IAuthenticationRepository,
) : IGetCurrentUserUseCase {

    override val userFlow: StateFlow<UserModelDomain?> = authenticationRepository.user

}
