package com.hollow.vpn.ui.components

import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material.icons.outlined.Dns
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Smartphone
import androidx.compose.material.icons.rounded.PowerSettingsNew
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hollow.vpn.ui.theme.DotoDisplayFamily
import com.hollow.vpn.ui.theme.HollowCream
import com.hollow.vpn.ui.theme.HollowCreamDivider
import com.hollow.vpn.ui.theme.HollowCreamSurface
import com.hollow.vpn.ui.theme.HollowDarkInk
import com.hollow.vpn.ui.theme.HollowForest
import com.hollow.vpn.ui.theme.HollowForestCard
import com.hollow.vpn.ui.theme.HollowForestDivider
import com.hollow.vpn.ui.theme.HollowForestMuted
import com.hollow.vpn.ui.theme.HollowInk
import com.hollow.vpn.ui.theme.HollowInkMuted
import com.hollow.vpn.ui.theme.HollowInkSecondary
import com.hollow.vpn.ui.theme.HollowLime
import com.hollow.vpn.ui.theme.HollowLimeBarMuted
import com.hollow.vpn.ui.theme.HollowObsidian
import com.hollow.vpn.ui.theme.HollowOrange
import com.hollow.vpn.ui.theme.JetBrainsMonoFamily
import com.hollow.vpn.ui.theme.NothingBlack
import com.hollow.vpn.ui.theme.NothingCanvas
import com.hollow.vpn.ui.theme.NothingMapDotMuted
import com.hollow.vpn.ui.theme.NothingPillActiveBg
import com.hollow.vpn.ui.theme.NothingPillActiveIcon
import com.hollow.vpn.ui.theme.NothingPillNavBorder
import com.hollow.vpn.ui.theme.NothingPillNavFill
import com.hollow.vpn.ui.theme.NothingSurface
import com.hollow.vpn.ui.theme.NothingSwitchOffBorder
import com.hollow.vpn.ui.theme.NothingSwitchOffThumb
import com.hollow.vpn.ui.theme.NothingSwitchOffTrack
import com.hollow.vpn.ui.theme.NothingTopBtnBorder
import com.hollow.vpn.ui.theme.SignalMediumDark
import com.hollow.vpn.ui.theme.SignalMediumLight
import com.hollow.vpn.ui.theme.SignalStrongDark
import com.hollow.vpn.ui.theme.SignalStrongLight
import com.hollow.vpn.ui.theme.SignalWeakDark
import com.hollow.vpn.ui.theme.SignalWeakLight
import com.hollow.vpn.ui.theme.SpaceGroteskFamily
import kotlinx.coroutines.launch
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.hypot
import kotlin.math.log10
import kotlin.math.roundToInt
import kotlin.math.sin

// ──────────────────────────────────────────────────────────────────────────────
// Hollow Editorial Redesign Primitives
// ──────────────────────────────────────────────────────────────────────────────

enum class HollowBottomTab {
    CONNECT,
    SERVERS,
    SETTINGS
}

@Composable
fun HollowBrandShieldLogo(
    size: Dp = 120.dp,
    cornerRadius: Dp = 34.dp,
    backgroundColor: Color = HollowOrange,
    strokeColor: Color = Color.White,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(RoundedCornerShape(cornerRadius))
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val scale = this.size.minDimension / 108f
            val shieldPath = Path().apply {
                moveTo(54f * scale, 28f * scale)
                lineTo(75f * scale, 37f * scale)
                lineTo(75f * scale, 54f * scale)
                cubicTo(
                    75f * scale, 68f * scale,
                    66f * scale, 77f * scale,
                    54f * scale, 81f * scale
                )
                cubicTo(
                    42f * scale, 77f * scale,
                    33f * scale, 68f * scale,
                    33f * scale, 54f * scale
                )
                lineTo(33f * scale, 37f * scale)
                close()
            }
            drawPath(
                path = shieldPath,
                color = strokeColor,
                style = Stroke(
                    width = 7f * scale,
                    join = StrokeJoin.Round,
                    cap = StrokeCap.Round
                )
            )
            drawCircle(
                color = strokeColor,
                radius = 7f * scale,
                center = Offset(54f * scale, 56f * scale)
            )
        }
    }
}

@Composable
fun HollowStatusBrandRow(
    dotColor: Color,
    textColor: Color,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(9.dp)
    ) {
        Box(
            modifier = Modifier
                .size(9.dp)
                .clip(CircleShape)
                .background(dotColor)
        )
        Text(
            text = "HOLLOW",
            fontFamily = DotoDisplayFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            letterSpacing = 0.9.sp,
            maxLines = 1,
            color = textColor
        )
    }
}

@Composable
fun HollowTopSquareButton(
    containerColor: Color,
    contentColor: Color,
    borderColor: Color? = NothingTopBtnBorder,
    contentDescription: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.(Color) -> Unit
) {
    val interactionSource = remember { androidx.compose.foundation.interaction.MutableInteractionSource() }
    val animContainer by animateColorAsState(
        targetValue = containerColor,
        animationSpec = tween(200, easing = com.hollow.vpn.ui.theme.NothingMotion.FluidEase),
        label = "top_btn_container"
    )
    val animBorder by animateColorAsState(
        targetValue = borderColor ?: NothingTopBtnBorder,
        animationSpec = tween(200, easing = com.hollow.vpn.ui.theme.NothingMotion.FluidEase),
        label = "top_btn_border"
    )
    val animContent by animateColorAsState(
        targetValue = contentColor,
        animationSpec = tween(200, easing = com.hollow.vpn.ui.theme.NothingMotion.FluidEase),
        label = "top_btn_content"
    )
    Box(
        modifier = modifier
            .minimumInteractiveComponentSize()
            .then(with(com.hollow.vpn.ui.theme.NothingMotion) { Modifier })
            .clip(CircleShape)
            .clickable(
                interactionSource = interactionSource,
                indication = androidx.compose.material3.ripple(),
                role = Role.Button,
                onClick = onClick
            )
            .semantics { this.contentDescription = contentDescription },
        contentAlignment = Alignment.Center
    ) {
        val isPressed by interactionSource.collectIsPressedAsState()
        val btnScale by animateFloatAsState(
            targetValue = if (isPressed) 0.90f else 1f,
            animationSpec = if (isPressed) {
                tween(80, easing = com.hollow.vpn.ui.theme.NothingMotion.FluidEase)
            } else {
                com.hollow.vpn.ui.theme.NothingMotion.tactileSpring()
            },
            label = "top_btn_scale"
        )
        Surface(
            modifier = Modifier
                .size(40.dp)
                .graphicsLayer {
                    scaleX = btnScale
                    scaleY = btnScale
                },
            shape = CircleShape,
            color = animContainer,
            border = BorderStroke(1.dp, animBorder)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                content(animContent)
            }
        }
    }
}

fun countryCodeToFlagEmoji(countryCode: String?): String {
    val clean = countryCode?.trim()?.uppercase().orEmpty()
    if (clean.length != 2 || clean == "XX" || clean == "VP" || !clean.all { it in 'A'..'Z' }) {
        return "🌐"
    }
    val first = Character.toChars(0x1F1E6 + (clean[0] - 'A'))
    val second = Character.toChars(0x1F1E6 + (clean[1] - 'A'))
    return String(first) + String(second)
}

/**
 * Crisp 8-ray gear/sun icon kept for compatibility if referenced elsewhere.
 */
@Composable
fun HollowGearSunIcon(
    tint: Color,
    modifier: Modifier = Modifier.size(20.dp)
) {
    Canvas(modifier = modifier) {
        val cx = size.width / 2f
        val cy = size.height / 2f
        val rInner = size.minDimension * 0.16f
        val rMid = size.minDimension * 0.28f
        val rOuter = size.minDimension * 0.44f
        val strokeW = size.minDimension * 0.085f

        drawCircle(
            color = tint,
            radius = rInner,
            center = Offset(cx, cy),
            style = Stroke(width = strokeW)
        )
        for (i in 0 until 8) {
            val angle = (i * 45.0) * (PI / 180.0)
            val start = Offset(
                cx + (rMid * cos(angle)).toFloat(),
                cy + (rMid * sin(angle)).toFloat()
            )
            val end = Offset(
                cx + (rOuter * cos(angle)).toFloat(),
                cy + (rOuter * sin(angle)).toFloat()
            )
            drawLine(
                color = tint,
                start = start,
                end = end,
                strokeWidth = strokeW,
                cap = StrokeCap.Round
            )
        }
    }
}

