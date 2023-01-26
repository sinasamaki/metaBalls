package com.sinasamaki.metaballs.pages

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sinasamaki.metaballs.MetaContainer
import com.sinasamaki.metaballs.MetaEntity

@Composable
fun ExpandableFAB() {
    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        var expanded: Boolean by remember {
            mutableStateOf(false)
        }
        val offset by animateDpAsState(
            targetValue = if (expanded) 120.dp else 0.dp,
            spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = 100f,
            )
        )

        MetaContainer(
            modifier = Modifier.fillMaxSize()
        ) {

            FABButton(
                Modifier.offset(y = -offset),
                onClick = {}
            ) {
                Icon(
                    imageVector = Icons.Default.Call,
                    contentDescription = null,
                    tint = Color.White,
                )
            }

            FABButton(
                Modifier.offset(x = -offset),
                onClick = {}
            ) {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = null,
                    tint = Color.White,
                )
            }

            FABButton(
                Modifier.offset(x = offset),
                onClick = {}
            ) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = null,
                    tint = Color.White,
                )
            }

            FABButton(
                Modifier,
                onClick = {
                    expanded = !expanded
                }
            ) {
                val rotation by animateFloatAsState(targetValue = if (expanded) 45f else 0f)
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    modifier = Modifier.rotate(rotation),
                    tint = Color.White,
                )
            }
        }

    }
}

@Composable
fun BoxScope.FABButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    content: @Composable BoxScope.() -> Unit
) {
    MetaEntity(
        modifier = modifier
            .clickable(
                interactionSource = remember {
                    MutableInteractionSource()
                },
                indication = null,
                onClick = onClick,
            )
            .align(Alignment.Center),
        blur = 50f,
        metaContent = {
            Box(
                Modifier
                    .background(Color.Black, CircleShape)
                    .fillMaxSize()
            )
        }
    ) {
        Box(
            Modifier.size(100.dp),
            content = content,
            contentAlignment = Alignment.Center,
        )
    }
}