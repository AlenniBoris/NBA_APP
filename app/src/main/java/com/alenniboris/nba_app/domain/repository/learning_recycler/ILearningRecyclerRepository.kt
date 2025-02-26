package com.alenniboris.nba_app.domain.repository.learning_recycler

import com.alenniboris.nba_app.domain.model.CustomResultModelDomain
import com.alenniboris.nba_app.domain.model.exception.NbaApiExceptionModelDomain
import com.alenniboris.nba_app.domain.model.learning_recycler.LearningRecyclerDataModelDomain

interface ILearningRecyclerRepository {
    suspend fun getData():
            CustomResultModelDomain<List<LearningRecyclerDataModelDomain>, NbaApiExceptionModelDomain>
}