@Composable
fun HollowBottomNavigationBar(
    selectedTab: HollowBottomTab,
    backgroundColor: Color,
    borderColor: Color,
    activeIndicatorColor: Color,
    activeContentColor: Color,
    inactiveContentColor: Color,
    onSelectConnect: () -> Unit,
    onSelectServers: () -> Unit,
    onSelectSettings: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isDarkPhaseBg = backgroundColor == HollowForest || backgroundColor == HollowObsidian
    val capsuleFill = when (backgroundColor) {
        HollowForest -> HollowForestCard
        HollowObsidian -> Color(0xFF141417)
        else -> NothingPillNavFill
    }
    val capsuleBorder = when (backgroundColor) {
        HollowForest -> HollowForestDivider
        HollowObsidian -> Color(0xFF26262C)
        else -> NothingPillNavBorder
    }
    val activePillBg = if (isDarkPhaseBg) Color.White else NothingPillActiveBg
    val activeIconTint = if (isDarkPhaseBg) NothingBlack else NothingPillActiveIcon

    val animBg by animateColorAsState(
        targetValue = backgroundColor,
        animationSpec = tween(260, easing = com.hollow.vpn.ui.theme.NothingMotion.FluidEase),
        label = "nav_bar_bg"
    )
    val animCapsuleFill by animateColorAsState(
        targetValue = capsuleFill,
        animationSpec = tween(260, easing = com.hollow.vpn.ui.theme.NothingMotion.FluidEase),
        label = "nav_capsule_fill"
    )
    val animCapsuleBorder by animateColorAsState(
        targetValue = capsuleBorder,
        animationSpec = tween(260, easing = com.hollow.vpn.ui.theme.NothingMotion.FluidEase),
        label = "nav_capsule_border"
    )
    val animActivePillBg by animateColorAsState(
        targetValue = activePillBg,
        animationSpec = tween(240, easing = com.hollow.vpn.ui.theme.NothingMotion.FluidEase),
        label = "nav_active_pill"
    )

    val selectedIndex = when (selectedTab) {
        HollowBottomTab.CONNECT -> 0
        HollowBottomTab.SERVERS -> 1
        HollowBottomTab.SETTINGS -> 2
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(animBg)
            .navigationBarsPadding()
            .padding(horizontal = 24.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .widthIn(max = 352.dp)
                .height(58.dp),
            shape = CircleShape,
            color = animCapsuleFill,
            border = BorderStroke(1.dp, animCapsuleBorder)
        ) {
            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 7.dp, vertical = 6.dp)
            ) {
                val itemGap = 6.dp
                val slotWidth = ((maxWidth - itemGap * 2) / 3f).coerceAtLeast(1.dp)
                val targetIndicatorOffset = (slotWidth + itemGap) * selectedIndex
                val animatedIndicatorOffset by animateDpAsState(
                    targetValue = targetIndicatorOffset,
                    animationSpec = spring(
                        dampingRatio = 0.78f,
                        stiffness = 460f
                    ),
                    label = "nav_capsule_glide"
                )

                // Gliding Nothing OS 3.0 active capsule indicator
                Box(
                    modifier = Modifier
                        .offset(x = animatedIndicatorOffset)
                        .width(slotWidth)
                        .fillMaxHeight()
                        .clip(CircleShape)
                        .background(animActivePillBg)
                )

                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(itemGap)
                ) {
                    HollowBottomNavItem(
                        label = "Connect",
                        selected = selectedTab == HollowBottomTab.CONNECT,
                        activeIconColor = activeIconTint,
                        inactiveContentColor = inactiveContentColor,
                        onClick = onSelectConnect,
                        modifier = Modifier
                            .weight(1f)
                            .testTag("nav_tab_connect")
                    ) { tint ->
                        Icon(
                            imageVector = Icons.Rounded.PowerSettingsNew,
                            contentDescription = "Connect",
                            tint = tint,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    HollowBottomNavItem(
                        label = "Servers",
                        selected = selectedTab == HollowBottomTab.SERVERS,
                        activeIconColor = activeIconTint,
                        inactiveContentColor = inactiveContentColor,
                        onClick = onSelectServers,
                        modifier = Modifier
                            .weight(1f)
                            .testTag("nav_tab_servers")
                    ) { tint ->
                        Icon(
                            imageVector = Icons.Outlined.Dns,
                            contentDescription = "Servers",
                            tint = tint,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    HollowBottomNavItem(
                        label = "Settings",
                        selected = selectedTab == HollowBottomTab.SETTINGS,
                        activeIconColor = activeIconTint,
                        inactiveContentColor = inactiveContentColor,
                        onClick = onSelectSettings,
                        modifier = Modifier
                            .weight(1f)
                            .testTag("nav_tab_settings")
                    ) { tint ->
                        Icon(
                            imageVector = if (selectedTab == HollowBottomTab.SETTINGS) {
                                Icons.Rounded.Settings
                            } else {
                                Icons.Outlined.Settings
                            },
                            contentDescription = "Settings",
                            tint = tint,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun HollowBottomNavItem(
    label: String,
    selected: Boolean,
    activeIconColor: Color,
    inactiveContentColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: @Composable (Color) -> Unit
) {
    val interactionSource = remember { androidx.compose.foundation.interaction.MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val iconTint by animateColorAsState(
        targetValue = if (selected) activeIconColor else inactiveContentColor,
        animationSpec = tween(durationMillis = 200, easing = com.hollow.vpn.ui.theme.NothingMotion.FluidEase),
        label = "nav_pill_tint"
    )
    val iconScale by animateFloatAsState(
        targetValue = when {
            isPressed -> 0.86f
            selected -> 1.08f
            else -> 0.96f
        },
        animationSpec = com.hollow.vpn.ui.theme.NothingMotion.tactileSpring(),
        label = "nav_icon_scale"
    )
    Box(
        modifier = modifier
            .fillMaxHeight()
            .minimumInteractiveComponentSize()
            .clip(CircleShape)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                role = Role.Tab,
                onClick = onClick
            )
            .semantics { contentDescription = label },
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier.graphicsLayer {
                scaleX = iconScale
                scaleY = iconScale
            }
        ) {
            icon(iconTint)
        }
    }
}

/**
 * Interactive Nothing OS slide-to-connect / slide-to-disconnect pill.
 */
@Composable
fun HollowSlideActionPill(
    label: String,
    trackColor: Color,
    thumbColor: Color,
    arrowColor: Color,
    labelColor: Color,
    onSlideComplete: () -> Unit,
    modifier: Modifier = Modifier,
    borderColor: Color = HollowCreamDivider
) {
    val density = LocalDensity.current
    val haptic = LocalHapticFeedback.current
    val scope = rememberCoroutineScope()
    val thumbSize = 46.dp
    val trackPadding = 6.dp

    val nudgeMotion = rememberInfiniteTransition(label = "slide_nudge")
    val nudgeOffsetDp by nudgeMotion.animateFloat(
        initialValue = 0f,
        targetValue = 6f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 850, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "slide_nudge_x"
    )

    val interactionSource = remember { androidx.compose.foundation.interaction.MutableInteractionSource() }

    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth()
            .height(58.dp)
            .clip(RoundedCornerShape(29.dp))
            .background(trackColor)
            .border(1.dp, borderColor, RoundedCornerShape(29.dp))
            .clickable(
                interactionSource = interactionSource,
                indication = androidx.compose.material3.ripple(),
                role = Role.Button,
                onClick = onSlideComplete
            )
            .semantics { contentDescription = label }
    ) {
        val maxOffsetPx = with(density) {
            (maxWidth - thumbSize - trackPadding * 2).toPx().coerceAtLeast(1f)
        }
        val offsetX = remember(label) { Animatable(0f) }
        val progress = (offsetX.value / maxOffsetPx).coerceIn(0f, 1f)
        val nudgePx = with(density) { nudgeOffsetDp.dp.toPx() }
        val nudgeFactor = (1f - progress * 10f).coerceIn(0f, 1f)

        // Progressive Nothing OS track fill glow while dragging
        if (progress > 0.01f) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(with(density) { (offsetX.value + thumbSize.toPx() + trackPadding.toPx() * 2).toDp() })
                    .clip(RoundedCornerShape(29.dp))
                    .background(thumbColor.copy(alpha = 0.14f + progress * 0.22f))
            )
        }

        Text(
            text = label.uppercase(),
            fontFamily = DotoDisplayFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 12.5.sp,
            letterSpacing = 0.8.sp,
            color = labelColor,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(start = 58.dp, end = 38.dp)
                .alpha((1f - progress * 1.45f).coerceIn(0f, 1f))
        )

        val thumbScale = 1f + progress * 0.06f
        Box(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(horizontal = trackPadding)
                .offset { IntOffset((offsetX.value + nudgePx * nudgeFactor).roundToInt(), 0) }
                .size(thumbSize)
                .graphicsLayer {
                    scaleX = thumbScale
                    scaleY = thumbScale
                }
                .clip(CircleShape)
                .background(thumbColor)
                .pointerInput(maxOffsetPx, label) {
                    detectHorizontalDragGestures(
                        onDragEnd = {
                            scope.launch {
                                if (offsetX.value >= maxOffsetPx * 0.65f) {
                                    offsetX.animateTo(maxOffsetPx, tween(120))
                                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                    onSlideComplete()
                                    offsetX.snapTo(0f)
                                } else {
                                    offsetX.animateTo(
                                        0f,
                                        spring(
                                            dampingRatio = Spring.DampingRatioMediumBouncy,
                                            stiffness = Spring.StiffnessMedium
                                        )
                                    )
                                }
                            }
                        },
                        onDragCancel = {
                            scope.launch {
                                offsetX.animateTo(0f, spring())
                            }
                        },
                        onHorizontalDrag = { change, dragAmount ->
                            change.consume()
                            scope.launch {
                                val next = (offsetX.value + dragAmount).coerceIn(0f, maxOffsetPx)
                                offsetX.snapTo(next)
                            }
                        }
                    )
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Rounded.ArrowForward,
                contentDescription = null,
                tint = arrowColor,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

/**
 * Nothing OS toggle switch:
 * ON: Signature Nothing Red track with crisp white circular dot thumb
 * OFF: Matte black track with thin graphite border and muted gray dot thumb
 */
@Composable
fun HollowSwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val haptic = LocalHapticFeedback.current
    val interactionSource = remember { androidx.compose.foundation.interaction.MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val trackColor by animateColorAsState(
        targetValue = if (checked) HollowOrange else NothingSwitchOffTrack,
        animationSpec = tween(200, easing = com.hollow.vpn.ui.theme.NothingMotion.FluidEase),
        label = "hollow_switch_track"
    )
    val borderColor by animateColorAsState(
        targetValue = if (checked) HollowOrange else NothingSwitchOffBorder,
        animationSpec = tween(200, easing = com.hollow.vpn.ui.theme.NothingMotion.FluidEase),
        label = "hollow_switch_border"
    )
    val thumbColor by animateColorAsState(
        targetValue = if (checked) Color.White else NothingSwitchOffThumb,
        animationSpec = tween(200, easing = com.hollow.vpn.ui.theme.NothingMotion.FluidEase),
        label = "hollow_switch_thumb"
    )
    val thumbOffset by animateDpAsState(
        targetValue = if (checked) 22.dp else 0.dp,
        animationSpec = spring(
            dampingRatio = 0.68f,
            stiffness = 520f
        ),
        label = "hollow_switch_offset"
    )
    val thumbScale by animateFloatAsState(
        targetValue = if (isPressed) 0.88f else 1f,
        animationSpec = com.hollow.vpn.ui.theme.NothingMotion.tactileSpring(),
        label = "hollow_switch_scale"
    )

    Box(
        modifier = modifier
            .minimumInteractiveComponentSize()
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                role = Role.Switch
            ) {
                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                onCheckedChange(!checked)
            },
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .width(50.dp)
                .height(28.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(trackColor)
                .border(1.4.dp, borderColor, RoundedCornerShape(14.dp))
                .padding(horizontal = 4.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Box(
                modifier = Modifier
                    .offset(x = thumbOffset)
                    .size(20.dp)
                    .graphicsLayer {
                        scaleX = thumbScale
                        scaleY = thumbScale
                    }
                    .clip(CircleShape)
                    .background(thumbColor)
            )
        }
    }
}

/**
 * Route visual phase for HollowEditorialRouteCard.
 */
enum class HollowRoutePhase {
    DISCONNECTED,
    CONNECTING,
    CONNECTED
}

/**
 * Compact 120dp editorial route card matching the Hollow VPN Redesign HTML (`You -> Server -> Internet`).
 * Fits within the 732dp content budget without scrolling.
 */
@Composable
fun HollowEditorialRouteCard(
    phase: HollowRoutePhase,
    centerLabel: String,
    cardColor: Color,
    backgroundColor: Color,
    inkColor: Color,
    mutedColor: Color,
    lineColor: Color,
    accentColor: Color,
    onAccentColor: Color,
    modifier: Modifier = Modifier,
    rightStatusNote: String? = null
) {
    val motion = rememberInfiniteTransition(label = "editorial_route_motion")
    val linearProgress by motion.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "route_linear"
    )
    val spinDeg by motion.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "route_spinner"
    )
    val pingPulse by motion.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "route_ping"
    )

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(120.dp),
        shape = RoundedCornerShape(20.dp),
        color = cardColor
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 14.dp, vertical = 12.dp)
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val w = size.width
                val nodeR = 25.dp.toPx()
                val cy = 34.dp.toPx()
                val leftX = nodeR + 8.dp.toPx()
                val rightX = w - nodeR - 8.dp.toPx()
                val midX = (leftX + rightX) / 2f

                val leftEdgeEnd = leftX + nodeR + 3.dp.toPx()
                val midEdgeStart = midX - nodeR - 3.dp.toPx()
                val midEdgeEnd = midX + nodeR + 3.dp.toPx()
                val rightEdgeStart = rightX - nodeR - 3.dp.toPx()

                when (phase) {
                    HollowRoutePhase.DISCONNECTED -> {
                        val dashEffect = PathEffect.dashPathEffect(
                            floatArrayOf(6.dp.toPx(), 6.dp.toPx()),
                            phase = -linearProgress * 24.dp.toPx()
                        )
                        drawLine(
                            color = accentColor.copy(alpha = 0.78f),
                            start = Offset(leftEdgeEnd, cy),
                            end = Offset(midEdgeStart, cy),
                            strokeWidth = 2.6.dp.toPx(),
                            pathEffect = dashEffect,
                            cap = StrokeCap.Round
                        )
                        drawLine(
                            color = lineColor,
                            start = Offset(midEdgeEnd, cy),
                            end = Offset(rightEdgeStart, cy),
                            strokeWidth = 2.6.dp.toPx(),
                            cap = StrokeCap.Round
                        )
                    }

                    HollowRoutePhase.CONNECTING -> {
                        val dashEffect = PathEffect.dashPathEffect(
                            floatArrayOf(6.dp.toPx(), 6.dp.toPx()),
                            phase = -linearProgress * 24.dp.toPx()
                        )
                        drawLine(
                            color = accentColor,
                            start = Offset(leftEdgeEnd, cy),
                            end = Offset(midEdgeStart, cy),
                            strokeWidth = 3.dp.toPx(),
                            pathEffect = dashEffect,
                            cap = StrokeCap.Round
                        )
                        drawLine(
                            color = lineColor,
                            start = Offset(midEdgeEnd, cy),
                            end = Offset(rightEdgeStart, cy),
                            strokeWidth = 3.dp.toPx(),
                            cap = StrokeCap.Round
                        )
                    }

                    HollowRoutePhase.CONNECTED -> {
                        drawLine(
                            color = accentColor,
                            start = Offset(leftEdgeEnd, cy),
                            end = Offset(rightEdgeStart, cy),
                            strokeWidth = 3.dp.toPx(),
                            cap = StrokeCap.Round
                        )
                        // 3 animated traffic packets gliding across You -> Server -> Internet
                        for (i in 0 until 3) {
                            val t = (linearProgress + i / 3f) % 1f
                            val px = leftEdgeEnd + (rightEdgeStart - leftEdgeEnd) * t
                            drawCircle(
                                color = accentColor,
                                radius = 4.2.dp.toPx(),
                                center = Offset(px, cy)
                            )
                        }
                    }
                }

                // Left Node: You
                drawCircle(
                    color = backgroundColor,
                    radius = nodeR,
                    center = Offset(leftX, cy)
                )
                drawCircle(
                    color = inkColor,
                    radius = nodeR,
                    center = Offset(leftX, cy),
                    style = Stroke(width = 2.dp.toPx())
                )
                // Phone icon inside Left Node
                val pw = 10.dp.toPx()
                val ph = 18.dp.toPx()
                drawRoundRect(
                    color = inkColor,
                    topLeft = Offset(leftX - pw / 2f, cy - ph / 2f),
                    size = Size(pw, ph),
                    cornerRadius = androidx.compose.ui.geometry.CornerRadius(2.5.dp.toPx()),
                    style = Stroke(width = 1.9.dp.toPx())
                )
                drawLine(
                    color = inkColor,
                    start = Offset(leftX - 1.5.dp.toPx(), cy + ph / 2f - 3.5.dp.toPx()),
                    end = Offset(leftX + 1.5.dp.toPx(), cy + ph / 2f - 3.5.dp.toPx()),
                    strokeWidth = 1.9.dp.toPx(),
                    cap = StrokeCap.Round
                )

                // Center Node: Server / Route target
                if (phase == HollowRoutePhase.CONNECTING) {
                    val spinR = nodeR + 6.dp.toPx()
                    drawArc(
                        color = accentColor,
                        startAngle = spinDeg,
                        sweepAngle = 72f,
                        useCenter = false,
                        topLeft = Offset(midX - spinR, cy - spinR),
                        size = Size(spinR * 2f, spinR * 2f),
                        style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)
                    )
                } else if (phase == HollowRoutePhase.CONNECTED) {
                    val pulseR = nodeR * (1f + pingPulse * 0.65f)
                    val pulseAlpha = (1f - pingPulse) * 0.55f
                    drawCircle(
                        color = accentColor.copy(alpha = pulseAlpha),
                        radius = pulseR,
                        center = Offset(midX, cy),
                        style = Stroke(width = 2.dp.toPx())
                    )
                }

                val midFill = when (phase) {
                    HollowRoutePhase.CONNECTED -> accentColor
                    else -> backgroundColor
                }
                val midStroke = when (phase) {
                    HollowRoutePhase.DISCONNECTED -> lineColor
                    else -> accentColor
                }
                val midIconColor = when (phase) {
                    HollowRoutePhase.CONNECTED -> onAccentColor
                    HollowRoutePhase.DISCONNECTED -> mutedColor
                    HollowRoutePhase.CONNECTING -> inkColor
                }
                drawCircle(
                    color = midFill,
                    radius = nodeR,
                    center = Offset(midX, cy)
                )
                drawCircle(
                    color = midStroke,
                    radius = nodeR,
                    center = Offset(midX, cy),
                    style = Stroke(width = 2.dp.toPx())
                )
                // Stacked server rack icon inside Center Node
                val sw = 16.dp.toPx()
                val sh = 6.dp.toPx()
                drawRoundRect(
                    color = midIconColor,
                    topLeft = Offset(midX - sw / 2f, cy - 8.dp.toPx()),
                    size = Size(sw, sh),
                    cornerRadius = androidx.compose.ui.geometry.CornerRadius(2.dp.toPx()),
                    style = Stroke(width = 1.9.dp.toPx())
                )
                drawRoundRect(
                    color = midIconColor,
                    topLeft = Offset(midX - sw / 2f, cy + 2.dp.toPx()),
                    size = Size(sw, sh),
                    cornerRadius = androidx.compose.ui.geometry.CornerRadius(2.dp.toPx()),
                    style = Stroke(width = 1.9.dp.toPx())
                )

                // Right Node: Internet
                drawCircle(
                    color = backgroundColor,
                    radius = nodeR,
                    center = Offset(rightX, cy)
                )
                drawCircle(
                    color = inkColor,
                    radius = nodeR,
                    center = Offset(rightX, cy),
                    style = Stroke(width = 2.dp.toPx())
                )
                // Globe icon inside Right Node
                val gr = 9.dp.toPx()
                drawCircle(
                    color = inkColor,
                    radius = gr,
                    center = Offset(rightX, cy),
                    style = Stroke(width = 1.9.dp.toPx())
                )
                drawLine(
                    color = inkColor,
                    start = Offset(rightX - gr, cy),
                    end = Offset(rightX + gr, cy),
                    strokeWidth = 1.7.dp.toPx()
                )
                drawOval(
                    color = inkColor,
                    topLeft = Offset(rightX - gr * 0.45f, cy - gr),
                    size = Size(gr * 0.9f, gr * 2f),
                    style = Stroke(width = 1.7.dp.toPx())
                )
            }

            // Bottom node labels aligned cleanly below circles
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(horizontal = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "You",
                    fontFamily = SpaceGroteskFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 13.sp,
                    color = mutedColor,
                    modifier = Modifier.width(66.dp),
                    maxLines = 1
                )
                Text(
                    text = centerLabel,
                    fontFamily = SpaceGroteskFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 13.sp,
                    color = if (phase == HollowRoutePhase.DISCONNECTED) mutedColor else inkColor,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = rightStatusNote ?: "Internet",
                    fontFamily = SpaceGroteskFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 13.sp,
                    color = mutedColor,
                    maxLines = 1
                )
            }
        }
    }
}

