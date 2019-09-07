package com.getz.setthegoal.presentationpart.feature.goals

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.viewpager.widget.ViewPager
import com.getz.setthegoal.R
import com.getz.setthegoal.presentationpart.core.BaseFragment
import com.getz.setthegoal.presentationpart.customview.ExpandableTextView
import com.getz.setthegoal.presentationpart.utils.setSingleClickListener
import com.google.android.material.shape.CutCornerTreatment
import com.google.android.material.shape.ShapeAppearanceModel
import kotlinx.android.synthetic.main.fragment_goals.*

class GoalsFragment : BaseFragment(R.layout.fragment_goals) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupGoodQuoteCard()
        setupPager()
        setupExpandableListener()

        llFamily.setSingleClickListener {
            vpGoals.setCurrentItem(GoalsPagerAdapter.FAMILY_TAB_POSITION, true)
        }
        llMyself.setSingleClickListener {
            vpGoals.setCurrentItem(GoalsPagerAdapter.MYSELF_TAB_POSITION, true)
        }
    }

    private fun setupExpandableListener() {
        tvQuoteContent.addOnExpandListener(object : ExpandableTextView.OnExpandListener {
            override fun onStartExpand(view: ExpandableTextView) {
                ivArrow.isEnabled = false
            }

            override fun onStartCollapse(view: ExpandableTextView) {
                ivArrow.isEnabled = true
            }

            override fun onEndExpand(view: ExpandableTextView) = Unit

            override fun onEndCollapse(view: ExpandableTextView) = Unit

            override fun onDrewInLayout(isEllipsize: Boolean, realLineCount: Int) {
                cardMotivation.setSingleClickListener { tvQuoteContent.toggle() }
            }
        })
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
                startScaleAnimation(ivFamily, 1.0f, 1.15f, 250)
                startScaleAnimation(ivMyself, 1.15f, 1.0f, 250)
                ivFamily.isEnabled = true
                ivMyself.isEnabled = false
                tvFamily.setTextColor(resources.getColor(R.color.colorAccent))
                tvMyself.setTextColor(resources.getColor(R.color.colorSecondaryDark))
            }
            GoalsPagerAdapter.MYSELF_TAB_POSITION -> {
                startScaleAnimation(ivFamily, 1.15f, 1.0f, 250)
                startScaleAnimation(ivMyself, 1.0f, 1.15f, 250)
                ivFamily.isEnabled = false
                ivMyself.isEnabled = true
                tvFamily.setTextColor(resources.getColor(R.color.colorSecondaryDark))
                tvMyself.setTextColor(resources.getColor(R.color.colorAccent))
            }
        }
    }

    private fun startScaleAnimation(view: View, startVal: Float, endVal: Float, durationVal: Long) {
        val animator = ValueAnimator.ofFloat(startVal, endVal)
            .apply {
                duration = durationVal
                interpolator = AccelerateDecelerateInterpolator()
                addUpdateListener {
                    val animatedVal = it.animatedValue as Float
                    view.scaleX = animatedVal
                    view.scaleY = animatedVal
                }
            }
        animator.start()
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