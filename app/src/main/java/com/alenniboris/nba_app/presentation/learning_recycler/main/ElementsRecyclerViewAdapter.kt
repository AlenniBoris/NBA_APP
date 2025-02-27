package com.alenniboris.nba_app.presentation.learning_recycler.main

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alenniboris.nba_app.R
import com.alenniboris.nba_app.databinding.FirstTypeItemBinding
import com.alenniboris.nba_app.databinding.SecondTypeItemBinding
import com.alenniboris.nba_app.domain.model.learning_recycler.LRFirstTypeModelDomain
import com.alenniboris.nba_app.domain.model.learning_recycler.LRModelDomain
import com.alenniboris.nba_app.domain.model.learning_recycler.LRSecondTypeModelDomain

class ElementsRecyclerViewAdapter(
    private val onClick: (LRModelDomain) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var elements: List<LRModelDomain> = emptyList()
    private var selectedElements: MutableList<LRModelDomain> =
        emptyList<LRModelDomain>().toMutableList()

    class FirstTypeViewHolder(
        private val binding: FirstTypeItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            element: LRFirstTypeModelDomain,
            isSelected: Boolean,
            onClick: (LRFirstTypeModelDomain) -> Unit,
        ) {
            binding.firstTypeItemName.text = element.name
            binding.firstTypeItemText.text = element.text

            val backgroundShape =
                if (isSelected) R.drawable.type_item_shape_rounded else R.drawable.type_item_shape
            val backgroundColor = if (isSelected) Color.BLUE else Color.WHITE
            val textColor = if (isSelected) Color.WHITE else Color.BLACK

            val drawable =
                ContextCompat.getDrawable(binding.root.context, backgroundShape) as GradientDrawable
            drawable.setColor(backgroundColor)

            binding.root.background = drawable
            binding.firstTypeItemName.setTextColor(textColor)
            binding.firstTypeItemText.setTextColor(textColor)

            binding.root.setOnClickListener {
                onClick(element)
            }
        }
    }

    class SecondTypeViewHolder(
        private val binding: SecondTypeItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            element: LRSecondTypeModelDomain,
            isSelected: Boolean,
            onClick: (LRSecondTypeModelDomain) -> Unit
        ) {
            binding.secondTypeItemName.text = element.name

            val backgroundShape =
                if (isSelected) R.drawable.type_item_shape_rounded else R.drawable.type_item_shape
            val backgroundColor = if (isSelected) Color.RED else Color.GREEN
            val textColor = if (isSelected) Color.WHITE else Color.BLUE

            val drawable =
                ContextCompat.getDrawable(binding.root.context, backgroundShape) as GradientDrawable
            drawable.setColor(backgroundColor)

            binding.root.background = drawable
            binding.secondTypeItemName.setTextColor(textColor)

            binding.root.setOnClickListener {
                onClick(element)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            FIRST_TYPE_VIEW -> {
                FirstTypeViewHolder(
                    binding = FirstTypeItemBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                )
            }

            SECOND_TYPE_VIEW -> {
                SecondTypeViewHolder(
                    binding = SecondTypeItemBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                )
            }

            else -> throw IllegalArgumentException("Invalid type of view type $viewType")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (elements[position]) {
            is LRFirstTypeModelDomain -> FIRST_TYPE_VIEW
            is LRSecondTypeModelDomain -> SECOND_TYPE_VIEW
            else -> throw IllegalArgumentException("Invalid type of data $position")
        }
    }

    override fun getItemCount(): Int {
        return elements.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val element = elements[position]
        val isSelected = selectedElements.contains(element)
        when (holder) {
            is FirstTypeViewHolder -> {
                (element as? LRFirstTypeModelDomain)?.let {
                    holder.bind(
                        element = element,
                        isSelected = isSelected,
                        onClick = {
                            onClick(element)
                            notifyItemChanged(position)
                        }
                    )
                }
            }

            is SecondTypeViewHolder -> {
                (element as? LRSecondTypeModelDomain)?.let {
                    holder.bind(
                        element = element,
                        isSelected = isSelected,
                        onClick = {
                            onClick(element)
                            notifyItemChanged(position)
                        }
                    )
                }
            }
        }
    }

    fun submitElementsList(newItems: List<LRModelDomain>) {
        val diffUtil = ElementsDiffUtil(
            oldList = elements,
            newList = newItems
        )

        val result: DiffUtil.DiffResult = DiffUtil.calculateDiff(diffUtil)

        elements = newItems

        result.dispatchUpdatesTo(this)
    }

    fun updateSelectedElements(selected: List<LRModelDomain>) {
        selectedElements = selected.toMutableList()
    }

    class ElementsDiffUtil(
        private val oldList: List<LRModelDomain>,
        private val newList: List<LRModelDomain>,
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldValue = oldList[oldItemPosition]
            val newValue = newList[newItemPosition]

            return (oldValue is LRFirstTypeModelDomain && newValue is LRFirstTypeModelDomain) ||
                    (oldValue is LRSecondTypeModelDomain && newValue is LRSecondTypeModelDomain)
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }

    }

    private companion object {
        private const val FIRST_TYPE_VIEW = 0
        private const val SECOND_TYPE_VIEW = 1
    }

}