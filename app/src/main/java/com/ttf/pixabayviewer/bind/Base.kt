package com.ttf.pixabayviewer.bind

import android.annotation.SuppressLint
import android.util.Size
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.ttf.pixabayviewer.data.models.ImageHit
import com.ttf.pixabayviewer.ui.main.ImagesAdapter

object Base {

    @SuppressLint("NotifyDataSetChanged")
    @BindingAdapter("pixabayHits", "onScroll", "onClick")
    @JvmStatic
    fun setPixabayHits(
        list: RecyclerView,
        events: List<ImageHit>?,
        onScroll: (Int) -> Unit,
        onClick: (ImageHit) -> Unit,
    ) {
        list.adapter ?: run {
            list.itemAnimator?.changeDuration = 0
            list.adapter = ImagesAdapter(onClick)
            (list.adapter as ImagesAdapter).stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
            list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    onScroll.invoke((recyclerView.layoutManager as StaggeredGridLayoutManager).findLastVisibleItemPositions(
                        null).max())
                }
            })
        }
        (list.adapter as ImagesAdapter).submitList(events)
        (list.adapter as ImagesAdapter).notifyDataSetChanged()
    }

    @BindingAdapter("imageUrl", "size")
    @JvmStatic
    fun ImageView.setConstraintSizedImage(imageUrl: String?, size: Size?) {
        if (this.layoutParams is ConstraintLayout.LayoutParams) {
            val lp = this.layoutParams as ConstraintLayout.LayoutParams
            lp.dimensionRatio = "${size?.width ?: 1}:${size?.height ?: 1}"
            this.layoutParams = lp
        }

        Glide.with(this.context).load(imageUrl).into(this)
    }

    @BindingAdapter("string", "defaultRes")
    @JvmStatic
    fun TextView.setStringOrDefaultRes(string: String?, @StringRes defaultRes: Int) {
        if (string == null) {
            this.setText(defaultRes)
        } else {
            this.text = string
        }
    }

    @BindingAdapter("onQuerySubmit")
    @JvmStatic
    fun SearchView.setOnQuerySubmit(onQuerySubmit: (String) -> Unit) {
        setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                onQuerySubmit.invoke(query ?: "")
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
    }

    @BindingAdapter("query")
    @JvmStatic
    fun SearchView.setQuery(query: String?) {
        this.setQuery(query, false)
    }

}