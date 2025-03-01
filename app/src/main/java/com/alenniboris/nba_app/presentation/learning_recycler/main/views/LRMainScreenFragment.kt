package com.alenniboris.nba_app.presentation.learning_recycler.main.views

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.alenniboris.nba_app.R
import com.alenniboris.nba_app.databinding.FragmentLrMainScreenBinding
import com.alenniboris.nba_app.databinding.SecondTypeDialogBinding
import com.alenniboris.nba_app.presentation.learning_recycler.collectFlow
import com.alenniboris.nba_app.presentation.learning_recycler.main.BaseRecyclerView
import com.alenniboris.nba_app.presentation.learning_recycler.main.FirstTypeItem
import com.alenniboris.nba_app.presentation.learning_recycler.main.LRMainScreenVM
import com.alenniboris.nba_app.presentation.learning_recycler.main.SecondTypeItem
import com.alenniboris.nba_app.presentation.learning_recycler.model.FirstTypeModelUi
import com.alenniboris.nba_app.presentation.learning_recycler.model.SecondTypeModelUi
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import org.koin.androidx.viewmodel.ext.android.viewModel

class LRMainScreenFragment : Fragment(R.layout.fragment_lr_main_screen) {

    private val binding by viewBinding(FragmentLrMainScreenBinding::bind)

    private val mainScreenVM by viewModel<LRMainScreenVM>()

    private val adapter: BaseRecyclerView.BaseAdapter = BaseRecyclerView.BaseAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.elementsRv.adapter = adapter

        collectFlow(
            mainScreenVM.state
                .map { it.isLoading }
                .distinctUntilChanged()
        ) { isLoading ->
            binding.elementsRv.isVisible = !isLoading
            binding.loadingIsActiveLayout.isVisible = isLoading
        }

        val cursorMap = mutableMapOf<Int, Int>()
        collectFlow(
            mainScreenVM.state
                .map { it.data }
                .distinctUntilChanged()
        ) { data ->
            adapter.update(
                data.map { element ->
                    when (element) {
                        is FirstTypeModelUi ->
                            FirstTypeItem(
                                item = element,
                                onClick = {
//                                    val detailsFragmentInstance =
//                                        LRDetailsScreenFragment.getInstance(element = it.getModel())
//                                    findNavController().navigate(
//                                        detailsFragmentInstance.first,
//                                        detailsFragmentInstance.second
//                                    )
                                },
                                onText = { newText, position ->
                                    cursorMap[element.getModel().id] = position
                                    mainScreenVM.changeElementText(
                                        element = element,
                                        newText = newText
                                    )
                                },
                                cursor = cursorMap[element.getModel().id] ?: 0
                            )

                        is SecondTypeModelUi ->
                            SecondTypeItem(
                                item = element,
                                onClick = {
                                    val dialogBuilder = AlertDialog.Builder(context)
                                    val dialogLayout: View =
                                        layoutInflater.inflate(R.layout.second_type_dialog, null)
                                    val dialogBinding = SecondTypeDialogBinding.bind(dialogLayout)
                                    dialogBuilder.setView(dialogBinding.root)
                                    val dialog = dialogBuilder.create()
                                    var newText = ""

                                    dialogBinding.dialogEditText.apply{
                                        setText(element.getModel().name)
                                        addTextChangedListener(object : TextWatcher{
                                            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                                            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                                            override fun afterTextChanged(p0: Editable?) {
                                                newText = p0?.toString().orEmpty()
                                            }

                                        })
                                    }

                                    dialogBinding.dialogApproveBtn.setOnClickListener {
                                        Log.e("!!!", newText)
                                        mainScreenVM.changeElementText(
                                            element = element,
                                            newText = newText
                                        )
                                        dialog.hide()
                                    }

                                    dialogBinding.dialogCancelBtn.setOnClickListener {
                                        dialog.hide()
                                    }

                                    dialog.show()
                                }
                            )

                        else -> throw IllegalArgumentException("Invalid type of element")
                    }
                }
            )
        }

    }
}