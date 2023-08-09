package com.eric.composearch.modules.movie

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import coil.compose.AsyncImage
import com.eric.composearch.R
import com.eric.composearch.data.remote.HonorInfo
import com.eric.composearch.data.remote.MovieItem
import com.eric.composearch.data.remote.Rating
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarConfig

/**
 * Created by eric on 2022/5/7
 */
// viewModel()这个方法只能在Activity或Fragment上下文里调用，如果从NavHost里调用会失败
@Composable
fun MovieListScreen(
    onMovieClicked: (String) -> Unit,
    modifier: Modifier = Modifier,
    movieListViewModel: MovieListViewModel = viewModel()
) {
    val uiState = movieListViewModel.uiState
    val sortTypeArg = stringArrayResource(id = R.array.movieSortArg)
    val lazyPagingItems = movieListViewModel.movieListData.collectAsLazyPagingItems()

    Column(
        modifier
            .statusBarsPadding()
            .fillMaxSize()
            .padding(horizontal = 8.dp)
    ) {
        // 标题-热门电影
        MovieListTitle()
        // 电影分类Tab
        MovieTypeTabRow(movieTypeIndex = uiState.movieTypeIndex) {
            movieListViewModel.updateMovieTypeIndex(it)
        }
        Divider(Modifier.fillMaxWidth())
        // 电影顶部排序Tab
        MovieSortTab(
            uiState.sortTypeIndex,
            Modifier
                .fillMaxWidth(0.5f)
                .padding(vertical = 8.dp)
                .clip(RoundedCornerShape(8.dp))
                .align(Alignment.End)
        ) {
            movieListViewModel.updateMovieSortType(it, sortTypeArg[it])
        }

        // 电影列表
        MovieList(lazyPagingItems, onMovieClicked)
    }
}

@Composable
private fun MovieListTitle() {
    Text(
        stringResource(R.string.title_movie_screen),
        Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .wrapContentWidth(Alignment.CenterHorizontally),
        fontSize = 18.sp
    )
}

