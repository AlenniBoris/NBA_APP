package com.alenniboris.nba_app.presentation.learning_recycler.model

import com.alenniboris.nba_app.domain.model.learning_recycler.LRFirstTypeModelDomain

data class FirstTypeModelUi private constructor(
    private val firstTypeModelDomain: LRFirstTypeModelDomain,
    var editableText: String = firstTypeModelDomain.text
): LRModelUi {
    override fun getModel() = firstTypeModelDomain.copy(
        text = editableText
    )

    companion object {
        fun LRFirstTypeModelDomain.toModelUi(): FirstTypeModelUi =
            FirstTypeModelUi(firstTypeModelDomain = this)
    }
}
