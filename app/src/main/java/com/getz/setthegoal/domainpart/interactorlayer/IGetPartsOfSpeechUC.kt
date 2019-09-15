package com.getz.setthegoal.domainpart.interactorlayer

import com.getz.setthegoal.domainpart.core.IRequestableUseCase
import com.getz.setthegoal.domainpart.entitylayer.Word

interface IGetPartsOfSpeechUC : IRequestableUseCase<String, List<Word>>