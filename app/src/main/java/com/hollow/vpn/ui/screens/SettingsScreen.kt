package com.hollow.vpn.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.provider.Settings
import androidx.activity.compose.BackHandler
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.outlined.Apps
import androidx.compose.material.icons.outlined.CloudQueue
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.Dns
import androidx.compose.material.icons.outlined.FileUpload
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material.icons.outlined.Link
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material.icons.outlined.SettingsBrightness
import androidx.compose.material.icons.outlined.SystemUpdate
import androidx.compose.material.icons.outlined.Terminal
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material.icons.rounded.AccountTree
import androidx.compose.material.icons.rounded.Route
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hollow.vpn.BuildConfig
import com.hollow.vpn.data.AppSettingsState
import com.hollow.vpn.data.AppUpdateState
import com.hollow.vpn.ui.components.HollowBrandShieldLogo
import com.hollow.vpn.ui.components.HollowSwitch
import com.hollow.vpn.ui.components.HollowTopSquareButton
import com.hollow.vpn.ui.components.NothingDottedDivider
import com.hollow.vpn.ui.theme.DotoDisplayFamily
import com.hollow.vpn.ui.theme.HollowCream
import com.hollow.vpn.ui.theme.HollowCreamDivider
import com.hollow.vpn.ui.theme.HollowCreamSurface
import com.hollow.vpn.ui.theme.HollowInk
import com.hollow.vpn.ui.theme.HollowInkSecondary
import com.hollow.vpn.ui.theme.HollowOrange
import com.hollow.vpn.ui.theme.JetBrainsMonoFamily
import com.hollow.vpn.ui.theme.NothingMotion
import com.hollow.vpn.ui.theme.NothingOnCanvas
import com.hollow.vpn.ui.theme.NothingPillActiveBg
import com.hollow.vpn.ui.theme.NothingPillActiveIcon
import com.hollow.vpn.ui.theme.NothingRed
import com.hollow.vpn.ui.theme.NothingSurfaceElevated
import com.hollow.vpn.ui.theme.NothingTopBtnBorder
import com.hollow.vpn.ui.theme.NothingTopBtnFill
import com.hollow.vpn.ui.theme.SpaceGroteskFamily
import com.hollow.vpn.ui.theme.nothingPressScale

private enum class ActiveSettingDialog {
    UPDATE_INTERVAL,
    PROTOCOL,
    ROUTING_MODE,
    DNS_PROVIDER,
    XRAY_LOGS,
    ABOUT,
    CHECK_UPDATE
}

private val PROTOCOL_OPTIONS = listOf(
    SettingChoiceOption("Auto (V2Ray)", "Selects the fastest & stealthiest protocol automatically", "Auto (Recommended)"),
    SettingChoiceOption("VLESS (Reality)", "Modern TLS-fingerprint camouflage for strict networks", "VLESS (Reality)"),
    SettingChoiceOption("VLESS (WS / TCP)", "Low-overhead tunnel for everyday high-speed browsing", "VLESS (WS / TCP)"),
    SettingChoiceOption("VMess (WebSocket)", "Broadly compatible WebSocket fallback transport", "VMess (WebSocket)"),
    SettingChoiceOption("Trojan (TLS)", "Blends with standard HTTPS web traffic", "Trojan (TLS)"),
    SettingChoiceOption("Hysteria2", "QUIC-based high-throughput mode for streaming", "Hysteria2"),
    SettingChoiceOption("gRPC", "Multiplexed HTTP/2 tunnel for unstable mobile data", "gRPC"),
    SettingChoiceOption("Shadowsocks", "Lightweight AEAD cipher proxy", "Shadowsocks")
)

private val ROUTING_MODE_OPTIONS = listOf(
    SettingChoiceOption("Global", "Route all device internet traffic through the VPN", "Global (Recommended)"),
    SettingChoiceOption("Bypass LAN", "Keep local Wi‑Fi devices like printers & TVs reachable directly", "Bypass LAN"),
    SettingChoiceOption("Direct Only", "Diagnostic mode — sends traffic directly without VPN", "Direct Only (Testing)")
)

