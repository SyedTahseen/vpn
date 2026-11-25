package com.hollow.vpn.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Dns
import androidx.compose.material.icons.outlined.PhoneAndroid
import androidx.compose.material.icons.outlined.Public
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hollow.vpn.ui.theme.DotoDisplayFamily
import com.hollow.vpn.ui.theme.HollowAmber
import com.hollow.vpn.ui.theme.HollowCreamDivider
import com.hollow.vpn.ui.theme.HollowCreamSurface
import com.hollow.vpn.ui.theme.HollowDarkInk
import com.hollow.vpn.ui.theme.HollowForest
import com.hollow.vpn.ui.theme.HollowForestCard
import com.hollow.vpn.ui.theme.HollowForestDivider
import com.hollow.vpn.ui.theme.HollowForestMuted
import com.hollow.vpn.ui.theme.HollowInk
import com.hollow.vpn.ui.theme.HollowInkSecondary
import com.hollow.vpn.ui.theme.HollowLime
import com.hollow.vpn.ui.theme.HollowObsidian
import com.hollow.vpn.ui.theme.HollowObsidianDivider
import com.hollow.vpn.ui.theme.HollowObsidianMuted
import com.hollow.vpn.ui.theme.HollowObsidianSurface
import com.hollow.vpn.ui.theme.HollowOrange
import com.hollow.vpn.ui.theme.JetBrainsMonoFamily
import com.hollow.vpn.ui.theme.NothingBackdropDot
import com.hollow.vpn.ui.theme.NothingBlack
import com.hollow.vpn.ui.theme.NothingCanvas
import com.hollow.vpn.ui.theme.NothingDotInactive
import com.hollow.vpn.ui.theme.NothingDottedLine
import com.hollow.vpn.ui.theme.NothingMotion
import com.hollow.vpn.ui.theme.NothingOnCanvas
import com.hollow.vpn.ui.theme.NothingPillActiveBg
import com.hollow.vpn.ui.theme.NothingPillActiveIcon
import com.hollow.vpn.ui.theme.NothingTopBtnBorder
import com.hollow.vpn.ui.theme.nothingPressScale

enum class HollowRouteState { OFF, BUSY, SECURED }

/**
 * Subtle Nothing OS OLED dot-matrix background texture.
 */
@Composable
fun NothingDotMatrixBackdrop(
    modifier: Modifier = Modifier,
    dotColor: Color = NothingBackdropDot,
    spacing: Dp = 18.dp,
    dotRadius: Dp = 1.dp
) {
    Canvas(modifier = modifier.fillMaxSize()) {
        val stepPx = spacing.toPx().coerceAtLeast(8f)
        val rPx = dotRadius.toPx().coerceAtLeast(1f)
        var y = stepPx * 0.5f
        while (y < size.height) {
            var x = stepPx * 0.5f
            while (x < size.width) {
                drawCircle(
                    color = dotColor,
                    radius = rPx,
                    center = Offset(x, y)
                )
                x += stepPx
            }
            y += stepPx
        }
    }
}

/**
 * Authentic Nothing OS dotted hairline divider (`· · · · · · · · ·`).
 */
@Composable
fun NothingDottedDivider(
    modifier: Modifier = Modifier,
    color: Color = NothingDottedLine,
    dotWidth: Dp = 1.5.dp,
    gapWidth: Dp = 3.5.dp
) {
    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(1.dp)
    ) {
        val cy = size.height / 2f
        drawLine(
            color = color,
            start = Offset(0f, cy),
            end = Offset(size.width, cy),
            strokeWidth = 1.dp.toPx(),
            cap = StrokeCap.Round,
            pathEffect = PathEffect.dashPathEffect(
                floatArrayOf(dotWidth.toPx(), gapWidth.toPx()),
                0f
            )
        )
    }
}

/**
 * Authentic Nothing OS 5-dot latency quality indicator (`• • • • ○`).
 * Lower latency lights up more crisp white dots; timeout shows dimmed / red state.
 */
