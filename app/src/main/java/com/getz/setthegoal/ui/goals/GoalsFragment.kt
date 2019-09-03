package com.getz.setthegoal.ui.goals

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.getz.setthegoal.R
import com.getz.setthegoal.ui.goals.pager.GoalsPagerAdapter
import com.getz.setthegoal.ui.utils.setSingleClickListener
import com.google.android.material.shape.CutCornerTreatment
import com.google.android.material.shape.ShapeAppearanceModel
import kotlinx.android.synthetic.main.fragment_goals.*

class GoalsFragment : Fragment(R.layout.fragment_goals) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupGoodQuoteCard()
        setupPager()
        llFamily.setSingleClickListener {
            vpGoals.setCurrentItem(GoalsPagerAdapter.FAMILY_TAB_POSITION, true)
        }
        llMyself.setSingleClickListener {
            vpGoals.setCurrentItem(GoalsPagerAdapter.MYSELF_TAB_POSITION, true)
        }
    }

    private fun setupPager() {
        selectBottomElement(GoalsPagerAdapter.FAMILY_TAB_POSITION)
        vpGoals.adapter = GoalsPagerAdapter(childFragmentManager)
        vpGoals.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) = Unit
            override fun onPageScrolled(pos: Int, posOffset: Float, posOffsetPixels: Int) = Unit
            override fun onPageSelected(position: Int) {
                println("GETZ.GoalsFragment.onPageSelected ---> selected page=$position")
                selectBottomElement(position)
            }
        })
    }

    private fun selectBottomElement(position: Int) {
        when (position) {
            GoalsPagerAdapter.FAMILY_TAB_POSITION -> {
                val familyAnimator = ValueAnimator.ofFloat(1.0f, 1.2f)
                    .apply {
                        duration = 300
                        interpolator = AccelerateDecelerateInterpolator()
                        addUpdateListener {
                            val animatedVal = it.animatedValue as Float
                            ivFamily.scaleX = animatedVal
                            ivFamily.scaleY = animatedVal
                        }
                    }
                familyAnimator.start()

                val myselfAnimator = ValueAnimator.ofFloat(1.2f, 1.0f)
                    .apply {
                        duration = 300
                        interpolator = AccelerateDecelerateInterpolator()
                        addUpdateListener {
                            val animatedVal = it.animatedValue as Float
                            ivMyself.scaleX = animatedVal
                            ivMyself.scaleY = animatedVal
                        }
                    }
                myselfAnimator.start()

                ivFamily.isEnabled = true
                ivMyself.isEnabled = false
                tvFamily.setTextColor(resources.getColor(R.color.colorAccent))
                tvMyself.setTextColor(resources.getColor(R.color.colorSecondaryDark))
            }
            GoalsPagerAdapter.MYSELF_TAB_POSITION -> {

                val familyAnimator = ValueAnimator.ofFloat(1.2f, 1.0f)
                    .apply {
                        duration = 300
                        interpolator = AccelerateDecelerateInterpolator()
                        addUpdateListener {
                            val animatedVal = it.animatedValue as Float
                            ivFamily.scaleX = animatedVal
                            ivFamily.scaleY = animatedVal
                        }
                    }
                familyAnimator.start()

                val myselfAnimator = ValueAnimator.ofFloat(1.0f, 1.2f)
                    .apply {
                        duration = 300
                        interpolator = AccelerateDecelerateInterpolator()
                        addUpdateListener {
                            val animatedVal = it.animatedValue as Float
                            ivMyself.scaleX = animatedVal
                            ivMyself.scaleY = animatedVal
                        }
                    }
                myselfAnimator.start()

                ivFamily.isEnabled = false
                ivMyself.isEnabled = true
                tvFamily.setTextColor(resources.getColor(R.color.colorSecondaryDark))
                tvMyself.setTextColor(resources.getColor(R.color.colorAccent))
            }
        }
    }

    private fun setupGoodQuoteCard() {
        val shape = ShapeAppearanceModel()
        val cutCorner =
            CutCornerTreatment(resources.getDimensionPixelSize(R.dimen.cornerCutSize).toFloat())
        val standardCorner = CutCornerTreatment(0f)
        shape.setCornerTreatments(standardCorner, standardCorner, cutCorner, cutCorner)
        cardMotivation.shapeAppearanceModel = shape
    }
}