package com.alenniboris.nba_app.presentation.learning_recycler.details.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.alenniboris.nba_app.R
import com.alenniboris.nba_app.databinding.FragmentLrDetailsScreenBinding
import com.alenniboris.nba_app.domain.model.learning_recycler.LRFirstTypeModelDomain
import com.alenniboris.nba_app.domain.utils.GsonUtil.fromJson
import com.alenniboris.nba_app.domain.utils.GsonUtil.toJson
import com.alenniboris.nba_app.presentation.learning_recycler.collectFlow
import com.alenniboris.nba_app.presentation.learning_recycler.details.LRDetailsScreenVM
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class LRDetailsScreenFragment : Fragment(R.layout.fragment_lr_details_screen) {


    private val binding by viewBinding(FragmentLrDetailsScreenBinding::bind)
    private val detailsScreenVM by viewModel<LRDetailsScreenVM>() {
        val transferredElement =
            arguments?.getString(KEY_STRING)?.fromJson<LRFirstTypeModelDomain>()
        parametersOf(transferredElement)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnDetailsNavigateBack.setOnClickListener {
            findNavController().popBackStack()

        }

        collectFlow(
            detailsScreenVM.state
                .map { it.element }
                .distinctUntilChanged()
        ) { element ->
            binding.elementDetailsName.text = element.name
            binding.elementDetailsText.text = element.text
        }
    }

    companion object {
        private const val KEY_STRING = "ELEMENT"
        fun getInstance(element: LRFirstTypeModelDomain) =
            R.id.LRDetailsScreenFragment to Bundle().apply {
                putString(
                    KEY_STRING,
                    element.toJson()
                )
            }

    }

}