@Composable
fun NothingSignalDots(
    pingMs: Int,
    modifier: Modifier = Modifier,
    dotCount: Int = 5,
    dotSize: Dp = 5.5.dp,
    dotSpacing: Dp = 4.5.dp,
    activeColor: Color = NothingOnCanvas,
    inactiveColor: Color = NothingDotInactive,
    timeoutColor: Color = HollowOrange
) {
    val litCount = when {
        pingMs <= 0 -> 0
        pingMs <= 120 -> 5
        pingMs <= 240 -> 4
        pingMs <= 420 -> 3
        pingMs <= 750 -> 2
        else -> 1
    }.coerceIn(0, dotCount)

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(dotSpacing),
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 0 until dotCount) {
            val targetDotColor = when {
                pingMs <= 0 && i == 0 -> timeoutColor
                i < litCount -> activeColor
                else -> inactiveColor
            }
            val dotColor by animateColorAsState(
                targetValue = targetDotColor,
                animationSpec = tween(durationMillis = 220, easing = NothingMotion.FluidEase),
                label = "signal_dot_$i"
            )
            Box(
                modifier = Modifier
                    .size(dotSize)
                    .clip(CircleShape)
                    .background(dotColor)
            )
        }
    }
}

/**
 * Nothing OS uppercase monospace pill filter/tab row (solid white active pill + hairline dark pills)
 * with smooth color morphing and tactile spring press-scale.
 */
@Composable
fun NothingPillTabRow(
    tabs: List<String>,
    selectedIndex: Int,
    onSelectTab: (Int) -> Unit,
    modifier: Modifier = Modifier,
    tabTestTags: List<String> = emptyList(),
    fontSizeSp: Float = 10.5f
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(7.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        tabs.forEachIndexed { index, title ->
            val isSelected = index == selectedIndex
            val tag = tabTestTags.getOrNull(index)
            val interactionSource = remember { MutableInteractionSource() }
            val containerColor by animateColorAsState(
                targetValue = if (isSelected) NothingPillActiveBg else Color.Transparent,
                animationSpec = tween(durationMillis = 210, easing = NothingMotion.FluidEase),
                label = "pill_tab_bg_$index"
            )
            val borderColor by animateColorAsState(
                targetValue = if (isSelected) NothingPillActiveBg else NothingTopBtnBorder,
                animationSpec = tween(durationMillis = 210, easing = NothingMotion.FluidEase),
                label = "pill_tab_border_$index"
            )
            val contentColor by animateColorAsState(
                targetValue = if (isSelected) NothingPillActiveIcon else HollowInk,
                animationSpec = tween(durationMillis = 190, easing = NothingMotion.FluidEase),
                label = "pill_tab_text_$index"
            )
            Box(
                modifier = Modifier
                    .minimumInteractiveComponentSize()
                    .then(if (tag != null) Modifier.testTag(tag) else Modifier)
                    .nothingPressScale(interactionSource, pressedScale = 0.93f)
                    .clip(CircleShape)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = androidx.compose.material3.ripple(),
                        role = Role.Tab
                    ) { onSelectTab(index) },
                contentAlignment = Alignment.Center
            ) {
                Surface(
                    shape = CircleShape,
                    color = containerColor,
                    border = BorderStroke(1.dp, borderColor)
                ) {
                    Text(
                        text = title.uppercase(),
                        fontFamily = JetBrainsMonoFamily,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                        fontSize = fontSizeSp.sp,
                        letterSpacing = 1.0.sp,
                        color = contentColor,
                        maxLines = 1,
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 7.dp)
                    )
                }
            }
        }
    }
}

/**
 * You -> Server -> Internet Nothing Glyph diagram.
 * OFF = exposed dotted line, BUSY = active Glyph ring + dotted leg, SECURED = animated dot-matrix stream.
 */
