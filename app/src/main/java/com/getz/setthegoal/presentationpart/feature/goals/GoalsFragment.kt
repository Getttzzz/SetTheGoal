package com.getz.setthegoal.presentationpart.feature.goals

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.getz.setthegoal.R
import com.getz.setthegoal.presentationpart.core.BaseFragment
import com.getz.setthegoal.presentationpart.customview.ExpandableTextView
import com.getz.setthegoal.presentationpart.util.setSingleClickListener
import com.google.android.material.shape.CutCornerTreatment
import com.google.android.material.shape.ShapeAppearanceModel
import kotlinx.android.synthetic.main.fragment_goals.*
import kotlinx.android.synthetic.main.layout_floating_buttons.*
import org.kodein.di.direct
import org.kodein.di.generic.instance
import java.util.Locale

class GoalsFragment : BaseFragment(R.layout.fragment_goals) {

    lateinit var vm: GoalsViewModel
    lateinit var bridge: GoalsBridge

    private var isOpened = false
    private val fabRotateClock: Animation by lazy {
        AnimationUtils.loadAnimation(activity, R.anim.fab_rotate_clock)
    }
    private val fabRotateOpositeClock: Animation by lazy {
        AnimationUtils.loadAnimation(activity, R.anim.fab_rotate_oposite_clock)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        bridge = context as GoalsBridge
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm = ViewModelProviders.of(this, direct.instance()).get(GoalsViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupGoodQuoteCard()
        setupPager()
        setupBottomAppBar()
        setupExpandableListener()
        setupLD()
        setupFabMenu()

        ivNewIdea.setSingleClickListener { vm.loadRandomQuote(Locale.getDefault()) }
        fabCreateForMyself.setSingleClickListener {
            fabAddNewGoal.performClick()
            bridge.openCreateGoalScreen(false)
        }
        fabCreateForFamily.setSingleClickListener {
            fabAddNewGoal.performClick()
            bridge.openCreateGoalScreen(true)
        }

        vm.loadRandomQuote(Locale.getDefault())
    }

    private fun setupLD() {
        vm.quoteLD.observe(this, Observer { quote ->
            tvQuoteContent.text =
                getString(R.string.quote_with_author, quote.quoteText, quote.quoteAuthor)
        })
    }

    private fun setupFabMenu() {
        fabAddNewGoal.setSingleClickListener {
            isOpened = if (isOpened) {
                //hide two fab buttons
                fabAddNewGoal.startAnimation(fabRotateOpositeClock)
                fabCreateForMyself.hide()
                fabCreateForFamily.hide()
                tvCreateForMyself.visibility = View.GONE
                tvCreateForFamily.visibility = View.GONE
                false
            } else {
                //show two fab buttons
                fabAddNewGoal.startAnimation(fabRotateClock)
                fabCreateForMyself.show()
                fabCreateForFamily.show()
                tvCreateForMyself.visibility = View.VISIBLE
                tvCreateForFamily.visibility = View.VISIBLE
                true
            }
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
                println("GETTTZZZ.GoalsFragment.onPageSelected ---> selected page=$position")
                selectBottomElement(position)
            }
        })
    }

    private fun setupBottomAppBar() {
        mcvFamily.setSingleClickListener {
            vpGoals.setCurrentItem(GoalsPagerAdapter.FAMILY_TAB_POSITION, true)
        }
        mcvMyself.setSingleClickListener {
            vpGoals.setCurrentItem(GoalsPagerAdapter.MYSELF_TAB_POSITION, true)
        }
    }

    //todo add animation for tabs
    private fun selectBottomElement(position: Int) {
        when (position) {
            GoalsPagerAdapter.FAMILY_TAB_POSITION -> {
                ivFamily.isEnabled = true
                ivMyself.isEnabled = false
                tvFamily.setTextColor(resources.getColor(R.color.colorSecondaryDark))
                tvMyself.setTextColor(resources.getColor(R.color.colorSecondary))
            }
            GoalsPagerAdapter.MYSELF_TAB_POSITION -> {
                ivFamily.isEnabled = false
                ivMyself.isEnabled = true
                tvFamily.setTextColor(resources.getColor(R.color.colorSecondary))
                tvMyself.setTextColor(resources.getColor(R.color.colorSecondaryDark))
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