@Composable
fun MovieTypeTabRow(
    movieTypeIndex: Int,
    modifier: Modifier = Modifier,
    onMovieTypeIndexChange: (Int) -> Unit
) {
    val movieTypeArray = stringArrayResource(id = R.array.movieType)

    ScrollableTabRow(
        movieTypeIndex,
        modifier = modifier,
        backgroundColor = Color.White,
        edgePadding = 0.dp,
        divider = {},
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier
                    .customTabIndicatorOffset(
                        tabPositions[movieTypeIndex],
                        tabPositions[movieTypeIndex].width / 2
                    )
                    .clip(RoundedCornerShape(4.dp))
            )
        }) {
        movieTypeArray.forEachIndexed { index, label ->
            val currentSelected = movieTypeIndex == index
            Tab(
                selected = currentSelected,
                selectedContentColor = Color(0xFF4C4C4C),
                unselectedContentColor = Color(0xFF828282),
                onClick = {
                    onMovieTypeIndexChange(index)
                }) {
                Text(
                    text = label,
                    modifier = Modifier.padding(vertical = 8.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun MovieSortTab(
    sortTypeIndex: Int,
    modifier: Modifier = Modifier,
    onSortTypeIndexChange: (Int) -> Unit
) {
    val sortTypeArray = stringArrayResource(id = R.array.movieSortType)

    TabRow(
        selectedTabIndex = sortTypeIndex,
        backgroundColor = Color(0xFFF7F7F7),
        modifier = modifier,
        indicator = {}, divider = {}) {
        sortTypeArray.forEachIndexed { index, sortName ->
            val currentSelected = sortTypeIndex == index
            Tab(
                selected = currentSelected,
                selectedContentColor = Color.White,
                modifier = Modifier.padding(start = 1.dp, top = 1.dp, bottom = 1.dp),
                onClick = {
                    onSortTypeIndexChange(index)
                }) {
                val textModifier =
                    if (currentSelected) {
                        Modifier
                            .fillMaxWidth()
                            .shadow(1.dp, RoundedCornerShape(8.dp))
                            .background(Color.White)
                    } else {
                        Modifier.fillMaxWidth()
                    }
                val color = if (currentSelected) Color(0xFF353535) else Color(0xFF828282)
                Text(
                    text = sortName,
                    color = color,
                    modifier = textModifier.padding(vertical = 4.dp),
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
private fun MovieList(
    lazyPagingItems: LazyPagingItems<MovieItem>,
    onMovieClicked: (String) -> Unit
) {
    LazyColumn(
        Modifier.fillMaxSize(),
        // 列表上下内边距
        contentPadding = PaddingValues(vertical = 8.dp),
        // 内容Item之间的边距
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        when (lazyPagingItems.loadState.refresh) {
            is LoadState.Loading -> {
                item {
                    Text(
                        text = "正在加载中...",
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth()
                    )
                }
            }
            is LoadState.Error -> {
                item {
                    Text(
                        text = "初始加载出错...",
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth()
                    )
                }
            }
            is LoadState.NotLoading -> {
            }
        }

        itemsIndexed(lazyPagingItems, key = { _, item ->
            item.id
        }) { index, item ->
            val hasBottomLine = index < lazyPagingItems.itemCount - 1
            item?.let {
                MovieItem(
                    item = it,
                    hasBottomLine, modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onMovieClicked(item.id)
                        }
                )
            }
        }

        when (val appendState = lazyPagingItems.loadState.append) {
            is LoadState.Loading -> {
                item {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(Alignment.CenterHorizontally)
                    )
                }
            }
            is LoadState.Error -> {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "--加载错误--")
                    }
                }
            }
            is LoadState.NotLoading -> {
                if (appendState.endOfPaginationReached) {
                    item {
                        Text(
                            text = "--没有更多数据--",
                            color = Color.Gray,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp)
                                .wrapContentWidth()
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MovieItem(item: MovieItem, hasBottomLine: Boolean, modifier: Modifier = Modifier) {
    Column(modifier) {
        MovieHonorInfo(item.honorInfos)
        MovieImage(item, Modifier.fillMaxWidth())
        MovieTitle(item.title, item.year)
        MovieRating(item.rating)
        MovieSubTitle(item.cardSubtitle)
        MovieIntro(item.comment)
        if (hasBottomLine) {
            Divider()
        }
    }
}

@Composable
private fun MovieRating(
    rating: Rating?
) {
    val ratingBarCfg = remember {
        RatingBarConfig().size(12.dp).isIndicator(true).padding(1.dp).activeColor(Color(0xFFF89216))
            .inactiveColor(Color(0xFFDBE2DB))
    }
    if (rating == null) {
        Text(
            text = stringResource(id = R.string.label_no_rating),
            color = Color(0xFF949494),
            fontSize = 10.sp
        )
    } else {
        Row {
            RatingBar(
                rating.starCount,
                Modifier.align(Alignment.CenterVertically),
                ratingBarCfg,
                {},
                {})
            Text(
                text = rating.value.toString(),
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(start = 4.dp),
                color = Color(0xFFF89216)
            )
        }
    }
}

@Composable
private fun MovieHonorInfo(
    honorInfos: List<HonorInfo>?
) {
    if (!honorInfos.isNullOrEmpty()) {
        Box(
            Modifier
                .padding(bottom = 16.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(Color(0xFFFFF6E7))
                .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Text(
                text = "${honorInfos[0].title} >",
                color = Color(0xFFA9731B),
                fontSize = 14.sp
            )
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun MovieImage(
    item: MovieItem, modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState()
    Row(modifier) {
        AsyncImage(
            model = item.pic?.normal,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .weight(1.9f)
                .aspectRatio(1.9f / 2.65f)
                .clip(RoundedCornerShape(8.dp))
        )
        Spacer(Modifier.size(12.dp))
        Box(
            Modifier
                .weight(4.45f)
                .aspectRatio(4.45f / 2.65f)
                .clip(RoundedCornerShape(8.dp))
        ) {
            HorizontalPager(
                count = item.photos?.size ?: 0,
                state = pagerState,
                modifier = Modifier
                    .fillMaxSize()
            ) { page ->
                AsyncImage(
                    model = item.photos?.get(page),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            HorizontalPagerIndicator(
                pagerState = pagerState,
                activeColor = Color.White,
                indicatorWidth = 6.dp,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(vertical = 8.dp)
            )
        }
    }
}

@Composable
private fun MovieTitle(
    title: String,
    year: String
) {
    Text(
        "$title ($year)",
        Modifier.padding(top = 8.dp),
        fontSize = 20.sp
    )
}

@Composable
private fun MovieSubTitle(subTitle: String) {
    Text(text = subTitle, color = Color(0xFF949494), fontSize = 10.sp)
}

@Composable
private fun MovieIntro(intro: String) {
    Text(
        text = intro,
        Modifier.padding(vertical = 16.dp),
        color = Color(0xFF505050),
        fontSize = 14.sp,
        letterSpacing = 0.5.sp
    )
}

@Preview(showBackground = true, widthDp = 200)
@Composable
fun PreviewMovieTypeTab() {
    var currentIndex by remember {
        mutableStateOf(0)
    }
    MovieTypeTabRow(currentIndex) {
        currentIndex = it
    }
}

@Preview(showBackground = true, widthDp = 300)
@Composable
fun PreviewMovieSortTab() {
    var sortType by remember {
        mutableStateOf(0)
    }
    Box(
        Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        MovieSortTab(
            sortType,
            Modifier
                .fillMaxWidth(0.5f)
                .align(Alignment.CenterEnd)
        ) {
            sortType = it
        }
    }
}

//@Preview(showBackground = true, widthDp = 200, heightDp = 200)
//@Composable
//fun Test() {
//    CircularProgressIndicator(
//        modifier = Modifier.wrapContentSize(Alignment.Center)
//    )
//}

//@Preview(showBackground = true)
//@Composable
//fun PreviewMovieItemView() {
//    val picUrl = "https://img9.doubanio.com/view/photo/s_ratio_poster/public/p2870295964.webp"
//    val photos = listOf(
//        "https://img9.doubanio.com/view/photo/m/public/p2308854706.webp",
//        "https://img2.doubanio.com/view/photo/m/public/p2308854702.webp",
//        "https://img2.doubanio.com/view/photo/m/public/p2871350242.webp",
//        "https://img2.doubanio.com/view/photo/m/public/p2871350241.webp"
//    )
//    val item = MovieItem(pic = Pic(picUrl, picUrl), photos = photos)
//    MovieItemView(item, Modifier.fillMaxWidth())
//}
