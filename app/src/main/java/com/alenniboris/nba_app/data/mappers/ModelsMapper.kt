package com.alenniboris.nba_app.data.mappers

import com.alenniboris.nba_app.domain.model.UserDomainModel
import com.google.firebase.auth.FirebaseUser

fun FirebaseUser.toUserDomainModel(): UserDomainModel = UserDomainModel()