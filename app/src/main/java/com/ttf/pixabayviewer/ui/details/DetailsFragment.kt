package com.ttf.pixabayviewer.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.ttf.pixabayviewer.databinding.FragmentDetailsBinding
import com.ttf.pixabayviewer.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : BaseFragment<DetailsNavigator, DetailsViewModel>(), DetailsNavigator {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        viewModel = ViewModelProvider(this)[DetailsViewModel::class.java]
        viewModel.navigator = this

        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.image.postValue(DetailsFragmentArgs.fromBundle(requireArguments()).item)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}