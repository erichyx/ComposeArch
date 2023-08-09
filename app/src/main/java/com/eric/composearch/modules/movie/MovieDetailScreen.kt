package com.eric.composearch.modules.movie

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIos
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.eric.composearch.R
import com.eric.composearch.data.remote.*
import com.eric.composearch.ui.DetectNetworkState
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.fade
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarConfig

/**
 * Created by eric on 2022/5/12
 */
@Composable
fun MovieDetailScreen(
    movieId: String,
    upPress: () -> Unit,
    movieDetailViewModel: MovieDetailViewModel = viewModel()
) {
    DetectNetworkState {
        MovieDetailContent(movieId, upPress, movieDetailViewModel)
    }
}

@Composable
fun MovieDetailContent(
    movieId: String,
    upPress: () -> Unit,
    movieDetailViewModel: MovieDetailViewModel = viewModel()
) {
    LaunchedEffect(Unit) {
        movieDetailViewModel.fetchMovieDetail(movieId)
        movieDetailViewModel.fetchMovieCredits(movieId)
        movieDetailViewModel.fetchMoviePhotos(movieId)
        movieDetailViewModel.fetchRelatedItems(movieId)
    }

    val movieDetail by movieDetailViewModel.movieDetail.collectAsState()
    val bgColor by remember(movieDetail) {
        val color = if (movieDetail != null) {
            val colorVal = "ff${movieDetail?.headerBgColor}".toLong(16)
            Color(colorVal)
        } else {
            Color.White
        }
        mutableStateOf(color)
    }

    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setStatusBarColor(bgColor)
    }

    Column(
        Modifier
            .statusBarsPadding()
            .fillMaxSize()
            .background(bgColor)
    ) {
        // 标题
        TitleBar(
            Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(horizontal = 16.dp)
        ) {
            upPress()
        }

        val scrollState = rememberScrollState()
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(scrollState),
            Arrangement.spacedBy(16.dp)
        ) {
            // 电影海报和基本信息
            MovieHeader(
                movieDetail,
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .placeholder(
                        visible = movieDetail == null,
                        highlight = PlaceholderHighlight.fade()
                    )
            )
            // 电影简介
            MovieIntro(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                movieDetail?.intro ?: ""
            )
            // 演职员照片
            val credits by movieDetailViewModel.movieCredits.collectAsState()
            MovieCredits(
                credits,
                Modifier.fillMaxWidth()
            )
            // 预告片/剧照
            val moviePhotos by movieDetailViewModel.moviePhotos.collectAsState()
            MovieTrailerAndPhotos(movieDetail?.trailer, moviePhotos, Modifier.fillMaxWidth())
            // 同类的喜欢项
            val relatedItem by movieDetailViewModel.relatedItems.collectAsState()
            MovieRelatedItems(relatedItem, Modifier.fillMaxWidth())
        }
    }
}

