package com.alenniboris.nba_app.di

import com.alenniboris.nba_app.data.repository.learning_recycler.LearningRecyclerRepositoryImpl
import com.alenniboris.nba_app.domain.model.IAppDispatchers
import com.alenniboris.nba_app.domain.model.learning_recycler.LRFirstTypeModelDomain
import com.alenniboris.nba_app.domain.repository.learning_recycler.ILearningRecyclerRepository
import com.alenniboris.nba_app.domain.usecase.impl.learning_recycler.GetLearningRecyclerDataUseCaseImpl
import com.alenniboris.nba_app.domain.usecase.learning_recycler.IGetLearningRecyclerDataUseCase
import com.alenniboris.nba_app.presentation.learning_recycler.details.LRDetailsScreenVM
import com.alenniboris.nba_app.presentation.learning_recycler.main.LRMainScreenVM
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val LearningRecyclerModule = module {
    single<ILearningRecyclerRepository> {
        LearningRecyclerRepositoryImpl(
            dispatchers = get<IAppDispatchers>()
        )
    }

    factory<IGetLearningRecyclerDataUseCase> {
        GetLearningRecyclerDataUseCaseImpl(
            dispatchers = get<IAppDispatchers>(),
            learningRecyclerRepository = get<ILearningRecyclerRepository>()
        )
    }

    viewModel<LRMainScreenVM> {
        LRMainScreenVM(
            getLearningRecyclerDataUseCase = get<IGetLearningRecyclerDataUseCase>()
        )
    }

    viewModel<LRDetailsScreenVM> { (element: LRFirstTypeModelDomain) ->
        LRDetailsScreenVM(
            element = element
        )
    }
}