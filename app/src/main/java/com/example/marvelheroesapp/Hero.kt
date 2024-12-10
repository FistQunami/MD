import com.squareup.moshi.Json

data class MarvelResponse(
    val code: Int,
    val status: String,
    val copyright: String,
    val attributionText: String,
    val attributionHTML: String,
    val data: MarvelDataContainer,
    val etag: String
)

data class MarvelDataContainer(
    val offset: Int,
    val limit: Int,
    val total: Int,
    val count: Int,
    val results: List<Hero>
)

data class Hero(
    val id: Int,
    val name: String,
    val description: String,
    val modified: String,
    val resourceURI: String,
    val urls: List<Url>,
    val thumbnail: Thumbnail,
    val comics: ComicList,
    val stories: StoryList,
    val events: EventList,
    val series: SeriesList
)

data class Url(
    val type: String,
    val url: String
)

data class Thumbnail(
    val path: String,
    val extension: String
) {
    val fullUrl: String get() = "$path.$extension"
}

data class ComicList(
    val available: Int,
    val returned: Int,
    @Json(name = "collectionURI") val collectionUri: String,
    val items: List<ComicSummary>
)

data class ComicSummary(
    val resourceURI: String,
    val name: String
)

data class StoryList(
    val available: Int,
    val returned: Int,
    @Json(name = "collectionURI") val collectionUri: String,
    val items: List<StorySummary>
)

data class StorySummary(
    val resourceURI: String,
    val name: String,
    val type: String
)

data class EventList(
    val available: Int,
    val returned: Int,
    @Json(name = "collectionURI") val collectionUri: String,
    val items: List<EventSummary>
)

data class EventSummary(
    val resourceURI: String,
    val name: String
)

data class SeriesList(
    val available: Int,
    val returned: Int,
    @Json(name = "collectionURI") val collectionUri: String,
    val items: List<SeriesSummary>
)

data class SeriesSummary(
    val resourceURI: String,
    val name: String
)