/**
 * Paired Download & Upload speed cards matching the Connected screen HTML redesign.
 */
@Composable
fun HollowConnectedSpeedPair(
    downloadNum: String,
    downloadUnit: String,
    uploadNum: String,
    uploadUnit: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Surface(
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(20.dp),
            color = HollowForestCard
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp)
            ) {
                Text(
                    text = "DOWNLOAD",
                    fontFamily = JetBrainsMonoFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 12.sp,
                    letterSpacing = 1.2.sp,
                    color = HollowForestMuted,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        text = downloadNum,
                        fontFamily = JetBrainsMonoFamily,
                        fontWeight = FontWeight.Medium,
                        fontSize = 26.sp,
                        color = HollowDarkInk,
                        maxLines = 1
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = downloadUnit,
                        fontFamily = JetBrainsMonoFamily,
                        fontSize = 13.sp,
                        color = HollowForestMuted,
                        modifier = Modifier.padding(bottom = 3.dp)
                    )
                }
            }
        }

        Surface(
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(20.dp),
            color = HollowForestCard
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp)
            ) {
                Text(
                    text = "UPLOAD",
                    fontFamily = JetBrainsMonoFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 12.sp,
                    letterSpacing = 1.2.sp,
                    color = HollowForestMuted,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        text = uploadNum,
                        fontFamily = JetBrainsMonoFamily,
                        fontWeight = FontWeight.Medium,
                        fontSize = 26.sp,
                        color = HollowDarkInk,
                        maxLines = 1
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = uploadUnit,
                        fontFamily = JetBrainsMonoFamily,
                        fontSize = 13.sp,
                        color = HollowForestMuted,
                        modifier = Modifier.padding(bottom = 3.dp)
                    )
                }
            }
        }
    }
}

/**
 * 24-bar animated lime throughput equalizer matching Connected screen HTML.
 */
@Composable
fun HollowThroughputEqualizer(
    downloadBytesPerSec: Long,
    uploadBytesPerSec: Long,
    elapsedSeconds: Long,
    modifier: Modifier = Modifier
) {
    val samples = remember {
        mutableStateListOf(
            0.25f, 0.37f, 0.32f, 0.48f, 0.43f, 0.62f,
            0.54f, 0.75f, 0.57f, 0.52f, 0.68f, 0.86f,
            0.77f, 0.66f, 0.82f, 0.95f, 0.71f, 0.61f,
            0.80f, 1.00f, 0.89f, 0.68f, 0.77f, 0.91f
        )
    }

    LaunchedEffect(elapsedSeconds, downloadBytesPerSec, uploadBytesPerSec) {
        val totalBps = downloadBytesPerSec + uploadBytesPerSec
        val normalized = if (totalBps <= 0L) {
            val phase = (elapsedSeconds % 12).toFloat() / 12f
            0.25f + 0.30f * abs(sin(phase * PI.toFloat()))
        } else {
            val logVal = log10(totalBps.toDouble().coerceAtLeast(100.0)).toFloat()
            ((logVal - 2.0f) / 5.3f).coerceIn(0.25f, 0.98f)
        }
        if (samples.size >= 24) {
            samples.removeAt(0)
        }
        samples.add(normalized)
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.Bottom
    ) {
        samples.forEachIndexed { index, targetFraction ->
            val animatedHeight by animateFloatAsState(
                targetValue = targetFraction.coerceIn(0.20f, 1f),
                animationSpec = tween(durationMillis = 360, easing = FastOutSlowInEasing),
                label = "eq_bar_$index"
            )
            val t = index / (samples.size - 1).coerceAtLeast(1).toFloat()
            val barAlpha = 0.55f + 0.43f * t
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(animatedHeight)
                    .clip(RoundedCornerShape(3.dp))
                    .background(HollowLime.copy(alpha = barAlpha))
            )
        }
    }
}

/**
 * Encrypted tunnel route card for the Protected screen matching Connected__enhanced_27363b4e.pdf:
 * Shows You -> ENCRYPTED -> Country badge on top, and YOUR REAL IP (strikethrough) vs WEBSITES SEE below.
 */
