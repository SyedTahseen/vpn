package com.hollow.vpn.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

// Static brand & phase constants (safe to use inside non-@Composable contexts)
val NothingBlack = Color(0xFF000000)
val NothingRed = Color(0xFFE5252D)
val HollowOrange = NothingRed
val HollowDarkInk = Color(0xFFF5F5F7)

// Static palette base colors for Material 3 schemes
private val DarkCanvas = Color(0xFF000000)
private val DarkSurface = Color(0xFF121214)
private val DarkSurfaceElevated = Color(0xFF1A1A1E)
private val DarkCardSurface = Color(0xFF0E0E10)
private val DarkBorder = Color(0xFF27272A)
private val DarkInk = Color(0xFFF5F5F7)
private val DarkInkSecondary = Color(0xFF98989F)
private val DarkInkMuted = Color(0xFF636369)
private val DarkMatrixGreen = Color(0xFF2EE59D)
private val DarkOrangeContainer = Color(0xFF2D0B0D)

// Nothing OS neutral achromatic matte dark palette for non-OLED (LCD/IPS) displays (zero blue tint)
private val CarbonCanvas = Color(0xFF121212)
private val CarbonSurface = Color(0xFF1B1B1B)
private val CarbonSurfaceElevated = Color(0xFF242424)
private val CarbonCardSurface = Color(0xFF191919)
private val CarbonBorder = Color(0xFF2E2E2E)
private val CarbonInk = Color(0xFFF4F4F4)
private val CarbonInkSecondary = Color(0xFF9E9E9E)
private val CarbonInkMuted = Color(0xFF6E6E6E)
private val CarbonOrangeContainer = Color(0xFF331416)

private val LightCanvas = Color(0xFFF4F4F2)
private val LightSurface = Color(0xFFFFFFFF)
private val LightSurfaceElevated = Color(0xFFEAEAE6)
private val LightCardSurface = Color(0xFFFFFFFF)
private val LightBorder = Color(0xFFD8D8D3)
private val LightInk = Color(0xFF0E0E10)
private val LightInkSecondary = Color(0xFF5D5D65)
private val LightInkMuted = Color(0xFF8A8A92)
private val LightMatrixGreen = Color(0xFF0F9D58)
private val LightOrangeContainer = Color(0xFFFFE5E7)

@Immutable
data class HollowPalette(
    val canvas: Color,
    val onCanvas: Color,
    val surface: Color,
    val surfaceElevated: Color,
    val cardSurface: Color,
    val border: Color,
    val ink: Color,
    val inkSecondary: Color,
    val inkMuted: Color,
    val orangeContainer: Color,
    val matrixGreen: Color,
    val invertedSurface: Color,
    val onInvertedSurface: Color,
    val onInvertedSecondary: Color,
    val onInvertedMuted: Color,
    val pillNavFill: Color,
    val pillNavBorder: Color,
    val pillActiveBg: Color,
    val pillActiveIcon: Color,
    val topBtnFill: Color,
    val topBtnBorder: Color,
    val dottedLine: Color,
    val dotInactive: Color,
    val switchOffTrack: Color,
    val switchOffBorder: Color,
    val switchOffThumb: Color,
    val mapDotMuted: Color,
    val backdropDot: Color
)

val DarkHollowPalette = HollowPalette(
    canvas = DarkCanvas,
    onCanvas = Color.White,
    surface = DarkSurface,
    surfaceElevated = DarkSurfaceElevated,
    cardSurface = DarkCardSurface,
    border = DarkBorder,
    ink = DarkInk,
    inkSecondary = DarkInkSecondary,
    inkMuted = DarkInkMuted,
    orangeContainer = DarkOrangeContainer,
    matrixGreen = DarkMatrixGreen,
    invertedSurface = Color.White,
    onInvertedSurface = NothingBlack,
    onInvertedSecondary = Color(0xFF3A3A3F),
    onInvertedMuted = Color(0xFF636369),
    pillNavFill = Color(0xFF141417),
    pillNavBorder = Color(0xFF26262C),
    pillActiveBg = Color.White,
    pillActiveIcon = NothingBlack,
    topBtnFill = Color(0xFF0A0A0C),
    topBtnBorder = Color(0xFF2E2E34),
    dottedLine = Color(0xFF2B2B31),
    dotInactive = Color(0xFF2E2E34),
    switchOffTrack = NothingBlack,
    switchOffBorder = Color(0xFF424249),
    switchOffThumb = Color(0xFF8A8A94),
    mapDotMuted = Color(0xFF2D2D33),
    backdropDot = Color.White.copy(alpha = 0.028f)
)

