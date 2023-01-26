package com.sinasamaki.metaballs.pages

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Home
import androidx.compose.material.icons.twotone.Phone
import androidx.compose.material.icons.twotone.Search
import androidx.compose.material.icons.twotone.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.sinasamaki.metaballs.MetaContainer
import com.sinasamaki.metaballs.MetaEntity
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlin.math.roundToInt

@Composable
fun BottomNavigationAnimation() {
    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Box(
            modifier = Modifier
                .padding(32.dp)
                .shadow(10.dp, RoundedCornerShape(20.dp))
                .clip(RoundedCornerShape(20.dp))
                .background(MaterialTheme.colors.background, RoundedCornerShape(20.dp))
                .aspectRatio(2 / 3f)
        ) {
            BottomNavigation(
                modifier = Modifier.align(Alignment.BottomCenter),
                backgroundColor = Color.Blue,
                contentColor = Color.White,
            ) {
                Box {
                    var selectedXOffset by remember {
                        mutableStateOf(0)
                    }
                    var size by remember {
                        mutableStateOf(Size.Zero)
                    }

                    val density = LocalDensity.current

                    MetaContainer(
                        modifier = Modifier.fillMaxSize()
                    ) {

                        val x1 by animateIntAsState(targetValue = selectedXOffset)

                        MetaEntity(
                            modifier = Modifier
                                .offset { IntOffset(x1, 0) }
                                .size(with(density) { size.toDpSize() }),
                            blur = 60f,
                            metaContent = {
                                Box(
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .background(Color.Black, CircleShape)
                                        .fillMaxSize()
                                )
                            }
                        )

                        val x2 by animateIntAsState(
                            targetValue = selectedXOffset, spring(
                                dampingRatio = Spring.DampingRatioMediumBouncy,
                                stiffness = Spring.StiffnessMediumLow,
                            )
                        )

                        MetaEntity(
                            modifier = Modifier
                                .offset { IntOffset(x2, 0) }
                                .size(with(density) { size.toDpSize() }),
                            blur = 60f,
                            metaContent = {
                                Box(
                                    modifier = Modifier
                                        .padding(14.dp)
                                        .background(Color.Black, CircleShape)
                                        .fillMaxSize()
                                )
                            }
                        )


                    }


                    Row {
                        var selectedIndex: Int by remember {
                            mutableStateOf(0)
                        }
                        pages.forEachIndexed { index, navItem ->
                            var xOffset by remember {
                                mutableStateOf(0)
                            }
                            BottomNavigationItem(
                                selected = selectedIndex == index,
                                onClick = {
                                    selectedIndex = index
                                    selectedXOffset = xOffset
                                },
                                icon = {
                                    Icon(
                                        imageVector = navItem.icon,
                                        contentDescription = null,
                                    )
                                },
                                modifier = Modifier
                                    .onGloballyPositioned {
                                        xOffset = it.positionInParent().x.roundToInt()
                                        size = it.size.toSize()
                                    },

                                interactionSource = remember { NoInteraction() },
                                selectedContentColor = Color.Yellow,
                                unselectedContentColor = Color.White.copy(alpha = .6f)
                            )
                        }
                    }
                }
            }
        }
    }
}

val pages = listOf(
    NavItem(Icons.TwoTone.Home),
    NavItem(Icons.TwoTone.Phone),
    NavItem(Icons.TwoTone.Search),
    NavItem(Icons.TwoTone.Settings),
)


data class NavItem(val icon: ImageVector)

@Stable
private class NoInteraction : MutableInteractionSource {
    override val interactions = MutableSharedFlow<Interaction>()
    override suspend fun emit(interaction: Interaction) {}
    override fun tryEmit(interaction: Interaction) = false
}