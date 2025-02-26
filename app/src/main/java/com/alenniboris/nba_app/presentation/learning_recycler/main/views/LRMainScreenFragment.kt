package com.alenniboris.nba_app.presentation.learning_recycler.main.views

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.alenniboris.nba_app.R
import com.alenniboris.nba_app.databinding.FragmentLrMainScreenBinding
import com.alenniboris.nba_app.domain.utils.GsonUtil.toJson
import com.alenniboris.nba_app.presentation.learning_recycler.main.ElementsRecyclerViewAdapter
import com.alenniboris.nba_app.presentation.learning_recycler.main.LRMainScreenState
import com.alenniboris.nba_app.presentation.learning_recycler.main.LRMainScreenVM
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class LRMainScreenFragment : Fragment() {

    private var _mainScreenBinding: FragmentLrMainScreenBinding? = null
    private val binding
        get() = _mainScreenBinding!!

    private val mainScreenVM by viewModel<LRMainScreenVM>()

    private lateinit var elementsAdapter: ElementsRecyclerViewAdapter
    private lateinit var context: Context

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _mainScreenBinding =
            FragmentLrMainScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.elementsRv.adapter = null
        _mainScreenBinding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        context = requireActivity().applicationContext

        elementsAdapter = ElementsRecyclerViewAdapter(
            onClick = { element ->
                val bundle = Bundle()
                bundle.putString("ELEMENT", element.toJson())
                Navigation.findNavController(requireView())
                    .navigate(R.id.actionNavigateToDetailsScreen, bundle)
            }
        )
        binding.elementsRv.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )
        binding.elementsRv.adapter = elementsAdapter

        setOnClicks()

        mainScreenVM.state
            .flowWithLifecycle(lifecycle = viewLifecycleOwner.lifecycle)
            .onEach { state -> renderState(state = state) }
            .launchIn(scope = viewLifecycleOwner.lifecycleScope)

    }

    private fun renderState(state: LRMainScreenState) {
        when {
            state.isLoading -> {
                binding.elementsRv.visibility = View.GONE
                binding.loadingIsActiveLayout.visibility = View.VISIBLE
                binding.nothingFoundLayout.visibility = View.GONE
            }

            state.isError -> {
                binding.elementsRv.visibility = View.GONE
                binding.loadingIsActiveLayout.visibility = View.GONE
                binding.nothingFoundLayout.visibility = View.VISIBLE
            }

            else -> {
                binding.elementsRv.visibility = View.VISIBLE
                binding.loadingIsActiveLayout.visibility = View.GONE
                binding.nothingFoundLayout.visibility = View.GONE

                elementsAdapter.submitList(state.data)
            }
        }
    }

    private fun setOnClicks() {
        binding.btnSearchIfError.setOnClickListener {
            mainScreenVM.loadDataFromServer()
        }

        binding.btnSearchIfNothingFound.setOnClickListener {
            mainScreenVM.loadDataFromServer()
        }
    }

}