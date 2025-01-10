package com.alenniboris.nba_app.data.mappers

import com.alenniboris.nba_app.domain.model.UserModelDomain
import com.google.firebase.auth.FirebaseUser

fun FirebaseUser.toUserDomainModel(): UserModelDomain = UserModelDomain()