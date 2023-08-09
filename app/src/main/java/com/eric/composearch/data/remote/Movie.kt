package com.eric.composearch.data.remote

import com.google.gson.annotations.SerializedName


/**
 * Created by eric on 2022/5/6
 */
// 电影列表项
data class MoviePagingEntity(
    @field:SerializedName("count") val count: Int = 0,
    @field:SerializedName("start") val start: Int = 0,
    @field:SerializedName("total") val total: Int = 0,
    @field:SerializedName("sharing_info") val sharingInfo: SharingInfo? = null,
    @field:SerializedName("items") val movieItems: List<MovieItem> = listOf()
)

data class SharingInfo(
    @field:SerializedName("sharing_url") val sharingUrl: String = "",
    @field:SerializedName("title") val title: String = "",
    @field:SerializedName("screenshot_title") val screenshotTitle: String = "",
    @field:SerializedName("screenshot_url") val screenshotUrl: String = "",
    @field:SerializedName("screenshot_type") val screenshotType: String = "",
    @field:SerializedName("wechat_timeline_share") val wechatTimelineShare: String = ""
)

data class MovieItem(
    @field:SerializedName("comment") val comment: String = "",
    @field:SerializedName("rating") val rating: Rating? = null,
    @field:SerializedName("lineticket_url") val lineticketUrl: String = "",
    @field:SerializedName("has_linewatch") val hasLinewatch: Boolean = false,
    @field:SerializedName("pic") val pic: Pic? = null,
    @field:SerializedName("honor_infos") val honorInfos: List<HonorInfo>? = null,
    @field:SerializedName("uri") val uri: String = "",
    @field:SerializedName("photos") val photos: List<String>? = null,
    @field:SerializedName("vendor_icons") val vendorIcons: List<Any>? = null,
    @field:SerializedName("interest") val interest: Any? = null,
    @field:SerializedName("year") val year: String = "",
    @field:SerializedName("card_subtitle") val cardSubtitle: String = "",
    @field:SerializedName("title") val title: String = "",
    @field:SerializedName("type") val type: String = "",
    @field:SerializedName("id") val id: String = "",
    @field:SerializedName("tags") val tags: List<String>? = null
)

data class HonorInfo(
    @field:SerializedName("kind") val kind: String = "",
    @field:SerializedName("uri") val uri: String = "",
    @field:SerializedName("rank") val rank: Int = 0,
    @field:SerializedName("title") val title: String = ""
)

data class Rating(
    @field:SerializedName("count") val count: Int = 0,
    @field:SerializedName("max") val max: Int = 0,
    @field:SerializedName("star_count") val starCount: Float = 0f,
    @field:SerializedName("value") val value: Double = 0.0
)

data class Pic(
    @field:SerializedName("large") val large: String = "",
    @field:SerializedName("normal") val normal: String = ""
)