private val DNS_PROVIDER_OPTIONS = listOf(
    SettingChoiceOption("Auto (DoH)", "Encrypted private DNS lookups — safest default", "Auto (DoH)"),
    SettingChoiceOption("Cloudflare 1.1.1.1", "Fast privacy-focused DNS by Cloudflare", "Cloudflare 1.1.1.1"),
    SettingChoiceOption("Google 8.8.8.8", "Reliable global DNS by Google", "Google 8.8.8.8"),
    SettingChoiceOption("Quad9 9.9.9.9", "Blocks known malicious and phishing domains", "Quad9 9.9.9.9"),
    SettingChoiceOption("AdGuard 94.140.14.14", "Filters ads and trackers at the DNS level", "AdGuard DNS")
)

private fun formatRoutingSummary(mode: String): String = when {
    mode.equals("Global", ignoreCase = true) -> "Global"
    mode.equals("Bypass LAN", ignoreCase = true) -> "Bypass LAN"
    mode.contains("Direct", ignoreCase = true) -> "Direct Only"
    else -> "Global"
}

private fun formatDnsSummary(dns: String): String = when {
    dns.startsWith("Auto", ignoreCase = true) -> "Auto (DoH)"
    dns.contains("Cloudflare", ignoreCase = true) -> "1.1.1.1"
    dns.contains("Google", ignoreCase = true) -> "8.8.8.8"
    dns.contains("Quad9", ignoreCase = true) -> "9.9.9.9"
    dns.contains("AdGuard", ignoreCase = true) -> "AdGuard"
    else -> dns
}

/**
 * PAGE 6: Hollow Settings Screen (Compact inline header, clean single-line rows, no bottom nav bar)
 */