val CarbonDarkHollowPalette = HollowPalette(
    canvas = CarbonCanvas,
    onCanvas = CarbonInk,
    surface = CarbonSurface,
    surfaceElevated = CarbonSurfaceElevated,
    cardSurface = CarbonCardSurface,
    border = CarbonBorder,
    ink = CarbonInk,
    inkSecondary = CarbonInkSecondary,
    inkMuted = CarbonInkMuted,
    orangeContainer = CarbonOrangeContainer,
    matrixGreen = DarkMatrixGreen,
    invertedSurface = Color(0xFFF4F4F4),
    onInvertedSurface = CarbonCanvas,
    onInvertedSecondary = Color(0xFF3D3D3D),
    onInvertedMuted = Color(0xFF6B6B6B),
    pillNavFill = Color(0xFF1D1D1D),
    pillNavBorder = Color(0xFF303030),
    pillActiveBg = Color(0xFFF4F4F4),
    pillActiveIcon = CarbonCanvas,
    topBtnFill = Color(0xFF1A1A1A),
    topBtnBorder = Color(0xFF303030),
    dottedLine = Color(0xFF303030),
    dotInactive = Color(0xFF383838),
    switchOffTrack = CarbonSurface,
    switchOffBorder = Color(0xFF474747),
    switchOffThumb = CarbonInkSecondary,
    mapDotMuted = Color(0xFF3A3A3A),
    backdropDot = Color.White.copy(alpha = 0.030f)
)

val LightHollowPalette = HollowPalette(
    canvas = LightCanvas,
    onCanvas = LightInk,
    surface = LightSurface,
    surfaceElevated = LightSurfaceElevated,
    cardSurface = LightCardSurface,
    border = LightBorder,
    ink = LightInk,
    inkSecondary = LightInkSecondary,
    inkMuted = LightInkMuted,
    orangeContainer = LightOrangeContainer,
    matrixGreen = LightMatrixGreen,
    invertedSurface = LightInk,
    onInvertedSurface = Color.White,
    onInvertedSecondary = Color(0xFFD0D0D6),
    onInvertedMuted = Color(0xFF9898A0),
    pillNavFill = Color.White,
    pillNavBorder = LightBorder,
    pillActiveBg = LightInk,
    pillActiveIcon = Color.White,
    topBtnFill = Color.White,
    topBtnBorder = Color(0xFFD2D2CC),
    dottedLine = Color(0xFFCBCBC5),
    dotInactive = Color(0xFFD2D2CC),
    switchOffTrack = Color(0xFFE6E6E2),
    switchOffBorder = Color(0xFFB5B5AF),
    switchOffThumb = Color(0xFF6B6B73),
    mapDotMuted = Color(0xFFC8C8CE),
    backdropDot = Color.Black.copy(alpha = 0.045f)
)

val LocalHollowPalette = staticCompositionLocalOf { DarkHollowPalette }
val LocalHollowIsDark = staticCompositionLocalOf { true }
val LocalHollowIsOled = staticCompositionLocalOf { true }

// Reactive Nothing OS semantic tokens (automatically adapt to Dark & Light Mode)
val NothingCanvas: Color
    @Composable @ReadOnlyComposable get() = LocalHollowPalette.current.canvas

val NothingOnCanvas: Color
    @Composable @ReadOnlyComposable get() = LocalHollowPalette.current.onCanvas

val NothingSurface: Color
    @Composable @ReadOnlyComposable get() = LocalHollowPalette.current.surface

val NothingSurfaceElevated: Color
    @Composable @ReadOnlyComposable get() = LocalHollowPalette.current.surfaceElevated

val NothingCardSurface: Color
    @Composable @ReadOnlyComposable get() = LocalHollowPalette.current.cardSurface

val NothingBorder: Color
    @Composable @ReadOnlyComposable get() = LocalHollowPalette.current.border

val NothingMatrixGreen: Color
    @Composable @ReadOnlyComposable get() = LocalHollowPalette.current.matrixGreen

