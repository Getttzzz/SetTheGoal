package com.getz.setthegoal.datapart.entitylayer

import com.google.gson.annotations.SerializedName

data class SearchPhotoResponse(
    @SerializedName("total") val total: Int,
    @SerializedName("total_pages") val total_pages: Int,
    @SerializedName("results") val results: List<Results>
)

data class Results(
    @SerializedName("id") val id: String,
    @SerializedName("created_at") val created_at: String,
    @SerializedName("updated_at") val updated_at: String,
    @SerializedName("width") val width: Int,
    @SerializedName("height") val height: Int,
    @SerializedName("color") val color: String,
    @SerializedName("description") val description: String,
    @SerializedName("alt_description") val alt_description: String,
    @SerializedName("urls") val urls: Urls,
    @SerializedName("links") val links: LinksResult,
    @SerializedName("categories") val categories: List<String>,
    @SerializedName("likes") val likes: Int,
    @SerializedName("liked_by_user") val liked_by_user: Boolean,
    @SerializedName("current_user_collections") val current_user_collections: List<String>,
    @SerializedName("user") val user: User
//    @SerializedName("tags") val tags: List<Tags>
)

data class LinksUser(
    @SerializedName("self") val self: String,
    @SerializedName("html") val html: String,
    @SerializedName("photos") val photos: String,
    @SerializedName("likes") val likes: String,
    @SerializedName("portfolio") val portfolio: String,
    @SerializedName("following") val following: String,
    @SerializedName("followers") val followers: String
)

data class LinksResult(
    @SerializedName("self") val self: String,
    @SerializedName("html") val html: String,
    @SerializedName("download") val download: String,
    @SerializedName("download_location") val downloadLocation: String
)

data class User(
    @SerializedName("id") val id: String,
    @SerializedName("updated_at") val updated_at: String,
    @SerializedName("username") val username: String,
    @SerializedName("name") val name: String,
    @SerializedName("first_name") val first_name: String,
    @SerializedName("last_name") val last_name: String,
    @SerializedName("twitter_username") val twitter_username: String,
    @SerializedName("portfolio_url") val portfolio_url: String,
    @SerializedName("bio") val bio: String,
    @SerializedName("location") val location: String,
    @SerializedName("links") val links: LinksUser,
    @SerializedName("profile_image") val profile_image: Profile_image,
    @SerializedName("instagram_username") val instagram_username: String,
    @SerializedName("total_collections") val total_collections: Int,
    @SerializedName("total_likes") val total_likes: Int,
    @SerializedName("total_photos") val total_photos: Int,
    @SerializedName("accepted_tos") val accepted_tos: Boolean
)

data class Urls(
    @SerializedName("raw") val raw: String,
    @SerializedName("full") val full: String,
    @SerializedName("regular") val regular: String,
    @SerializedName("small") val small: String,
    @SerializedName("thumb") val thumb: String
)

data class Profile_image(
    @SerializedName("small") val small: String,
    @SerializedName("medium") val medium: String,
    @SerializedName("large") val large: String
)

//data class Type(
//    @SerializedName("slug") val slug: String,
//    @SerializedName("pretty_slug") val pretty_slug: String
//)

//data class Tags(
//    @SerializedName("type") val type: String,
//    @SerializedName("title") val title: String,
//    @SerializedName("source") val source: Source
//)

//data class Subcategory(
//    @SerializedName("slug") val slug: String,
//    @SerializedName("pretty_slug") val pretty_slug: String
//)

//data class Source(
//    @SerializedName("ancestry") val ancestry: Ancestry,
//    @SerializedName("title") val title: String,
//    @SerializedName("subtitle") val subtitle: String,
//    @SerializedName("description") val description: String,
//    @SerializedName("meta_title") val meta_title: String,
//    @SerializedName("meta_description") val meta_description: String,
//    @SerializedName("cover_photo") val cover_photo: Cover_photo
//)

//data class Cover_photo(
//    @SerializedName("id") val id: String,
//    @SerializedName("created_at") val created_at: String,
//    @SerializedName("updated_at") val updated_at: String,
//    @SerializedName("width") val width: Int,
//    @SerializedName("height") val height: Int,
//    @SerializedName("color") val color: String,
//    @SerializedName("description") val description: String,
//    @SerializedName("alt_description") val alt_description: String,
//    @SerializedName("urls") val urls: Urls,
//    @SerializedName("links") val links: LinksUser,
//    @SerializedName("categories") val categories: List<String>,
//    @SerializedName("likes") val likes: Int,
//    @SerializedName("liked_by_user") val liked_by_user: Boolean,
//    @SerializedName("current_user_collections") val current_user_collections: List<String>,
//    @SerializedName("user") val user: User
//)

//data class Category(
//    @SerializedName("slug") val slug: String,
//    @SerializedName("pretty_slug") val pretty_slug: String
//)

//data class Ancestry(
//    @SerializedName("type") val type: Type,
//    @SerializedName("category") val category: Category,
//    @SerializedName("subcategory") val subcategory: Subcategory
//)