@Composable
fun SettingsScreen(
    settings: AppSettingsState,
    xrayLogs: List<String> = emptyList(),
    serverCount: Int = 0,
    appUpdateState: AppUpdateState = AppUpdateState.Idle,
    bottomContentPadding: Dp = 0.dp,
    onUpdateSettings: ((AppSettingsState) -> AppSettingsState) -> Unit,
    onOpenAddSubscription: () -> Unit,
    onOpenGettingStarted: () -> Unit = {},
    onClearXrayLogs: () -> Unit = {},
    onExportServers: () -> Unit = {},
    onCheckForUpdates: () -> Unit = {},
    onShowMessage: (String) -> Unit = {},
    onBack: () -> Unit
) {
    BackHandler { onBack() }
    val context = LocalContext.current
    var activeDialog by remember { mutableStateOf<ActiveSettingDialog?>(null) }
    var showPerAppPicker by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(HollowCream)
            .statusBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .widthIn(max = 600.dp)
                .align(Alignment.TopCenter)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(12.dp))

            // Compact inline header: Back button + SETTINGS on the same row
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                HollowTopSquareButton(
                    containerColor = NothingTopBtnFill,
                    contentColor = NothingOnCanvas,
                    borderColor = NothingTopBtnBorder,
                    contentDescription = "Back",
                    onClick = onBack,
                    modifier = Modifier.testTag("btn_navigate_back")
                ) { tint ->
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowLeft,
                        contentDescription = null,
                        tint = tint,
                        modifier = Modifier.size(21.dp)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = "SETTINGS",
                    fontFamily = DotoDisplayFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    letterSpacing = 0.9.sp,
                    maxLines = 1,
                    color = NothingOnCanvas
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            // 0. APPEARANCE
            SettingsSectionHeader(title = "APPEARANCE")

            ThemeModeInlineSelector(
                selectedMode = settings.themeMode,
                onSelectMode = { mode ->
                    onUpdateSettings { it.copy(themeMode = mode) }
                }
            )

            Spacer(modifier = Modifier.height(20.dp))

            // 1. SUBSCRIPTIONS
            SettingsSectionHeader(title = "SUBSCRIPTIONS")

            SettingsValueNavigationRow(
                icon = Icons.Outlined.Link,
                title = "Servers & subscriptions",
                valueText = if (serverCount > 0) "$serverCount saved" else "Add",
                onClick = onOpenAddSubscription,
                testTag = "row_custom_subscription"
            )
            NothingDottedDivider()

            SettingsToggleRow(
                icon = Icons.Outlined.CloudQueue,
                title = "Auto-update servers",
                checked = settings.autoConfigure,
                onCheckedChange = { enabled ->
                    onUpdateSettings { it.copy(autoConfigure = enabled) }
                },
                testTag = "switch_auto_configure"
            )
            if (settings.autoConfigure) {
                NothingDottedDivider()
                SettingsValueNavigationRow(
                    icon = Icons.Outlined.Refresh,
                    title = "Update interval",
                    valueText = settings.updateIntervalLabel,
                    onClick = { activeDialog = ActiveSettingDialog.UPDATE_INTERVAL },
                    testTag = "row_update_interval"
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // 2. PER-APP ROUTING
            SettingsSectionHeader(title = "PER-APP ROUTING")

            SettingsToggleRow(
                icon = Icons.Outlined.Apps,
                title = "Per-app routing",
                checked = settings.perAppProxyEnabled,
                onCheckedChange = { enabled ->
                    onUpdateSettings {
                        it.copy(
                            perAppProxyEnabled = enabled,
                            bypassSelectedApps = if (enabled && !it.perAppProxyEnabled) true else it.bypassSelectedApps
                        )
                    }
                },
                testTag = "switch_per_app_proxy"
            )
            NothingDottedDivider()

            SettingsValueNavigationRow(
                icon = Icons.Outlined.Apps,
                title = if (!settings.perAppProxyEnabled || settings.bypassSelectedApps) {
                    "Apps skipping VPN"
                } else {
                    "Apps using VPN"
                },
                valueText = if (settings.selectedAppPackages.isEmpty()) {
                    "None"
                } else {
                    "${settings.selectedAppPackages.size} apps"
                },
                onClick = { showPerAppPicker = true },
                testTag = "row_per_app_selection"
            )

            if (settings.perAppProxyEnabled) {
                NothingDottedDivider()
                PerAppInlineModeRow(
                    bypassSelectedApps = settings.bypassSelectedApps,
                    onSelectBypass = { bypass ->
                        onUpdateSettings { it.copy(bypassSelectedApps = bypass) }
                    }
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // 3. CONNECTION
            SettingsSectionHeader(title = "CONNECTION")

            SettingsToggleRow(
                icon = Icons.Outlined.PlayArrow,
                title = "Start on app open",
                checked = settings.startOnAppOpen,
                onCheckedChange = { enabled ->
                    onUpdateSettings { it.copy(startOnAppOpen = enabled) }
                },
                testTag = "switch_start_on_open"
            )
            NothingDottedDivider()

            SettingsValueNavigationRow(
                icon = Icons.Rounded.AccountTree,
                title = "Protocol",
                valueText = formatConsumerProtocol(settings.protocolPreference),
                onClick = { activeDialog = ActiveSettingDialog.PROTOCOL },
                testTag = "row_protocol"
            )
            NothingDottedDivider()

            SettingsToggleRow(
                icon = Icons.Outlined.Refresh,
                title = "Auto-switch slow servers",
                checked = settings.smartSwitchEnabled,
                onCheckedChange = { enabled ->
                    onUpdateSettings { it.copy(smartSwitchEnabled = enabled) }
                },
                testTag = "switch_smart_switch"
            )
            NothingDottedDivider()

            SettingsToggleRow(
                icon = Icons.Outlined.Lock,
                title = "Strict TLS verification",
                checked = settings.strictTlsEnabled,
                onCheckedChange = { enabled ->
                    onUpdateSettings { it.copy(strictTlsEnabled = enabled) }
                },
                testTag = "switch_strict_tls"
            )

            Spacer(modifier = Modifier.height(20.dp))

            // 4. ROUTING
            SettingsSectionHeader(title = "ROUTING")

            SettingsValueNavigationRow(
                icon = Icons.Rounded.Route,
                title = "Routing mode",
                valueText = formatRoutingSummary(settings.routingMode),
                onClick = { activeDialog = ActiveSettingDialog.ROUTING_MODE },
                testTag = "row_routing_mode"
            )
            NothingDottedDivider()

            SettingsValueNavigationRow(
                icon = Icons.Outlined.Dns,
                title = "Private DNS",
                valueText = formatDnsSummary(settings.dnsMode),
                onClick = { activeDialog = ActiveSettingDialog.DNS_PROVIDER },
                testTag = "row_dns"
            )
            NothingDottedDivider()

            SettingsToggleRow(
                icon = Icons.Outlined.Language,
                title = "IPv6 routing",
                checked = settings.ipv6Enabled,
                onCheckedChange = { enabled ->
                    onUpdateSettings { it.copy(ipv6Enabled = enabled) }
                },
                testTag = "switch_ipv6"
            )

            Spacer(modifier = Modifier.height(20.dp))

            // 5. SYSTEM & PRIVACY
            SettingsSectionHeader(title = "SYSTEM & PRIVACY")

            SettingsValueNavigationRow(
                icon = Icons.Outlined.Lock,
                title = "Always-on VPN & kill switch",
                valueText = "System",
                onClick = {
                    try {
                        context.startActivity(
                            Intent(Settings.ACTION_VPN_SETTINGS).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        )
                    } catch (_: Exception) {
                    }
                },
                testTag = "row_system_vpn_settings"
            )
            NothingDottedDivider()

            SettingsToggleRow(
                icon = Icons.Outlined.VisibilityOff,
                title = "Hide from recent apps",
                checked = settings.hideFromRecentApps,
                onCheckedChange = { enabled ->
                    onUpdateSettings { it.copy(hideFromRecentApps = enabled) }
                },
                testTag = "switch_hide_recents"
            )

            Spacer(modifier = Modifier.height(20.dp))

            // 6. DIAGNOSTICS & BACKUP
            SettingsSectionHeader(title = "DIAGNOSTICS & BACKUP")

            SettingsValueNavigationRow(
                icon = Icons.Outlined.Info,
                title = "Setup & permissions guide",
                valueText = null,
                onClick = onOpenGettingStarted,
                testTag = "row_getting_started"
            )
            NothingDottedDivider()

            SettingsValueNavigationRow(
                icon = Icons.Outlined.Terminal,
                title = "Xray diagnostic logs",
                valueText = if (xrayLogs.isEmpty()) "Empty" else "${xrayLogs.size} lines",
                onClick = { activeDialog = ActiveSettingDialog.XRAY_LOGS },
                testTag = "row_xray_logs"
            )
            NothingDottedDivider()

            SettingsValueNavigationRow(
                icon = Icons.Outlined.FileUpload,
                title = "Export server backup",
                valueText = "$serverCount",
                onClick = onExportServers,
                testTag = "row_export_servers"
            )

            Spacer(modifier = Modifier.height(20.dp))

            // 7. ABOUT
            SettingsSectionHeader(title = "ABOUT")

            val updateBadge = when (appUpdateState) {
                AppUpdateState.Idle -> "Check"
                AppUpdateState.Checking -> "Checking"
                is AppUpdateState.UpToDate -> "Latest"
                is AppUpdateState.UpdateAvailable -> appUpdateState.latestVersion
                is AppUpdateState.Error -> "Retry"
            }

            SettingsValueNavigationRow(
                icon = Icons.Outlined.SystemUpdate,
                title = "Check for updates",
                valueText = updateBadge,
                onClick = {
                    activeDialog = ActiveSettingDialog.CHECK_UPDATE
                    onCheckForUpdates()
                },
                testTag = "row_check_updates"
            )

            Spacer(modifier = Modifier.height(12.dp))

            val aboutInteraction = remember { MutableInteractionSource() }
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .nothingPressScale(aboutInteraction, pressedScale = 0.97f)
                    .clip(RoundedCornerShape(20.dp))
                    .clickable(
                        interactionSource = aboutInteraction,
                        indication = androidx.compose.material3.ripple(),
                        role = Role.Button
                    ) { activeDialog = ActiveSettingDialog.ABOUT }
                    .testTag("card_about_hollow"),
                shape = RoundedCornerShape(20.dp),
                color = HollowCreamSurface,
                border = BorderStroke(1.dp, HollowCreamDivider)
            ) {
                val aboutRingInk = HollowInk
                Box(modifier = Modifier.fillMaxWidth()) {
                    Canvas(modifier = Modifier.matchParentSize()) {
                        val center = Offset(size.width * 0.88f, size.height * 0.24f)
                        drawCircle(
                            color = NothingRed.copy(alpha = 0.22f),
                            radius = 52.dp.toPx(),
                            center = center,
                            style = Stroke(width = 1.dp.toPx())
                        )
                        drawCircle(
                            color = aboutRingInk.copy(alpha = 0.08f),
                            radius = 84.dp.toPx(),
                            center = center,
                            style = Stroke(width = 1.dp.toPx())
                        )
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(RoundedCornerShape(11.dp))
                                    .background(NothingSurfaceElevated)
                                    .border(1.dp, HollowCreamDivider, RoundedCornerShape(11.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                HollowBrandShieldLogo(
                                    backgroundColor = NothingSurfaceElevated,
                                    strokeColor = HollowInk,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "HOLLOW VPN",
                                    fontFamily = DotoDisplayFamily,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.5.sp,
                                    letterSpacing = 0.6.sp,
                                    color = HollowInk
                                )
                                Text(
                                    text = "Xray Multi-Protocol Tunnel",
                                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.5.sp),
                                    color = HollowInkSecondary,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                            Surface(
                                shape = RoundedCornerShape(50),
                                color = HollowOrange
                            ) {
                                Text(
                                    text = "v${BuildConfig.VERSION_NAME}",
                                    fontFamily = JetBrainsMonoFamily,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 10.5.sp,
                                    color = Color.White,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))
                        NothingDottedDivider()
                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            AboutCardStat(label = "ENGINE", value = "Xray Core")
                            AboutCardStat(label = "BUILD", value = "#${BuildConfig.VERSION_CODE}")
                            AboutCardStat(label = "PROTOCOLS", value = "Reality · HY2")
                            Icon(
                                imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                                contentDescription = "Open About Hollow",
                                tint = HollowInkSecondary,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(22.dp + bottomContentPadding))
            if (bottomContentPadding == 0.dp) {
                Spacer(modifier = Modifier.navigationBarsPadding())
            }
        }
    }

    when (activeDialog) {
        ActiveSettingDialog.UPDATE_INTERVAL -> {
            SettingOptionPickerDialog(
                title = "Update interval",
                options = UPDATE_INTERVAL_OPTIONS,
                selectedValue = settings.updateIntervalLabel,
                onSelect = { chosen ->
                    onUpdateSettings {
                        it.copy(updateIntervalLabel = chosen, subUpdateInterval = chosen)
                    }
                    activeDialog = null
                },
                onDismiss = { activeDialog = null }
            )
        }

        ActiveSettingDialog.PROTOCOL -> {
            SettingOptionPickerDialog(
                title = "Protocol",
                options = PROTOCOL_OPTIONS,
                selectedValue = settings.protocolPreference,
                onSelect = { chosen ->
                    onUpdateSettings { it.copy(protocolPreference = chosen) }
                    activeDialog = null
                },
                onDismiss = { activeDialog = null }
            )
        }

        ActiveSettingDialog.ROUTING_MODE -> {
            SettingOptionPickerDialog(
                title = "Routing mode",
                options = ROUTING_MODE_OPTIONS,
                selectedValue = settings.routingMode,
                onSelect = { chosen ->
                    onUpdateSettings { it.copy(routingMode = chosen) }
                    activeDialog = null
                },
                onDismiss = { activeDialog = null }
            )
        }

        ActiveSettingDialog.DNS_PROVIDER -> {
            SettingOptionPickerDialog(
                title = "Private DNS",
                options = DNS_PROVIDER_OPTIONS,
                selectedValue = settings.dnsMode,
                onSelect = { chosen ->
                    onUpdateSettings { it.copy(dnsMode = chosen) }
                    activeDialog = null
                },
                onDismiss = { activeDialog = null }
            )
        }

        ActiveSettingDialog.XRAY_LOGS -> {
            XrayDiagnosticLogsDialog(
                logs = xrayLogs,
                onCopyLogs = {
                    val joined = xrayLogs.joinToString("\n")
                    if (joined.isNotBlank()) {
                        val cm = context.getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager
                        cm?.setPrimaryClip(ClipData.newPlainText("Hollow VPN Connection Logs", joined))
                        onShowMessage("Copied ${xrayLogs.size} log line(s)")
                    }
                },
                onClearLogs = onClearXrayLogs,
                onDismiss = { activeDialog = null }
            )
        }

        ActiveSettingDialog.ABOUT -> {
            AboutHollowSheet(
                onCopyBuildInfo = onShowMessage,
                onCheckUpdates = {
                    activeDialog = ActiveSettingDialog.CHECK_UPDATE
                    onCheckForUpdates()
                },
                onDismiss = { activeDialog = null }
            )
        }

        ActiveSettingDialog.CHECK_UPDATE -> {
            AppUpdateCheckDialog(
                updateState = appUpdateState,
                onRetry = onCheckForUpdates,
                onDismiss = { activeDialog = null }
            )
        }

        null -> Unit
    }

    if (showPerAppPicker) {
        PerAppProxyPickerDialog(
            selectedPackages = settings.selectedAppPackages,
            bypassSelectedApps = !settings.perAppProxyEnabled || settings.bypassSelectedApps,
            onSave = { packages, bypassMode ->
                onShowMessage(
                    if (packages.isEmpty()) "Per-app routing disabled (all apps use VPN)"
                    else "Per-app routing saved for ${packages.size} app(s)"
                )
                onUpdateSettings {
                    it.copy(
                        perAppProxyEnabled = packages.isNotEmpty(),
                        bypassSelectedApps = bypassMode,
                        selectedAppPackages = packages
                    )
                }
                showPerAppPicker = false
            },
            onDismiss = { showPerAppPicker = false }
        )
    }
}

@Composable
private fun SettingsSectionHeader(title: String) {
    Text(
        text = title,
        fontFamily = JetBrainsMonoFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 10.5.sp,
        letterSpacing = 1.3.sp,
        color = HollowOrange,
        modifier = Modifier.padding(bottom = 6.dp)
    )
}

@Composable
internal fun SettingsGroupCard(
    sectionTitle: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        SettingsSectionHeader(title = sectionTitle.uppercase())
        Column(
            modifier = Modifier.fillMaxWidth(),
            content = content
        )
    }
}

@Composable
private fun SettingsToggleRow(
    icon: ImageVector,
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    testTag: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .minimumInteractiveComponentSize()
            .clickable { onCheckedChange(!checked) }
            .padding(vertical = 11.dp)
            .testTag(testTag),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = HollowInkSecondary,
            modifier = Modifier.size(19.dp)
        )
        Spacer(modifier = Modifier.width(14.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Medium,
                fontSize = 14.5.sp
            ),
            color = HollowInk,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(10.dp))
        HollowSwitch(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
    }
}

@Composable
private fun SettingsValueNavigationRow(
    icon: ImageVector,
    title: String,
    valueText: String?,
    onClick: () -> Unit,
    testTag: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .minimumInteractiveComponentSize()
            .clickable(role = Role.Button, onClick = onClick)
            .padding(vertical = 11.dp)
            .testTag(testTag),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = HollowInkSecondary,
            modifier = Modifier.size(19.dp)
        )
        Spacer(modifier = Modifier.width(14.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Medium,
                fontSize = 14.5.sp
            ),
            color = HollowInk,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f)
        )
        if (!valueText.isNullOrBlank()) {
            Spacer(modifier = Modifier.width(10.dp))
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = HollowCreamSurface,
                border = BorderStroke(1.dp, HollowCreamDivider)
            ) {
                Text(
                    text = valueText,
                    fontFamily = JetBrainsMonoFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 11.5.sp,
                    color = HollowInk,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(horizontal = 9.dp, vertical = 4.dp)
                )
            }
            Spacer(modifier = Modifier.width(6.dp))
        } else {
            Spacer(modifier = Modifier.width(6.dp))
        }
        Icon(
            imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
            contentDescription = null,
            tint = HollowInkSecondary,
            modifier = Modifier.size(18.dp)
        )
    }
}

