package com.getz.setthegoal.presentationpart.feature.viewgoals

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.request.RequestOptions
import com.getz.setthegoal.R
import com.getz.setthegoal.presentationpart.core.BaseFragment
import com.getz.setthegoal.presentationpart.core.GlideApp
import com.getz.setthegoal.presentationpart.customview.ExpandableTextView
import com.getz.setthegoal.presentationpart.util.setSingleClickListener
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_goals.*
import org.kodein.di.direct
import org.kodein.di.generic.instance
import java.util.Locale

class GoalsFragment : BaseFragment(R.layout.fragment_goals) {

    lateinit var vm: GoalsVM
    lateinit var bridge: GoalsBridge

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
        setupExpandableListener()
        setupLD()
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
        fabAddNewGoal.setSingleClickListener {
            bridge.openCreateGoalScreen()
        }
    }

    private fun setupLD() {
        vm.quoteLD.observe(this, Observer { quote ->
            tvQuoteContent.text =
                getString(R.string.quote_with_author, quote.quoteText, quote.quoteAuthor)
        })
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
        val titles = arrayListOf(
            getString(R.string.goals_for_my_family),
            getString(R.string.goals_for_myself),
            getString(R.string.achieved_tab_title)
        )
        vpGoals.adapter = GoalsPagerAdapter(childFragmentManager, titles)
        vpGoals.offscreenPageLimit = titles.size
        tabs.setupWithViewPager(vpGoals)
    }
}