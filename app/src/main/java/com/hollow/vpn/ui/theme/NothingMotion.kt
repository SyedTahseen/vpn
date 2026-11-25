package com.hollow.vpn.ui.theme

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Nothing OS 3.0 motion tokens and GPU-accelerated micro-interaction primitives.
 * Tuned for crisp, weighted tactile spring physics with zero sluggishness.
 */
object NothingMotion {
    /** Fast exponential ease-out matching Nothing OS 3.0 screen & modal transitions. */
    val FluidEase = CubicBezierEasing(0.22f, 1f, 0.36f, 1f)

    /** Snappy critically-damped spring for sliding pill indicators & toggles. */
    fun <T> snappySpring() = spring<T>(
        dampingRatio = 0.80f,
        stiffness = 500f
    )

    /** Tactile slightly-bouncy spring for button press-release rebounds. */
    fun <T> tactileSpring() = spring<T>(
        dampingRatio = 0.62f,
        stiffness = 560f
    )

    /** Gentle no-bounce spring for layout reveals and container morphs. */
    fun <T> settleSpring() = spring<T>(
        dampingRatio = Spring.DampingRatioNoBouncy,
        stiffness = 420f
    )
}

/**
 * Scales down smoothly while pressed and rebounds with Nothing OS 3.0 spring physics.
 */
fun Modifier.nothingPressScale(
    interactionSource: InteractionSource,
    pressedScale: Float = 0.965f
): Modifier = composed {
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (isPressed) pressedScale else 1f,
        animationSpec = if (isPressed) {
            tween(durationMillis = 85, easing = NothingMotion.FluidEase)
        } else {
            NothingMotion.tactileSpring()
        },
        label = "nothing_press_scale"
    )
    this.graphicsLayer {
        scaleX = scale
        scaleY = scale
    }
}

/**
 * Subtle GPU-accelerated staggered entrance animation for cards and sections.
 */
@Composable
fun NothingStaggerReveal(
    index: Int,
    modifier: Modifier = Modifier,
    slideDistance: Dp = 12.dp,
    content: @Composable () -> Unit
) {
    val density = LocalDensity.current
    val slidePx = with(density) { slideDistance.toPx() }
    val alpha = remember { Animatable(0f) }
    val offsetY = remember { Animatable(slidePx) }

    LaunchedEffect(Unit) {
        val staggerDelay = (index.coerceAtMost(6) * 32L)
        if (staggerDelay > 0L) delay(staggerDelay)
        launch {
            alpha.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 240, easing = NothingMotion.FluidEase)
            )
        }
        launch {
            offsetY.animateTo(
                targetValue = 0f,
                animationSpec = tween(durationMillis = 280, easing = NothingMotion.FluidEase)
            )
        }
    }

    Box(
        modifier = modifier.graphicsLayer {
            this.alpha = alpha.value
            this.translationY = offsetY.value
        }
    ) {
        content()
    }
}
