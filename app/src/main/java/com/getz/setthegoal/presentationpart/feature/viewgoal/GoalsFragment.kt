package com.getz.setthegoal.presentationpart.feature.viewgoal

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat.getColor
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.request.RequestOptions
import com.getz.setthegoal.R
import com.getz.setthegoal.presentationpart.core.BaseFragment
import com.getz.setthegoal.presentationpart.core.GlideApp
import com.getz.setthegoal.presentationpart.customview.ExpandableTextView
import com.getz.setthegoal.presentationpart.util.addOnPageSelectedListener
import com.getz.setthegoal.presentationpart.util.setSingleClickListener
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_goals.*
import kotlinx.android.synthetic.main.layout_floating_buttons.*
import org.kodein.di.direct
import org.kodein.di.generic.instance
import java.util.Locale

class GoalsFragment : BaseFragment(R.layout.fragment_goals) {

    lateinit var vm: GoalsVM
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
        vm = ViewModelProviders.of(this, direct.instance()).get(GoalsVM::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupPager()
        setupBottomAppBar()
        setupExpandableListener()
        setupLD()
        setupFabMenu()
        setupClickListeners()
        setupUserIcon()

        vm.loadRandomQuote(Locale.getDefault())
    }

    fun scrollToAppropriateTab(isForFamily: Boolean) {
        vpGoals.currentItem = if (isForFamily) 0 else 1
    }

    private fun setupUserIcon() {
        FirebaseAuth.getInstance().currentUser?.let { user ->
            if (!user.isAnonymous) {
                GlideApp.with(this)
                    .load(user.photoUrl)
                    .error(R.drawable.ic_person)
                    .apply(RequestOptions().circleCrop())
                    .into(ivAvatar)
            }
        }
    }

    private fun setupClickListeners() {
        ivAvatar.setSingleClickListener {
            bridge.openProfileScreen()
        }
        ivNewIdea.setSingleClickListener { vm.loadRandomQuote(Locale.getDefault()) }
        fabCreateForMyself.setSingleClickListener {
            immediatelyHideFab()
            bridge.openCreateGoalScreen(false)
        }
        mcvCreateForMyself.setSingleClickListener {
            immediatelyHideFab()
            bridge.openCreateGoalScreen(false)
        }

        fabCreateForFamily.setSingleClickListener {
            immediatelyHideFab()
            bridge.openCreateGoalScreen(true)
        }
        mcvCreateForFamily.setSingleClickListener {
            immediatelyHideFab()
            bridge.openCreateGoalScreen(true)
        }
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
                mcvCreateForMyself.visibility = View.GONE
                mcvCreateForFamily.visibility = View.GONE
                false
            } else {
                //show two fab buttons
                fabAddNewGoal.startAnimation(fabRotateClock)
                fabCreateForMyself.show()
                fabCreateForFamily.show()
                mcvCreateForMyself.visibility = View.VISIBLE
                mcvCreateForFamily.visibility = View.VISIBLE
                true
            }
        }
    }

    private fun immediatelyHideFab() {
        fabAddNewGoal.startAnimation(fabRotateOpositeClock)
        fabCreateForMyself.visibility = View.INVISIBLE
        fabCreateForFamily.visibility = View.INVISIBLE
        mcvCreateForMyself.visibility = View.GONE
        mcvCreateForFamily.visibility = View.GONE
        isOpened = false
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
        vpGoals.addOnPageSelectedListener { position -> selectBottomElement(position) }
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
                tvFamily.setTextColor(getColor(context!!, R.color.colorWhite))
                tvMyself.setTextColor(getColor(context!!, R.color.colorText))
            }
            GoalsPagerAdapter.MYSELF_TAB_POSITION -> {
                ivFamily.isEnabled = false
                ivMyself.isEnabled = true
                tvFamily.setTextColor(getColor(context!!, R.color.colorText))
                tvMyself.setTextColor(getColor(context!!, R.color.colorWhite))
            }
        }
    }
}