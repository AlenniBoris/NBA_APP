package com.alenniboris.nba_app.presentation.learning_recycler.main.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.Navigation.findNavController
import com.alenniboris.nba_app.presentation.learning_recycler.details.views.LRDetailsScreenFragment
import com.alenniboris.nba_app.presentation.learning_recycler.main.LRMainScreenVM
import com.alenniboris.nba_app.presentation.learning_recycler.model.FirstTypeModelUi
import com.alenniboris.nba_app.presentation.learning_recycler.model.SecondTypeModelUi
import com.alenniboris.nba_app.presentation.uikit.views.AppProgressBar
import org.koin.androidx.viewmodel.ext.android.viewModel

//import android.os.Bundle
//import android.util.Log
//import android.view.Menu
//import android.view.MenuInflater
//import android.view.MenuItem
//import android.view.View
//import androidx.activity.addCallback
//import androidx.activity.compose.setContent
//import androidx.core.view.MenuProvider
//import androidx.core.view.isVisible
//import androidx.fragment.app.Fragment
//import androidx.navigation.fragment.findNavController
//import by.kirich1409.viewbindingdelegate.viewBinding
//import com.alenniboris.nba_app.R
//import com.alenniboris.nba_app.databinding.FragmentLrMainScreenBinding
//import com.alenniboris.nba_app.presentation.learning_recycler.collectFlow
//import com.alenniboris.nba_app.presentation.learning_recycler.details.views.LRDetailsScreenFragment
//import com.alenniboris.nba_app.presentation.learning_recycler.main.BaseRecyclerView
//import com.alenniboris.nba_app.presentation.learning_recycler.main.FirstTypeItem
//import com.alenniboris.nba_app.presentation.learning_recycler.main.LRMainScreenVM
//import com.alenniboris.nba_app.presentation.learning_recycler.main.SecondTypeItem
//import com.alenniboris.nba_app.presentation.learning_recycler.model.FirstTypeModelUi
//import com.alenniboris.nba_app.presentation.learning_recycler.model.SecondTypeModelUi
//import kotlinx.coroutines.flow.distinctUntilChanged
//import kotlinx.coroutines.flow.map
//import org.koin.androidx.viewmodel.ext.android.viewModel
//
class LRMainScreenFragment : Fragment() {

