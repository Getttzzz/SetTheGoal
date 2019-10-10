package com.getz.setthegoal.presentationpart.customview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewEmptyState @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : RecyclerView(context, attributeSet, defStyle) {

    private var emptyView: View? = null

    private val dataObserver: AdapterDataObserver = object : AdapterDataObserver() {
        override fun onChanged() {
            super.onChanged()
            val a = adapter
            if (a != null && emptyView != null) {
                emptyView?.isVisible = a.itemCount == 0
                this@RecyclerViewEmptyState.isVisible = a.itemCount != 0
            }
        }
    }

    override fun setAdapter(adapter: Adapter<*>?) {
        super.setAdapter(adapter)
        adapter?.registerAdapterDataObserver(dataObserver)
        dataObserver.onChanged()
    }

    fun setupEmptyView(v: View) {
        emptyView = v
    }

}