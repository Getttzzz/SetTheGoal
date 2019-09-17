package com.getz.setthegoal.domainpart.repositorylayer

import com.getz.setthegoal.domainpart.core.IRepository
import com.getz.setthegoal.domainpart.entitylayer.Photo

interface IPhotoRepository : IRepository {
    suspend fun getPhotos(query: String, onResult: suspend (List<Photo>) -> Unit)
}