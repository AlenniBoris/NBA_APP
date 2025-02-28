package com.alenniboris.nba_app.presentation.learning_recycler.main

import android.view.View
import com.alenniboris.nba_app.R
import com.alenniboris.nba_app.databinding.FirstTypeItemBinding
import com.alenniboris.nba_app.domain.model.learning_recycler.LRFirstTypeModelDomain

data class FirstTypeItem(
    val item: LRFirstTypeModelDomain,
    val onClick: (LRFirstTypeModelDomain) -> Unit
) : BaseRecyclerView.BaseItem {
    override fun getItemViewType(): Int = R.layout.first_type_item

    override fun getItemsSame(): String = item.id.toString()

    override fun onBindViewHolder(view: View) {
        view?.apply {
            val binding = FirstTypeItemBinding.bind(this)

            binding.firstTypeItemName.text = item.name
            binding.firstTypeItemText.text = item.text
            binding.root.setOnClickListener {
                onClick(item)
            }
        }
    }

}