@Composable
private fun PerAppInlineModeRow(
    bypassSelectedApps: Boolean,
    onSelectBypass: (Boolean) -> Unit
) {
    val selectedIndex = if (bypassSelectedApps) 0 else 1
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
            .testTag("switch_bypass_selected_apps"),
        shape = RoundedCornerShape(12.dp),
        color = HollowCreamSurface,
        border = BorderStroke(1.dp, HollowCreamDivider)
    ) {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        ) {
            val gap = 4.dp
            val slotWidth = ((maxWidth - gap) / 2f).coerceAtLeast(1.dp)
            val pillOffset by animateDpAsState(
                targetValue = (slotWidth + gap) * selectedIndex,
                animationSpec = NothingMotion.snappySpring(),
                label = "per_app_pill_glide"
            )
            Box(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .offset(x = pillOffset)
                    .width(slotWidth)
                    .height(36.dp)
                    .clip(RoundedCornerShape(9.dp))
                    .background(NothingPillActiveBg)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(gap)
            ) {
                InlineModeSegment(
                    label = "Skip VPN",
                    selected = bypassSelectedApps,
                    onClick = { onSelectBypass(true) },
                    modifier = Modifier.weight(1f)
                )
                InlineModeSegment(
                    label = "VPN only",
                    selected = !bypassSelectedApps,
                    onClick = { onSelectBypass(false) },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun ThemeModeInlineSelector(
    selectedMode: String,
    onSelectMode: (String) -> Unit
) {
    val activeMode = when {
        selectedMode.equals("Light", ignoreCase = true) -> "Light"
        selectedMode.equals("System", ignoreCase = true) -> "System"
        else -> "Dark"
    }
    val selectedIndex = when (activeMode) {
        "Dark" -> 0
        "Light" -> 1
        else -> 2
    }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        shape = RoundedCornerShape(13.dp),
        color = HollowCreamSurface,
        border = BorderStroke(1.dp, HollowCreamDivider)
    ) {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        ) {
            val gap = 4.dp
            val slotWidth = ((maxWidth - gap * 2) / 3f).coerceAtLeast(1.dp)
            val pillOffset by animateDpAsState(
                targetValue = (slotWidth + gap) * selectedIndex,
                animationSpec = NothingMotion.snappySpring(),
                label = "theme_pill_glide"
            )
            Box(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .offset(x = pillOffset)
                    .width(slotWidth)
                    .height(36.dp)
                    .clip(RoundedCornerShape(9.dp))
                    .background(NothingPillActiveBg)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(gap)
            ) {
                ThemeModeSegment(
                    label = "Dark",
                    icon = Icons.Outlined.DarkMode,
                    selected = activeMode == "Dark",
                    onClick = { onSelectMode("Dark") },
                    modifier = Modifier
                        .weight(1f)
                        .testTag("theme_mode_dark")
                )
                ThemeModeSegment(
                    label = "Light",
                    icon = Icons.Outlined.LightMode,
                    selected = activeMode == "Light",
                    onClick = { onSelectMode("Light") },
                    modifier = Modifier
                        .weight(1f)
                        .testTag("theme_mode_light")
                )
                ThemeModeSegment(
                    label = "System",
                    icon = Icons.Outlined.SettingsBrightness,
                    selected = activeMode == "System",
                    onClick = { onSelectMode("System") },
                    modifier = Modifier
                        .weight(1f)
                        .testTag("theme_mode_system")
                )
            }
        }
    }
}

@Composable
private fun ThemeModeSegment(
    label: String,
    icon: ImageVector,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val contentTint by animateColorAsState(
        targetValue = if (selected) NothingPillActiveIcon else HollowInkSecondary,
        animationSpec = tween(200, easing = NothingMotion.FluidEase),
        label = "theme_seg_tint_$label"
    )
    val interactionSource = remember { MutableInteractionSource() }
    Box(
        modifier = modifier
            .minimumInteractiveComponentSize()
            .nothingPressScale(interactionSource, pressedScale = 0.94f)
            .clip(RoundedCornerShape(9.dp))
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                role = Role.RadioButton,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .height(36.dp)
                .padding(horizontal = 6.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = contentTint,
                modifier = Modifier.size(15.dp)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = label,
                fontFamily = SpaceGroteskFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.5.sp,
                maxLines = 1,
                color = contentTint
            )
        }
    }
}

@Composable
private fun InlineModeSegment(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val contentTint by animateColorAsState(
        targetValue = if (selected) NothingPillActiveIcon else HollowInkSecondary,
        animationSpec = tween(200, easing = NothingMotion.FluidEase),
        label = "inline_seg_tint_$label"
    )
    val interactionSource = remember { MutableInteractionSource() }
    Box(
        modifier = modifier
            .minimumInteractiveComponentSize()
            .nothingPressScale(interactionSource, pressedScale = 0.94f)
            .clip(RoundedCornerShape(9.dp))
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                role = Role.RadioButton,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(36.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = label,
                fontFamily = SpaceGroteskFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.5.sp,
                color = contentTint
            )
        }
    }
}

@Composable
private fun AboutCardStat(
    label: String,
    value: String
) {
    Column {
        Text(
            text = label,
            fontFamily = JetBrainsMonoFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 9.sp,
            letterSpacing = 0.9.sp,
            color = HollowInkSecondary
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = value,
            fontFamily = JetBrainsMonoFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 11.5.sp,
            color = HollowInk
        )
    }
}
