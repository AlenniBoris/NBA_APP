package com.alenniboris.nba_app.presentation.learning_recycler.details.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.alenniboris.nba_app.R
import com.alenniboris.nba_app.databinding.FragmentLrDetailsScreenBinding
import com.alenniboris.nba_app.domain.model.learning_recycler.LearningRecyclerDataModelDomain
import com.alenniboris.nba_app.domain.utils.GsonUtil.fromJson
import com.alenniboris.nba_app.presentation.learning_recycler.details.LRDetailsScreenState
import com.alenniboris.nba_app.presentation.learning_recycler.details.LRDetailsScreenVM
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class LRDetailsScreenFragment : Fragment() {

    private var _detailsScreenBinding: FragmentLrDetailsScreenBinding? = null
    private val binding
        get() = _detailsScreenBinding!!
    private val detailsScreenVM by viewModel<LRDetailsScreenVM>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _detailsScreenBinding = FragmentLrDetailsScreenBinding.inflate(
            inflater,
            container,
            false
        )

        val transferredElement =
            arguments?.getString("ELEMENT")?.fromJson<LearningRecyclerDataModelDomain>()
        detailsScreenVM.setElement(element = transferredElement)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _detailsScreenBinding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnDetailsNavigateBack.setOnClickListener {
            Navigation.findNavController(requireView())
                .navigate(R.id.actionNavigateBackFromDetailsScreen)
        }

        detailsScreenVM.state
            .flowWithLifecycle(lifecycle = viewLifecycleOwner.lifecycle)
            .onEach { state -> renderState(state) }
            .launchIn(scope = viewLifecycleOwner.lifecycleScope)

    }

    private fun renderState(state: LRDetailsScreenState) {
        when {
            state.element == null -> {
                binding.elementDetailsName.text = "NaN"
                binding.elementDetailsText.text = "NaN"
            }

            else -> {
                binding.elementDetailsName.text = state.element.name
                binding.elementDetailsText.text = state.element.text
            }
        }
    }

}