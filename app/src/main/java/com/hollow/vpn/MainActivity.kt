package com.hollow.vpn

import android.Manifest
import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.content.pm.PackageManager
import android.net.VpnService
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Dns
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.rounded.PowerSettingsNew
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hollow.vpn.ui.AddServerTab
import com.hollow.vpn.ui.VpnScreen
import com.hollow.vpn.ui.VpnViewModel
import com.hollow.vpn.ui.components.HollowBlinkingDot
import com.hollow.vpn.ui.components.HollowBottomNavigationBar
import com.hollow.vpn.ui.components.HollowBottomTab
import com.hollow.vpn.ui.components.ServerQrShareDialog
import com.hollow.vpn.ui.screens.AddServerScreen
import com.hollow.vpn.ui.screens.ConnectedHomeScreen
import com.hollow.vpn.ui.screens.CountryServerListScreen
import com.hollow.vpn.ui.screens.GettingStartedScreen
import com.hollow.vpn.ui.screens.RawXrayJsonEditorDialog
import com.hollow.vpn.ui.screens.SettingsScreen
import com.hollow.vpn.ui.screens.SplashFetchingScreen
import com.hollow.vpn.ui.theme.DotoDisplayFamily
import com.hollow.vpn.ui.theme.HollowAmber
import com.hollow.vpn.ui.theme.HollowCream
import com.hollow.vpn.ui.theme.HollowCreamDivider
import com.hollow.vpn.ui.theme.HollowDarkInk
import com.hollow.vpn.ui.theme.HollowForest
import com.hollow.vpn.ui.theme.HollowForestDivider
import com.hollow.vpn.ui.theme.HollowForestMuted
import com.hollow.vpn.ui.theme.HollowInk
import com.hollow.vpn.ui.theme.HollowInkSecondary
import com.hollow.vpn.ui.theme.HollowLime
import com.hollow.vpn.ui.theme.HollowObsidian
import com.hollow.vpn.ui.theme.HollowObsidianDivider
import com.hollow.vpn.ui.theme.HollowObsidianMuted
import com.hollow.vpn.ui.theme.HollowOrange
import com.hollow.vpn.ui.theme.HollowVpnTheme
import com.hollow.vpn.ui.theme.JetBrainsMonoFamily
import com.hollow.vpn.ui.theme.NothingBorder
import com.hollow.vpn.ui.theme.NothingMatrixGreen
import com.hollow.vpn.ui.theme.NothingMotion
import com.hollow.vpn.ui.theme.NothingSurfaceElevated
import com.hollow.vpn.ui.theme.SpaceGroteskFamily
import com.hollow.vpn.ui.theme.detectOledDisplay
import com.hollow.vpn.vpn.XrayVpnService

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HollowVpnApp()
        }
    }
}

