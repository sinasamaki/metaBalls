@file:OptIn(ExperimentalMaterialApi::class)

package com.sinasamaki.metaballs.pages

import androidx.compose.animation.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.sinasamaki.metaballs.MetaContainer
import com.sinasamaki.metaballs.MetaEntity
import kotlin.math.roundToInt

@Composable
fun Switch() {
    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {

        val width = 300.dp
        val squareSize = width / 2

        val swipeableState = rememberSwipeableState(0)
        val widthPx = with(LocalDensity.current) { width.toPx() }
        val sizePx = with(LocalDensity.current) { squareSize.toPx() }
        val anchors = mapOf(0f to 0, sizePx to 1) // Maps anchor points (in px) to states

        Box(
            modifier = Modifier
                .width(width)
                .height(squareSize)
                .swipeable(
                    state = swipeableState,
                    anchors = anchors,
                    thresholds = { _, _ -> FractionalThreshold(0.3f) },
                    orientation = Orientation.Horizontal
                )
                .background(Color.Black, RoundedCornerShape(width))
        ) {
            val progress = (swipeableState.offset.value / (widthPx / 2))
            var color by remember {
                mutableStateOf(Color.Transparent)
            }
            LaunchedEffect(progress) {
                color = lerp(Color.Gray, Color.Yellow, progress.coerceAtLeast(0f).coerceAtMost(1f))
            }
            MetaContainer(
                modifier = Modifier.clip(RoundedCornerShape(width)),
                color = color
            ) {
                MetaEntity(
                    modifier = Modifier.fillMaxSize(),
                    blur = 70f,
                    metaContent = {
                        Box(modifier = Modifier.fillMaxSize()) {
                            Box(
                                Modifier
                                    .offset {
                                        IntOffset(
                                            swipeableState.offset.value.roundToInt(),
                                            0
                                        )
                                    }
                                    .size(squareSize)
                                    .padding(24.dp)
                                    .scale(1f - progress)
                                    .background(Color.Black, CircleShape)
                            )
                            Box(
                                Modifier
                                    .offset {
                                        IntOffset((widthPx * .16f).roundToInt(), 0)
                                    }
                                    .offset {
                                        IntOffset(
                                            (swipeableState.offset.value * .68f).roundToInt(),
                                            0
                                        )
                                    }
                                    .offset {
                                        IntOffset((50 * (1f - progress)).roundToInt(), 0)
                                    }
                                    .size(squareSize)
                                    .padding(8.dp)
                                    .scale(progress)
                                    .background(Color.Black, CircleShape)
                            )
                        }
                    }) {

                }
            }

        }

    }
}