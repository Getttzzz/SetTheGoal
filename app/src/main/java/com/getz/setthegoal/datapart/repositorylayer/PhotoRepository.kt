package com.getz.setthegoal.datapart.repositorylayer

import com.getz.setthegoal.datapart.core.BaseRepository
import com.getz.setthegoal.datapart.datasource.IPhotoDS
import com.getz.setthegoal.datapart.entitylayer.SearchPhotoResponse
import com.getz.setthegoal.domainpart.core.Gandalf
import com.getz.setthegoal.domainpart.entitylayer.Photo
import com.getz.setthegoal.domainpart.repositorylayer.IPhotoRepository

class PhotoRepository(
    private val remotePhotoDS: IPhotoDS,
    private val gandalfMapper: Gandalf<SearchPhotoResponse, List<Photo>>
) : BaseRepository(), IPhotoRepository {

    override suspend fun getPhotos(query: String, onResult: suspend (List<Photo>) -> Unit) {
        remotePhotoDS.getPhotosAsync(query) { response ->
            onResult(gandalfMapper.transform(response))
        }
    }

    override suspend fun markAsDownloadedForUnsplash(incrementDownloadLink: String) {
        remotePhotoDS.markAsDownloadedForUnsplash(incrementDownloadLink)
    }
}