// 电影详情
data class MovieDetail(
    @field:SerializedName("rating") val rating: Rating? = null,
    @field:SerializedName("lineticket_url") val lineticketUrl: String = "",
    @field:SerializedName("rate_info") val rateInfo: String = "",
    @field:SerializedName("controversy_reason") val controversyReason: String = "",
    @field:SerializedName("pubdate") val pubDate: List<String>? = null,
    @field:SerializedName("last_episode_number") val lastEpisodeNumber: Any? = null,
    @field:SerializedName("interest_control_info") val interestControlInfo: Any? = null,
    @field:SerializedName("pic") val pic: Pic? = null,
    @field:SerializedName("year") val year: String = "",
    @field:SerializedName("vendor_count") val vendorCount: Int = 0,
    @field:SerializedName("body_bg_color") val bodyBgColor: String = "",
    @field:SerializedName("is_tv") val isTv: Boolean = false,
    @field:SerializedName("head_info") val headInfo: Any? = null,
    @field:SerializedName("album_no_interact") val albumNoInteract: Boolean = false,
    @field:SerializedName("ticket_price_info") val ticketPriceInfo: String = "",
    @field:SerializedName("vendor_icons") val vendorIcons: List<Any> = listOf(),
    @field:SerializedName("can_rate") val canRate: Boolean = false,
    @field:SerializedName("card_subtitle") val cardSubtitle: String = "",
    @field:SerializedName("forum_info") val forumInfo: Any? = null,
    @field:SerializedName("webisode") val webisode: Any? = null,
    @field:SerializedName("id") val id: String = "",
    @field:SerializedName("is_restrictive") val isRestrictive: Boolean = false,
    @field:SerializedName("gallery_topic_count") val galleryTopicCount: Int = 0,
    @field:SerializedName("languages") val languages: List<String>? = null,
    @field:SerializedName("genres") val genres: List<String>? = null,
    @field:SerializedName("review_count") val reviewCount: Int = 0,
    @field:SerializedName("title") val title: String = "",
    @field:SerializedName("intro") val intro: String = "",
    @field:SerializedName("interest_cmt_earlier_tip_title") val interestCmtEarlierTipTitle: String = "",
    @field:SerializedName("has_linewatch") val hasLinewatch: Boolean = false,
    @field:SerializedName("ugc_tabs") val ugcTabs: List<UgcTab> = listOf(),
    @field:SerializedName("forum_topic_count") val forumTopicCount: Int = 0,
    @field:SerializedName("ticket_promo_text") val ticketPromoText: String = "",
    @field:SerializedName("webview_info") val webviewInfo: Any? = null,
    @field:SerializedName("is_released") val isReleased: Boolean = false,
    @field:SerializedName("vendors") val vendors: List<Any>? = null,
    @field:SerializedName("actors") val actors: List<PersonName>? = null,
    @field:SerializedName("interest") val interest: Any? = null,
    @field:SerializedName("episodes_count") val episodesCount: Int = 0,
    @field:SerializedName("color_scheme") val colorScheme: ColorScheme? = null,
    @field:SerializedName("type") val type: String = "",
    @field:SerializedName("linewatches") val linewatches: List<Any> = listOf(),
    @field:SerializedName("info_url") val infoUrl: String = "",
    @field:SerializedName("tags") val tags: List<Any> = listOf(),
    @field:SerializedName("durations") val durations: List<String>? = null,
    @field:SerializedName("comment_count") val commentCount: Int = 0,
    @field:SerializedName("cover") val cover: Cover? = null,
    @field:SerializedName("cover_url") val coverUrl: String = "",
    @field:SerializedName("restrictive_icon_url") val restrictiveIconUrl: String = "",
    @field:SerializedName("header_bg_color") val headerBgColor: String = "",
    @field:SerializedName("is_douban_intro") val isDoubanIntro: Boolean = false,
    @field:SerializedName("ticket_vendor_icons") val ticketVendorIcons: List<String>? = null,
    @field:SerializedName("honor_infos") val honorInfos: List<HonorInfo>? = null,
    @field:SerializedName("trailer") val trailer: Trailer? = null,
    @field:SerializedName("sharing_url") val sharingUrl: String = "",
    @field:SerializedName("subject_collections") val subjectCollections: List<SubjectCollection>? = null,
    @field:SerializedName("wechat_timeline_share") val wechatTimelineShare: String = "",
    @field:SerializedName("countries") val countries: List<String>? = null,
    @field:SerializedName("url") val url: String = "",
    @field:SerializedName("release_date") val releaseDate: Any? = null,
    @field:SerializedName("original_title") val originalTitle: String = "",
    @field:SerializedName("uri") val uri: String = "",
    @field:SerializedName("pre_playable_date") val prePlayableDate: Any? = null,
    @field:SerializedName("episodes_info") val episodesInfo: String = "",
    @field:SerializedName("subtype") val subtype: String = "",
    @field:SerializedName("directors") val directors: List<PersonName>? = null,
    @field:SerializedName("is_show") val isShow: Boolean = false,
    @field:SerializedName("in_blacklist") val inBlacklist: Boolean = false,
    @field:SerializedName("pre_release_desc") val preReleaseDesc: String = "",
    @field:SerializedName("video") val video: Any? = null,
    @field:SerializedName("aka") val aka: List<String>? = null,
    @field:SerializedName("webisode_count") val webisodeCount: Int = 0,
    @field:SerializedName("null_rating_reason") val nullRatingReason: String = "",
    @field:SerializedName("interest_cmt_earlier_tip_desc") val interestCmtEarlierTipDesc: String = ""
)