@Composable
fun HollowProtectedTunnelCard(
    countryCode: String,
    countryName: String,
    realIp: String,
    websitesSeeIp: String,
    modifier: Modifier = Modifier
) {
    val motion = rememberInfiniteTransition(label = "protected_tunnel_motion")
    val packetTravel by motion.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "tunnel_packet_travel"
    )

    val badgeCode = countryCode
        .takeIf { it.isNotBlank() && !it.equals("XX", ignoreCase = true) }
        ?.uppercase()
        ?: "VP"

    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        color = HollowForestCard,
        border = BorderStroke(1.dp, HollowForestDivider)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 18.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.width(56.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(HollowForest)
                            .border(1.2.dp, HollowForestDivider, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Smartphone,
                            contentDescription = "Your device",
                            tint = HollowDarkInk,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "You",
                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                        color = HollowForestMuted,
                        maxLines = 1
                    )
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(bottom = 18.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Canvas(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(16.dp)
                            .padding(horizontal = 6.dp)
                    ) {
                        val y = size.height / 2f
                        drawLine(
                            color = HollowLime.copy(alpha = 0.38f),
                            start = Offset(0f, y),
                            end = Offset(size.width, y),
                            strokeWidth = 1.6.dp.toPx(),
                            cap = StrokeCap.Round
                        )
                        val dotX = packetTravel * size.width
                        drawCircle(
                            color = HollowLime.copy(alpha = 0.24f),
                            radius = 6.dp.toPx(),
                            center = Offset(dotX, y)
                        )
                        drawCircle(
                            color = HollowLime,
                            radius = 3.dp.toPx(),
                            center = Offset(dotX, y)
                        )
                    }

                    Box(
                        modifier = Modifier
                            .background(HollowForestCard)
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "ENCRYPTED",
                            fontFamily = JetBrainsMonoFamily,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 10.sp,
                            letterSpacing = 1.2.sp,
                            color = HollowLime,
                            maxLines = 1
                        )
                    }
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.width(64.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(HollowLime),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = badgeCode,
                            fontFamily = JetBrainsMonoFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = HollowForest
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = countryName,
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
                        ),
                        color = HollowDarkInk,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            HorizontalDivider(
                thickness = 1.dp,
                color = HollowForestDivider,
                modifier = Modifier.padding(horizontal = 18.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 14.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "YOUR REAL IP",
                        fontFamily = JetBrainsMonoFamily,
                        fontWeight = FontWeight.Medium,
                        fontSize = 10.sp,
                        letterSpacing = 1.1.sp,
                        color = HollowForestMuted,
                        maxLines = 1
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = realIp,
                        fontFamily = JetBrainsMonoFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 13.5.sp,
                        color = HollowForestMuted,
                        textDecoration = TextDecoration.LineThrough,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = "WEBSITES SEE",
                        fontFamily = JetBrainsMonoFamily,
                        fontWeight = FontWeight.Medium,
                        fontSize = 10.sp,
                        letterSpacing = 1.1.sp,
                        color = HollowForestMuted,
                        maxLines = 1
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = websitesSeeIp,
                        fontFamily = JetBrainsMonoFamily,
                        fontWeight = FontWeight.Medium,
                        fontSize = 13.5.sp,
                        color = HollowDarkInk,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

/**
 * Paired DOWNLOAD / UPLOAD card with live dual-line area chart matching Connected__enhanced_27363b4e.pdf.
 */
@Composable
fun HollowProtectedSpeedChartCard(
    downloadNum: String,
    downloadUnit: String,
    uploadNum: String,
    uploadUnit: String,
    downloadBytesPerSec: Long,
    uploadBytesPerSec: Long,
    elapsedSeconds: Long,
    chartHeight: Dp = 54.dp,
    modifier: Modifier = Modifier
) {
    val dlHistory = remember {
        mutableStateListOf<Float>().apply {
            repeat(24) { add(0f) }
        }
    }
    val ulHistory = remember {
        mutableStateListOf<Float>().apply {
            repeat(24) { add(0f) }
        }
    }

    LaunchedEffect(elapsedSeconds, downloadBytesPerSec, uploadBytesPerSec) {
        if (dlHistory.size >= 24) dlHistory.removeAt(0)
        dlHistory.add(downloadBytesPerSec.coerceAtLeast(0L).toFloat())
        if (ulHistory.size >= 24) ulHistory.removeAt(0)
        ulHistory.add(uploadBytesPerSec.coerceAtLeast(0L).toFloat())
    }

    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        color = HollowForestCard,
        border = BorderStroke(1.dp, HollowForestDivider)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 18.dp, end = 18.dp, top = 11.dp, bottom = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(7.dp)
                                .clip(RoundedCornerShape(2.dp))
                                .background(HollowLime)
                        )
                        Text(
                            text = "DOWNLOAD",
                            fontFamily = JetBrainsMonoFamily,
                            fontWeight = FontWeight.Medium,
                            fontSize = 10.5.sp,
                            letterSpacing = 1.2.sp,
                            color = HollowForestMuted
                        )
                    }
                    Spacer(modifier = Modifier.height(2.dp))
                    Row(verticalAlignment = Alignment.Bottom) {
                        Text(
                            text = downloadNum,
                            fontFamily = JetBrainsMonoFamily,
                            fontWeight = FontWeight.Medium,
                            fontSize = 20.sp,
                            color = HollowDarkInk,
                            maxLines = 1
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = downloadUnit,
                            fontFamily = JetBrainsMonoFamily,
                            fontSize = 11.5.sp,
                            color = HollowForestMuted,
                            modifier = Modifier.padding(bottom = 2.dp)
                        )
                    }
                }

                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.End
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(7.dp)
                                .clip(RoundedCornerShape(2.dp))
                                .background(Color(0xFFC8D5CC))
                        )
                        Text(
                            text = "UPLOAD",
                            fontFamily = JetBrainsMonoFamily,
                            fontWeight = FontWeight.Medium,
                            fontSize = 10.sp,
                            letterSpacing = 1.1.sp,
                            color = HollowForestMuted
                        )
                    }
                    Spacer(modifier = Modifier.height(2.dp))
                    Row(verticalAlignment = Alignment.Bottom) {
                        Text(
                            text = uploadNum,
                            fontFamily = JetBrainsMonoFamily,
                            fontWeight = FontWeight.Medium,
                            fontSize = 20.sp,
                            color = HollowDarkInk,
                            maxLines = 1
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = uploadUnit,
                            fontFamily = JetBrainsMonoFamily,
                            fontSize = 11.5.sp,
                            color = HollowForestMuted,
                            modifier = Modifier.padding(bottom = 2.dp)
                        )
                    }
                }
            }

            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(chartHeight)
                    .padding(horizontal = 16.dp)
            ) {
                val w = size.width
                val h = size.height
                val topPad = 4.dp.toPx()
                val bottomPad = 3.dp.toPx()
                val drawH = (h - topPad - bottomPad).coerceAtLeast(1f)

                val gridEffect = PathEffect.dashPathEffect(floatArrayOf(3.dp.toPx(), 4.dp.toPx()), 0f)
                for (i in 0..2) {
                    val gy = topPad + (drawH * i / 2f)
                    drawLine(
                        color = HollowForestDivider.copy(alpha = 0.75f),
                        start = Offset(0f, gy),
                        end = Offset(w, gy),
                        strokeWidth = 1.dp.toPx(),
                        pathEffect = gridEffect
                    )
                }

                val maxPeak = maxOf(
                    dlHistory.maxOrNull() ?: 0f,
                    ulHistory.maxOrNull() ?: 0f,
                    1024f * 32f // 32 KB/s minimum baseline for balanced idle scaling
                )

                val count = dlHistory.size.coerceAtLeast(2)
                val stepX = w / (count - 1).toFloat()

                fun pointY(value: Float): Float {
                    val norm = (value / maxPeak).coerceIn(0f, 1f)
                    return topPad + drawH * (1f - norm * 0.90f)
                }

                // Upload subtle baseline line
                val ulPath = Path().apply {
                    ulHistory.forEachIndexed { idx, value ->
                        val x = idx * stepX
                        val y = pointY(value)
                        if (idx == 0) moveTo(x, y) else lineTo(x, y)
                    }
                }
                drawPath(
                    path = ulPath,
                    color = Color(0xFFC8D5CC).copy(alpha = 0.72f),
                    style = Stroke(
                        width = 1.6.dp.toPx(),
                        cap = StrokeCap.Round,
                        join = StrokeJoin.Round
                    )
                )

                // Download gradient fill + primary lime stroke
                val dlFillPath = Path()
                val dlLinePath = Path()
                var lastDlOffset = Offset(0f, topPad + drawH)

                dlHistory.forEachIndexed { idx, value ->
                    val x = idx * stepX
                    val y = pointY(value)
                    lastDlOffset = Offset(x, y)
                    if (idx == 0) {
                        dlLinePath.moveTo(x, y)
                        dlFillPath.moveTo(x, topPad + drawH)
                        dlFillPath.lineTo(x, y)
                    } else {
                        dlLinePath.lineTo(x, y)
                        dlFillPath.lineTo(x, y)
                    }
                }
                dlFillPath.lineTo(lastDlOffset.x, topPad + drawH)
                dlFillPath.close()

                drawPath(
                    path = dlFillPath,
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            HollowLime.copy(alpha = 0.34f),
                            HollowLime.copy(alpha = 0.02f)
                        ),
                        startY = topPad,
                        endY = topPad + drawH
                    )
                )
                drawPath(
                    path = dlLinePath,
                    color = HollowLime,
                    style = Stroke(
                        width = 2.2.dp.toPx(),
                        cap = StrokeCap.Round,
                        join = StrokeJoin.Round
                    )
                )

                drawCircle(
                    color = HollowLime.copy(alpha = 0.25f),
                    radius = 7.dp.toPx(),
                    center = lastDlOffset
                )
                drawCircle(
                    color = HollowLime,
                    radius = 3.2.dp.toPx(),
                    center = lastDlOffset
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 2.dp, bottom = 9.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "60s ago",
                    fontFamily = JetBrainsMonoFamily,
                    fontSize = 9.5.sp,
                    color = HollowForestMuted
                )
                Text(
                    text = "now",
                    fontFamily = JetBrainsMonoFamily,
                    fontSize = 9.5.sp,
                    color = HollowForestMuted
                )
            }
        }
    }
}

/**
 * Editorial underlined tab strip matching Pages 4 and 5.
 */
@Composable
fun HollowUnderlinedTabRow(
    tabs: List<String>,
    selectedIndex: Int,
    onSelectTab: (Int) -> Unit,
    modifier: Modifier = Modifier,
    tabTestTags: List<String> = emptyList(),
    tabFontSize: TextUnit = 15.sp,
    tabSpacing: Dp = 24.dp
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(tabSpacing)
        ) {
            tabs.forEachIndexed { index, title ->
                val isSelected = index == selectedIndex
                val tag = tabTestTags.getOrNull(index)
                Column(
                    modifier = Modifier
                        .then(if (tag != null) Modifier.testTag(tag) else Modifier)
                        .clickable(role = Role.Tab) { onSelectTab(index) }
                        .padding(top = 6.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontSize = tabFontSize,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                        ),
                        color = if (isSelected) HollowInk else HollowInkSecondary,
                        maxLines = 1
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Box(
                        modifier = Modifier
                            .height(2.5.dp)
                            .width(if (isSelected) 36.dp else 0.dp)
                            .background(if (isSelected) HollowOrange else Color.Transparent)
                    )
                }
            }
        }
        HorizontalDivider(thickness = 1.dp, color = HollowCreamDivider)
    }
}

/**
 * Editorial exposure & route readiness card for the Disconnected / Unprotected home state.
 * Displays detected ISP provider on the left and public country location on the right.
 */