val NothingInvertedSurface: Color
    @Composable @ReadOnlyComposable get() = LocalHollowPalette.current.invertedSurface

val NothingOnInvertedSurface: Color
    @Composable @ReadOnlyComposable get() = LocalHollowPalette.current.onInvertedSurface

val NothingOnInvertedSecondary: Color
    @Composable @ReadOnlyComposable get() = LocalHollowPalette.current.onInvertedSecondary

val NothingOnInvertedMuted: Color
    @Composable @ReadOnlyComposable get() = LocalHollowPalette.current.onInvertedMuted

val NothingPillNavFill: Color
    @Composable @ReadOnlyComposable get() = LocalHollowPalette.current.pillNavFill

val NothingPillNavBorder: Color
    @Composable @ReadOnlyComposable get() = LocalHollowPalette.current.pillNavBorder

val NothingPillActiveBg: Color
    @Composable @ReadOnlyComposable get() = LocalHollowPalette.current.pillActiveBg

val NothingPillActiveIcon: Color
    @Composable @ReadOnlyComposable get() = LocalHollowPalette.current.pillActiveIcon

val NothingTopBtnFill: Color
    @Composable @ReadOnlyComposable get() = LocalHollowPalette.current.topBtnFill

val NothingTopBtnBorder: Color
    @Composable @ReadOnlyComposable get() = LocalHollowPalette.current.topBtnBorder

val NothingDottedLine: Color
    @Composable @ReadOnlyComposable get() = LocalHollowPalette.current.dottedLine

val NothingDotInactive: Color
    @Composable @ReadOnlyComposable get() = LocalHollowPalette.current.dotInactive

val NothingSwitchOffTrack: Color
    @Composable @ReadOnlyComposable get() = LocalHollowPalette.current.switchOffTrack

val NothingSwitchOffBorder: Color
    @Composable @ReadOnlyComposable get() = LocalHollowPalette.current.switchOffBorder

val NothingSwitchOffThumb: Color
    @Composable @ReadOnlyComposable get() = LocalHollowPalette.current.switchOffThumb

val NothingMapDotMuted: Color
    @Composable @ReadOnlyComposable get() = LocalHollowPalette.current.mapDotMuted

val NothingBackdropDot: Color
    @Composable @ReadOnlyComposable get() = LocalHollowPalette.current.backdropDot

// Legacy semantic aliases mapped dynamically to active HollowPalette
val HollowCream: Color
    @Composable @ReadOnlyComposable get() = LocalHollowPalette.current.canvas

val HollowCreamSurface: Color
    @Composable @ReadOnlyComposable get() = LocalHollowPalette.current.surface

val HollowCreamSurfaceHigh: Color
    @Composable @ReadOnlyComposable get() = LocalHollowPalette.current.surfaceElevated

val HollowCreamDivider: Color
    @Composable @ReadOnlyComposable get() = LocalHollowPalette.current.border

val HollowInk: Color
    @Composable @ReadOnlyComposable get() = LocalHollowPalette.current.ink

val HollowInkSecondary: Color
    @Composable @ReadOnlyComposable get() = LocalHollowPalette.current.inkSecondary

val HollowInkMuted: Color
    @Composable @ReadOnlyComposable get() = LocalHollowPalette.current.inkMuted

val HollowOrangeContainer: Color
    @Composable @ReadOnlyComposable get() = LocalHollowPalette.current.orangeContainer

// Connecting Phase (.busy — Pure OLED Black + Glyph Amber)
val HollowObsidian = Color(0xFF000000)
val HollowObsidianSurface = Color(0xFF141416)
val HollowObsidianDivider = Color(0xFF29292E)
val HollowObsidianMuted = Color(0xFF94949C)
val HollowAmber = Color(0xFFFFB020)

// Connected Phase (.ok — Deep Matrix Black + Glyph Lime)
val HollowForest = Color(0xFF050D08)
val HollowForestCard = Color(0xFF0E1B13)
val HollowForestDivider = Color(0xFF1E3728)
val HollowForestMuted = Color(0xFF8FB8A0)
val HollowLime = Color(0xFFB8F85C)
val HollowLimeBarMuted = Color(0xFF2E5438)

