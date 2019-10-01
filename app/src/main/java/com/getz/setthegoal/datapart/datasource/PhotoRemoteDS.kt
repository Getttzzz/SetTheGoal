package com.getz.setthegoal.datapart.datasource

import com.getz.setthegoal.BuildConfig
import com.getz.setthegoal.datapart.api.UnsplashApi
import com.getz.setthegoal.datapart.entitylayer.SearchPhotoResponse

class PhotoRemoteDS(
    val api: UnsplashApi
) : IPhotoDS {

    override suspend fun getPhotosAsync(
        query: String,
        onResult: suspend (SearchPhotoResponse) -> Unit
    ) {
        val response = api.searchPhotos(
            clientId = BuildConfig.UnsplashClientKey,
            query = query,
            page = 1,
            perPage = 10
        )

        if (response.results.isNotEmpty()) {
            onResult(response)
        } else {
            throw Throwable("Result was empty.")
        }
    }

    override suspend fun markAsDownloadedForUnsplash(incrementDownloadLink: String) {
        api.markAsDownloadedForUnsplash(
            downloadLinkToTrigger = incrementDownloadLink,
            clientId = BuildConfig.UnsplashClientKey
        )
    }
}