@Composable
fun HollowDisconnectedExposureCard(
    originCountryCode: String,
    originLocationLabel: String,
    targetCountryCode: String?,
    targetCountryName: String?,
    tunModeEnabled: Boolean,
    ispLabel: String = "Detecting…",
    mapHeight: Dp = 132.dp,
    fillCanvasHeight: Boolean = false,
    modifier: Modifier = Modifier
) {
    val motion = rememberInfiniteTransition(label = "exposure_map_motion")
    val pulse by motion.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "exposure_beacon_pulse"
    )
    val sweep by motion.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 5200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "exposure_sweep"
    )

    val hasValidOrigin = originCountryCode.length == 2 && !originCountryCode.equals("XX", ignoreCase = true)
    val hasValidTarget = !targetCountryCode.isNullOrBlank() &&
        targetCountryCode.length == 2 &&
        !targetCountryCode.equals("XX", ignoreCase = true)

    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        color = HollowCreamSurface,
        border = BorderStroke(1.dp, HollowCreamDivider)
    ) {
        Column(modifier = if (fillCanvasHeight) Modifier.fillMaxSize() else Modifier.fillMaxWidth()) {
            Spacer(modifier = Modifier.height(8.dp))

            val mapInkColor = HollowInk
            val mapCanvasColor = NothingCanvas
            val mapDotMutedColor = NothingMapDotMuted

            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .then(
                        if (fillCanvasHeight) {
                            Modifier.weight(1f)
                        } else {
                            Modifier.height(mapHeight)
                        }
                    )
                    .padding(horizontal = 12.dp)
            ) {
                val w = size.width
                val h = size.height
                val padX = 10.dp.toPx()
                val padY = 8.dp.toPx()
                val availW = (w - padX * 2f).coerceAtLeast(1f)
                val availH = (h - padY * 2f).coerceAtLeast(1f)
                val targetRatio = 1.92f
                val mapW: Float
                val mapH: Float
                if (availW / availH > targetRatio) {
                    mapH = availH
                    mapW = availH * targetRatio
                } else {
                    mapW = availW
                    mapH = availW / targetRatio
                }
                val originOffsetX = (w - mapW) / 2f
                val originOffsetY = (h - mapH) / 2f
                val dotRadius = (mapW * 0.0068f).coerceIn(1.6.dp.toPx(), 2.6.dp.toPx())

                val originNorm = if (hasValidOrigin) countryMapCoordinate(originCountryCode) else Offset(0.50f, 0.45f)
                val originCenter = Offset(originOffsetX + originNorm.x * mapW, originOffsetY + originNorm.y * mapH)

                val targetNorm = if (hasValidTarget) countryMapCoordinate(targetCountryCode!!) else null
                val targetCenter = targetNorm?.let { Offset(originOffsetX + it.x * mapW, originOffsetY + it.y * mapH) }

                for (pt in WORLD_DOT_POINTS) {
                    val px = originOffsetX + pt.x * mapW
                    val py = originOffsetY + pt.y * mapH
                    val waveDist = abs(pt.x - sweep)
                    val waveBoost = 1f - (waveDist / 0.18f).coerceIn(0f, 1f)
                    val nearOrigin = hasValidOrigin && hypot(pt.x - originNorm.x, pt.y - originNorm.y) < 0.085f
                    val nearTarget = targetNorm != null && hypot(pt.x - targetNorm.x, pt.y - targetNorm.y) < 0.085f

                    val dotColor = when {
                        nearOrigin -> HollowOrange.copy(alpha = 0.65f + 0.35f * waveBoost)
                        nearTarget -> mapInkColor.copy(alpha = 0.60f + 0.35f * waveBoost)
                        else -> mapDotMutedColor.copy(alpha = 0.72f + 0.28f * waveBoost)
                    }
                    drawCircle(
                        color = dotColor,
                        radius = dotRadius * (1f + waveBoost * 0.22f),
                        center = Offset(px, py)
                    )
                }

                // Dashed planned route arc between exposed origin and selected exit node
                if (targetCenter != null && hypot(targetCenter.x - originCenter.x, targetCenter.y - originCenter.y) > 8.dp.toPx()) {
                    val midX = (originCenter.x + targetCenter.x) / 2f
                    val lift = (abs(targetCenter.x - originCenter.x) * 0.28f).coerceIn(14.dp.toPx(), 36.dp.toPx())
                    val ctrlY = (minOf(originCenter.y, targetCenter.y) - lift).coerceAtLeast(4.dp.toPx())

                    val arcPath = Path().apply {
                        moveTo(originCenter.x, originCenter.y)
                        quadraticTo(midX, ctrlY, targetCenter.x, targetCenter.y)
                    }
                    drawPath(
                        path = arcPath,
                        color = mapInkColor.copy(alpha = 0.42f),
                        style = Stroke(
                            width = 1.4.dp.toPx(),
                            cap = StrokeCap.Round,
                            pathEffect = PathEffect.dashPathEffect(
                                floatArrayOf(5.dp.toPx(), 4.dp.toPx()),
                                phase = -sweep * 28.dp.toPx()
                            )
                        )
                    )
                }

                // Exposed origin radar ripple
                val rippleAlpha = (1f - pulse) * 0.50f
                val rippleRadius = 6.dp.toPx() + pulse * 18.dp.toPx()
                drawCircle(
                    color = HollowOrange.copy(alpha = rippleAlpha),
                    radius = rippleRadius,
                    center = originCenter,
                    style = Stroke(width = 1.5.dp.toPx())
                )
                drawCircle(
                    color = HollowOrange.copy(alpha = 0.16f),
                    radius = 9.dp.toPx(),
                    center = originCenter
                )
                drawCircle(
                    color = HollowOrange,
                    radius = 4.2.dp.toPx(),
                    center = originCenter
                )

                // Target VPN exit node marker
                if (targetCenter != null) {
                    drawCircle(
                        color = mapInkColor,
                        radius = 5.5.dp.toPx(),
                        center = targetCenter
                    )
                    drawCircle(
                        color = mapCanvasColor,
                        radius = 2.2.dp.toPx(),
                        center = targetCenter
                    )
                }
            }

            NothingDottedDivider(
                color = HollowCreamDivider,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 11.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 10.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "ISP PROVIDER",
                        fontFamily = JetBrainsMonoFamily,
                        fontWeight = FontWeight.Medium,
                        fontSize = 9.5.sp,
                        letterSpacing = 0.8.sp,
                        color = HollowInkSecondary,
                        maxLines = 1
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = ispLabel.ifBlank { "Detecting…" },
                        fontFamily = SpaceGroteskFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 12.5.sp,
                        color = HollowInk,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Box(
                    modifier = Modifier
                        .width(1.dp)
                        .height(26.dp)
                        .background(HollowCreamDivider)
                )

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 10.dp),
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = "YOUR LOCATION",
                        fontFamily = JetBrainsMonoFamily,
                        fontWeight = FontWeight.Medium,
                        fontSize = 9.5.sp,
                        letterSpacing = 0.8.sp,
                        color = HollowInkSecondary,
                        maxLines = 1
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        CountryFlag(countryCode = originCountryCode, width = 20.dp, height = 14.dp)
                        Text(
                            text = originLocationLabel,
                            fontFamily = SpaceGroteskFamily,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 12.5.sp,
                            color = HollowInk,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.weight(1f, fill = false)
                        )
                    }
                }
            }
        }
    }
}

// ──────────────────────────────────────────────────────────────────────────────
// Legacy/Shared Visuals preserved for compatibility & Splash/Server cards
// ──────────────────────────────────────────────────────────────────────────────

private val WORLD_DOT_MASK = listOf(
    ".....######...........##.......#########....",
    "...##########........####...##############..",
    "..###########.......######################..",
    "...#########.........#####################..",
    "....#######..........####################...",
    ".....####............#########..########....",
    ".......###...........########....######.....",
    "........###...........######......####......",
    ".........#####........#######......##.......",
    ".........#######.....#########..............",
    "..........#######....########.......###.....",
    "..........######......######.......#####....",
    "...........####........####........######...",
    "...........###..........##..........####....",
    "............#...............................",
    "............................................"
)

private val WORLD_DOT_POINTS: List<Offset> by lazy {
    val rows = WORLD_DOT_MASK.size
    val cols = WORLD_DOT_MASK.first().length
    buildList {
        for (r in 0 until rows) {
            val row = WORLD_DOT_MASK[r]
            for (c in 0 until cols) {
                if (row[c] == '#') {
                    add(Offset((c + 0.5f) / cols.toFloat(), (r + 0.5f) / rows.toFloat()))
                }
            }
        }
    }
}

private fun countryMapCoordinate(countryCode: String): Offset {
    return when (countryCode.uppercase()) {
        "US" -> Offset(0.18f, 0.28f)
        "CA" -> Offset(0.19f, 0.16f)
        "BR" -> Offset(0.29f, 0.68f)
        "GB", "IE" -> Offset(0.46f, 0.19f)
        "FR", "NL", "BE" -> Offset(0.49f, 0.22f)
        "DE", "CH", "AT", "PL" -> Offset(0.52f, 0.22f)
        "IT" -> Offset(0.53f, 0.29f)
        "SE", "FI", "NO" -> Offset(0.54f, 0.12f)
        "UA", "RO" -> Offset(0.57f, 0.24f)
        "RU" -> Offset(0.68f, 0.15f)
        "TR", "AE" -> Offset(0.61f, 0.34f)
        "IN" -> Offset(0.71f, 0.44f)
        "SG", "MY", "TH" -> Offset(0.77f, 0.55f)
        "HK", "TW", "CN" -> Offset(0.81f, 0.36f)
        "KR", "JP" -> Offset(0.87f, 0.29f)
        "AU" -> Offset(0.85f, 0.74f)
        else -> Offset(0.52f, 0.24f)
    }
}

private fun buildExpressiveScallopPath(
    center: Offset,
    baseRadius: Float,
    lobeAmplitude: Float,
    lobes: Int = 10
): Path {
    val steps = 120
    return Path().apply {
        for (i in 0..steps) {
            val angle = (2.0 * PI * i) / steps
            val r = baseRadius + lobeAmplitude * cos(lobes * angle).toFloat()
            val x = center.x + r * cos(angle).toFloat()
            val y = center.y + r * sin(angle).toFloat()
            if (i == 0) moveTo(x, y) else lineTo(x, y)
        }
        close()
    }
}

@Composable
fun HollowAtmosphereBackground(
    modifier: Modifier = Modifier,
    isConnected: Boolean = false,
    isConnecting: Boolean = false,
    showWorldMap: Boolean = false,
    activeCountryCode: String = "",
    content: @Composable BoxScope.() -> Unit
) {
    val scheme = MaterialTheme.colorScheme
    val isDark = scheme.background.luminance() < 0.5f

    val auraTint by animateColorAsState(
        targetValue = when {
            isConnected -> scheme.tertiary
            isConnecting -> scheme.primary
            else -> scheme.secondary
        },
        animationSpec = spring(stiffness = Spring.StiffnessLow),
        label = "m3_aura_tint"
    )

    val mapMotion = rememberInfiniteTransition(label = "m3_world_map_motion")
    val sweepProgress by mapMotion.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = if (isConnecting) 2400 else 5800, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "m3_map_sweep"
    )
    val beaconPulse by mapMotion.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = if (isConnecting) 1400 else 2600, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "m3_map_beacon"
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(scheme.background)
    ) {
        if (showWorldMap) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val w = size.width
                val h = size.height
                val mapLeft = w * 0.04f
                val mapTop = h * 0.08f
                val mapW = w * 0.92f
                val mapH = h * 0.34f
                val dotRadius = (w * 0.0062f).coerceIn(1.8.dp.toPx(), 3.0.dp.toPx())
                val hasActiveLocation = activeCountryCode.length == 2 && !activeCountryCode.equals("XX", ignoreCase = true)
                val targetNorm = if (hasActiveLocation) countryMapCoordinate(activeCountryCode) else null
                val targetCenter = targetNorm?.let { Offset(mapLeft + it.x * mapW, mapTop + it.y * mapH) }

                for (pt in WORLD_DOT_POINTS) {
                    val px = mapLeft + pt.x * mapW
                    val py = mapTop + pt.y * mapH
                    val distToWave = abs(pt.x - sweepProgress)
                    val waveBoost = 1f - (distToWave / 0.16f).coerceIn(0f, 1f)
                    val nearTarget = targetNorm != null && hypot(pt.x - targetNorm.x, pt.y - targetNorm.y) < 0.09f

                    val dotColor = when {
                        nearTarget && (isConnected || isConnecting) ->
                            auraTint.copy(alpha = 0.58f + 0.35f * waveBoost)
                        else ->
                            scheme.outlineVariant.copy(alpha = (if (isDark) 0.45f else 0.75f) + waveBoost * 0.2f)
                    }
                    drawCircle(
                        color = dotColor,
                        radius = dotRadius * (1f + waveBoost * 0.25f),
                        center = Offset(px, py)
                    )
                }

                if (targetCenter != null) {
                    val rippleAlpha = (1f - beaconPulse) * if (isConnected || isConnecting) 0.55f else 0.28f
                    val rippleRadius = 6.dp.toPx() + beaconPulse * 24.dp.toPx()
                    drawCircle(
                        color = auraTint.copy(alpha = rippleAlpha),
                        radius = rippleRadius,
                        center = targetCenter,
                        style = Stroke(width = 1.6.dp.toPx())
                    )
                    drawCircle(
                        color = auraTint.copy(alpha = if (isConnected || isConnecting) 0.95f else 0.65f),
                        radius = 4.dp.toPx(),
                        center = targetCenter
                    )
                }
            }
        }
        content()
    }
}

