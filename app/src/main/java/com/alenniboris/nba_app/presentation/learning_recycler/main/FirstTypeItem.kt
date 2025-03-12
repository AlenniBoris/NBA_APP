package com.alenniboris.nba_app.presentation.learning_recycler.main

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import com.alenniboris.nba_app.R
import com.alenniboris.nba_app.databinding.FirstTypeItemBinding
import com.alenniboris.nba_app.presentation.learning_recycler.model.FirstTypeModelUi

data class FirstTypeItem(
    private val item: FirstTypeModelUi,
    private val onText: (String, Int) -> Unit,
    private val cursor: Int,
    private val onClick: () -> Unit
) : BaseRecyclerView.BaseItem {
    override fun getItemViewType(): Int = R.layout.first_type_item

    override fun getItemsSame(): String = item.getModel().id.toString()

    override fun onBindViewHolder(view: View) {
        view.apply {



            val binding = FirstTypeItemBinding.bind(this)

            binding.firstTypeItemName.text = item.getModel().name

            binding.firstTypeItemText.apply {
                setText(item.editableText)
                setSelection(cursor)

                val textWatcher = object : TextWatcher {
                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                    override fun afterTextChanged(p0: Editable?) {
                        onText(
                            p0?.toString().orEmpty(),
                            binding.firstTypeItemText.selectionStart
                        )
                    }
                }
                this.setOnFocusChangeListener { view, isFocused ->
                    if (isFocused) {
                        addTextChangedListener(textWatcher)
                    } else {
                        removeTextChangedListener(textWatcher)
                    }
                }
            }

            binding.root.setOnClickListener {
                onClick()
            }
        }
    }

}