data class UgcTab(
    @field:SerializedName("source") val source: String = "",
    @field:SerializedName("type") val type: String = "",
    @field:SerializedName("title") val title: String = ""
)


data class PersonName(
    @field:SerializedName("name") val name: String = ""
)

data class ColorScheme(
    @field:SerializedName("is_dark") val isDark: Boolean = false,
    @field:SerializedName("primary_color_light") val primaryColorLight: String = "",
    @field:SerializedName("_base_color") val baseColor: List<Double>? = null,
    @field:SerializedName("secondary_color") val secondaryColor: String = "",
    @field:SerializedName("_avg_color") val avgColor: List<Double>? = null,
    @field:SerializedName("primary_color_dark") val primaryColorDark: String = ""
)

data class SubjectCollection(
    @field:SerializedName("is_follow") val isFollow: Boolean = false,
    @field:SerializedName("title") val title: String = "",
    @field:SerializedName("id") val id: String = "",
    @field:SerializedName("uri") val uri: String = ""
)

data class Cover(
    @field:SerializedName("description") val description: String = "",
    @field:SerializedName("author") val author: Author = Author(),
    @field:SerializedName("url") val url: String = "",
    @field:SerializedName("image") val image: Image = Image(),
    @field:SerializedName("uri") val uri: String = "",
    @field:SerializedName("create_time") val createTime: String = "",
    @field:SerializedName("position") val position: Int = 0,
    @field:SerializedName("owner_uri") val ownerUri: String = "",
    @field:SerializedName("type") val type: String = "",
    @field:SerializedName("id") val id: String = "",
    @field:SerializedName("sharing_url") val sharingUrl: String = ""
)

data class Trailer(
    @field:SerializedName("sharing_url") val sharingUrl: String = "",
    @field:SerializedName("video_url") val videoUrl: String = "",
    @field:SerializedName("title") val title: String = "",
    @field:SerializedName("uri") val uri: String = "",
    @field:SerializedName("cover_url") val coverUrl: String = "",
    @field:SerializedName("term_num") val termNum: Int = 0,
    @field:SerializedName("n_comments") val nComments: Int = 0,
    @field:SerializedName("create_time") val createTime: String = "",
    @field:SerializedName("subject_title") val subjectTitle: String = "",
    @field:SerializedName("file_size") val fileSize: Int = 0,
    @field:SerializedName("runtime") val runtime: String = "",
    @field:SerializedName("type") val type: String = "",
    @field:SerializedName("id") val id: String = "",
    @field:SerializedName("desc") val desc: String = ""
)

data class Author(
    @field:SerializedName("loc") val loc: Loc? = null,
    @field:SerializedName("kind") val kind: String = "",
    @field:SerializedName("name") val name: String = "",
    @field:SerializedName("url") val url: String = "",
    @field:SerializedName("id") val id: String = "",
    @field:SerializedName("reg_time") val regTime: String = "",
    @field:SerializedName("uri") val uri: String = "",
    @field:SerializedName("avatar") val avatar: String = "",
    @field:SerializedName("is_club") val isClub: Boolean = false,
    @field:SerializedName("type") val type: String = "",
    @field:SerializedName("avatar_side_icon") val avatarSideIcon: String = "",
    @field:SerializedName("uid") val uid: String = ""
)

data class Image(
    @field:SerializedName("large") val large: ImageMetaData = ImageMetaData(),
    @field:SerializedName("raw") val raw: String? = null,
    @field:SerializedName("small") val small: ImageMetaData = ImageMetaData(),
    @field:SerializedName("is_animated") val isAnimated: Boolean = false,
    @field:SerializedName("normal") val normal: ImageMetaData = ImageMetaData()
)

data class Loc(
    @field:SerializedName("id") val id: String = "",
    @field:SerializedName("name") val name: String = "",
    @field:SerializedName("uid") val uid: String = ""
)

data class ImageMetaData(
    @field:SerializedName("url") val url: String = "",
    @field:SerializedName("width") val width: Int = 0,
    @field:SerializedName("height") val height: Int = 0,
    @field:SerializedName("size") val size: Int = 0
)

