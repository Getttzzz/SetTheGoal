package com.getz.setthegoal.ui.goals

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.getz.setthegoal.R
import com.getz.setthegoal.ui.goals.pager.GoalsPagerAdapter
import com.google.android.material.shape.CutCornerTreatment
import com.google.android.material.shape.ShapeAppearanceModel
import kotlinx.android.synthetic.main.fragment_goals.*

class GoalsFragment : Fragment(R.layout.fragment_goals) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        println("GETZ.GoalsFragment.onViewCreated ---> ")

        setupQuoteCard()

        vpGoals.adapter = GoalsPagerAdapter(childFragmentManager)
        vpGoals.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) = Unit
            override fun onPageScrolled(pos: Int, posOffset: Float, posOffsetPixels: Int) = Unit
            override fun onPageSelected(position: Int) {
                println("GETZ.GoalsFragment.onPageSelected ---> selected page=$position")
            }

        })


    }

    private fun setupQuoteCard() {
        val shape = ShapeAppearanceModel()
        val cutCorner =
            CutCornerTreatment(resources.getDimensionPixelSize(R.dimen.cornerCutSize).toFloat())
        val standardCorner = CutCornerTreatment(0f)
        shape.setCornerTreatments(standardCorner, standardCorner, cutCorner, cutCorner)
        cardMotivation.shapeAppearanceModel = shape
    }
}