package com.alenniboris.nba_app.domain.usecase.impl.learning_recycler

import com.alenniboris.nba_app.domain.model.CustomResultModelDomain
import com.alenniboris.nba_app.domain.model.IAppDispatchers
import com.alenniboris.nba_app.domain.model.exception.NbaApiExceptionModelDomain
import com.alenniboris.nba_app.domain.model.learning_recycler.LearningRecyclerDataModelDomain
import com.alenniboris.nba_app.domain.repository.learning_recycler.ILearningRecyclerRepository
import com.alenniboris.nba_app.domain.usecase.learning_recycler.IGetLearningRecyclerDataUseCase
import kotlinx.coroutines.withContext

class GetLearningRecyclerDataUseCaseImpl(
    private val dispatchers: IAppDispatchers,
    private val learningRecyclerRepository: ILearningRecyclerRepository
) : IGetLearningRecyclerDataUseCase {
    override suspend fun invoke():
            CustomResultModelDomain<List<LearningRecyclerDataModelDomain>, NbaApiExceptionModelDomain> =
        withContext(dispatchers.IO) {
            return@withContext learningRecyclerRepository.getData()
        }
}