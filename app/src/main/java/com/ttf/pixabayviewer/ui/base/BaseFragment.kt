package com.ttf.pixabayviewer.ui.base

import android.graphics.Color
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.ttf.pixabayviewer.R

open class BaseFragment<T : BaseNavigator, VM : BaseViewModel<T>> : Fragment(), BaseNavigator {

    lateinit var viewModel: VM

    override fun onStart() {
        super.onStart()
        //requireActivity().updateStatusBarColor(R.color.background)
    }

    override fun showToast(msg: String?) {
        context?.let {
            Toast.makeText(it, msg, Toast.LENGTH_SHORT).show()
        }
    }

    override fun showToast(msg: Int) {
        context?.let {
            Toast.makeText(it, msg, Toast.LENGTH_SHORT).show()
        }
    }

    override fun showSnackBar(msg: String?, success: Boolean) {
        view?.let {
            val snackBar: Snackbar = Snackbar.make(it, msg ?: it.context.getString(R.string.something_went_wrong), Snackbar.LENGTH_SHORT)
            if (success)
                snackBar.view.setBackgroundColor(Color.GREEN)
            val textView = snackBar.view.findViewById<View>(com.google.android.material.R.id.snackbar_text) as TextView
            textView.maxLines = 5
            textView.textAlignment = View.TEXT_ALIGNMENT_CENTER
            snackBar.setTextColor(Color.WHITE).show()
        }
    }

    override fun showSnackBar(msg: Int, success: Boolean) {
        (showSnackBar(view?.context?.getString(msg), success))
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