@Composable
fun HollowVpnApp(
    vpnViewModel: VpnViewModel = viewModel()
) {
    val context = LocalContext.current
    val view = LocalView.current
    val currentScreen by vpnViewModel.currentScreen.collectAsStateWithLifecycle()
    val gettingStartedStep by vpnViewModel.gettingStartedStep.collectAsStateWithLifecycle()
    val allServers by vpnViewModel.allServers.collectAsStateWithLifecycle()
    val filteredServers by vpnViewModel.filteredServers.collectAsStateWithLifecycle()
    val selectedFilter by vpnViewModel.selectedFilter.collectAsStateWithLifecycle()
    val selectedServer by vpnViewModel.selectedServer.collectAsStateWithLifecycle()
    val telemetry by vpnViewModel.telemetry.collectAsStateWithLifecycle()
    val settings by vpnViewModel.settings.collectAsStateWithLifecycle()
    val fetchingStatus by vpnViewModel.fetchingStatusMessage.collectAsStateWithLifecycle()
    val fetchingProgress by vpnViewModel.fetchingProgress.collectAsStateWithLifecycle()
    val snackbarMessage by vpnViewModel.snackbarMessage.collectAsStateWithLifecycle()
    val activeAddTab by vpnViewModel.activeAddTab.collectAsStateWithLifecycle()
    val inspectingJsonServer by vpnViewModel.inspectingJsonServer.collectAsStateWithLifecycle()
    val sharingQrServer by vpnViewModel.sharingQrServer.collectAsStateWithLifecycle()
    val appUpdateState by vpnViewModel.appUpdateState.collectAsStateWithLifecycle()
    val xrayLogs by XrayVpnService.logs.collectAsStateWithLifecycle()

    val systemDark = isSystemInDarkTheme()
    val isDarkTheme = when (settings.themeMode.lowercase()) {
        "light" -> false
        "system" -> systemDark
        else -> true
    }
    val isOledHardware = remember(context) { detectOledDisplay(context) }

    val snackbarHostState = remember { SnackbarHostState() }

    val isDarkSurfaceNow = isDarkTheme ||
        (currentScreen == VpnScreen.CONNECTED_HOME && (telemetry.isConnecting || telemetry.isConnected))

    SideEffect {
        val window = (view.context as? Activity)?.window ?: return@SideEffect
        val insetsController = WindowCompat.getInsetsController(window, view)
        insetsController.isAppearanceLightStatusBars = !isDarkSurfaceNow
        insetsController.isAppearanceLightNavigationBars = !isDarkSurfaceNow
    }

    HollowVpnTheme(darkTheme = isDarkTheme, oledDark = isOledHardware) {

    LaunchedEffect(settings.hideFromRecentApps) {
        try {
            val am = context.getSystemService(Context.ACTIVITY_SERVICE) as? ActivityManager
            am?.appTasks?.firstOrNull()?.setExcludeFromRecents(settings.hideFromRecentApps)
        } catch (_: Exception) {
        }
    }

    LaunchedEffect(snackbarMessage) {
        val msg = snackbarMessage ?: return@LaunchedEffect
        snackbarHostState.showSnackbar(msg)
        vpnViewModel.clearSnackbar()
    }

    val vpnPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            vpnViewModel.toggleConnection()
        } else {
            vpnViewModel.showMessage("VPN permission was denied")
        }
    }

    var pendingNotificationAction by remember { mutableStateOf<(() -> Unit)?>(null) }
    var notificationPromptedThisSession by remember { mutableStateOf(false) }

    val notificationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { _ ->
        pendingNotificationAction?.invoke()
        pendingNotificationAction = null
    }

    val runWithNotificationPermission: (() -> Unit) -> Unit = { action ->
        val shouldRequest = Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            !notificationPromptedThisSession &&
            ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED
        if (shouldRequest) {
            notificationPromptedThisSession = true
            pendingNotificationAction = action
            notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        } else {
            action()
        }
    }

    val startOrToggleVpnConsent = {
        val prepareIntent = try {
            if (settings.tunMode && !telemetry.isConnected && !telemetry.isConnecting) {
                VpnService.prepare(context)
            } else {
                null
            }
        } catch (_: Exception) {
            null
        }
        if (prepareIntent != null && context is Activity) {
            vpnPermissionLauncher.launch(prepareIntent)
        } else {
            vpnViewModel.toggleConnection()
        }
    }

    val requestOrToggleVpn = {
        if (telemetry.isConnected || telemetry.isConnecting) {
            vpnViewModel.toggleConnection()
        } else {
            runWithNotificationPermission(startOrToggleVpnConsent)
        }
    }

    val exportServersToClipboard: () -> Unit = {
        val servers = allServers
        val payload = vpnViewModel.buildExportBackupPayload(servers)
        if (payload.isBlank()) {
            vpnViewModel.showMessage("No saved servers to export")
        } else {
            val cm = context.getSystemService(Context.CLIPBOARD_SERVICE) as? android.content.ClipboardManager
            cm?.setPrimaryClip(android.content.ClipData.newPlainText("Hollow VPN Servers", payload))
            vpnViewModel.showMessage("Exported ${servers.size} server(s) to clipboard")
        }
    }

    // Floating Nothing OS capsule navigation dock shows on primary top-level tabs only (hidden on Settings & Add Server)
    val showBottomNav = currentScreen == VpnScreen.CONNECTED_HOME ||
        currentScreen == VpnScreen.SERVER_LIST

    val selectedBottomTab = when (currentScreen) {
        VpnScreen.CONNECTED_HOME -> HollowBottomTab.CONNECT
        VpnScreen.SERVER_LIST, VpnScreen.ADD_SERVER -> HollowBottomTab.SERVERS
        VpnScreen.SETTINGS -> HollowBottomTab.SETTINGS
        VpnScreen.GETTING_STARTED, VpnScreen.SPLASH_FETCHING -> HollowBottomTab.CONNECT
    }

    val navBackgroundColor = when {
        currentScreen == VpnScreen.CONNECTED_HOME && telemetry.isConnecting -> HollowObsidian
        currentScreen == VpnScreen.CONNECTED_HOME && telemetry.isConnected -> HollowForest
        else -> HollowCream
    }
    val navBorderColor = when {
        currentScreen == VpnScreen.CONNECTED_HOME && telemetry.isConnecting -> HollowObsidianDivider
        currentScreen == VpnScreen.CONNECTED_HOME && telemetry.isConnected -> HollowForestDivider
        else -> HollowCreamDivider
    }
    val navIndicatorColor = when {
        currentScreen == VpnScreen.CONNECTED_HOME && telemetry.isConnecting -> HollowAmber
        currentScreen == VpnScreen.CONNECTED_HOME && telemetry.isConnected -> HollowLime
        else -> HollowOrange
    }
    val navActiveTextColor = when {
        currentScreen == VpnScreen.CONNECTED_HOME && (telemetry.isConnecting || telemetry.isConnected) -> HollowDarkInk
        else -> HollowInk
    }
    val navInactiveTextColor = when {
        currentScreen == VpnScreen.CONNECTED_HOME && telemetry.isConnecting -> HollowObsidianMuted
        currentScreen == VpnScreen.CONNECTED_HOME && telemetry.isConnected -> HollowForestMuted
        else -> HollowInkSecondary
    }

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val useNavigationRail = showBottomNav && maxWidth >= 600.dp

        Row(modifier = Modifier.fillMaxSize()) {
            if (useNavigationRail) {
                NavigationRail(
                    containerColor = navBackgroundColor,
                    modifier = Modifier.fillMaxHeight()
                ) {
                    Spacer(Modifier.weight(1f))
                    val railColors = NavigationRailItemDefaults.colors(
                        selectedIconColor = navActiveTextColor,
                        selectedTextColor = navActiveTextColor,
                        unselectedIconColor = navInactiveTextColor,
                        unselectedTextColor = navInactiveTextColor,
                        indicatorColor = navIndicatorColor.copy(alpha = 0.22f)
                    )
                    NavigationRailItem(
                        selected = selectedBottomTab == HollowBottomTab.CONNECT,
                        onClick = { vpnViewModel.navigateTo(VpnScreen.CONNECTED_HOME) },
                        icon = { Icon(Icons.Rounded.PowerSettingsNew, contentDescription = "Connect") },
                        label = { Text("Connect") },
                        colors = railColors
                    )
                    NavigationRailItem(
                        selected = selectedBottomTab == HollowBottomTab.SERVERS,
                        onClick = { vpnViewModel.navigateTo(VpnScreen.SERVER_LIST) },
                        icon = { Icon(Icons.Outlined.Dns, contentDescription = "Servers") },
                        label = { Text("Servers") },
                        colors = railColors
                    )
                    NavigationRailItem(
                        selected = selectedBottomTab == HollowBottomTab.SETTINGS,
                        onClick = { vpnViewModel.navigateTo(VpnScreen.SETTINGS) },
                        icon = { Icon(Icons.Outlined.Settings, contentDescription = "Settings") },
                        label = { Text("Settings") },
                        colors = railColors
                    )
                    Spacer(Modifier.weight(1f))
                }
            }

            Scaffold(
                modifier = Modifier.weight(1f),
                containerColor = Color.Transparent,
                contentWindowInsets = WindowInsets(0, 0, 0, 0),
                snackbarHost = {
                    SnackbarHost(hostState = snackbarHostState) { data ->
                        Snackbar(
                            snackbarData = data,
                            shape = RoundedCornerShape(16.dp),
                            containerColor = NothingSurfaceElevated,
                            contentColor = HollowInk,
                            actionColor = HollowOrange
                        )
                    }
                },
                bottomBar = {
                    val showFetchingPill = fetchingProgress.isActive &&
                        currentScreen != VpnScreen.SPLASH_FETCHING &&
                        currentScreen != VpnScreen.GETTING_STARTED
                    val renderNavBar = showBottomNav && !useNavigationRail
                    if (showFetchingPill || renderNavBar) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            AnimatedVisibility(
                                visible = showFetchingPill,
                                enter = fadeIn() + slideInVertically { it / 2 },
                                exit = fadeOut() + slideOutVertically { it / 2 }
                            ) {
                                Surface(
                                    shape = RoundedCornerShape(999.dp),
                                    color = NothingSurfaceElevated,
                                    border = BorderStroke(1.dp, NothingBorder),
                                    shadowElevation = 6.dp,
                                    modifier = Modifier
                                        .padding(
                                            start = 18.dp,
                                            end = 18.dp,
                                            top = 4.dp,
                                            bottom = if (renderNavBar) 6.dp else 12.dp
                                        )
                                        .then(if (!renderNavBar) Modifier.navigationBarsPadding() else Modifier)
                                        .minimumInteractiveComponentSize()
                                        .clip(RoundedCornerShape(999.dp))
                                        .clickable(role = Role.Button) {
                                            vpnViewModel.reopenFetchingScreen()
                                        }
                                ) {
                                    Row(
                                        modifier = Modifier.padding(start = 14.dp, end = 8.dp, top = 7.dp, bottom = 7.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                                    ) {
                                        HollowBlinkingDot(
                                            color = if (fetchingProgress.workingCount > 0) NothingMatrixGreen else HollowOrange
                                        )
                                        Text(
                                            text = "Fetching · ${fetchingProgress.workingCount} ready",
                                            fontFamily = JetBrainsMonoFamily,
                                            fontWeight = FontWeight.SemiBold,
                                            fontSize = 12.sp,
                                            color = HollowInk
                                        )
                                        Surface(
                                            shape = RoundedCornerShape(999.dp),
                                            color = HollowOrange
                                        ) {
                                            Text(
                                                text = "SHOW",
                                                fontFamily = JetBrainsMonoFamily,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 10.5.sp,
                                                letterSpacing = 0.8.sp,
                                                color = Color.White,
                                                modifier = Modifier.padding(horizontal = 9.dp, vertical = 3.dp)
                                            )
                                        }
                                    }
                                }
                            }

                            AnimatedVisibility(
                                visible = renderNavBar,
                                enter = slideInVertically(
                                    animationSpec = tween(260, easing = NothingMotion.FluidEase)
                                ) { it } + fadeIn(tween(180)),
                                exit = slideOutVertically(
                                    animationSpec = tween(220, easing = NothingMotion.FluidEase)
                                ) { it } + fadeOut(tween(150))
                            ) {
                                HollowBottomNavigationBar(
                                    selectedTab = selectedBottomTab,
                                    backgroundColor = navBackgroundColor,
                                    borderColor = navBorderColor,
                                    activeIndicatorColor = navIndicatorColor,
                                    activeContentColor = navActiveTextColor,
                                    inactiveContentColor = navInactiveTextColor,
                                    onSelectConnect = { vpnViewModel.navigateTo(VpnScreen.CONNECTED_HOME) },
                                    onSelectServers = { vpnViewModel.navigateTo(VpnScreen.SERVER_LIST) },
                                    onSelectSettings = { vpnViewModel.navigateTo(VpnScreen.SETTINGS) }
                                )
                            }
                        }
                    }
                }
            ) { innerPadding ->
                Box(modifier = Modifier.fillMaxSize()) {
                    AnimatedContent(
                        targetState = currentScreen,
                        transitionSpec = {
                            val ease = NothingMotion.FluidEase
                            val isSubscreenPush = targetState == VpnScreen.SETTINGS || targetState == VpnScreen.ADD_SERVER
                            val isSubscreenPop = initialState == VpnScreen.SETTINGS || initialState == VpnScreen.ADD_SERVER
                            val isModalFlow = targetState == VpnScreen.SPLASH_FETCHING ||
                                initialState == VpnScreen.SPLASH_FETCHING ||
                                targetState == VpnScreen.GETTING_STARTED ||
                                initialState == VpnScreen.GETTING_STARTED

                            when {
                                isModalFlow -> {
                                    (fadeIn(tween(260, easing = ease)) +
                                        slideInVertically(tween(280, easing = ease)) { it / 16 } +
                                        scaleIn(tween(280, easing = ease), initialScale = 0.97f))
                                        .togetherWith(
                                            fadeOut(tween(200, easing = ease)) +
                                                scaleOut(tween(200, easing = ease), targetScale = 0.98f)
                                        )
                                }
                                isSubscreenPush && !isSubscreenPop -> {
                                    (slideInHorizontally(tween(280, easing = ease)) { (it * 0.24f).toInt() } +
                                        fadeIn(tween(220, easing = ease)))
                                        .togetherWith(
                                            slideOutHorizontally(tween(260, easing = ease)) { -(it * 0.08f).toInt() } +
                                                scaleOut(tween(260, easing = ease), targetScale = 0.96f) +
                                                fadeOut(tween(180))
                                        )
                                }
                                isSubscreenPop && !isSubscreenPush -> {
                                    (slideInHorizontally(tween(280, easing = ease)) { -(it * 0.10f).toInt() } +
                                        scaleIn(tween(280, easing = ease), initialScale = 0.96f) +
                                        fadeIn(tween(220, easing = ease)))
                                        .togetherWith(
                                            slideOutHorizontally(tween(240, easing = ease)) { (it * 0.22f).toInt() } +
                                                fadeOut(tween(180))
                                        )
                                }
                                targetState == VpnScreen.SERVER_LIST && initialState == VpnScreen.CONNECTED_HOME -> {
                                    (slideInHorizontally(tween(260, easing = ease)) { (it * 0.12f).toInt() } +
                                        fadeIn(tween(220, easing = ease)))
                                        .togetherWith(
                                            slideOutHorizontally(tween(240, easing = ease)) { -(it * 0.10f).toInt() } +
                                                fadeOut(tween(170))
                                        )
                                }
                                targetState == VpnScreen.CONNECTED_HOME && initialState == VpnScreen.SERVER_LIST -> {
                                    (slideInHorizontally(tween(260, easing = ease)) { -(it * 0.12f).toInt() } +
                                        fadeIn(tween(220, easing = ease)))
                                        .togetherWith(
                                            slideOutHorizontally(tween(240, easing = ease)) { (it * 0.10f).toInt() } +
                                                fadeOut(tween(170))
                                        )
                                }
                                else -> {
                                    (fadeIn(tween(220, easing = ease)) +
                                        scaleIn(tween(240, easing = ease), initialScale = 0.98f))
                                        .togetherWith(fadeOut(tween(170)))
                                }
                            }
                        },
                        label = "vpn_screen_motion"
                    ) { screen ->
                        when (screen) {
                            VpnScreen.GETTING_STARTED -> {
                                GettingStartedScreen(
                                    currentStep = gettingStartedStep,
                                    settings = settings,
                                    serverCount = allServers.size,
                                    onSelectStep = vpnViewModel::setGettingStartedStep,
                                    onFetchSubscription = vpnViewModel::fetchSubscriptionInOnboarding,
                                    onImportConfig = vpnViewModel::importConfigInOnboarding,
                                    onSkip = {
                                        vpnViewModel.completeOnboarding(
                                            targetScreen = VpnScreen.CONNECTED_HOME,
                                            autoConnectIfReady = false
                                        )
                                    },
                                    onFinish = { autoConnect ->
                                        vpnViewModel.completeOnboarding(
                                            targetScreen = VpnScreen.CONNECTED_HOME,
                                            autoConnectIfReady = autoConnect
                                        )
                                    },
                                    onShowMessage = vpnViewModel::showMessage
                                )
                            }

                            VpnScreen.SPLASH_FETCHING -> {
                                SplashFetchingScreen(
                                    statusSubtitle = fetchingStatus,
                                    progress = fetchingProgress,
                                    onCancelOrSkip = vpnViewModel::hideFetchingScreen
                                )
                            }

                            VpnScreen.SERVER_LIST -> {
                                CountryServerListScreen(
                                    servers = filteredServers,
                                    selectedFilter = selectedFilter,
                                    activeProtocolPreference = settings.protocolPreference,
                                    bottomContentPadding = innerPadding.calculateBottomPadding(),
                                    onSelectFilter = vpnViewModel::setFilter,
                                    onResetProtocolFilter = {
                                        vpnViewModel.updateSettings { it.copy(protocolPreference = "Auto (V2Ray)") }
                                    },
                                    onSelectServer = { server ->
                                        runWithNotificationPermission {
                                            val prepareIntent = try {
                                                if (settings.tunMode) VpnService.prepare(context) else null
                                            } catch (_: Exception) {
                                                null
                                            }
                                            if (prepareIntent != null && context is Activity) {
                                                vpnViewModel.selectServer(server, openConnectedScreen = true)
                                                vpnPermissionLauncher.launch(prepareIntent)
                                            } else {
                                                vpnViewModel.selectAndConnectServer(
                                                    server = server,
                                                    openConnectedScreen = true
                                                )
                                            }
                                        }
                                    },
                                    onRefresh = {
                                        vpnViewModel.runFletcherAutoFetchAndPingSweep(
                                            returnScreen = VpnScreen.SERVER_LIST
                                        )
                                    },
                                    onOpenSettings = {
                                        vpnViewModel.navigateTo(VpnScreen.SETTINGS)
                                    },
                                    onOpenAddServer = {
                                        vpnViewModel.navigateTo(VpnScreen.ADD_SERVER)
                                    },
                                    onOpenAddManualServer = {
                                        vpnViewModel.setAddServerTab(AddServerTab.MANUAL)
                                        vpnViewModel.navigateTo(VpnScreen.ADD_SERVER)
                                    },
                                    onPingServer = vpnViewModel::pingSingleServer,
                                    onRepingAll = vpnViewModel::repingAllSavedServers,
                                    onShowMessage = vpnViewModel::showMessage,
                                    onShareQr = vpnViewModel::openQrShare,
                                    onInspectJson = vpnViewModel::openJsonInspector,
                                    onDeleteServer = vpnViewModel::deleteServer,
                                    onDeleteServersByIds = vpnViewModel::deleteServersByIds,
                                    onDeleteDuplicates = vpnViewModel::deleteDuplicateServers,
                                    onDeleteInvalid = vpnViewModel::deleteInvalidServers,
                                    onExportAllServers = exportServersToClipboard,
                                    onDeleteAllServers = vpnViewModel::clearServerCacheAndReset,
                                    onBack = {
                                        if (!vpnViewModel.navigateBack()) {
                                            vpnViewModel.navigateTo(VpnScreen.CONNECTED_HOME)
                                        }
                                    }
                                )
                            }

                            VpnScreen.CONNECTED_HOME -> {
                                ConnectedHomeScreen(
                                    selectedServer = selectedServer,
                                    telemetry = telemetry,
                                    tunModeEnabled = settings.tunMode,
                                    bottomContentPadding = innerPadding.calculateBottomPadding(),
                                    onToggleConnect = requestOrToggleVpn,
                                    onOpenServerList = {
                                        vpnViewModel.navigateTo(VpnScreen.SERVER_LIST)
                                    },
                                    onOpenSettings = {
                                        vpnViewModel.navigateTo(VpnScreen.SETTINGS)
                                    },
                                    onOpenAddServer = {
                                        vpnViewModel.navigateTo(VpnScreen.ADD_SERVER)
                                    },
                                    onRefreshRealIp = vpnViewModel::refreshRealPublicIp
                                )
                            }

                            VpnScreen.SETTINGS -> {
                                SettingsScreen(
                                    settings = settings,
                                    xrayLogs = xrayLogs,
                                    serverCount = allServers.size,
                                    appUpdateState = appUpdateState,
                                    bottomContentPadding = innerPadding.calculateBottomPadding(),
                                    onUpdateSettings = vpnViewModel::updateSettings,
                                    onOpenAddSubscription = {
                                        vpnViewModel.navigateTo(VpnScreen.ADD_SERVER)
                                    },
                                    onOpenGettingStarted = vpnViewModel::reopenGettingStarted,
                                    onClearXrayLogs = vpnViewModel::clearXrayLogs,
                                    onExportServers = exportServersToClipboard,
                                    onCheckForUpdates = vpnViewModel::checkForAppUpdates,
                                    onShowMessage = vpnViewModel::showMessage,
                                    onBack = {
                                        if (!vpnViewModel.navigateBack()) {
                                            vpnViewModel.navigateTo(VpnScreen.CONNECTED_HOME)
                                        }
                                    }
                                )
                            }

                            VpnScreen.ADD_SERVER -> {
                                AddServerScreen(
                                    activeTab = activeAddTab,
                                    settings = settings,
                                    bottomContentPadding = innerPadding.calculateBottomPadding(),
                                    onSelectTab = vpnViewModel::setAddServerTab,
                                    onAddAndFetchSubscription = vpnViewModel::addAndFetchSubscriptionUrl,
                                    onImportClipboardText = vpnViewModel::importFromTextOrClipboard,
                                    onAddManualServer = vpnViewModel::addManualCustomServer,
                                    onUpdateSettings = vpnViewModel::updateSettings,
                                    onBack = {
                                        if (!vpnViewModel.navigateBack()) {
                                            vpnViewModel.navigateTo(VpnScreen.SERVER_LIST)
                                        }
                                    }
                                )
                            }
                        }
                    }

                    inspectingJsonServer?.let { server ->
                        RawXrayJsonEditorDialog(
                            server = server,
                            onDismiss = vpnViewModel::closeJsonInspector,
                            onSaveJson = { updatedJson ->
                                vpnViewModel.saveEditedServerJson(server, updatedJson)
                            }
                        )
                    }

                    sharingQrServer?.let { server ->
                        ServerQrShareDialog(
                            server = server,
                            onCopyPayload = { payload ->
                                if (payload.isNotBlank()) {
                                    val cm = context.getSystemService(Context.CLIPBOARD_SERVICE) as? android.content.ClipboardManager
                                    cm?.setPrimaryClip(android.content.ClipData.newPlainText("Hollow VPN Server", payload))
                                    vpnViewModel.showMessage("Copied server config for ${server.countryName}")
                                    vpnViewModel.closeQrShare()
                                }
                            },
                            onDismiss = vpnViewModel::closeQrShare
                        )
                    }
                }
            }
        }
    }
    }
}