@Composable
fun TitleBar(modifier: Modifier = Modifier, onBackArrowClick: () -> Unit) {
    Box(modifier) {
        Icon(
            imageVector = Icons.Outlined.ArrowBackIos,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .clickable(onClick = onBackArrowClick),
        )
        Text(
            text = stringResource(id = R.string.title_movie_detail),
            color = Color.White,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0x4c4834, widthDp = 200)
@Composable
fun PreviewTitleBar() {
    TitleBar(
        Modifier
            .fillMaxWidth()
            .height(56.dp)
    ) {}
}

@Composable
fun MovieHeader(movieDetail: MovieDetail?, modifier: Modifier = Modifier) {
    if (movieDetail == null) return

    Row(modifier) {
        AsyncImage(
            movieDetail.coverUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .weight(1.8f)
                .aspectRatio(1.8f / 2.5f)
                .clip(RoundedCornerShape(4.dp))
        )
        Column(
            Modifier
                .padding(start = 16.dp)
                .weight(4.5f)
        ) {
            Text(text = movieDetail.title, color = Color.White, fontSize = 20.sp)
            Text(
                text = "${movieDetail.originalTitle} (${movieDetail.year})",
                color = Color.White,
                fontSize = 14.sp
            )
            if (!movieDetail.honorInfos.isNullOrEmpty()) {
                val honorInfo = movieDetail.honorInfos[0]
                Row(
                    Modifier
                        .padding(vertical = 10.dp)
                        .clip(RoundedCornerShape(4.dp))
                ) {
                    Text(
                        text = "No.${honorInfo.rank}", color = Color(0xFF915800),
                        modifier = Modifier
                            .background(Color(0xFFFFE6BE))
                            .padding(horizontal = 8.dp, vertical = 4.dp), fontSize = 12.sp
                    )
                    Text(
                        text = "${honorInfo.title} >",
                        modifier = Modifier
                            .wrapContentWidth()
                            .background(Color(0xFFFABB5A))
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                        color = Color(0xFF915800), overflow = TextOverflow.Ellipsis,
                        maxLines = 1, fontSize = 12.sp
                    )
                }
            }
            var desc =
                "${movieDetail.countries?.get(0)} / ${movieDetail.genres?.joinToString(" ")} / ${
                    movieDetail.pubDate?.get(0)
                }上映"
            desc += if (movieDetail.durations?.isNotEmpty() == true) {
                " / 片长${movieDetail.durations[0]} >"
            } else {
                " >"
            }
            Text(text = desc, color = Color.White.copy(alpha = 0.5f), fontSize = 12.sp)
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0x4c4834, widthDp = 400)
@Composable
fun PreviewMovieHeader() {
    val movieDetail = MovieDetail(
        title = "神奇动物：邓布利多之谜",
        originalTitle = "Fantastic Beasts: The Secrets of Dumbledore",
        year = "2022",
        honorInfos = listOf(HonorInfo(rank = 12, title = "2022最值得期待的影视")),
        countries = listOf("英国", "美国"),
        genres = listOf("奇幻", "冒险"),
        pubDate = listOf("2022-04-08(中国大陆)"),
        durations = listOf("142分钟")
    )
    MovieHeader(movieDetail, Modifier.padding(horizontal = 8.dp))
}

@Composable
fun MovieIntro(modifier: Modifier = Modifier, movieIntro: String) {
    Column(modifier) {
        Text(
            text = stringResource(id = R.string.label_intro),
            modifier = Modifier.padding(vertical = 8.dp),
            fontSize = 18.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
        Text(text = movieIntro, color = Color.White, lineHeight = 26.sp)
    }
}

@Composable
fun MovieCredits(credits: MovieCredits?, modifier: Modifier = Modifier) {
    Column(modifier) {
        Text(
            text = stringResource(id = R.string.label_movie_credits),
            Modifier.padding(start = 16.dp, bottom = 16.dp),
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        if (credits != null) {
            val configuration = LocalConfiguration.current
            val availableWidthDp = configuration.screenWidthDp.dp - 16.dp * 5

            LazyRow(
                Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                itemsIndexed(credits.directors) { index, director ->
                    CharacterCard(
                        director.toSimplifyInfo(),
                        Modifier.width(availableWidthDp / 4)
                    )
                }
                itemsIndexed(credits.actors) { index, actor ->
                    CharacterCard(
                        actor.toSimplifyInfo(),
                        Modifier.width(availableWidthDp / 4)
                    )
                }
            }
        }
    }
}

@Composable
fun CharacterCard(characterInfo: SimpleCharacterInfo, modifier: Modifier = Modifier) {
    Column(modifier) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.45f / 2f)
        ) {
            AsyncImage(
                model = characterInfo.avatar.normal,
                contentDescription = null,
                Modifier
                    .fillMaxWidth()
                    .clip(
                        RoundedCornerShape(8.dp)
                    ),
                contentScale = ContentScale.Crop
            )
            if (characterInfo.inDouban) {
                Text(
                    text = stringResource(id = R.string.label_in_douban),
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .padding(bottom = 2.dp)
                        .align(Alignment.BottomStart)
                        .background(Color(0xFFFF9600))
                        .padding(vertical = 2.dp),
                    fontSize = 10.sp
                )
            }
        }

        Text(
            text = characterInfo.name,
            Modifier.padding(top = 4.dp),
            color = Color.White,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis, fontSize = 14.sp
        )
        Text(
            text = characterInfo.character,
            color = Color.White.copy(alpha = 0.5f),
            fontSize = 12.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun MovieTrailerAndPhotos(
    trailer: Trailer?,
    moviePhotos: MoviePhotos?,
    modifier: Modifier = Modifier
) {
    if (trailer == null && moviePhotos?.photos.isNullOrEmpty()) return

    val configuration = LocalConfiguration.current
    val availableWidth = remember(configuration) {
        configuration.screenWidthDp.dp - 16.dp * 2
    }

    Column(modifier) {
        Text(
            text = stringResource(id = R.string.label_movie_photos),
            Modifier.padding(start = 16.dp, bottom = 16.dp),
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        if (moviePhotos != null) {
            LazyRow(
                Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                val bigPhotoModifier = Modifier
                    .width(availableWidth * 2 / 3)
                    .aspectRatio(4.35f / 2.9f)

                if (trailer != null) {
                    item {
                        TrailerItem(trailer, bigPhotoModifier)
                    }
                }

                val bigPhotoList = if (moviePhotos.photos.size >= 2) {
                    moviePhotos.photos.subList(0, 2)
                } else {
                    moviePhotos.photos
                }
                items(bigPhotoList) { photo ->
                    AsyncImage(
                        model = photo.image?.normal?.url,
                        contentDescription = photo.description,
                        bigPhotoModifier,
                        contentScale = ContentScale.Crop
                    )
                }

                val gridPhotoList = moviePhotos.photos.subList(2, moviePhotos.photos.size)
                val smallPhotoWidth = availableWidth - 4.dp
                val smallPhotoGridHeight = availableWidth * 2 / 3 * 2.9f / 4.35f
                item {
                    MovieSmallPhotoGrid(
                        availableWidth,
                        smallPhotoWidth,
                        smallPhotoGridHeight,
                        gridPhotoList
                    )
                }
            }
        }
    }
}

@Composable
private fun MovieSmallPhotoGrid(
    availableWidth: Dp,
    smallPhotoWidth: Dp,
    smallPhotoGridHeight: Dp,
    gridPhotoList: List<Photo>
) {
    // 这里由于嵌套在LazyRow里，所以必须指定宽度
    LazyHorizontalGrid(
        GridCells.Fixed(2),
        Modifier
            .width(availableWidth)
            .height(smallPhotoGridHeight),
        verticalArrangement = Arrangement.spacedBy(2.dp),
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        userScrollEnabled = false
    ) {
        this.items(gridPhotoList.size, key = {
            gridPhotoList[it].id
        }) { index ->
            val smallPhoto = gridPhotoList[index]
            AsyncImage(
                model = smallPhoto.image?.normal?.url,
                contentDescription = smallPhoto.description,
                modifier = Modifier
                    .width(smallPhotoWidth / 3)
                    .height((smallPhotoGridHeight - 2.dp) / 2),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
fun TrailerItem(trailer: Trailer, modifier: Modifier = Modifier) {
    Box(modifier) {
        AsyncImage(
            model = trailer.coverUrl,
            contentDescription = trailer.title,
            Modifier.fillMaxWidth(),
            contentScale = ContentScale.Crop
        )
        Text(
            text = stringResource(id = R.string.label_trailer), Modifier
                .align(Alignment.TopStart)
                .padding(start = 4.dp, top = 4.dp)
                .background(Color(0xFFFF9600), RoundedCornerShape(4.dp))
                .padding(horizontal = 6.dp, vertical = 2.dp),
            Color.White,
            fontSize = 12.sp
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_video_large),
            contentDescription = null,
            modifier = Modifier.align(Alignment.Center),
            tint = Color.Unspecified
        )
        Text(
            text = trailer.runtime,
            Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 16.dp, bottom = 8.dp),
            Color.White, fontSize = 12.sp
        )
    }
}

@Composable
fun MovieRelatedItems(relatedItems: RelatedItems?, modifier: Modifier = Modifier) {
    Column(modifier) {
        Text(
            text = stringResource(id = R.string.label_movie_related),
            Modifier.padding(start = 16.dp, bottom = 16.dp),
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        val configuration = LocalConfiguration.current
        val availableWidthDp = configuration.screenWidthDp.dp - 16.dp * 5

        if (relatedItems != null) {
            LazyRow(
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                itemsIndexed(relatedItems.subjects) { index, subjectItem ->
                    Column {
                        RelatedMovieItem(subjectItem, Modifier.width(availableWidthDp / 4))
                        if (index < relatedItems.doulists.size) {
                            RelatedDouItem(
                                relatedItems.doulists[index],
                                Modifier
                                    .padding(top = 16.dp)
                                    .width(availableWidthDp / 4)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RelatedMovieItem(subject: Subject, modifier: Modifier = Modifier) {
    Column(modifier) {
        AsyncImage(
            model = subject.pic?.normal,
            contentDescription = subject.title,
            Modifier
                .aspectRatio(1.42f / 2.2f)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )
        Text(
            text = subject.title,
            color = Color.White,
            modifier = Modifier.padding(top = 8.dp),
            fontSize = 13.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        if (subject.rating != null) {
            val ratingBarCfg = remember {
                RatingBarConfig().size(8.dp).padding(1.dp).isIndicator(true)
                    .activeColor(Color(0xFFFAAE36))
                    .inactiveColor(Color.White.copy(alpha = 0.5f))
            }
            Row(Modifier.fillMaxWidth()) {
                RatingBar(
                    subject.rating.starCount,
                    Modifier.align(Alignment.CenterVertically),
                    ratingBarCfg,
                    {},
                    {})
                Text(
                    text = subject.rating.value.toString(),
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(start = 4.dp),
                    color = Color.White.copy(alpha = 0.5f),
                    fontSize = 10.sp
                )
            }
        } else {
            Text(
                text = stringResource(id = R.string.label_no_rating),
                color = Color.White.copy(alpha = 0.5f),
                fontSize = 12.sp
            )
        }
    }
}

@Composable
fun RelatedDouItem(douList: DouList, modifier: Modifier = Modifier) {
    Column(modifier) {
        Box(Modifier.fillMaxWidth()) {
            AsyncImage(
                model = douList.coverUrl,
                contentDescription = douList.title,
                modifier = Modifier
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            if (douList.isOfficial) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_dou_official_label_s),
                    contentDescription = null,
                    Modifier
                        .align(Alignment.TopStart)
                        .padding(start = 4.dp, top = 4.dp),
                    tint = Color.Unspecified
                )
            }

            if (douList.iconText.isNotEmpty()) {
                Row(
                    Modifier
                        .fillMaxWidth(0.5f)
                        .align(Alignment.BottomStart)
                        .clip(RoundedCornerShape(topEnd = 8.dp, bottomStart = 8.dp))
                        .background(Color(0xA0000000))
                        .padding(vertical = 2.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_videos_s),
                        contentDescription = douList.iconText,
                        tint = Color.White
                    )
                    Text(
                        text = douList.iconText,
//                    Modifier.padding(start = 2.dp),
                        color = Color.White,
                        fontSize = 8.sp
                    )
                }
            }
        }
        Text(
            text = douList.title,
            Modifier.padding(top = 8.dp),
            color = Color.White,
            maxLines = 2,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            overflow = TextOverflow.Ellipsis
        )
        Row(Modifier.padding(top = 8.dp)) {
            Text(
                text = stringResource(id = R.string.fmt_movie_watch_num, douList.doneCount),
                color = Color.White, fontSize = 12.sp
            )
            Text(
                text = "/${douList.itemsCount}",
                color = Color.White.copy(alpha = 0.5f),
                fontSize = 12.sp
            )
        }
        LinearProgressIndicator(
            (douList.doneCount / douList.itemsCount).toFloat(),
            Modifier
                .padding(top = 4.dp)
                .fillMaxWidth(0.5f)
                .height(8.dp)
//                .background(Color.Blue)
//                .padding(0.5.dp)
//                .clip(CircleShape)
                .border(0.5.dp, Color.White, CircleShape),
            backgroundColor = Color.Transparent
        )
    }
}


