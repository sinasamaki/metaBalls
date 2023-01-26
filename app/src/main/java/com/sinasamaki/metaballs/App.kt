@file:OptIn(ExperimentalPagerApi::class)

package com.sinasamaki.metaballs

import androidx.compose.runtime.Composable
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.sinasamaki.metaballs.pages.BottomNavigationAnimation
import com.sinasamaki.metaballs.pages.ExpandableFAB
import com.sinasamaki.metaballs.pages.ProgressLoader
import com.sinasamaki.metaballs.pages.SwipeDismiss
import com.sinasamaki.metaballs.pages.Switch
import com.sinasamaki.metaballs.pages.TextChange

class Page(val content: @Composable () -> Unit)

val pages = listOf(
    Page { SwipeDismiss() },
    Page { BottomNavigationAnimation() },
    Page { Switch() },
    Page { ProgressLoader() },
    Page { TextChange() },
    Page { ExpandableFAB() },
)


@Composable
fun App() {
    HorizontalPager(count = pages.size) { page ->
        pages[page].content()
    }
}