package ru.kryu.musicplayer.data.network.dto


import com.google.gson.annotations.SerializedName

data class ChartDto(
    @SerializedName("tracks")
    val tracks: Tracks?,
    @SerializedName("albums")
    val albums: Albums?,
    @SerializedName("artists")
    val artists: Artists?,
    @SerializedName("playlists")
    val playlists: Playlists?,
    @SerializedName("podcasts")
    val podcasts: Podcasts?
) {
    data class Tracks(
        @SerializedName("data")
        val `data`: List<Data?>?,
        @SerializedName("total")
        val total: Int?
    ) {
        data class Data(
            @SerializedName("id")
            val id: Long?,
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
            @SerializedName("position")
            val position: Int?,
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
                @SerializedName("radio")
                val radio: Boolean?,
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

    data class Albums(
        @SerializedName("data")
        val `data`: List<Data?>?,
        @SerializedName("total")
        val total: Int?
    ) {
        data class Data(
            @SerializedName("id")
            val id: Int?,
            @SerializedName("title")
            val title: String?,
            @SerializedName("link")
            val link: String?,
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
            @SerializedName("record_type")
            val recordType: String?,
            @SerializedName("tracklist")
            val tracklist: String?,
            @SerializedName("explicit_lyrics")
            val explicitLyrics: Boolean?,
            @SerializedName("position")
            val position: Int?,
            @SerializedName("artist")
            val artist: Artist?,
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
                @SerializedName("radio")
                val radio: Boolean?,
                @SerializedName("tracklist")
                val tracklist: String?,
                @SerializedName("type")
                val type: String?
            )
        }
    }

    data class Artists(
        @SerializedName("data")
        val `data`: List<Data?>?,
        @SerializedName("total")
        val total: Int?
    ) {
        data class Data(
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
            @SerializedName("radio")
            val radio: Boolean?,
            @SerializedName("tracklist")
            val tracklist: String?,
            @SerializedName("position")
            val position: Int?,
            @SerializedName("type")
            val type: String?
        )
    }

    data class Playlists(
        @SerializedName("data")
        val `data`: List<Data?>?,
        @SerializedName("total")
        val total: Int?
    ) {
        data class Data(
            @SerializedName("id")
            val id: Long?,
            @SerializedName("title")
            val title: String?,
            @SerializedName("public")
            val `public`: Boolean?,
            @SerializedName("nb_tracks")
            val nbTracks: Int?,
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
            @SerializedName("checksum")
            val checksum: String?,
            @SerializedName("tracklist")
            val tracklist: String?,
            @SerializedName("creation_date")
            val creationDate: String?,
            @SerializedName("add_date")
            val addDate: String?,
            @SerializedName("mod_date")
            val modDate: String?,
            @SerializedName("md5_image")
            val md5Image: String?,
            @SerializedName("picture_type")
            val pictureType: String?,
            @SerializedName("user")
            val user: User?,
            @SerializedName("type")
            val type: String?
        ) {
            data class User(
                @SerializedName("id")
                val id: Long?,
                @SerializedName("name")
                val name: String?,
                @SerializedName("tracklist")
                val tracklist: String?,
                @SerializedName("type")
                val type: String?
            )
        }
    }

    data class Podcasts(
        @SerializedName("data")
        val `data`: List<Data?>?,
        @SerializedName("total")
        val total: Int?
    ) {
        data class Data(
            @SerializedName("id")
            val id: Int?,
            @SerializedName("title")
            val title: String?,
            @SerializedName("description")
            val description: String?,
            @SerializedName("available")
            val available: Boolean?,
            @SerializedName("fans")
            val fans: Int?,
            @SerializedName("link")
            val link: String?,
            @SerializedName("share")
            val share: String?,
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
            @SerializedName("type")
            val type: String?
        )
    }
}