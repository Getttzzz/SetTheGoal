package com.getz.setthegoal.presentationpart.feature.viewgoaldetails

import com.getz.setthegoal.domainpart.core.Gandalf
import com.getz.setthegoal.domainpart.entitylayer.Goal
import com.getz.setthegoal.domainpart.interactorlayer.IDeleteGoalUC
import com.getz.setthegoal.domainpart.interactorlayer.IUpdateGoalUC
import com.getz.setthegoal.presentationpart.core.BaseVm
import com.getz.setthegoal.presentationpart.entitylayer.GoalUI
import com.getz.setthegoal.presentationpart.entitylayer.SubGoalUI
import kotlinx.coroutines.launch

class ViewGoalVM(
    private val updateGoalUC: IUpdateGoalUC,
    private val deleteGoalUC: IDeleteGoalUC,
    private val presentationToDomainGoalMapper: Gandalf<GoalUI, Goal>
) : BaseVm() {

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
}