@Composable
fun TunnelPowerReactor(
    isConnected: Boolean,
    isConnecting: Boolean,
    onToggleClick: () -> Unit,
    modifier: Modifier = Modifier,
    reactorSize: Dp = 198.dp
) {
    val scheme = MaterialTheme.colorScheme
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val pressScale by animateFloatAsState(
        targetValue = if (isPressed) 0.91f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMediumLow
        ),
        label = "m3_expressive_press_scale"
    )

    val containerColor by animateColorAsState(
        targetValue = when {
            isConnected -> scheme.tertiary
            isConnecting -> scheme.primaryContainer
            else -> scheme.surfaceContainerHigh
        },
        animationSpec = spring(stiffness = Spring.StiffnessMediumLow),
        label = "m3_core_container_color"
    )
    val contentColor by animateColorAsState(
        targetValue = when {
            isConnected -> scheme.onTertiary
            isConnecting -> scheme.onPrimaryContainer
            else -> scheme.primary
        },
        animationSpec = spring(stiffness = Spring.StiffnessMediumLow),
        label = "m3_core_content_color"
    )
    val ringTint by animateColorAsState(
        targetValue = when {
            isConnected -> scheme.tertiary
            isConnecting -> scheme.primary
            else -> scheme.primaryContainer
        },
        animationSpec = spring(stiffness = Spring.StiffnessLow),
        label = "m3_ring_tint"
    )

    val cornerRadius by animateDpAsState(
        targetValue = when {
            isPressed -> 24.dp
            isConnected -> 48.dp
            isConnecting -> 30.dp
            else -> 32.dp
        },
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMediumLow
        ),
        label = "m3_expressive_corner_morph"
    )

    val motion = rememberInfiniteTransition(label = "m3_reactor_motion")
    val rotation by motion.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = if (isConnecting) 3200 else 18000,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "m3_scallop_rotation"
    )
    val pulse by motion.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "m3_sonar_pulse"
    )

    val buttonSize = reactorSize * 0.50f

    Box(
        modifier = modifier.size(reactorSize),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val center = Offset(size.width / 2f, size.height / 2f)
            val maxR = size.minDimension * 0.44f

            if (isConnected || isConnecting) {
                val expandR = maxR * (0.72f + pulse * 0.34f)
                drawCircle(
                    color = ringTint.copy(alpha = (1f - pulse) * 0.32f),
                    radius = expandR,
                    center = center,
                    style = Stroke(width = 2.dp.toPx())
                )
            }

            val scallopRotation = if (isConnected || isConnecting) rotation else rotation * 0.25f
            rotate(degrees = scallopRotation, pivot = center) {
                val outerScallop = buildExpressiveScallopPath(
                    center = center,
                    baseRadius = maxR * 0.85f,
                    lobeAmplitude = maxR * if (isConnecting) 0.085f else 0.055f,
                    lobes = 10
                )
                drawPath(
                    path = outerScallop,
                    color = ringTint.copy(alpha = if (isConnected) 0.22f else 0.55f)
                )
                drawPath(
                    path = outerScallop,
                    color = ringTint.copy(alpha = if (isConnected) 0.55f else 0.35f),
                    style = Stroke(width = 1.6.dp.toPx())
                )
            }
        }

        if (isConnecting) {
            CircularProgressIndicator(
                modifier = Modifier.size(buttonSize + 20.dp),
                color = scheme.primary,
                trackColor = scheme.surfaceContainerHighest,
                strokeWidth = 3.5.dp,
                strokeCap = StrokeCap.Round
            )
        }

        val actionDesc = when {
            isConnected -> "Disconnect VPN tunnel"
            isConnecting -> "Cancel VPN connection"
            else -> "Connect VPN tunnel"
        }

        Surface(
            modifier = Modifier
                .scale(pressScale)
                .size(buttonSize)
                .clip(RoundedCornerShape(cornerRadius))
                .clickable(
                    interactionSource = interactionSource,
                    indication = ripple(bounded = true),
                    role = Role.Button,
                    onClick = onToggleClick
                )
                .semantics { contentDescription = actionDesc }
                .testTag("hero_power_button"),
            shape = RoundedCornerShape(cornerRadius),
            color = containerColor,
            border = BorderStroke(
                width = 1.5.dp,
                color = if (isConnected) scheme.tertiary.copy(alpha = 0.4f) else scheme.outlineVariant.copy(alpha = 0.6f)
            ),
            tonalElevation = 6.dp,
            shadowElevation = if (isConnected) 8.dp else 4.dp
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = Icons.Rounded.PowerSettingsNew,
                    contentDescription = null,
                    tint = contentColor,
                    modifier = Modifier.size(buttonSize * 0.40f)
                )
            }
        }
    }
}

@Composable
fun HollowVpnLogoCard(
    modifier: Modifier = Modifier,
    cardSize: Dp = 88.dp
) {
    Surface(
        modifier = modifier.size(cardSize),
        shape = RoundedCornerShape(24.dp),
        color = NothingSurface,
        border = BorderStroke(1.dp, HollowCreamDivider)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(18.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(cardSize * 0.17f)
                    .align(Alignment.TopStart)
                    .clip(CircleShape)
                    .background(HollowOrange)
            )
            Text(
                text = "H",
                fontFamily = DotoDisplayFamily,
                fontWeight = FontWeight.Bold,
                fontSize = (cardSize.value * 0.46f).sp,
                color = HollowInk,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .offset(x = (-2).dp, y = 4.dp)
            )
        }
    }
}

@Composable
fun FetchingSpinnerRing(
    modifier: Modifier = Modifier,
    size: Dp = 64.dp
) {
    val transition = rememberInfiniteTransition(label = "nothing_glyph_loader")
    val rot by transition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2800, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "glyph_loader_rot"
    )

    val spinnerInkColor = HollowInk

    Box(
        modifier = modifier.size(size),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val cx = this.size.width / 2f
            val cy = this.size.height / 2f
            val r = this.size.minDimension * 0.42f
            val dotCount = 12
            rotate(degrees = rot, pivot = Offset(cx, cy)) {
                for (i in 0 until dotCount) {
                    val angle = (2.0 * PI * i) / dotCount
                    val dx = cx + r * cos(angle).toFloat()
                    val dy = cy + r * sin(angle).toFloat()
                    val isLead = i == 0
                    val trailFade = ((dotCount - i).toFloat() / dotCount.toFloat()).coerceIn(0.22f, 1f)
                    drawCircle(
                        color = if (isLead) HollowOrange else spinnerInkColor.copy(alpha = trailFade * 0.78f),
                        radius = if (isLead) 3.4.dp.toPx() else 2.0.dp.toPx(),
                        center = Offset(dx, dy)
                    )
                }
            }
        }
        Box(
            modifier = Modifier
                .size(size * 0.34f)
                .clip(CircleShape)
                .border(1.5.dp, HollowCreamDivider, CircleShape)
                .background(NothingCanvas),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(7.dp)
                    .clip(CircleShape)
                    .background(HollowOrange)
            )
        }
    }
}

@Composable
fun SignalStrengthBars(
    pingMs: Int,
    modifier: Modifier = Modifier
) {
    val scheme = MaterialTheme.colorScheme
    val isDark = scheme.background.luminance() < 0.5f
    val activeColor = when {
        pingMs in 1..89 -> if (isDark) SignalStrongDark else SignalStrongLight
        pingMs in 90..179 -> if (isDark) SignalMediumDark else SignalMediumLight
        else -> if (isDark) SignalWeakDark else SignalWeakLight
    }
    val activeBars = when {
        pingMs in 1..89 -> 3
        pingMs in 90..179 -> 2
        else -> 1
    }

    Row(
        modifier = modifier
            .height(14.dp)
            .width(16.dp),
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        verticalAlignment = Alignment.Bottom
    ) {
        val heights = listOf(6.dp, 10.dp, 14.dp)
        heights.forEachIndexed { index, barHeight ->
            val barColor = if (index < activeBars) activeColor else scheme.surfaceContainerHighest
            Box(
                modifier = Modifier
                    .width(3.4.dp)
                    .height(barHeight)
                    .clip(RoundedCornerShape(1.5.dp))
                    .background(barColor)
            )
        }
    }
}

