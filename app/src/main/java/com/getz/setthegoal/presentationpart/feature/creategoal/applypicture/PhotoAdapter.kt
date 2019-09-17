package com.getz.setthegoal.presentationpart.feature.creategoal.applypicture

import android.graphics.Paint.UNDERLINE_TEXT_FLAG
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.getz.setthegoal.R
import com.getz.setthegoal.domainpart.entitylayer.Photo
import com.getz.setthegoal.presentationpart.core.BaseAdapter
import com.getz.setthegoal.presentationpart.util.loadPicture
import com.getz.setthegoal.presentationpart.util.setSingleClickListener
import kotlinx.android.synthetic.main.item_photo.view.*


class PhotoAdapter : BaseAdapter<Photo, PhotoAdapter.VH>() {

    lateinit var onClick: (position: Int) -> Unit
    lateinit var onAuthorClick: (position: Int) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(LayoutInflater.from(parent.context).inflate(R.layout.item_photo, parent, false))

    override fun getItemCount() = godList.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        val photo = godList[position]
        holder.view.tvAuthor.paintFlags = holder.view.tvAuthor.paintFlags or UNDERLINE_TEXT_FLAG
        holder.view.tvAuthor.text = photo.userName
        loadPicture(holder.view.ivPhoto, photo.urls.small)
    }

    inner class VH(val view: View) : RecyclerView.ViewHolder(view) {
        init {
            view.setSingleClickListener { onClick.invoke(adapterPosition) }
            view.tvAuthor.setSingleClickListener { onAuthorClick.invoke(adapterPosition) }
        }
    }
}