@Composable
fun HollowRouteCard(
    state: HollowRouteState,
    serverLabel: String,
    modifier: Modifier = Modifier,
    realIp: String? = null,
    exitIp: String? = null
) {
    val accent = when (state) {
        HollowRouteState.OFF -> HollowOrange
        HollowRouteState.BUSY -> HollowAmber
        HollowRouteState.SECURED -> HollowLime
    }
    val ink = if (state == HollowRouteState.OFF) HollowInk else HollowDarkInk
    val muted = when (state) {
        HollowRouteState.OFF -> HollowInkSecondary
        HollowRouteState.BUSY -> HollowObsidianMuted
        HollowRouteState.SECURED -> HollowForestMuted
    }
    val lineColor = when (state) {
        HollowRouteState.OFF -> HollowCreamDivider
        HollowRouteState.BUSY -> HollowObsidianDivider
        HollowRouteState.SECURED -> HollowForestDivider
    }
    val cardBg = when (state) {
        HollowRouteState.OFF -> HollowCreamSurface
        HollowRouteState.BUSY -> HollowObsidianSurface
        HollowRouteState.SECURED -> HollowForestCard
    }
    val nodeBg = when (state) {
        HollowRouteState.OFF -> NothingCanvas
        HollowRouteState.BUSY -> HollowObsidian
        HollowRouteState.SECURED -> HollowForest
    }
    val midFilled = state == HollowRouteState.SECURED
    val midLabel = if (state == HollowRouteState.OFF) "NO NODE" else serverLabel.uppercase()
    val transition = rememberInfiniteTransition(label = "route")
    val dashPhase by transition.animateFloat(
        initialValue = 0f,
        targetValue = 24f,
        animationSpec = infiniteRepeatable(tween(1000, easing = LinearEasing), RepeatMode.Restart),
        label = "dash"
    )
    val packet by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(1800, easing = LinearEasing), RepeatMode.Restart),
        label = "packet"
    )
    val ping by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(1800, easing = LinearOutSlowInEasing), RepeatMode.Restart),
        label = "ping"
    )
    val spin by transition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(tween(1000, easing = LinearEasing), RepeatMode.Restart),
        label = "spin"
    )
    val nodeSize = 52.dp
    val labelWidth = 80.dp
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .semantics(mergeDescendants = true) {
                contentDescription = "Route: You to $midLabel to Internet"
            },
        shape = RoundedCornerShape(20.dp),
        color = cardBg,
        border = BorderStroke(1.dp, lineColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 14.dp)
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                Canvas(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(nodeSize)
                ) {
                    val r = nodeSize.toPx() / 2f
                    val cy = size.height / 2f
                    val x0 = labelWidth.toPx() / 2f
                    val x1 = size.width / 2f
                    val x2 = size.width - x0
                    val gap = 4.dp.toPx()
                    val sw = 2.4.dp.toPx()
                    val dottedEffect = PathEffect.dashPathEffect(
                        floatArrayOf(2.dp.toPx(), 6.dp.toPx()),
                        -dashPhase.dp.toPx()
                    )
                    when (state) {
                        HollowRouteState.OFF -> drawLine(
                            accent,
                            Offset(x0 + r + gap, cy),
                            Offset(x2 - r - gap, cy),
                            sw,
                            cap = StrokeCap.Round,
                            pathEffect = dottedEffect
                        )
                        HollowRouteState.BUSY -> {
                            drawLine(
                                accent,
                                Offset(x0 + r + gap, cy),
                                Offset(x1 - r - gap, cy),
                                sw,
                                cap = StrokeCap.Round,
                                pathEffect = dottedEffect
                            )
                            drawLine(
                                lineColor,
                                Offset(x1 + r + gap, cy),
                                Offset(x2 - r - gap, cy),
                                sw
                            )
                        }
                        HollowRouteState.SECURED -> {
                            drawLine(
                                lineColor,
                                Offset(x0 + r + gap, cy),
                                Offset(x2 - r - gap, cy),
                                sw,
                                cap = StrokeCap.Round,
                                pathEffect = PathEffect.dashPathEffect(
                                    floatArrayOf(2.dp.toPx(), 5.dp.toPx()),
                                    0f
                                )
                            )
                            for (i in 0..3) {
                                val p = (packet + i / 4f) % 1f
                                val px = (x0 + r) + ((x2 - r) - (x0 + r)) * p
                                drawCircle(accent, 3.8.dp.toPx(), Offset(px, cy))
                            }
                            drawCircle(
                                color = accent.copy(alpha = (1f - ping) * 0.72f),
                                radius = r + r * 0.78f * ping,
                                center = Offset(x1, cy),
                                style = Stroke(width = 1.6.dp.toPx())
                            )
                        }
                    }
                    if (state == HollowRouteState.BUSY) {
                        val ar = r + 6.dp.toPx()
                        drawArc(
                            color = accent,
                            startAngle = spin,
                            sweepAngle = 72f,
                            useCenter = false,
                            topLeft = Offset(x1 - ar, cy - ar),
                            size = Size(ar * 2, ar * 2),
                            style = Stroke(width = 2.6.dp.toPx(), cap = StrokeCap.Round)
                        )
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    RouteNode(Icons.Outlined.PhoneAndroid, "YOU", nodeBg, lineColor, ink, muted, nodeSize, labelWidth)
                    RouteNode(
                        Icons.Outlined.Dns,
                        midLabel,
                        fill = if (midFilled) accent else nodeBg,
                        stroke = when (state) {
                            HollowRouteState.OFF -> lineColor
                            else -> accent
                        },
                        tint = when {
                            midFilled -> NothingBlack
                            state == HollowRouteState.OFF -> muted
                            else -> ink
                        },
                        labelColor = if (midFilled) ink else muted,
                        nodeSize = nodeSize,
                        width = labelWidth
                    )
                    RouteNode(Icons.Outlined.Public, "WEB", nodeBg, lineColor, ink, muted, nodeSize, labelWidth)
                }
            }
            if (realIp != null && exitIp != null) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(SpanStyle(color = muted, textDecoration = TextDecoration.LineThrough)) {
                            append(realIp)
                        }
                        withStyle(SpanStyle(color = muted)) { append("  →  ") }
                        withStyle(SpanStyle(color = accent, fontWeight = FontWeight.SemiBold)) { append(exitIp) }
                    },
                    fontFamily = JetBrainsMonoFamily,
                    fontSize = 12.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                )
            }
        }
    }
}