@Composable
fun CircularCountryFlag(
    countryCode: String,
    size: Dp = 32.dp,
    modifier: Modifier = Modifier
) {
    val scheme = MaterialTheme.colorScheme
    Canvas(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
    ) {
        val w = this.size.width
        val h = this.size.height
        when (countryCode.uppercase()) {
            "DE" -> {
                drawRect(color = Color(0xFF1A1A1A), topLeft = Offset(0f, 0f), size = Size(w, h / 3f))
                drawRect(color = Color(0xFFD92B2B), topLeft = Offset(0f, h / 3f), size = Size(w, h / 3f))
                drawRect(color = Color(0xFFF4C430), topLeft = Offset(0f, 2f * h / 3f), size = Size(w, h / 3f))
            }
            "SG" -> {
                drawRect(color = Color(0xFFE52535), topLeft = Offset(0f, 0f), size = Size(w, h / 2f))
                drawRect(color = Color(0xFFFFFFFF), topLeft = Offset(0f, h / 2f), size = Size(w, h / 2f))
                drawCircle(color = Color.White, radius = w * 0.14f, center = Offset(w * 0.34f, h * 0.26f))
                drawCircle(color = Color(0xFFE52535), radius = w * 0.11f, center = Offset(w * 0.39f, h * 0.26f))
            }
            "US" -> {
                val stripeCount = 7
                val stripeH = h / stripeCount
                for (i in 0 until stripeCount) {
                    drawRect(
                        color = if (i % 2 == 0) Color(0xFFD12B38) else Color.White,
                        topLeft = Offset(0f, i * stripeH),
                        size = Size(w, stripeH)
                    )
                }
                drawRect(color = Color(0xFF1E3A8A), topLeft = Offset(0f, 0f), size = Size(w * 0.52f, h * 0.54f))
            }
            "NL" -> {
                drawRect(color = Color(0xFFC8102E), topLeft = Offset(0f, 0f), size = Size(w, h / 3f))
                drawRect(color = Color.White, topLeft = Offset(0f, h / 3f), size = Size(w, h / 3f))
                drawRect(color = Color(0xFF1D4ED8), topLeft = Offset(0f, 2f * h / 3f), size = Size(w, h / 3f))
            }
            "GB" -> {
                drawRect(color = Color(0xFF0A2A66), size = Size(w, h))
                drawLine(Color.White, Offset(0f, 0f), Offset(w, h), strokeWidth = w * 0.22f)
                drawLine(Color.White, Offset(w, 0f), Offset(0f, h), strokeWidth = w * 0.22f)
                drawLine(Color(0xFFC8102E), Offset(0f, 0f), Offset(w, h), strokeWidth = w * 0.10f)
                drawLine(Color(0xFFC8102E), Offset(w, 0f), Offset(0f, h), strokeWidth = w * 0.10f)
                drawRect(Color.White, topLeft = Offset(0f, h * 0.36f), size = Size(w, h * 0.28f))
                drawRect(Color.White, topLeft = Offset(w * 0.36f, 0f), size = Size(w * 0.28f, h))
                drawRect(Color(0xFFC8102E), topLeft = Offset(0f, h * 0.42f), size = Size(w, h * 0.16f))
                drawRect(Color(0xFFC8102E), topLeft = Offset(w * 0.42f, 0f), size = Size(w * 0.16f, h))
            }
            "CA" -> {
                drawRect(color = Color(0xFFD81E28), topLeft = Offset(0f, 0f), size = Size(w * 0.26f, h))
                drawRect(color = Color.White, topLeft = Offset(w * 0.26f, 0f), size = Size(w * 0.48f, h))
                drawRect(color = Color(0xFFD81E28), topLeft = Offset(w * 0.74f, 0f), size = Size(w * 0.26f, h))
                drawCircle(color = Color(0xFFD81E28), radius = w * 0.14f, center = Offset(w * 0.5f, h * 0.5f))
            }
            "FR" -> {
                drawRect(color = Color(0xFF1D428A), topLeft = Offset(0f, 0f), size = Size(w / 3f, h))
                drawRect(color = Color.White, topLeft = Offset(w / 3f, 0f), size = Size(w / 3f, h))
                drawRect(color = Color(0xFFE02B38), topLeft = Offset(2f * w / 3f, 0f), size = Size(w / 3f, h))
            }
            "JP" -> {
                drawRect(color = Color.White, size = Size(w, h))
                drawCircle(color = Color(0xFFBC002D), radius = w * 0.28f, center = Offset(w / 2f, h / 2f))
            }
            "IN" -> {
                drawRect(color = Color(0xFFFF9933), topLeft = Offset(0f, 0f), size = Size(w, h / 3f))
                drawRect(color = Color.White, topLeft = Offset(0f, h / 3f), size = Size(w, h / 3f))
                drawRect(color = Color(0xFF138808), topLeft = Offset(0f, 2f * h / 3f), size = Size(w, h / 3f))
                drawCircle(color = Color(0xFF000080), radius = w * 0.11f, center = Offset(w / 2f, h / 2f))
            }
            "AU" -> {
                drawRect(color = Color(0xFF0A2A66), size = Size(w, h))
                drawRect(color = Color(0xFFC8102E), topLeft = Offset(0f, h * 0.18f), size = Size(w * 0.48f, h * 0.12f))
                drawRect(color = Color(0xFFC8102E), topLeft = Offset(w * 0.18f, 0f), size = Size(w * 0.12f, h * 0.48f))
                drawCircle(color = Color.White, radius = w * 0.06f, center = Offset(w * 0.25f, h * 0.72f))
                drawCircle(color = Color.White, radius = w * 0.05f, center = Offset(w * 0.72f, h * 0.32f))
                drawCircle(color = Color.White, radius = w * 0.05f, center = Offset(w * 0.72f, h * 0.68f))
                drawCircle(color = Color.White, radius = w * 0.05f, center = Offset(w * 0.58f, h * 0.50f))
            }
            "CH" -> {
                drawRect(color = Color(0xFFD52B1E), size = Size(w, h))
                drawRect(color = Color.White, topLeft = Offset(w * 0.24f, h * 0.41f), size = Size(w * 0.52f, h * 0.18f))
                drawRect(color = Color.White, topLeft = Offset(w * 0.41f, h * 0.24f), size = Size(w * 0.18f, h * 0.52f))
            }
            "SE" -> {
                drawRect(color = Color(0xFF006AA7), size = Size(w, h))
                drawRect(color = Color(0xFFFECC02), topLeft = Offset(0f, h * 0.40f), size = Size(w, h * 0.20f))
                drawRect(color = Color(0xFFFECC02), topLeft = Offset(w * 0.32f, 0f), size = Size(w * 0.18f, h))
            }
            "BR" -> {
                drawRect(color = Color(0xFF009C3B), size = Size(w, h))
                drawCircle(color = Color(0xFFFFDF00), radius = w * 0.30f, center = Offset(w / 2f, h / 2f))
                drawCircle(color = Color(0xFF002776), radius = w * 0.18f, center = Offset(w / 2f, h / 2f))
            }
            "FI" -> {
                drawRect(color = Color.White, size = Size(w, h))
                drawRect(color = Color(0xFF003580), topLeft = Offset(0f, h * 0.38f), size = Size(w, h * 0.24f))
                drawRect(color = Color(0xFF003580), topLeft = Offset(w * 0.30f, 0f), size = Size(w * 0.22f, h))
            }
            "RU" -> {
                drawRect(color = Color.White, topLeft = Offset(0f, 0f), size = Size(w, h / 3f))
                drawRect(color = Color(0xFF0039A6), topLeft = Offset(0f, h / 3f), size = Size(w, h / 3f))
                drawRect(color = Color(0xFFD52B1E), topLeft = Offset(0f, 2f * h / 3f), size = Size(w, h / 3f))
            }
            "PL" -> {
                drawRect(color = Color.White, topLeft = Offset(0f, 0f), size = Size(w, h / 2f))
                drawRect(color = Color(0xFFDC143C), topLeft = Offset(0f, h / 2f), size = Size(w, h / 2f))
            }
            "AT" -> {
                drawRect(color = Color(0xFFED2939), topLeft = Offset(0f, 0f), size = Size(w, h / 3f))
                drawRect(color = Color.White, topLeft = Offset(0f, h / 3f), size = Size(w, h / 3f))
                drawRect(color = Color(0xFFED2939), topLeft = Offset(0f, 2f * h / 3f), size = Size(w, h / 3f))
            }
            "IT" -> {
                drawRect(color = Color(0xFF009246), topLeft = Offset(0f, 0f), size = Size(w / 3f, h))
                drawRect(color = Color.White, topLeft = Offset(w / 3f, 0f), size = Size(w / 3f, h))
                drawRect(color = Color(0xFFCE2B37), topLeft = Offset(2f * w / 3f, 0f), size = Size(w / 3f, h))
            }
            "TR", "HK" -> {
                drawRect(color = Color(0xFFE30A17), size = Size(w, h))
                drawCircle(color = Color.White, radius = w * 0.22f, center = Offset(w * 0.46f, h * 0.5f))
                drawCircle(color = Color(0xFFE30A17), radius = w * 0.17f, center = Offset(w * 0.52f, h * 0.5f))
            }
            "UA" -> {
                drawRect(color = Color(0xFF0057B7), topLeft = Offset(0f, 0f), size = Size(w, h / 2f))
                drawRect(color = Color(0xFFFFD700), topLeft = Offset(0f, h / 2f), size = Size(w, h / 2f))
            }
            "KR" -> {
                drawRect(color = Color.White, size = Size(w, h))
                drawCircle(color = Color(0xFFCD2E3A), radius = w * 0.26f, center = Offset(w / 2f, h / 2f))
                drawArc(
                    color = Color(0xFF0047A0),
                    startAngle = 0f,
                    sweepAngle = 180f,
                    useCenter = true,
                    topLeft = Offset(w * 0.24f, h * 0.24f),
                    size = Size(w * 0.52f, h * 0.52f)
                )
            }
            else -> {
                drawRect(color = scheme.surfaceContainerHigh, size = Size(w, h))
                val stroke = Stroke(width = (w * 0.065f).coerceAtLeast(1.4f))
                drawCircle(
                    color = scheme.primary.copy(alpha = 0.78f),
                    radius = w * 0.30f,
                    center = Offset(w / 2f, h / 2f),
                    style = stroke
                )
                drawLine(
                    color = scheme.primary.copy(alpha = 0.78f),
                    start = Offset(w * 0.20f, h / 2f),
                    end = Offset(w * 0.80f, h / 2f),
                    strokeWidth = stroke.width
                )
                drawOval(
                    color = scheme.primary.copy(alpha = 0.78f),
                    topLeft = Offset(w * 0.36f, h * 0.20f),
                    size = Size(w * 0.28f, h * 0.60f),
                    style = stroke
                )
            }
        }
        drawCircle(
            color = scheme.outlineVariant.copy(alpha = 0.6f),
            radius = (w / 2f) - 0.5f,
            center = Offset(w / 2f, h / 2f),
            style = Stroke(width = 1.2f)
        )
    }
}

private fun DrawScope.hStripes(vararg colors: Long) {
    val sh = size.height / colors.size
    colors.forEachIndexed { i, c -> drawRect(Color(c), Offset(0f, i * sh), Size(size.width, sh + 0.5f)) }
}

private fun DrawScope.vStripes(vararg colors: Long) {
    val sw = size.width / colors.size
    colors.forEachIndexed { i, c -> drawRect(Color(c), Offset(i * sw, 0f), Size(sw + 0.5f, size.height)) }
}

private fun DrawScope.nordicCross(bg: Long, cross: Long, inner: Long? = null) {
    val w = size.width
    val h = size.height
    drawRect(Color(bg), size = size)
    if (inner != null) {
        drawRect(Color(cross), Offset(0f, h * 0.38f), Size(w, h * 0.24f))
        drawRect(Color(cross), Offset(w * 0.28f, 0f), Size(w * 0.24f, h))
        drawRect(Color(inner), Offset(0f, h * 0.44f), Size(w, h * 0.12f))
        drawRect(Color(inner), Offset(w * 0.34f, 0f), Size(w * 0.12f, h))
    } else {
        drawRect(Color(cross), Offset(0f, h * 0.40f), Size(w, h * 0.20f))
        drawRect(Color(cross), Offset(w * 0.30f, 0f), Size(w * 0.20f, h))
    }
}

/**
 * Straight (rectangular) country flag drawn with vector shapes. No emoji, no circle clipping.
 */
