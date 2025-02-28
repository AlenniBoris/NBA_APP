package com.alenniboris.nba_app.presentation.learning_recycler.main

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


/** Ресайклер защищен от утечек памяти в методе {[onDetachedFromWindow]},
 * поэтому его адаптер можно объявлять в шапке фрагмента и ничего и нигде потом не занулять */
class BaseRecyclerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : RecyclerView(context, attrs) {

    init {
        if (layoutManager == null) {
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun onDetachedFromWindow() {
        adapter = null
        super.onDetachedFromWindow()
    }

    class BaseAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        var dataList = listOf<BaseItem>()
            private set

        @SuppressLint("NotifyDataSetChanged")
        fun update(newList: List<BaseItem>, isUseDiffUtil: Boolean = true) {
            if (isUseDiffUtil) {
                val diffResult = DiffUtil.calculateDiff(
                    BaseDiffUtil(dataList, newList)
                )
                dataList = newList
                diffResult.dispatchUpdatesTo(this)
            } else {
                dataList = newList
                notifyDataSetChanged()
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return object : RecyclerView.ViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(viewType, parent, false)
            ) {}
        }

        override fun getItemCount(): Int {
            return dataList.size
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            dataList[position].onBindViewHolder(holder.itemView)
        }

        override fun getItemViewType(position: Int): Int {
            return dataList[position].getItemViewType()
        }
    }

    private class BaseDiffUtil(
        private val oldList: List<BaseItem>,
        private val newList: List<BaseItem>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return newList[newItemPosition].areItemsTheSame() == oldList[oldItemPosition].areItemsTheSame()
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return newList[newItemPosition].areContentsTheSame() == oldList[oldItemPosition].areContentsTheSame()
        }
    }

    interface BaseItem {
        /** возвращаем ссылку на разметку R.layout.test_item */
        fun getItemViewType(): Int

        /** возвращает строку, по которой можно понять "это тот же объект, или другой?" */
        fun getItemsSame(): String

        /** метод для биндинга разметки  */
        fun onBindViewHolder(view: View)

        /** именно этот метод используется в {[BaseDiffUtil]} для понимания "это тот же объект, или другой?" */
        fun areItemsTheSame(): String = this.javaClass.name + getItemsSame()

        /** возвращает строку, по которой можно понять "изменился ли тот же самый объект?".
         * для идеальной работы наследников этого интерфейса лучше делать data-классами */
        fun areContentsTheSame(): String = this.toString()
    }

    /**
     *
     * data class MyDataItem(
     *     val item: MyData,
     *     val onCLik: () -> Unit
     * ) : BaseRecyclerView.BaseItem {
     *     override fun getItemViewType(): Int = R.layout.my_data_item
     *
     *     override fun getItemsSame(): String = item.id
     *
     *     override fun onBindViewHolder(view: View) {
     *         view.apply {
     *             val binding = MyDataItemBinding.bind(this)
     *
     *             binding.textView.text = item.title
     *             binding.textView2.text = item.content
     *             binding.root.setOnClickListener { onCLik() }
     *         }
     *     }
     * }
     *
     */

}