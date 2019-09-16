package com.getz.setthegoal.presentationpart.core

import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<ENTITY, VH : RecyclerView.ViewHolder>(
    val godList: ArrayList<ENTITY> = arrayListOf()
) : RecyclerView.Adapter<VH>() {

    fun replace(collection: Collection<ENTITY>) {
        godList.clear()
        godList.addAll(collection)
        notifyDataSetChanged()
    }
}