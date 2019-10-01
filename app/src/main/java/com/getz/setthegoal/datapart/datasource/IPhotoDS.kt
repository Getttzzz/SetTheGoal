package com.getz.setthegoal.datapart.datasource

import com.getz.setthegoal.datapart.entitylayer.SearchPhotoResponse

interface IPhotoDS {
    suspend fun getPhotosAsync(
        query: String,
        onResult: suspend (SearchPhotoResponse) -> Unit
    )

    suspend fun markAsDownloadedForUnsplash(incrementDownloadLink: String)
}