package com.getz.setthegoal.datapart.mapper

import com.getz.setthegoal.datapart.entitylayer.SearchPhotoResponse
import com.getz.setthegoal.domainpart.core.Gandalf
import com.getz.setthegoal.domainpart.entitylayer.Photo
import com.getz.setthegoal.domainpart.entitylayer.UrlsDomain

class DataToDomainPhotoMapper : Gandalf<SearchPhotoResponse, List<Photo>> {

    override fun transform(source: SearchPhotoResponse): List<Photo> {
        val resultedList = arrayListOf<Photo>()
        source.results.forEach { result ->

            val urlsForUI = UrlsDomain(
                result.urls.raw,
                result.urls.full,
                result.urls.regular,
                result.urls.small,
                result.urls.thumb
            )

            resultedList.add(
                Photo(
                    urls = urlsForUI,
                    incrementDownloadLink = result.links.downloadLocation,
                    userName = result.user.name,
                    profileLink = result.user.links.html
                )
            )
        }
        return resultedList
    }
}