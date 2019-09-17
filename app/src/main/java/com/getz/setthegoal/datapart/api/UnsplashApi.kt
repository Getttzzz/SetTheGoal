package com.getz.setthegoal.datapart.api

import com.getz.setthegoal.datapart.entitylayer.SearchPhotoResponse
import retrofit2.http.GET
import retrofit2.http.Query


/**
 * Unsplash api requires next thing to implement (to get product api key):
 * 1.Hotlinking (I can't save image, I should call link to it because it brings views for author.
 * 2.Trigger "download_location" url when user selects pic as background or smthg like it. (This url
 *  increments download counter for contributor)
 * 3.Add attribute for selected photo. Uses this template Photo by Annie Spratt on Unsplash:
 *          Photo by
 *      <a href="https://unsplash.com/@anniespratt?utm_source=your_app_name&utm_medium=referral">
 *          Annie Spratt
 *      </a>
 *          on
 *      <a href="https://unsplash.com/?utm_source=your_app_name&utm_medium=referral">
 *          Unsplash
 *      </a>
 * 4.client_id and secret_id should keep in secret and don't push them to public repo.
 * */
interface UnsplashApi {

    @GET("search/photos")
    suspend fun searchPhotos(
        @Query("client_id") clientId: String,
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
//        @Query("orientation") orientation: String
    ): SearchPhotoResponse
}