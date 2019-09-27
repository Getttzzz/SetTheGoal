package com.getz.setthegoal.domainpart.interactorlayer

import com.getz.setthegoal.domainpart.core.IWritableUseCase
import com.getz.setthegoal.domainpart.entitylayer.Goal

interface ICreateGoalUC : IWritableUseCase<Goal, Boolean>