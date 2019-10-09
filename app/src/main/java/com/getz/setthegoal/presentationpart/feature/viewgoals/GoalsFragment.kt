package com.getz.setthegoal.presentationpart.feature.viewgoals

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.request.RequestOptions
import com.getz.setthegoal.R
import com.getz.setthegoal.di.AndroidScope
import com.getz.setthegoal.domainpart.core.Gandalf
import com.getz.setthegoal.domainpart.entitylayer.Goal
import com.getz.setthegoal.domainpart.entitylayer.Quote
import com.getz.setthegoal.domainpart.interactorlayer.IDeleteGoalUC
import com.getz.setthegoal.domainpart.interactorlayer.IGetGoalsUC
import com.getz.setthegoal.domainpart.interactorlayer.IGetQuoteUC
import com.getz.setthegoal.presentationpart.core.BaseFragment
import com.getz.setthegoal.presentationpart.core.BaseVm
import com.getz.setthegoal.presentationpart.core.GlideApp
import com.getz.setthegoal.presentationpart.customview.ExpandableTextView
import com.getz.setthegoal.presentationpart.entitylayer.GoalUI
import com.getz.setthegoal.presentationpart.feature.creategoal.CONST_FAMILY
import com.getz.setthegoal.presentationpart.feature.creategoal.CONST_MYSELF
import com.getz.setthegoal.presentationpart.util.setSingleClickListener
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_goals.*
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.on
import org.kodein.di.generic.scoped
import org.kodein.di.generic.singleton
import java.util.Locale

const val GOALS_VM_MODULE = "GOALS_VM_MODULE"

fun getGoalsModule() = Kodein.Module(GOALS_VM_MODULE) {
    bind<GoalsVM>() with scoped<Fragment>(AndroidScope).singleton {
        GoalsVM.getInstance(instance(), GoalsVMF(instance(), instance(), instance(), instance()))
    }
}

class GoalsFragment : BaseFragment(R.layout.fragment_goals) {

    val vm: GoalsVM by kodein.on(context = this).instance()
    private lateinit var bridge: GoalsBridge

    override fun provideOverridingModule() = getGoalsModule()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        bridge = context as GoalsBridge
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

class GoalsVM(
    private val getQuoteUC: IGetQuoteUC,
    private val getGoalsUC: IGetGoalsUC,
    private val deleteGoalUC: IDeleteGoalUC,
    private val presentationToDomainGoalMapper: Gandalf<List<Goal>, List<GoalUI>>
) : BaseVm() {

    init {
        println("GETTTZZZ.GoalsVM.init ---> this.hashCode=${this.hashCode()}")
    }

    override fun onCleared() {
        super.onCleared()
        println("GETTTZZZ.GoalsVM.onCleared ---> this.hashCode=${this.hashCode()}")
    }

    val quoteLD = MutableLiveData<Quote>()

    val goalsForFamilyLD = MutableLiveData<List<GoalUI>>()
    val goalsForMyselfLD = MutableLiveData<List<GoalUI>>()
    val achievedLD = MutableLiveData<List<GoalUI>>()

    fun loadRandomQuote(locale: Locale) = launch {
        getQuoteUC.invoke(locale, ::processError) { quote ->
            quoteLD.value = quote
        }
    }

    fun loadGoals() {
        getGoalsUC.invoke(Unit, ::processError) { goals ->
            val goalsUI = presentationToDomainGoalMapper.transform(goals)
            val goalsForFamily = goalsUI.filter { it.forWhom == CONST_FAMILY && !it.done }
            val goalsForMyself = goalsUI.filter { it.forWhom == CONST_MYSELF && !it.done }
            val achieved = goalsUI.filter { it.done }
            goalsForFamilyLD.value = goalsForFamily
            goalsForMyselfLD.value = goalsForMyself
            achievedLD.value = achieved
        }
    }

    fun deleteGoal(goalId: String) = launch {
        deleteGoalUC.invoke(goalId, ::processError) {}
    }

    companion object {
        fun getInstance(fragment: Fragment, factory: GoalsVMF) =
            ViewModelProviders.of(fragment, factory)[GoalsVM::class.java]
    }
}

class GoalsVMF(
    private val getQuoteUC: IGetQuoteUC,
    private val getGoalsUC: IGetGoalsUC,
    private val deleteGoalUC: IDeleteGoalUC,
    private val presentationToDomainGoalMapper: Gandalf<List<Goal>, List<GoalUI>>
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        GoalsVM(getQuoteUC, getGoalsUC, deleteGoalUC, presentationToDomainGoalMapper) as T
}