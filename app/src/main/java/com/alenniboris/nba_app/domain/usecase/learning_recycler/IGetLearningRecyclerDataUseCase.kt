package com.alenniboris.nba_app.domain.usecase.learning_recycler

import com.alenniboris.nba_app.domain.model.CustomResultModelDomain
import com.alenniboris.nba_app.domain.model.exception.NbaApiExceptionModelDomain
import com.alenniboris.nba_app.domain.model.learning_recycler.LearningRecyclerDataModelDomain

interface IGetLearningRecyclerDataUseCase {
    suspend fun invoke():
            CustomResultModelDomain<List<LearningRecyclerDataModelDomain>, NbaApiExceptionModelDomain>
}