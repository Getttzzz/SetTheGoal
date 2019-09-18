package com.getz.setthegoal.presentationpart.mapper

import com.getz.setthegoal.domainpart.core.Gandalf
import com.getz.setthegoal.domainpart.entitylayer.Photo
import com.getz.setthegoal.presentationpart.entitylayer.PhotoUI
import com.getz.setthegoal.presentationpart.entitylayer.UrlsUI

class DomainToPresentationPhotoMapper : Gandalf<List<Photo>, List<PhotoUI>> {
    override fun transform(source: List<Photo>): List<PhotoUI> {
        val resultedList = arrayListOf<PhotoUI>()
        source.forEach { photo ->
            val urls = UrlsUI(
                photo.urls.raw,
                photo.urls.full,
                photo.urls.regular,
                photo.urls.small,
                photo.urls.thumb
            )
            resultedList.add(
                PhotoUI(
                    urls = urls,
                    incrementDownloadLink = photo.incrementDownloadLink,
                    userName = photo.userName,
                    profileLink = photo.profileLink,
                    isSelected = false
                )
            )
        }
        return resultedList
    }
}