// 演职员
data class MovieCredits(
    @field:SerializedName("directors")
    val directors: List<Director> = listOf(),
    @field:SerializedName("total") val total: Int = 0,
    @field:SerializedName("actors") val actors: List<Actor> = listOf()
)

data class SimpleCharacterInfo(
    val name: String,
    val character: String,
    val avatar: Pic,
    val inDouban: Boolean
)

data class Director(
    @field:SerializedName("user") val user: Any? = null,
    @field:SerializedName("roles") val roles: List<String>? = null,
    @field:SerializedName("latin_name") val latinName: String = "",
    @field:SerializedName("author") val author: Any? = null,
    @field:SerializedName("url") val url: String = "",
    @field:SerializedName("abstract") val summary: String = "",
    @field:SerializedName("title") val title: String = "",
    @field:SerializedName("character") val character: String = "",
    @field:SerializedName("uri") val uri: String = "",
    @field:SerializedName("cover_url") val coverUrl: String = "",
    @field:SerializedName("avatar") val avatar: Pic = Pic(),
    @field:SerializedName("sharing_url") val sharingUrl: String = "",
    @field:SerializedName("type") val type: String = "",
    @field:SerializedName("id") val id: String = "",
    @field:SerializedName("name") val name: String = ""
) {
    fun toSimplifyInfo(): SimpleCharacterInfo {
        return SimpleCharacterInfo(name, character, avatar, user != null)
    }
}

data class Actor(
    @field:SerializedName("user") val user: ActorUser? = null,
    @field:SerializedName("roles") val roles: List<String>? = null,
    @field:SerializedName("latin_name") val latinName: String = "",
    @field:SerializedName("author") val author: Any? = null,
    @field:SerializedName("url") val url: String = "",
    @field:SerializedName("abstract") val summary: String = "",
    @field:SerializedName("title") val title: String = "",
    @field:SerializedName("character") val character: String = "",
    @field:SerializedName("uri") val uri: String = "",
    @field:SerializedName("cover_url") val coverUrl: String = "",
    @field:SerializedName("avatar") val avatar: Pic = Pic(),
    @field:SerializedName("sharing_url") val sharingUrl: String = "",
    @field:SerializedName("type") val type: String = "",
    @field:SerializedName("id") val id: String = "",
    @field:SerializedName("name") val name: String = ""
) {
    fun toSimplifyInfo(): SimpleCharacterInfo {
        return SimpleCharacterInfo(name, character, avatar, user != null)
    }
}

data class ActorUser(
    @field:SerializedName("loc") val loc: Loc = Loc(),
    @field:SerializedName("kind") val kind: String = "",
    @field:SerializedName("name") val name: String = "",
    @field:SerializedName("url") val url: String = "",
    @field:SerializedName("verify_type") val verifyType: Int = 0,
    @field:SerializedName("id") val id: String = "",
    @field:SerializedName("reg_time") val regTime: String = "",
    @field:SerializedName("uri") val uri: String = "",
    @field:SerializedName("avatar") val avatar: String = "",
    @field:SerializedName("is_club") val isClub: Boolean = false,
    @field:SerializedName("type") val type: String = "",
    @field:SerializedName("avatar_side_icon") val avatarSideIcon: String = "",
    @field:SerializedName("uid") val uid: String = ""
)

// 剧照
data class MoviePhotos(
    @field:SerializedName("count") val count: Int = 0,
    @field:SerializedName("c") val c: Int = 0,
    @field:SerializedName("f") val f: Int = 0,
    @field:SerializedName("o") val o: Int = 0,
    @field:SerializedName("n") val n: Int = 0,
    @field:SerializedName("photos") val photos: List<Photo> = listOf(),
    @field:SerializedName("w") val w: Int = 0,
    @field:SerializedName("total") val total: Int = 0,
    @field:SerializedName("start") val start: Int = 0
)

