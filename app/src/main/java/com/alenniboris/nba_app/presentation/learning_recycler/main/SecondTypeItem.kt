package com.alenniboris.nba_app.presentation.learning_recycler.main

import android.view.View
import com.alenniboris.nba_app.R
import com.alenniboris.nba_app.databinding.SecondTypeItemBinding
import com.alenniboris.nba_app.domain.model.learning_recycler.LRSecondTypeModelDomain

data class SecondTypeItem(
    val item: LRSecondTypeModelDomain,
    val onClick: (LRSecondTypeModelDomain) -> Unit
) : BaseRecyclerView.BaseItem {
    override fun getItemViewType(): Int = R.layout.second_type_item

    override fun getItemsSame(): String = item.id.toString()

    override fun onBindViewHolder(view: View) {
        view?.apply {
            val binding = SecondTypeItemBinding.bind(this)

            binding.secondTypeItemName.text = item.name
            binding.root.setOnClickListener {
                onClick(item)
            }
        }
    }

}