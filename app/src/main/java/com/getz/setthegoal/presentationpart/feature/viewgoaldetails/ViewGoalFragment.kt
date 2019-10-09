package com.getz.setthegoal.presentationpart.feature.viewgoaldetails

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateInterpolator
import androidx.core.content.ContextCompat
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.request.RequestOptions
import com.getz.setthegoal.R
import com.getz.setthegoal.di.AndroidScope
import com.getz.setthegoal.domainpart.core.Gandalf
import com.getz.setthegoal.domainpart.entitylayer.Goal
import com.getz.setthegoal.domainpart.interactorlayer.IDeleteGoalUC
import com.getz.setthegoal.domainpart.interactorlayer.IUpdateGoalUC
import com.getz.setthegoal.presentationpart.core.BaseFragment
import com.getz.setthegoal.presentationpart.core.BaseVm
import com.getz.setthegoal.presentationpart.core.GlideApp
import com.getz.setthegoal.presentationpart.entitylayer.DeadlineEnum
import com.getz.setthegoal.presentationpart.entitylayer.GoalUI
import com.getz.setthegoal.presentationpart.entitylayer.SubGoalUI
import com.getz.setthegoal.presentationpart.util.getDaysIn
import com.getz.setthegoal.presentationpart.util.getHideableListener
import com.getz.setthegoal.presentationpart.util.setSingleClickListener
import com.getz.setthegoal.presentationpart.util.showDeleteDialog
import kotlinx.android.synthetic.main.fragment_view_goal.*
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.on
import org.kodein.di.generic.scoped
import org.kodein.di.generic.singleton

const val VIEW_GOAL_VM_MODULE = "VIEW_GOAL_VM_MODULE"

fun getViewGoalModule() = Kodein.Module(VIEW_GOAL_VM_MODULE) {
    bind<ViewGoalVM>() with scoped<Fragment>(AndroidScope).singleton {
        ViewGoalVM.getInstance(instance(), ViewGoalVMF(instance(), instance(), instance()))
    }
}

class ViewGoalFragment : BaseFragment(R.layout.fragment_view_goal) {

    val vm: ViewGoalVM by kodein.on(context = this).instance()
    private lateinit var goal: GoalUI
    private lateinit var bridge: ViewGoalBridge
    private var isGoalDone = false
    private val subGoalAdapter: ViewSubGoalAdapter by lazy { setupAdapter() }

    companion object {
        private const val PARCELABLE_GOAL = "PARCELABLE_GOAL"
        fun getInstance(goalUI: GoalUI) = ViewGoalFragment()
            .apply { arguments = Bundle().apply { this.putParcelable(PARCELABLE_GOAL, goalUI) } }
    }

