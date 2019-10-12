package com.getz.setthegoal.domainpart.interactorlayer

import com.getz.setthegoal.domainpart.core.IRequestableUseCase
import com.getz.setthegoal.domainpart.entitylayer.Goal

interface IGetUnfinishedGoalsUC : IRequestableUseCase<Unit, List<Goal>>