@Composable
private fun RouteNode(
    icon: ImageVector,
    label: String,
    fill: Color,
    stroke: Color,
    tint: Color,
    labelColor: Color,
    nodeSize: Dp,
    width: Dp
) {
    Column(modifier = Modifier.width(width), horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(nodeSize)
                .clip(CircleShape)
                .background(fill)
                .border(1.5.dp, stroke, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(imageVector = icon, contentDescription = null, tint = tint, modifier = Modifier.size(21.dp))
        }
        Text(
            text = label,
            fontFamily = JetBrainsMonoFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 11.sp,
            letterSpacing = 0.6.sp,
            color = labelColor,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 6.dp)
        )
    }
}

private const val SPEED_BAR_COUNT = 24
private const val SPEED_LED_ROWS = 7

/** Download / upload cells plus Nothing Glyph LED dot-matrix equalizer driven by real telemetry. */
@Composable
fun HollowSpeedBarsCard(
    downloadValue: String,
    downloadUnit: String,
    uploadValue: String,
    uploadUnit: String,
    downloadBytesPerSec: Long,
    elapsedSeconds: Long,
    barsHeight: Dp = 44.dp,
    modifier: Modifier = Modifier
) {
    val history = remember { mutableStateListOf<Long>() }
    LaunchedEffect(elapsedSeconds) {
        history.add(downloadBytesPerSec)
        while (history.size > SPEED_BAR_COUNT) history.removeAt(0)
    }
    Column(modifier = modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
            SpeedCell("DOWNLOAD", downloadValue, downloadUnit, Modifier.weight(1f))
            SpeedCell("UPLOAD", uploadValue, uploadUnit, Modifier.weight(1f))
        }
        val maxV = (history.maxOrNull() ?: 0L).coerceAtLeast(1L)
        val pad = SPEED_BAR_COUNT - history.size
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(barsHeight)
        ) {
            val colGap = 4.dp.toPx()
            val rowGap = 3.dp.toPx()
            val colW = ((size.width - colGap * (SPEED_BAR_COUNT - 1)) / SPEED_BAR_COUNT).coerceAtLeast(2f)
            val ledH = ((size.height - rowGap * (SPEED_LED_ROWS - 1)) / SPEED_LED_ROWS).coerceAtLeast(2f)
            val radius = (minOf(colW, ledH) * 0.45f)

            for (col in 0 until SPEED_BAR_COUNT) {
                val v = if (col < pad) 0L else history[col - pad]
                val fraction = (v.toFloat() / maxV.toFloat()).coerceIn(0f, 1f)
                val litRows = (1 + (fraction * (SPEED_LED_ROWS - 1)).toInt()).coerceIn(1, SPEED_LED_ROWS)
                val x = col * (colW + colGap)
                for (row in 0 until SPEED_LED_ROWS) {
                    val y = size.height - ((row + 1) * ledH + row * rowGap)
                    val isLit = row < litRows
                    val color = if (isLit) {
                        HollowLime.copy(alpha = if (row == 0 && v == 0L) 0.28f else 0.45f + 0.55f * (row.toFloat() / SPEED_LED_ROWS))
                    } else {
                        HollowForestDivider.copy(alpha = 0.45f)
                    }
                    drawRoundRect(
                        color = color,
                        topLeft = Offset(x, y),
                        size = Size(colW, ledH),
                        cornerRadius = CornerRadius(radius, radius)
                    )
                }
            }
        }
    }
}