    override fun provideOverridingModule() = getViewGoalModule()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        bridge = context as ViewGoalBridge
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            goal = it.getParcelable(PARCELABLE_GOAL) as GoalUI
            isGoalDone = goal.done
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupPhoto()
        setupGoalText()
        setupSubGoals()
        setupDeadline()
        setupDoneButton(isGoalDone)
        mcvClose.setSingleClickListener { bridge.closeViewGoalScreen() }
        btnDidIt.setSingleClickListener(1000) {
            isGoalDone = if (!isGoalDone) {
                markAsDoneAnimation()
                true
            } else {
                false
            }
            setupDoneButton(isGoalDone)
            vm.markGoalAsDone(goal, isGoalDone)
        }
        mcvMore.setSingleClickListener {
            //todo show custom spinner (don't use standard awful spinner) with options:
            //todo 1.Share 2.Generate pdf with the goal and send it to social network.
        }
        mcvDelete.setSingleClickListener {
            showDeleteDialog(context!!) {
                vm.deleteGoal(goal.goalId)
                bridge.closeViewGoalScreen()
            }
        }
    }

    private fun setupDoneButton(isDone: Boolean) {
        btnDidIt.text = if (isDone) getString(R.string.undo) else getString(R.string.did_it)
    }

    private fun setupDeadline() {
        if (goal.deadline.isNotEmpty()) {
            val timeRange = DeadlineEnum.getEnumByTimeRange(goal.deadline)
            tvDeadlineView.text = getString(timeRange.strRes)

            val daysRemain = goal.createdAt.getDaysIn(timeRange)

            tvTimeRemain.text = buildSpannedString {
                append(resources.getQuantityText(R.plurals.you_have_plural, daysRemain))
                append(" ")
                bold {
                    append(
                        resources.getQuantityString(R.plurals.days_plural, daysRemain, daysRemain)
                    )
                }
                append(" ")
                append(getString(R.string.to_get_it_done))
            }
        }
    }

    private fun setupSubGoals() {
        clSubGoalsContainerView.isVisible = goal.subGoals.isNotEmpty()
        vSeparator2.isVisible = goal.subGoals.isNotEmpty()
        subGoalAdapter.replace(goal.subGoals)
    }

    private fun setupGoalText() {
        etGoal.setText(goal.text)
    }

    private fun setupPhoto() {
        val photo = goal.photo?.urls?.regular ?: ""

        if (photo.isNotEmpty()) {
            GlideApp.with(this)
                .load(photo)
                .apply(RequestOptions().centerCrop())
                .listener(getHideableListener(pbPhotoView))
                .error(R.drawable.layer_list_bee)
                .into(ivPhotoView)
        } else {
            ivPhotoView.isVisible = false
            pbPhotoView.isVisible = false
            vSeparator1.isVisible = false
        }
    }

    private fun setupAdapter() = ViewSubGoalAdapter().apply {
        onClick = { vm.updateSubGoals(goal, godList) }
        rvSubGoal.setHasFixedSize(true)
        rvSubGoal.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rvSubGoal.adapter = this
    }

    private fun markAsDoneAnimation() {
        val objectAnimator = ObjectAnimator.ofObject(
            clRootViewGoal,
            "backgroundColor",
            ArgbEvaluator(),
            ContextCompat.getColor(context!!, R.color.colorBlackOpacity15),
            ContextCompat.getColor(context!!, R.color.colorGreenAccent)
        )
        objectAnimator.repeatCount = 1
        objectAnimator.repeatMode = ValueAnimator.REVERSE
        objectAnimator.duration = 2000
        objectAnimator.interpolator = AccelerateInterpolator()
        objectAnimator.start()
    }
}

class ViewGoalVM(
    private val updateGoalUC: IUpdateGoalUC,
    private val deleteGoalUC: IDeleteGoalUC,
    private val presentationToDomainGoalMapper: Gandalf<GoalUI, Goal>
) : BaseVm() {

    init {
        println("GETTTZZZ.ViewGoalVM.init ---> this.hashCode=${this.hashCode()}")
    }

    override fun onCleared() {
        super.onCleared()
        println("GETTTZZZ.ViewGoalVM.onCleared ---> this.hashCode=${this.hashCode()}")
    }

    fun markGoalAsDone(goalUI: GoalUI, done: Boolean) = launch {
        goalUI.done = done
        updateGoalUC(presentationToDomainGoalMapper.transform(goalUI), ::processError) {}
    }

    fun updateSubGoals(goalUI: GoalUI, newSubGoals: ArrayList<SubGoalUI>) = launch {
        goalUI.subGoals as ArrayList
        goalUI.subGoals.clear()
        goalUI.subGoals.addAll(newSubGoals)
        updateGoalUC(presentationToDomainGoalMapper.transform(goalUI), ::processError) {}
    }

    fun deleteGoal(goalId: String) = launch {
        deleteGoalUC(goalId, ::processError) {}
    }

    companion object {
        fun getInstance(fragment: Fragment, factory: ViewGoalVMF) =
            ViewModelProviders.of(fragment, factory)[ViewGoalVM::class.java]
    }
}

class ViewGoalVMF(
    private val updateGoalUC: IUpdateGoalUC,
    private val deleteGoalUC: IDeleteGoalUC,
    private val presentationToDomainGoalMapper: Gandalf<GoalUI, Goal>
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        ViewGoalVM(updateGoalUC, deleteGoalUC, presentationToDomainGoalMapper) as T
}