package com.alenniboris.nba_app.presentation.learning_recycler.main.views

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.alenniboris.nba_app.R
import com.alenniboris.nba_app.databinding.FragmentLrMainScreenBinding
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
        binding.elementsRv.itemAnimator = null

        val cursorMap = mutableMapOf<Int, Int>()
//        val dialogBuilder = AlertDialog.Builder(requireActivity())
//        val dialogLayout: View =
//            View.inflate(requireActivity(), R.layout.second_type_dialog, null)
//        val dialogBinding = SecondTypeDialogBinding.bind(dialogLayout)
//        dialogBuilder.setView(dialogBinding.root)
//        val dialog = dialogBuilder.create()
//
//        collectFlow(
//            mainScreenVM.state
//                .map { it.elementWithOpenedDialog }
//                .distinctUntilChanged()
//        ) { element ->
//            (element as? SecondTypeModelUi)?.let {
//                var newText = ""
//
//                dialog.setOnDismissListener {
//                    mainScreenVM.setElementWithOpenedDialog(null)
//                }
//
//                dialogBinding.dialogEditText.apply {
//                    setText(element.getModel().name)
//                    addTextChangedListener(
//                        afterTextChanged = { editable ->
//                            newText = editable?.toString().orEmpty()
//                        }
//                    )
//                }
//
//                dialogBinding.dialogApproveBtn.setOnClickListener {
//                    Log.e("!!!", newText)
//                    mainScreenVM.changeElementText(
//                        element = element,
//                        newText = newText
//                    )
//                    mainScreenVM.setElementWithOpenedDialog(null)
//                    dialog.hide()
//                }
//
//                dialogBinding.dialogCancelBtn.setOnClickListener {
//                    mainScreenVM.setElementWithOpenedDialog(null)
//                    dialog.hide()
//                }
//
//                dialog.show()
//            }
//        }

        val myDialog = binding.myAlertDialog
        collectFlow(
            mainScreenVM.state
                .map { it.elementWithOpenedDialog }
                .distinctUntilChanged()
        ) { element ->
            (element as? SecondTypeModelUi)?.let {

                myDialog.header = element.getModel().name
                myDialog.message = """
                    Message:
                    WELCOME BACK,  ${element.getModel().name.toUpperCase()} !!!
                    """.trimIndent()

                myDialog.approveButtonAction = {
                    Log.e("!!!", "Approved, name = ${element.getModel().name}")
                    mainScreenVM.setElementWithOpenedDialog(null)
                }

                myDialog.declineButtonAction = {
                    Log.e("!!!", "Declined, name = ${element.getModel().name}")
                    mainScreenVM.setElementWithOpenedDialog(null)
                }

            }
            myDialog.isVisible = element != null
        }

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
                                onClick = { mainScreenVM.setElementWithOpenedDialog(element) }
                            )

                        else -> throw IllegalArgumentException("Invalid type of element")
                    }
                }
            )
        }

    }
}