@Composable
private fun SpeedCell(label: String, value: String, unit: String, modifier: Modifier) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(18.dp),
        color = HollowForestCard,
        border = BorderStroke(1.dp, HollowForestDivider)
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp)
        ) {
            Text(
                text = label,
                fontFamily = JetBrainsMonoFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 9.5.sp,
                letterSpacing = 1.1.sp,
                color = HollowForestMuted,
                maxLines = 1
            )
            Row(verticalAlignment = Alignment.Bottom, modifier = Modifier.padding(top = 3.dp)) {
                Text(
                    text = value,
                    fontFamily = DotoDisplayFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = HollowDarkInk,
                    maxLines = 1
                )
                Text(
                    text = " $unit",
                    fontFamily = JetBrainsMonoFamily,
                    fontSize = 10.5.sp,
                    color = HollowForestMuted,
                    maxLines = 1,
                    modifier = Modifier.padding(bottom = 2.dp)
                )
            }
        }
    }
}

/** Small Glyph status dot that pulses crisply. */
@Composable
fun HollowBlinkingDot(color: Color, modifier: Modifier = Modifier) {
    val t = rememberInfiniteTransition(label = "blink")
    val a by t.animateFloat(
        initialValue = 1f,
        targetValue = 0.22f,
        animationSpec = infiniteRepeatable(tween(650, easing = LinearEasing), RepeatMode.Reverse),
        label = "blink_alpha"
    )
    Box(
        modifier = modifier
            .size(8.dp)
            .alpha(a)
            .clip(CircleShape)
            .background(color)
    )
}

/** Nothing OS segmented dot-matrix progress wave bar. */
@Composable
fun HollowIndeterminateBar(color: Color, track: Color, modifier: Modifier = Modifier) {
    val t = rememberInfiniteTransition(label = "bar_fill")
    val phase by t.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(1500, easing = LinearEasing), RepeatMode.Restart),
        label = "bar_fraction"
    )
    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(8.dp)
    ) {
        val count = 20
        val dotDiameter = 6.dp.toPx().coerceAtMost(size.height)
        val dotRadius = dotDiameter / 2f
        val usableWidth = (size.width - dotDiameter).coerceAtLeast(1f)
        val cy = size.height / 2f

        for (i in 0 until count) {
            val norm = i.toFloat() / (count - 1).toFloat()
            val cx = dotRadius + norm * usableWidth
            val dist = ((phase - norm) + 1f) % 1f
            val glow = if (dist < 0.34f) 1f - (dist / 0.34f) else 0f
            drawCircle(
                color = if (glow > 0.05f) color.copy(alpha = 0.25f + 0.75f * glow) else track,
                radius = dotRadius * (0.82f + 0.24f * glow),
                center = Offset(cx, cy)
            )
        }
    }
}

/** The Hollow launcher mark (hollow shield + dot). */
@Composable
fun HollowShieldMark(size: Dp, background: Color, foreground: Color, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(size)
            .clip(RoundedCornerShape(size * 0.28f))
            .background(background)
    ) {
        Canvas(modifier = Modifier.size(size)) {
            val s = this.size.width / 108f
            val shield = Path().apply {
                moveTo(54f * s, 28f * s)
                lineTo(75f * s, 37f * s)
                lineTo(75f * s, 54f * s)
                cubicTo(75f * s, 68f * s, 66f * s, 77f * s, 54f * s, 81f * s)
                cubicTo(42f * s, 77f * s, 33f * s, 68f * s, 33f * s, 54f * s)
                lineTo(33f * s, 37f * s)
                close()
            }
            drawPath(shield, foreground, style = Stroke(width = 7f * s, join = StrokeJoin.Round))
            drawCircle(foreground, 7f * s, Offset(54f * s, 56f * s))
        }
    }
}
