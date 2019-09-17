package com.getz.setthegoal.domainpart.interactorlayer

import com.getz.setthegoal.domainpart.core.IRequestableUseCase
import com.getz.setthegoal.domainpart.entitylayer.Photo
import java.util.Locale

interface IGetPhotoUC : IRequestableUseCase<Pair<String, Locale>, List<Photo>>