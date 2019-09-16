package com.getz.setthegoal.presentationpart.feature.creategoal.applypicture

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.getz.setthegoal.R
import com.getz.setthegoal.presentationpart.core.BaseFragment
import com.getz.setthegoal.presentationpart.feature.creategoal.CreateGoalVM
import com.getz.setthegoal.presentationpart.feature.creategoal.applysubtasks.WordsRV
import kotlinx.android.synthetic.main.fragment_apply_picture.*
import org.kodein.di.direct
import org.kodein.di.generic.instance

class ApplyPictureFragment : BaseFragment(R.layout.fragment_apply_picture) {

    lateinit var vm: CreateGoalVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm = ViewModelProviders.of(this, direct.instance()).get(CreateGoalVM::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val wordsRV = WordsRV()
            .apply {
                onClick = { position ->
                    val selectedWord = this.godList[position]
                    println("GETTTZZZ.ApplyPictureFragment.onViewCreated ---> selectedWord=$selectedWord")
                    //todo connect Unsplash Api
                }
            }
        rvWords.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rvWords.adapter = wordsRV

        vm.recognizedWordsLD.observe(this, Observer { words ->
            wordsRV.replace(words)
        })
    }

}