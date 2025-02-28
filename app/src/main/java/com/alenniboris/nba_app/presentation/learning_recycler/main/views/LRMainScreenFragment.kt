package com.alenniboris.nba_app.presentation.learning_recycler.main.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
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

class LRMainScreenFragment : Fragment() {

    private var _mainScreenBinding: FragmentLrMainScreenBinding? = null
    private val binding
        get() = _mainScreenBinding!!

    private val mainScreenVM by viewModel<LRMainScreenVM>()

    private val adapter: BaseRecyclerView.BaseAdapter = BaseRecyclerView.BaseAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _mainScreenBinding =
            FragmentLrMainScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        binding.elementsRv.adapter = null
        _mainScreenBinding = null
        super.onDestroyView()
    }

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