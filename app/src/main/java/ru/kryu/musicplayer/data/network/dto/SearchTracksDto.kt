package ru.kryu.musicplayer.data.network.dto


import com.google.gson.annotations.SerializedName

data class SearchTracksDto(
    @SerializedName("data")
    val `data`: List<Data?>?,
    @SerializedName("total")
    val total: Int?,
    @SerializedName("next")
    val next: String?
) {
    data class Data(
        @SerializedName("id")
        val id: Long?,
        @SerializedName("readable")
        val readable: Boolean?,
        @SerializedName("title")
        val title: String?,
        @SerializedName("title_short")
        val titleShort: String?,
        @SerializedName("title_version")
        val titleVersion: String?,
        @SerializedName("link")
        val link: String?,
        @SerializedName("duration")
        val duration: Int?,
        @SerializedName("rank")
        val rank: Int?,
        @SerializedName("explicit_lyrics")
        val explicitLyrics: Boolean?,
        @SerializedName("explicit_content_lyrics")
        val explicitContentLyrics: Int?,
        @SerializedName("explicit_content_cover")
        val explicitContentCover: Int?,
        @SerializedName("preview")
        val preview: String?,
        @SerializedName("md5_image")
        val md5Image: String?,
        @SerializedName("artist")
        val artist: Artist?,
        @SerializedName("album")
        val album: Album?,
        @SerializedName("type")
        val type: String?
    ) {
        data class Artist(
            @SerializedName("id")
            val id: Int?,
            @SerializedName("name")
            val name: String?,
            @SerializedName("link")
            val link: String?,
            @SerializedName("picture")
            val picture: String?,
            @SerializedName("picture_small")
            val pictureSmall: String?,
            @SerializedName("picture_medium")
            val pictureMedium: String?,
            @SerializedName("picture_big")
            val pictureBig: String?,
            @SerializedName("picture_xl")
            val pictureXl: String?,
            @SerializedName("tracklist")
            val tracklist: String?,
            @SerializedName("type")
            val type: String?
        )

        data class Album(
            @SerializedName("id")
            val id: Int?,
            @SerializedName("title")
            val title: String?,
            @SerializedName("cover")
            val cover: String?,
            @SerializedName("cover_small")
            val coverSmall: String?,
            @SerializedName("cover_medium")
            val coverMedium: String?,
            @SerializedName("cover_big")
            val coverBig: String?,
            @SerializedName("cover_xl")
            val coverXl: String?,
            @SerializedName("md5_image")
            val md5Image: String?,
            @SerializedName("tracklist")
            val tracklist: String?,
            @SerializedName("type")
            val type: String?
        )
    }
}