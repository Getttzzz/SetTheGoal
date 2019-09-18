package com.getz.setthegoal.presentationpart.feature.creategoal.applypicture

import android.graphics.Paint
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.getz.setthegoal.R
import com.getz.setthegoal.presentationpart.core.BaseFragment
import com.getz.setthegoal.presentationpart.feature.creategoal.CreateGoalVM
import com.getz.setthegoal.presentationpart.feature.creategoal.applysubtasks.WordAdapter
import com.getz.setthegoal.presentationpart.util.openLink
import com.getz.setthegoal.presentationpart.util.setSingleClickListener
import kotlinx.android.synthetic.main.fragment_apply_picture.*
import org.kodein.di.direct
import org.kodein.di.generic.instance
import java.util.Locale

class ApplyPictureFragment : BaseFragment(R.layout.fragment_apply_picture) {

    lateinit var vm: CreateGoalVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm = ViewModelProviders.of(this, direct.instance()).get(CreateGoalVM::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvUnsplash.paintFlags = tvUnsplash.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        tvUnsplash.setSingleClickListener { openLink(getString(R.string.unsplash_url), context!!) }

        val wordAdapter = setupWordAdapter()
        val photoAdapter = setupPhotoAdapter()

        vm.recognizedWordsLD.observe(this, Observer { words -> wordAdapter.replace(words) })
        vm.photosResultLD.observe(this, Observer { photos -> photoAdapter.replace(photos) })
    }

    private fun setupPhotoAdapter(): PhotoAdapter {
        val photoAdapter = PhotoAdapter()
            .apply {
                onClick = { position ->
                    this.select(position)
                    val photo = this.godList[position]
                    vm.selectedImageUrl = photo
                }
                onAuthorClick = { position ->
                    val photo = this.godList[position]
                    openLink(photo.profileLink, this@ApplyPictureFragment.context!!)
                }
            }
        rvPhotos.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rvPhotos.adapter = photoAdapter
        return photoAdapter
    }

    private fun setupWordAdapter(): WordAdapter {
        val wordAdapter = WordAdapter()
            .apply {
                onClick = { position ->
                    this.select(position)
                    val selectedWord = this.godList[position]
                    vm.getPhotos(selectedWord.originalWord, Locale.getDefault())
                }
            }
        rvWords.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rvWords.adapter = wordAdapter
        return wordAdapter
    }

}