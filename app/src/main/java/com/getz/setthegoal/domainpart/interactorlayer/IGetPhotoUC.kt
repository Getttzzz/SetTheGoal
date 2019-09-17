package com.getz.setthegoal.domainpart.interactorlayer

import com.getz.setthegoal.domainpart.core.IRequestableUseCase
import com.getz.setthegoal.domainpart.entitylayer.Photo
import com.getz.setthegoal.domainpart.entitylayer.Word
import java.util.Locale

interface IGetPhotoUC : IRequestableUseCase<Pair<Word, Locale>, List<Photo>>