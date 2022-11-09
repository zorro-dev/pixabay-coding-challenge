package com.ttf.pixabayviewer.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ttf.pixabayviewer.R
import com.ttf.pixabayviewer.data.models.ImageHit
import com.ttf.pixabayviewer.databinding.FragmentMainBinding
import com.ttf.pixabayviewer.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainFragment : BaseFragment<MainNavigator, MainViewModel>(), MainNavigator {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.navigator = this

        _binding = FragmentMainBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun openDetailsFragment(image: ImageHit) {
        if (findNavController().currentDestination?.id == R.id.mainFragment) {
            findNavController().navigate(MainFragmentDirections.actionGlobalDetailsFragment(image))
        }
    }

    override fun openConfirmDetailsDialog(image: ImageHit) {
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.confirm_details_title))
            .setMessage(getString(R.string.confirm_details_subtitle))
            .setPositiveButton(getString(R.string.yes)) { _, _ -> openDetailsFragment(image) }
            .setNegativeButton(getString(R.string.No)) { _, _ -> }
            .show()
    }
}