package com.alenniboris.nba_app.presentation.learning_recycler.model

import com.alenniboris.nba_app.domain.model.learning_recycler.LRSecondTypeModelDomain

class SecondTypeModelUi private constructor(
    private val secondTypeModelDomain: LRSecondTypeModelDomain,
) : LRModelUi {
    override fun getModel() = secondTypeModelDomain

    companion object {
        fun LRSecondTypeModelDomain.toModelUi(): SecondTypeModelUi =
            SecondTypeModelUi(secondTypeModelDomain = this)
    }
}
