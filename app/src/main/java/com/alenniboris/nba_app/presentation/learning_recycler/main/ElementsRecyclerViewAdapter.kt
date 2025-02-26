package com.alenniboris.nba_app.presentation.learning_recycler.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alenniboris.nba_app.databinding.ElementsRvItemBinding
import com.alenniboris.nba_app.domain.model.learning_recycler.LearningRecyclerDataModelDomain

class ElementsRecyclerViewAdapter(
    private val onClick: (LearningRecyclerDataModelDomain) -> Unit
) : RecyclerView.Adapter<ElementsRecyclerViewAdapter.ElementsRecyclerViewHolder>() {

    private var elements: List<LearningRecyclerDataModelDomain> = emptyList()

    class ElementsRecyclerViewHolder(
        private val binding: ElementsRvItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            element: LearningRecyclerDataModelDomain,
            onClick: (LearningRecyclerDataModelDomain) -> Unit
        ) {
            binding.tvItemName.text = element.name
            binding.tvItemText.text = element.text
            binding.elementItemLayout.setOnClickListener { onClick(element) }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ElementsRecyclerViewHolder {
        val binding = ElementsRvItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ElementsRecyclerViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return elements.size
    }

    override fun onBindViewHolder(holder: ElementsRecyclerViewHolder, position: Int) {
        val element = elements[position]
        holder.bind(
            element,
            onClick
        )
    }

    fun submitList(newItems: List<LearningRecyclerDataModelDomain>) {
        val diffUtil = ElementsDiffUtil(
            oldList = elements,
            newList = newItems
        )

        val result: DiffUtil.DiffResult = DiffUtil.calculateDiff(diffUtil)

        elements = newItems

        result.dispatchUpdatesTo(this)
    }

    class ElementsDiffUtil(
        private val oldList: List<LearningRecyclerDataModelDomain>,
        private val newList: List<LearningRecyclerDataModelDomain>,
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }

    }

}