package com.getz.setthegoal.presentationpart.feature.creategoal.applypicture

import android.os.Bundle
import android.view.View
import androidx.core.text.buildSpannedString
import androidx.core.text.underline
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.getz.setthegoal.R
import com.getz.setthegoal.presentationpart.core.BaseFragment
import com.getz.setthegoal.presentationpart.feature.creategoal.CreateGoalVM
import com.getz.setthegoal.presentationpart.util.hideKeyboard
import com.getz.setthegoal.presentationpart.util.openLink
import com.getz.setthegoal.presentationpart.util.setSingleClickListener
import kotlinx.android.synthetic.main.fragment_apply_picture.*
import kotlinx.android.synthetic.main.layout_found_nothing.*
import org.kodein.di.generic.instance
import org.kodein.di.generic.on
import java.util.Locale

class ApplyPictureFragment : BaseFragment(R.layout.fragment_apply_picture) {

    val vm: CreateGoalVM by kodein.on(context = this).instance()
    private val photoAdapter: PhotoAdapter by lazy { setupPhotoAdapter() }
    private val wordAdapter: WordAdapter by lazy { setupWordAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUnsplashView()
        showFoundNothingView(false)
        setupLD()

        btnFindAnotherPicture.setSingleClickListener {
            //todo implement get random pictures from unsplash request
        }
    }

    override fun onResume() {
        super.onResume()
        hideKeyboard(tvUnsplash)
        vm.validatePhoto()
    }

    private fun setupUnsplashView() {
        tvUnsplash.text = buildSpannedString {
            append(getString(R.string.inspiration_source)).append(" ")
            underline { append(getString(R.string.unsplash_com)) }
        }
        tvUnsplash.setSingleClickListener {
            openLink(
                getString(R.string.unsplash_url),
                requireContext()
            )
        }
    }

    private fun setupLD() {
        vm.recognizedWordsLD.observe(this, Observer { words ->
            changeDescriptionState(true)
            wordAdapter.replace(words)
            photoAdapter.clean()
            vm.selectedPhoto = null
        })
        vm.photosResultLD.observe(this, Observer { photos -> photoAdapter.replace(photos) })
        vm.loadingPhotosLD.observe(this, Observer { loading ->
            ivBeeIdle.isVisible = !loading
            rvPhotos.isVisible = !loading
            loadingPhotos.isVisible = loading
        })
        vm.loadingWordsLD.observe(this, Observer { loading ->
            rvWords?.let { it.isVisible = !loading }
            loadingWords?.let { it.isVisible = loading }
        })
        vm.photoWasEmptyLD.observe(this, Observer { show ->
            photoAdapter.clean()
            showFoundNothingView(show)
        })
    }

    private fun showFoundNothingView(show: Boolean) {
        ivBeeIdle.isVisible = !show
        llFoundNothing.isVisible = show
    }

    private fun setupPhotoAdapter() = PhotoAdapter().apply {
        onClick = { position ->
            this.select(position)
            val photo = this.godList[position]
            vm.selectedPhoto = photo
        }
        onAuthorClick = { position ->
            val photo = this.godList[position]
            openLink(photo.profileLink, requireContext())
        }
        rvPhotos.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rvPhotos.adapter = this
    }

    private fun setupWordAdapter() = WordAdapter().apply {
        onClick = { position ->
            this.select(position)
            val selectedWord = this.godList[position]
            vm.getPhotos(selectedWord.originalWord, Locale.getDefault())
            changeDescriptionState(false)
        }
        rvWords.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rvWords.adapter = this
    }

    private fun changeDescriptionState(needSelectWord: Boolean) {
        tvSelectWord.isVisible = needSelectWord
        tvSelectPhoto.isVisible = !needSelectWord
    }
}