    private val mainScreenVM by viewModel<LRMainScreenVM>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(context = requireActivity()).apply {
            setContent {
                A()
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun A() {
        val state by mainScreenVM.state.collectAsStateWithLifecycle()

        if (state.isLoading) {
            AppProgressBar(
                modifier = Modifier.fillMaxSize()
            )
        }
        else if (state.elementWithOpenedDialog != null){
            val element = state.elementWithOpenedDialog
            BasicAlertDialog(
                onDismissRequest = { mainScreenVM.setElementWithOpenedDialog(null) },
                modifier = Modifier.fillMaxSize(),
            ) {
                when (element) {
                    is FirstTypeModelUi -> {
                        Column(modifier = Modifier.size(100.dp)) {
                            Text(text = element.getModel().name)
                            Text(text = element.getModel().text)
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Button(
                                    onClick = {
                                        mainScreenVM.setElementWithOpenedDialog(null)
                                    }
                                ) {
                                    Text(text = "NO")
                                }
                                Button(
                                    onClick = {
                                        mainScreenVM.setElementWithOpenedDialog(null)
                                    }
                                ) {
                                    Text(text = "YES")
                                }
                            }
                        }
                    }

                    is SecondTypeModelUi -> {
                        Column(modifier = Modifier.size(100.dp)) {
                            Text(text = element.getModel().name)
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Button(
                                    onClick = {
                                        mainScreenVM.setElementWithOpenedDialog(null)
                                    }
                                ) {
                                    Text(text = "NO")
                                }
                                Button(
                                    onClick = {
                                        mainScreenVM.setElementWithOpenedDialog(null)
                                    }
                                ) {
                                    Text(text = "YES")
                                }
                            }
                        }
                    }
                }
            }
        }
        else {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(state.data) { element ->
                    when (element) {
                        is FirstTypeModelUi -> {
                            Card(
                                modifier = Modifier
                                    .padding(vertical = 10.dp, horizontal = 5.dp)
                                    .fillMaxWidth()
                                    .height(50.dp),
                                onClick = {
                                    val detailsFragmentInstance =
                                        LRDetailsScreenFragment.getInstance(element = element.getModel())
                                    findNavController(requireView()).navigate(
                                        detailsFragmentInstance.first,
                                        detailsFragmentInstance.second
                                    )
                                },
                                content = {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .background(Color.Black)
                                    ) {
                                        Text(text = element.getModel().name, color = Color.White)
                                        Text(text = element.getModel().text, color = Color.White)
                                    }
                                }
                            )
                        }

                        is SecondTypeModelUi -> {
                            Card(
                                modifier = Modifier
                                    .padding(vertical = 10.dp, horizontal = 5.dp)
                                    .fillMaxWidth()
                                    .padding(5.dp),
                                onClick = {
                                    mainScreenVM.setElementWithOpenedDialog(element)
                                },
                                content = {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .background(Color.Blue)
                                    ) {
                                        Text(text = element.getModel().name, color = Color.Black)
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }

}
//
//    private val binding by viewBinding(FragmentLrMainScreenBinding::bind)
//
//    private val mainScreenVM by viewModel<LRMainScreenVM>()
//
//    private val adapter: BaseRecyclerView.BaseAdapter = BaseRecyclerView.BaseAdapter()
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        binding.elementsRv.adapter = adapter
//        binding.elementsRv.itemAnimator = null
//
//        requireActivity().setContent {
//
//        }
//
//        requireActivity().addMenuProvider(object : MenuProvider{
//            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
//                menuInflater.inflate(R.menu.toolbar_menu, menu)
//            }
//
//            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
//                when (menuItem.itemId) {
//                    R.id.firstMenuItem -> {
//                        Log.e("!!!", "first menu item")
//                    }
//
//                    R.id.secondMenuItem -> {
//                        Log.e("!!!", "second menu item")
//                    }
//                }
//                return true
//            }
//        }, viewLifecycleOwner)
//
//        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
//            Log.e("!!!", "pRESSED BACK")
//        }
//
//        val cursorMap = mutableMapOf<Int, Int>()
////        val dialogBuilder = AlertDialog.Builder(requireActivity())
////        val dialogLayout: View =
////            View.inflate(requireActivity(), R.layout.second_type_dialog, null)
////        val dialogBinding = SecondTypeDialogBinding.bind(dialogLayout)
////        dialogBuilder.setView(dialogBinding.root)
////        val dialog = dialogBuilder.create()
////
////        collectFlow(
////            mainScreenVM.state
////                .map { it.elementWithOpenedDialog }
////                .distinctUntilChanged()
////        ) { element ->
////            (element as? SecondTypeModelUi)?.let {
////                var newText = ""
////
////                dialog.setOnDismissListener {
////                    mainScreenVM.setElementWithOpenedDialog(null)
////                }
////
////                dialogBinding.dialogEditText.apply {
////                    setText(element.getModel().name)
////                    addTextChangedListener(
////                        afterTextChanged = { editable ->
////                            newText = editable?.toString().orEmpty()
////                        }
////                    )
////                }
////
////                dialogBinding.dialogApproveBtn.setOnClickListener {
////                    Log.e("!!!", newText)
////                    mainScreenVM.changeElementText(
////                        element = element,
////                        newText = newText
////                    )
////                    mainScreenVM.setElementWithOpenedDialog(null)
////                    dialog.hide()
////                }
////
////                dialogBinding.dialogCancelBtn.setOnClickListener {
////                    mainScreenVM.setElementWithOpenedDialog(null)
////                    dialog.hide()
////                }
////
////                dialog.show()
////            }
////        }
//
//        val myDialog = binding.myAlertDialog
//        collectFlow(
//            mainScreenVM.state
//                .map { it.elementWithOpenedDialog }
//                .distinctUntilChanged()
//        ) { element ->
//            (element as? SecondTypeModelUi)?.let {
//
//                myDialog.header = element.getModel().name
//                myDialog.message = """
//                    Message:
//                    WELCOME BACK,  ${element.getModel().name.toUpperCase()} !!!
//                    """.trimIndent()
//
//                myDialog.approveButtonAction = {
//                    Log.e("!!!", "Approved, name = ${element.getModel().name}")
//                    mainScreenVM.setElementWithOpenedDialog(null)
//                }
//
//                myDialog.declineButtonAction = {
//                    Log.e("!!!", "Declined, name = ${element.getModel().name}")
//                    mainScreenVM.setElementWithOpenedDialog(null)
//                }
//
//            }
//            myDialog.isVisible = element != null
//        }
//
//        collectFlow(
//            mainScreenVM.state
//                .map { it.data }
//                .distinctUntilChanged()
//        ) { data ->
//            adapter.update(
//                data.map { element ->
//                    when (element) {
//                        is FirstTypeModelUi ->
//                            FirstTypeItem(
//                                item = element,
//                                onClick = {
//                                    val detailsFragmentInstance =
//                                        LRDetailsScreenFragment.getInstance(element = element.getModel())
//                                    findNavController().navigate(
//                                        detailsFragmentInstance.first,
//                                        detailsFragmentInstance.second
//                                    )
//                                },
//                                onText = { newText, position ->
//                                    cursorMap[element.getModel().id] = position
//                                    mainScreenVM.changeElementText(
//                                        element = element,
//                                        newText = newText
//                                    )
//                                },
//                                cursor = cursorMap[element.getModel().id] ?: 0
//                            )
//
//                        is SecondTypeModelUi ->
//                            SecondTypeItem(
//                                item = element,
//                                onClick = { mainScreenVM.setElementWithOpenedDialog(element) }
//                            )
//
//                        else -> throw IllegalArgumentException("Invalid type of element")
//                    }
//                }
//            )
//        }
//
//    }
//}