@Composable
fun CountryFlag(
    countryCode: String?,
    modifier: Modifier = Modifier,
    width: Dp = 24.dp,
    height: Dp = 16.dp
) {
    val scheme = MaterialTheme.colorScheme
    val code = countryCode?.trim()?.uppercase().orEmpty()
    Canvas(
        modifier = modifier
            .size(width, height)
            .clip(RoundedCornerShape(2.dp))
    ) {
        val w = size.width
        val h = size.height
        when (code) {
            "DE" -> hStripes(0xFF1A1A1A, 0xFFD92B2B, 0xFFF4C430)
            "NL" -> hStripes(0xFFAE1C28, 0xFFFFFFFF, 0xFF21468B)
            "RU" -> hStripes(0xFFFFFFFF, 0xFF0039A6, 0xFFD52B1E)
            "AT" -> hStripes(0xFFED2939, 0xFFFFFFFF, 0xFFED2939)
            "HU" -> hStripes(0xFFCE2939, 0xFFFFFFFF, 0xFF477050)
            "BG" -> hStripes(0xFFFFFFFF, 0xFF00966E, 0xFFD62612)
            "LT" -> hStripes(0xFFFDB913, 0xFF006A44, 0xFFC1272D)
            "EE" -> hStripes(0xFF0072CE, 0xFF000000, 0xFFFFFFFF)
            "LV" -> hStripes(0xFF9E3039, 0xFFFFFFFF, 0xFF9E3039)
            "LU" -> hStripes(0xFFED2939, 0xFFFFFFFF, 0xFF00A1DE)
            "YE" -> hStripes(0xFFCE1126, 0xFFFFFFFF, 0xFF000000)
            "IQ", "EG", "SY" -> hStripes(0xFFCE1126, 0xFFFFFFFF, 0xFF000000)
            "SL" -> hStripes(0xFF1EB53A, 0xFFFFFFFF, 0xFF0072C6)
            "PL", "ID", "MC" -> hStripes(if (code == "PL") 0xFFFFFFFF else 0xFFCE1126, if (code == "PL") 0xFFDC143C else 0xFFFFFFFF)
            "UA" -> hStripes(0xFF0057B7, 0xFFFFD700)
            "FR" -> vStripes(0xFF0055A4, 0xFFFFFFFF, 0xFFEF4135)
            "IT" -> vStripes(0xFF009246, 0xFFFFFFFF, 0xFFCE2B37)
            "IE" -> vStripes(0xFF169B62, 0xFFFFFFFF, 0xFFFF883E)
            "BE" -> vStripes(0xFF000000, 0xFFFAE042, 0xFFED2939)
            "RO" -> vStripes(0xFF002B7F, 0xFFFCD116, 0xFFCE1126)
            "MD" -> vStripes(0xFF0046AE, 0xFFFFD200, 0xFFCC092F)
            "NG" -> vStripes(0xFF008751, 0xFFFFFFFF, 0xFF008751)
            "ES" -> {
                drawRect(Color(0xFFAA151B), size = size)
                drawRect(Color(0xFFF1BF00), Offset(0f, h * 0.25f), Size(w, h * 0.5f))
            }
            "SG" -> {
                hStripes(0xFFED2939, 0xFFFFFFFF)
                drawCircle(Color.White, w * 0.17f, Offset(w * 0.24f, h * 0.26f))
                drawCircle(Color(0xFFED2939), w * 0.14f, Offset(w * 0.28f, h * 0.26f))
            }
            "US" -> {
                val n = 13
                val sh = h / n
                for (i in 0 until n) {
                    drawRect(
                        if (i % 2 == 0) Color(0xFFB22234) else Color.White,
                        Offset(0f, i * sh), Size(w, sh + 0.5f)
                    )
                }
                drawRect(Color(0xFF3C3B6E), size = Size(w * 0.42f, sh * 7))
            }
            "GB", "UK" -> {
                drawRect(Color(0xFF012169), size = size)
                drawLine(Color.White, Offset(0f, 0f), Offset(w, h), strokeWidth = h * 0.24f)
                drawLine(Color.White, Offset(w, 0f), Offset(0f, h), strokeWidth = h * 0.24f)
                drawLine(Color(0xFFC8102E), Offset(0f, 0f), Offset(w, h), strokeWidth = h * 0.10f)
                drawLine(Color(0xFFC8102E), Offset(w, 0f), Offset(0f, h), strokeWidth = h * 0.10f)
                drawRect(Color.White, Offset(0f, h * 0.33f), Size(w, h * 0.34f))
                drawRect(Color.White, Offset(w * 0.4f, 0f), Size(w * 0.2f, h))
                drawRect(Color(0xFFC8102E), Offset(0f, h * 0.42f), Size(w, h * 0.16f))
                drawRect(Color(0xFFC8102E), Offset(w * 0.44f, 0f), Size(w * 0.12f, h))
            }
            "CA" -> {
                drawRect(Color(0xFFD52B1E), size = size)
                drawRect(Color.White, Offset(w * 0.25f, 0f), Size(w * 0.5f, h))
                drawCircle(Color(0xFFD52B1E), w * 0.11f, Offset(w * 0.5f, h * 0.5f))
            }
            "JP" -> {
                drawRect(Color.White, size = size)
                drawCircle(Color(0xFFBC002D), h * 0.3f, Offset(w / 2f, h / 2f))
            }
            "KR" -> {
                drawRect(Color.White, size = size)
                drawArc(Color(0xFFCD2E3A), 180f, 180f, true, Offset(w * 0.34f, h * 0.24f), Size(w * 0.32f, h * 0.52f))
                drawArc(Color(0xFF0047A0), 0f, 180f, true, Offset(w * 0.34f, h * 0.24f), Size(w * 0.32f, h * 0.52f))
            }
            "IN" -> {
                hStripes(0xFFFF9933, 0xFFFFFFFF, 0xFF138808)
                drawCircle(Color(0xFF000080), h * 0.12f, Offset(w / 2f, h / 2f), style = Stroke(width = 1.2f))
            }
            "CN" -> {
                drawRect(Color(0xFFDE2910), size = size)
                drawCircle(Color(0xFFFFDE00), h * 0.17f, Offset(w * 0.22f, h * 0.30f))
                drawCircle(Color(0xFFFFDE00), h * 0.045f, Offset(w * 0.42f, h * 0.14f))
                drawCircle(Color(0xFFFFDE00), h * 0.045f, Offset(w * 0.48f, h * 0.30f))
                drawCircle(Color(0xFFFFDE00), h * 0.045f, Offset(w * 0.42f, h * 0.46f))
            }
            "TR" -> {
                drawRect(Color(0xFFE30A17), size = size)
                drawCircle(Color.White, h * 0.26f, Offset(w * 0.40f, h * 0.5f))
                drawCircle(Color(0xFFE30A17), h * 0.21f, Offset(w * 0.45f, h * 0.5f))
                drawCircle(Color.White, h * 0.08f, Offset(w * 0.58f, h * 0.5f))
            }
            "HK" -> {
                drawRect(Color(0xFFDE2910), size = size)
                drawCircle(Color.White, h * 0.22f, Offset(w / 2f, h / 2f))
            }
            "TW" -> {
                drawRect(Color(0xFFFE0000), size = size)
                drawRect(Color(0xFF000095), size = Size(w * 0.5f, h * 0.5f))
                drawCircle(Color.White, h * 0.14f, Offset(w * 0.25f, h * 0.25f))
            }
            "BR" -> {
                drawRect(Color(0xFF009C3B), size = size)
                val d = Path().apply {
                    moveTo(w * 0.5f, h * 0.1f); lineTo(w * 0.92f, h * 0.5f)
                    lineTo(w * 0.5f, h * 0.9f); lineTo(w * 0.08f, h * 0.5f); close()
                }
                drawPath(d, Color(0xFFFFDF00))
                drawCircle(Color(0xFF002776), h * 0.22f, Offset(w / 2f, h / 2f))
            }
            "CH" -> {
                drawRect(Color(0xFFDA291C), size = size)
                drawRect(Color.White, Offset(w * 0.44f, h * 0.2f), Size(w * 0.12f, h * 0.6f))
                drawRect(Color.White, Offset(w * 0.3f, h * 0.44f), Size(w * 0.4f, h * 0.12f))
            }
            "SE" -> nordicCross(0xFF006AA7, 0xFFFECC02)
            "FI" -> nordicCross(0xFFFFFFFF, 0xFF003580)
            "DK" -> nordicCross(0xFFC8102E, 0xFFFFFFFF)
            "NO" -> nordicCross(0xFFBA0C2F, 0xFFFFFFFF, 0xFF00205B)
            "IS" -> nordicCross(0xFF02529C, 0xFFFFFFFF, 0xFFDC1E35)
            "CZ" -> {
                hStripes(0xFFFFFFFF, 0xFFD7141A)
                val t = Path().apply { moveTo(0f, 0f); lineTo(w * 0.5f, h / 2f); lineTo(0f, h); close() }
                drawPath(t, Color(0xFF11457E))
            }
            "SK" -> {
                hStripes(0xFFFFFFFF, 0xFF0B4EA2, 0xFFEE1C25)
            }
            "RS" -> hStripes(0xFFC6363C, 0xFF0C4076, 0xFFFFFFFF)
            "HR" -> hStripes(0xFFFF0000, 0xFFFFFFFF, 0xFF171796)
            "SI" -> hStripes(0xFFFFFFFF, 0xFF005DA4, 0xFFED1C24)
            "GR" -> {
                for (i in 0 until 9) {
                    drawRect(
                        if (i % 2 == 0) Color(0xFF0D5EAF) else Color.White,
                        Offset(0f, i * h / 9f), Size(w, h / 9f + 0.5f)
                    )
                }
                drawRect(Color(0xFF0D5EAF), size = Size(w * 0.38f, h * 5f / 9f))
                drawRect(Color.White, Offset(w * 0.16f, 0f), Size(w * 0.06f, h * 5f / 9f))
                drawRect(Color.White, Offset(0f, h * 0.22f), Size(w * 0.38f, h * 0.11f))
            }
            "AU", "NZ" -> {
                drawRect(Color(0xFF00247D), size = size)
                drawRect(Color.White, Offset(0f, h * 0.14f), Size(w * 0.5f, h * 0.12f))
                drawRect(Color.White, Offset(w * 0.19f, 0f), Size(w * 0.12f, h * 0.4f))
                drawRect(Color(0xFFCC142B), Offset(0f, h * 0.17f), Size(w * 0.5f, h * 0.06f))
                drawRect(Color(0xFFCC142B), Offset(w * 0.22f, 0f), Size(w * 0.06f, h * 0.4f))
                drawCircle(Color.White, h * 0.05f, Offset(w * 0.75f, h * 0.30f))
                drawCircle(Color.White, h * 0.05f, Offset(w * 0.68f, h * 0.55f))
                drawCircle(Color.White, h * 0.05f, Offset(w * 0.82f, h * 0.60f))
                drawCircle(Color.White, h * 0.05f, Offset(w * 0.75f, h * 0.82f))
            }
            "MX" -> {
                vStripes(0xFF006847, 0xFFFFFFFF, 0xFFCE1126)
                drawCircle(Color(0xFF8C6B3F), h * 0.12f, Offset(w / 2f, h / 2f))
            }
            "AR" -> {
                hStripes(0xFF74ACDF, 0xFFFFFFFF, 0xFF74ACDF)
                drawCircle(Color(0xFFF6B40E), h * 0.09f, Offset(w / 2f, h / 2f))
            }
            "CL" -> {
                hStripes(0xFFFFFFFF, 0xFFD52B1E)
                drawRect(Color(0xFF0039A6), size = Size(w * 0.33f, h * 0.5f))
            }
            "CO" -> {
                drawRect(Color(0xFFFCD116), Offset(0f, 0f), Size(w, h * 0.5f))
                drawRect(Color(0xFF003893), Offset(0f, h * 0.5f), Size(w, h * 0.25f))
                drawRect(Color(0xFFCE1126), Offset(0f, h * 0.75f), Size(w, h * 0.25f))
            }
            "PT" -> {
                drawRect(Color(0xFF006600), Offset(0f, 0f), Size(w * 0.4f, h))
                drawRect(Color(0xFFFF0000), Offset(w * 0.4f, 0f), Size(w * 0.6f, h))
                drawCircle(Color(0xFFFFE000), h * 0.2f, Offset(w * 0.4f, h / 2f))
            }
            "IL" -> {
                drawRect(Color.White, size = size)
                drawRect(Color(0xFF0038B8), Offset(0f, h * 0.1f), Size(w, h * 0.12f))
                drawRect(Color(0xFF0038B8), Offset(0f, h * 0.78f), Size(w, h * 0.12f))
                drawCircle(Color(0xFF0038B8), h * 0.18f, Offset(w / 2f, h / 2f), style = Stroke(width = 1.4f))
            }
            "AE" -> {
                drawRect(Color(0xFF00732F), Offset(0f, 0f), Size(w, h / 3f))
                drawRect(Color.White, Offset(0f, h / 3f), Size(w, h / 3f))
                drawRect(Color(0xFF000000), Offset(0f, 2f * h / 3f), Size(w, h / 3f + 0.5f))
                drawRect(Color(0xFFFF0000), size = Size(w * 0.25f, h))
            }
            "SA" -> {
                drawRect(Color(0xFF006C35), size = size)
                drawRect(Color.White, Offset(w * 0.2f, h * 0.62f), Size(w * 0.6f, h * 0.06f))
            }
            "ZA" -> {
                drawRect(Color(0xFFDE3831), Offset(0f, 0f), Size(w, h / 2f))
                drawRect(Color(0xFF002395), Offset(0f, h / 2f), Size(w, h / 2f))
                val t = Path().apply { moveTo(0f, 0f); lineTo(w * 0.45f, h / 2f); lineTo(0f, h); close() }
                drawPath(t, Color(0xFF007A4D))
            }
            "TH" -> {
                hStripes(0xFFA51931, 0xFFF4F5F8, 0xFF2D2A4A, 0xFFF4F5F8, 0xFFA51931)
            }
            "VN" -> {
                drawRect(Color(0xFFDA251D), size = size)
                drawCircle(Color(0xFFFFFF00), h * 0.2f, Offset(w / 2f, h / 2f))
            }
            "MY" -> {
                for (i in 0 until 7) {
                    drawRect(
                        if (i % 2 == 0) Color(0xFFCC0001) else Color.White,
                        Offset(0f, i * h / 7f), Size(w, h / 7f + 0.5f)
                    )
                }
                drawRect(Color(0xFF010066), size = Size(w * 0.5f, h * 4f / 7f))
            }
            "PH" -> {
                drawRect(Color(0xFF0038A8), Offset(0f, 0f), Size(w, h / 2f))
                drawRect(Color(0xFFCE1126), Offset(0f, h / 2f), Size(w, h / 2f))
                val t = Path().apply { moveTo(0f, 0f); lineTo(w * 0.5f, h / 2f); lineTo(0f, h); close() }
                drawPath(t, Color.White)
            }
            "PK" -> {
                drawRect(Color(0xFF01411C), size = size)
                drawRect(Color.White, size = Size(w * 0.25f, h))
            }
            "KZ" -> {
                drawRect(Color(0xFF00AFCA), size = size)
                drawCircle(Color(0xFFFEC50C), h * 0.2f, Offset(w / 2f, h / 2f))
            }
            "IR" -> hStripes(0xFF239F40, 0xFFFFFFFF, 0xFFDA0000)
            else -> {
                drawRect(scheme.surfaceContainerHigh, size = size)
                val c = scheme.onSurfaceVariant.copy(alpha = 0.55f)
                val st = Stroke(width = 1.2f)
                drawCircle(c, h * 0.32f, Offset(w / 2f, h / 2f), style = st)
                drawLine(c, Offset(w / 2f - h * 0.32f, h / 2f), Offset(w / 2f + h * 0.32f, h / 2f), 1.2f)
                drawOval(c, Offset(w / 2f - h * 0.14f, h / 2f - h * 0.32f), Size(h * 0.28f, h * 0.64f), style = st)
            }
        }
        drawRect(
            color = Color.Black.copy(alpha = 0.18f),
            style = Stroke(width = 1f)
        )
    }
}
