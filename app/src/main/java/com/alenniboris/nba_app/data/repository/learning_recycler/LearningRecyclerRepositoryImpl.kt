package com.alenniboris.nba_app.data.repository.learning_recycler

import com.alenniboris.nba_app.data.mappers.toNbaApiExceptionModelDomain
import com.alenniboris.nba_app.domain.model.CustomResultModelDomain
import com.alenniboris.nba_app.domain.model.IAppDispatchers
import com.alenniboris.nba_app.domain.model.exception.NbaApiExceptionModelDomain
import com.alenniboris.nba_app.domain.model.learning_recycler.LearningRecyclerDataModelDomain
import com.alenniboris.nba_app.domain.repository.learning_recycler.ILearningRecyclerRepository
import com.alenniboris.nba_app.domain.utils.LogPrinter
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.util.UUID

class LearningRecyclerRepositoryImpl(
    private val dispatchers: IAppDispatchers
) : ILearningRecyclerRepository {
    override suspend fun getData():
            CustomResultModelDomain<List<LearningRecyclerDataModelDomain>, NbaApiExceptionModelDomain> =
        withContext(dispatchers.IO) {
            runCatching {
                val list = emptyList<LearningRecyclerDataModelDomain>().toMutableList()

                for (i in 0..100) {
                    delay(50)
                    list.add(
                        LearningRecyclerDataModelDomain(
                            id = i,
                            name = "BORIS, ind = $i",
                            text = UUID.randomUUID().toString()
                        )
                    )
                }

                CustomResultModelDomain.Success<List<LearningRecyclerDataModelDomain>, NbaApiExceptionModelDomain>(
                    list.toList()
                )
            }.getOrElse { exception ->
                LogPrinter.printLog("!!!", exception.stackTraceToString())
                CustomResultModelDomain.Error(exception.toNbaApiExceptionModelDomain())
            }
        }
}