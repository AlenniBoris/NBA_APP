package com.alenniboris.nba_app.presentation.learning_recycler.main.views

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.alenniboris.nba_app.R
import com.alenniboris.nba_app.databinding.FragmentLrMainScreenBinding
import com.alenniboris.nba_app.domain.model.learning_recycler.LRFirstTypeModelDomain
import com.alenniboris.nba_app.domain.model.learning_recycler.LRSecondTypeModelDomain
import com.alenniboris.nba_app.presentation.learning_recycler.collectFlow
import com.alenniboris.nba_app.presentation.learning_recycler.details.views.LRDetailsScreenFragment
import com.alenniboris.nba_app.presentation.learning_recycler.main.BaseRecyclerView
import com.alenniboris.nba_app.presentation.learning_recycler.main.FirstTypeItem
import com.alenniboris.nba_app.presentation.learning_recycler.main.LRMainScreenVM
import com.alenniboris.nba_app.presentation.learning_recycler.main.SecondTypeItem
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

        collectFlow(
            mainScreenVM.state
                .map { it.data }
                .distinctUntilChanged()
        ) { data ->
            adapter.update(
                data.map {
                    when (it) {
                        is LRFirstTypeModelDomain ->
                            FirstTypeItem(
                                item = it,
                                onClick = {
                                    val detailsFragmentInstance =
                                        LRDetailsScreenFragment.getInstance(element = it)
                                    findNavController().navigate(
                                        detailsFragmentInstance.first,
                                        detailsFragmentInstance.second
                                    )
                                }
                            )

                        is LRSecondTypeModelDomain ->
                            SecondTypeItem(
                                item = it,
                                onClick = {}
                            )

                        else -> throw IllegalArgumentException("Invalid type of element")
                    }
                }
            )
        }

    }
}