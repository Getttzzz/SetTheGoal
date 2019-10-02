package com.getz.setthegoal.presentationpart.feature.creategoal.applypicture

import android.graphics.Paint.UNDERLINE_TEXT_FLAG
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.request.RequestOptions
import com.getz.setthegoal.R
import com.getz.setthegoal.presentationpart.core.BaseAdapter
import com.getz.setthegoal.presentationpart.core.GlideApp
import com.getz.setthegoal.presentationpart.entitylayer.PhotoUI
import com.getz.setthegoal.presentationpart.util.getHideableListener
import com.getz.setthegoal.presentationpart.util.setSingleClickListener
import kotlinx.android.synthetic.main.item_photo.view.*


class PhotoAdapter : BaseAdapter<PhotoUI, PhotoAdapter.VH>() {

    lateinit var onClick: (position: Int) -> Unit
    lateinit var onAuthorClick: (position: Int) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(LayoutInflater.from(parent.context).inflate(R.layout.item_photo, parent, false))

    override fun getItemCount() = godList.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        val photo = godList[position]

        GlideApp.with(holder.view)
            .load(photo.urls.small)
            .apply(RequestOptions().centerCrop())
            .listener(getHideableListener(holder.view.pbPhoto))
            .error(R.drawable.layer_list_bee)
            .into(holder.view.ivPhoto)

        holder.view.tvAuthor.paintFlags = holder.view.tvAuthor.paintFlags or UNDERLINE_TEXT_FLAG
        holder.view.tvAuthor.text = photo.userName
        holder.view.llCarpet.isVisible = photo.isSelected
    }

    fun select(position: Int) {
        godList.forEach { it.isSelected = false }
        godList[position].isSelected = true
        notifyDataSetChanged()
    }

    inner class VH(val view: View) : RecyclerView.ViewHolder(view) {
        init {
            view.setSingleClickListener {
                if (adapterPosition == RecyclerView.NO_POSITION) return@setSingleClickListener
                onClick.invoke(adapterPosition)
            }
            view.tvAuthor.setSingleClickListener {
                if (adapterPosition == RecyclerView.NO_POSITION) return@setSingleClickListener
                onAuthorClick.invoke(adapterPosition)
            }
        }
    }
}