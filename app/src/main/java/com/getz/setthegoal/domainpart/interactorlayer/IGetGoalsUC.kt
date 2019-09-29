package com.getz.setthegoal.domainpart.interactorlayer

import com.getz.setthegoal.domainpart.core.ISuspendlessUseCase
import com.getz.setthegoal.domainpart.entitylayer.Goal

interface IGetGoalsUC : ISuspendlessUseCase<Unit, List<Goal>>