data class Photo(
    @field:SerializedName("read_count") val readCount: Int = 0,
    @field:SerializedName("image") val image: Image? = null,
    @field:SerializedName("create_time") val createTime: String = "",
    @field:SerializedName("collections_count") val collectionsCount: Int = 0,
    @field:SerializedName("reshares_count") val resharesCount: Int = 0,
    @field:SerializedName("id") val id: String = "",
    @field:SerializedName("author") val author: Author? = null,
    @field:SerializedName("is_collected") val isCollected: Boolean = false,
    @field:SerializedName("subtype") val subtype: String = "",
    @field:SerializedName("type") val type: String = "",
    @field:SerializedName("owner_uri") val ownerUri: String = "",
    @field:SerializedName("status") val status: Any? = null,
    @field:SerializedName("reaction_type") val reactionType: Int = 0,
    @field:SerializedName("description") val description: String = "",
    @field:SerializedName("sharing_url") val sharingUrl: String = "",
    @field:SerializedName("url") val url: String = "",
    @field:SerializedName("reply_limit") val replyLimit: String = "",
    @field:SerializedName("uri") val uri: String = "",
    @field:SerializedName("likers_count") val likersCount: Int = 0,
    @field:SerializedName("reactions_count") val reactionsCount: Int = 0,
    @field:SerializedName("comments_count") val commentsCount: Int = 0,
    @field:SerializedName("position") val position: Int = 0
)

// 相关影片
data class RelatedItems(
    @field:SerializedName("subjects") val subjects: List<Subject> = listOf(),
    @field:SerializedName("uri") val uri: String = "",
    @field:SerializedName("doulists") val doulists: List<DouList> = listOf()
)

data class Subject(
    @field:SerializedName("rating") val rating: Rating? = null,
    @field:SerializedName("alg_json") val algJson: String = "",
    @field:SerializedName("sharing_url") val sharingUrl: String = "",
    @field:SerializedName("title") val title: String = "",
    @field:SerializedName("url") val url: String = "",
    @field:SerializedName("pic") val pic: Pic? = null,
    @field:SerializedName("uri") val uri: String = "",
    @field:SerializedName("interest") val interest: Any? = null,
    @field:SerializedName("type") val type: String = "",
    @field:SerializedName("id") val id: String = ""
)

data class DouList(
    @field:SerializedName("is_official") val isOfficial: Boolean = false,
    @field:SerializedName("id") val id: String = "",
    @field:SerializedName("category") val category: String = "",
    @field:SerializedName("total") val total: Int = 0,
    @field:SerializedName("is_merged_cover") val isMergedCover: Boolean = false,
    @field:SerializedName("title") val title: String = "",
    @field:SerializedName("icon_text") val iconText: String = "",
    @field:SerializedName("followers_count") val followersCount: Int = 0,
    @field:SerializedName("badge") val badge: Any? = null,
    @field:SerializedName("rank_type") val rankType: String = "",
    @field:SerializedName("alg_json") val algJson: String = "",
    @field:SerializedName("cover_url") val coverUrl: String = "",
    @field:SerializedName("done_count") val doneCount: Int = 0,
    @field:SerializedName("subject_count") val subjectCount: Int = 0,
    @field:SerializedName("items_count") val itemsCount: Int = 0,
    @field:SerializedName("sharing_url") val sharingUrl: String = "",
    @field:SerializedName("collect_count") val collectCount: Int = 0,
    @field:SerializedName("url") val url: String = "",
    @field:SerializedName("type") val type: String = "",
    @field:SerializedName("is_badge_chart") val isBadgeChart: Boolean = false,
    @field:SerializedName("uri") val uri: String = "",
    @field:SerializedName("finish_soon") val finishSoon: Boolean = false,
    @field:SerializedName("list_type") val listType: String = "",
    @field:SerializedName("syncing_note") val syncingNote: Any? = null,
    @field:SerializedName("is_subject_selection") val isSubjectSelection: Boolean = false,
    @field:SerializedName("doulist_type") val doulistType: String = "",
    @field:SerializedName("owner") val owner: Owner? = null
)

data class Owner(
    @field:SerializedName("kind") val kind: String = "",
    @field:SerializedName("name") val name: String = "",
    @field:SerializedName("url") val url: String = "",
    @field:SerializedName("uri") val uri: String = "",
    @field:SerializedName("avatar") val avatar: String = "",
    @field:SerializedName("is_club") val isClub: Boolean = false,
    @field:SerializedName("type") val type: String = "",
    @field:SerializedName("id") val id: String = "",
    @field:SerializedName("uid") val uid: String = ""
)