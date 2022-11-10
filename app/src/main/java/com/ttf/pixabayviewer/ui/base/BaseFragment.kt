package com.ttf.pixabayviewer.ui.base

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

open class BaseFragment<T : BaseNavigator, VM : BaseViewModel<T>> : Fragment(), BaseNavigator {

    lateinit var viewModel: VM

    override fun showToast(msg: String) {
        context?.let {
            Toast.makeText(it, msg, Toast.LENGTH_SHORT).show()
        }
    }

    override fun showToast(@StringRes msg: Int) {
        context?.getString(msg)?.let { showToast(it) }
    }

    override fun showSnackBar(msg: String) {
        view?.let {
            val snackBar: Snackbar = Snackbar.make(it, msg, Snackbar.LENGTH_SHORT)
            snackBar.show()
        }
    }

    override fun showSnackBar(@StringRes msg: Int) {
        context?.getString(msg)?.let { showSnackBar(it) }
    }

    override fun close() {
        activity?.onBackPressed()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.navigator = null
    }

    fun isViewModelInitialized() = ::viewModel.isInitialized
}