package com.example.mybottomnavigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mybottomnavigation.ui.DetailEvent
import com.example.mybottomnavigation.ui.DetailScreen
import com.example.mybottomnavigation.ui.HomeEvent
import com.example.mybottomnavigation.ui.HomeScreen
import com.example.mybottomnavigation.ui.LoginScreen
import com.example.mybottomnavigation.ui.ProfileEvent
import com.example.mybottomnavigation.ui.ProfileScreen
import com.example.mybottomnavigation.ui.SplashEvent
import com.example.mybottomnavigation.ui.SplashScreen
import com.example.mybottomnavigation.ui.theme.MyBottomNavigationTheme
import kotlinx.coroutines.delay

enum class Routes(val route: String) {
    Home("home"),
    Profile("profile"),
    Splash("splash"),
    Login("login"),
    Detail("detail"),
}

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var uiState: MainActivityUiState by mutableStateOf(MainActivityUiState.SplashScreen)

        setContent {

            LaunchedEffect(key1 = Unit, block = {
                delay(2000L)
                uiState = MainActivityUiState.HomeSome
            })

            val startRoute by remember {
                derivedStateOf {
                    when (uiState) {
                        MainActivityUiState.HomeSome -> Routes.Home.route
                        MainActivityUiState.LoginScreen -> Routes.Login.route
                        MainActivityUiState.SplashScreen -> Routes.Splash.route
                    }
                }
            }

            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination

            var bottomBarState by remember { mutableStateOf(false) }
            val showBottomBar by remember { derivedStateOf { bottomBarState } }

            bottomBarState = when (currentDestination?.route) {
                Routes.Home.route -> true
                Routes.Profile.route -> true
                else -> false
            }

            val items = listOf(
                Screen.Home,
                Screen.Profile
            )

            MyBottomNavigationTheme {

                Scaffold(
                    bottomBar = {
                        AnimatedVisibility(
                            visible = showBottomBar,
                            enter = slideInVertically(initialOffsetY = { it }),
                            exit = slideOutVertically(targetOffsetY = { it })
                        ) {
                            NavigationBar {
                                items.forEach { screen ->
                                    NavigationBarItem(
                                        icon = {
                                            Icon(
                                                painter = painterResource(id = screen.iconId),
                                                contentDescription = screen.label
                                            )
                                        },
                                        label = { Text(screen.label) },
                                        selected = currentDestination?.route == screen.route,
                                        onClick = {
                                            navController.navigate(screen.route) {
                                                // Pop up to the start destination of the graph to
                                                // avoid building up a large stack of destinations
                                                // on the back stack as users select items
                                                popUpTo(navController.graph.findStartDestination().id) {
                                                    saveState = true
                                                }
                                                // Avoid multiple copies of the same destination when
                                                // reselecting the same item
                                                launchSingleTop = true
                                                // Restore state when reselecting a previously selected item
                                                restoreState = true
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    }
                ) { innerPadding ->

                    CompositionLocalProvider(
                        LocalEntryPadding provides innerPadding
                    ) {
                        NavHost(
                            navController = navController, startDestination = startRoute
                        ) {
                            composable(Routes.Splash.route) {
                                SplashScreen(
                                    onEvent = { event ->
                                        when (event) {
                                            is SplashEvent.GoToLogin -> {
                                                navController.navigate(Routes.Login.route)
                                            }

                                            is SplashEvent.GoToHome -> {
                                                navController.navigate(Routes.Home.route)
                                            }
                                        }
                                    }
                                )
                            }
                            composable(Routes.Profile.route) {
                                ProfileScreen(
                                    onEvent = { event ->
                                        when (event) {
                                            is ProfileEvent.Logout -> {
                                                uiState = MainActivityUiState.LoginScreen
                                                // navController.navigate(Routes.Login.route)
                                            }
                                        }
                                    }
                                )
                            }
                            composable(Routes.Home.route) {
                                HomeScreen(
                                    onEvent = { event ->
                                        when (event) {
                                            is HomeEvent.GoToDetail -> {
                                                navController.navigate(Routes.Detail.route)
                                            }
                                        }
                                    }
                                )
                            }
                            composable(Routes.Login.route) { LoginScreen() }
                            composable(Routes.Detail.route) {
                                DetailScreen(
                                    onEvent = { event ->
                                        when (event) {
                                            is DetailEvent.BackToHome -> navController.navigateUp()
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

val LocalEntryPadding = compositionLocalOf {
    PaddingValues()
}

sealed interface MainActivityUiState {
    object SplashScreen : MainActivityUiState
    object LoginScreen : MainActivityUiState
    object HomeSome : MainActivityUiState
}

sealed class Screen(val route: String, val label: String, @DrawableRes val iconId: Int) {
    object Home : Screen("home", "Home", R.drawable.baseline_home_24)
    object Profile : Screen("profile", "Profile", R.drawable.baseline_account_circle_24)
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyBottomNavigationTheme {
        Greeting("Android")
    }
}