val ExpressiveLightColorScheme = lightColorScheme(
    primary = LightInk,
    onPrimary = Color.White,
    primaryContainer = LightSurface,
    onPrimaryContainer = LightInk,
    secondary = NothingRed,
    onSecondary = Color.White,
    secondaryContainer = LightSurfaceElevated,
    onSecondaryContainer = LightInk,
    tertiary = LightMatrixGreen,
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFFDFF6E8),
    onTertiaryContainer = Color(0xFF0A3D22),
    error = NothingRed,
    onError = Color.White,
    errorContainer = LightOrangeContainer,
    onErrorContainer = Color(0xFF7D0E14),
    background = LightCanvas,
    onBackground = LightInk,
    surface = LightCanvas,
    onSurface = LightInk,
    surfaceVariant = LightSurface,
    onSurfaceVariant = LightInkSecondary,
    outline = Color(0xFFB5B5AF),
    outlineVariant = LightBorder,
    surfaceContainerLowest = Color.White,
    surfaceContainerLow = Color(0xFFFAF9F7),
    surfaceContainer = LightSurface,
    surfaceContainerHigh = LightSurfaceElevated,
    surfaceContainerHighest = Color(0xFFDFDFDA),
    inverseSurface = LightInk,
    inverseOnSurface = Color.White
)

val ExpressiveDarkColorScheme = darkColorScheme(
    primary = DarkInk,
    onPrimary = NothingBlack,
    primaryContainer = DarkSurface,
    onPrimaryContainer = DarkInk,
    secondary = NothingRed,
    onSecondary = Color.White,
    secondaryContainer = DarkSurfaceElevated,
    onSecondaryContainer = DarkInk,
    tertiary = HollowLime,
    onTertiary = NothingBlack,
    tertiaryContainer = HollowForestCard,
    onTertiaryContainer = DarkInk,
    error = NothingRed,
    onError = Color.White,
    errorContainer = DarkOrangeContainer,
    onErrorContainer = Color(0xFFFFD8CF),
    background = DarkCanvas,
    onBackground = DarkInk,
    surface = DarkCanvas,
    onSurface = DarkInk,
    surfaceVariant = DarkSurface,
    onSurfaceVariant = DarkInkSecondary,
    outline = Color(0xFF3A3A40),
    outlineVariant = DarkBorder,
    surfaceContainerLowest = DarkCanvas,
    surfaceContainerLow = Color(0xFF0A0A0B),
    surfaceContainer = DarkSurface,
    surfaceContainerHigh = DarkSurfaceElevated,
    surfaceContainerHighest = Color(0xFF242428),
    inverseSurface = DarkInk,
    inverseOnSurface = NothingBlack
)

val ExpressiveCarbonDarkColorScheme = darkColorScheme(
    primary = CarbonInk,
    onPrimary = CarbonCanvas,
    primaryContainer = CarbonSurface,
    onPrimaryContainer = CarbonInk,
    secondary = NothingRed,
    onSecondary = Color.White,
    secondaryContainer = CarbonSurfaceElevated,
    onSecondaryContainer = CarbonInk,
    tertiary = HollowLime,
    onTertiary = CarbonCanvas,
    tertiaryContainer = HollowForestCard,
    onTertiaryContainer = CarbonInk,
    error = NothingRed,
    onError = Color.White,
    errorContainer = CarbonOrangeContainer,
    onErrorContainer = Color(0xFFFFD8CF),
    background = CarbonCanvas,
    onBackground = CarbonInk,
    surface = CarbonCanvas,
    onSurface = CarbonInk,
    surfaceVariant = CarbonSurface,
    onSurfaceVariant = CarbonInkSecondary,
    outline = Color(0xFF424242),
    outlineVariant = CarbonBorder,
    surfaceContainerLowest = CarbonCanvas,
    surfaceContainerLow = Color(0xFF171717),
    surfaceContainer = CarbonSurface,
    surfaceContainerHigh = CarbonSurfaceElevated,
    surfaceContainerHighest = Color(0xFF2C2C2C),
    inverseSurface = CarbonInk,
    inverseOnSurface = CarbonCanvas
)

// Semantic signal quality accents
val SignalStrongLight = LightMatrixGreen
val SignalMediumLight = Color(0xFFD98600)
val SignalWeakLight = NothingRed

val SignalStrongDark = HollowLime
val SignalMediumDark = Color(0xFFFFB020)
val SignalWeakDark = NothingRed
