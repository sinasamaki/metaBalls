@file:OptIn(ExperimentalMaterialApi::class)

package com.sinasamaki.metaballs.pages

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.sinasamaki.metaballs.MetaContainer
import com.sinasamaki.metaballs.MetaEntity
import kotlin.math.roundToInt

@Composable
fun SwipeDismiss() {

    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val state = rememberDismissState(
            confirmStateChange = {
                false
            }
        )

        SwipeToDismiss(
            state = state,
            modifier = Modifier
                .padding(16.dp)
                .shadow(2.dp, RoundedCornerShape(20.dp))
                .background(Color(0xFFFDFDFD)),
            background = {
                MetaContainer(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFFE01111),
                    cutoff = .3f
                ) {
                    val willTrigger by remember {
                        derivedStateOf {
                            state.progress.fraction > .6f
                        }
                    }
                    MetaEntity(
                        modifier = Modifier
                            .align(Alignment.CenterEnd),
                        blur = 50f,
                        metaContent = {
                            Box(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                val width by animateDpAsState(
                                    targetValue = if (!willTrigger) 20.dp else 13.dp,
                                    animationSpec = spring(
                                        dampingRatio = Spring.DampingRatioNoBouncy,
                                        stiffness = Spring.StiffnessLow,
                                    )
                                )
                                Box(
                                    modifier = Modifier
                                        .align(Alignment.CenterEnd)
                                        .background(Color.Black)
                                        .requiredHeight(120.dp)
                                        .width(width)
                                )

                                androidx.compose.animation.AnimatedVisibility(
                                    visible = !willTrigger,
                                    modifier = Modifier
                                        .align(Alignment.CenterEnd),
                                    enter = fadeIn(),
                                    exit = fadeOut(),
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .align(Alignment.CenterEnd)
                                            .background(Color.Black)
                                            .width(50.dp)
                                            .height(12.dp)
                                    )
                                }
                            }
                        }
                    )


                    val bounce = remember {
                        Animatable(0f)
                    }

                    LaunchedEffect(willTrigger) {
                        if (willTrigger) {
                            bounce.animateTo(
                                -10f,
                                spring(
                                    dampingRatio = Spring.DampingRatioNoBouncy,
                                    stiffness = Spring.StiffnessHigh,
                                )
                            )
                            bounce.animateTo(
                                0f,
                                spring(
                                    dampingRatio = Spring.DampingRatioMediumBouncy,
                                    stiffness = Spring.StiffnessMedium
                                )
                            )
                        }
                    }

                    MetaEntity(
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .offset {
                                IntOffset(bounce.value.roundToInt(), 0)
                            }
                            .offset {
                                IntOffset(state.offset.value.roundToInt() / 4, 0)
                            },
                        blur = 50f,
                        metaContent = {
                            Box(
                                modifier = Modifier
                                    .background(Color.Black, CircleShape)
                                    .size(50.dp)
                            )
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = null,
                            tint = Color(0xFFFFFFFF)
                        )
                    }
                }
            }
        ) {
            Card(
                Modifier
                    .fillMaxWidth()
                    .height(96.dp),
                backgroundColor = Color(0xFFAEE0FA),
                shape = RoundedCornerShape(20.dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Swipe to delete",
                        style = MaterialTheme.typography.h3.copy(fontWeight = FontWeight.ExtraLight),
                    )
                }
            }
        }
    }
}