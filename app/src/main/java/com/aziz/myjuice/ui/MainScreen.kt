package com.aziz.myjuice.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.aziz.myjuice.ui.navigation.NavigationItem
import com.aziz.myjuice.ui.navigation.Screen
import com.aziz.myjuice.ui.screen.profile.ProfileScreen
import com.aziz.myjuice.ui.screen.detail.DetailScreen
import com.aziz.myjuice.ui.screen.favorite.FavoriteScreen
import com.aziz.myjuice.ui.screen.home.HomeScreen
import com.aziz.myjuice.ui.screen.splashcreen.SplashScreen

@Preview
@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        scaffoldState = scaffoldState,
        snackbarHost = {
            SnackbarHost(hostState = it) { data ->
                Snackbar(snackbarData = data, shape = RoundedCornerShape(8.dp))
            }
        },
        topBar = {
            if (currentRoute != Screen.Detail.route && currentRoute != Screen.Home.route) {
                TopBar(navController, currentRoute)
            }
        },
        modifier = modifier
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "splash_screen",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("splash_screen") {
                SplashScreen(navController)
            }
            composable(Screen.Home.route) {
                HomeScreen(navController)
            }
            composable(
                route = Screen.Detail.route,
                arguments = listOf(
                    navArgument("myjuiceId") { type = NavType.IntType }
                )
            ) {
                val myjuiceId = it.arguments?.getInt("myjuiceId") ?: 0
                DetailScreen(myjuiceId, navController, scaffoldState)
            }
            composable(Screen.Favorite.route) {
                FavoriteScreen(navController)
            }
            composable(Screen.Profile.route) {
                ProfileScreen()
            }
        }
    }
}

@Composable
fun TopBar(
    navController: NavController,
    currentRoute: String?,
) {
    val navigationItems = listOf(
        NavigationItem(
            title = "Home",
            icon = Icons.Rounded.Home,
            screen = Screen.Home
        ),
        NavigationItem(
            title = "Favorite",
            icon = Icons.Rounded.Favorite,
            screen = Screen.Favorite
        ),
        NavigationItem(
            title = "Profile",
            icon = Icons.Rounded.Person,
            screen = Screen.Profile
        ),
    )

    TopAppBar(
        backgroundColor = Color.Yellow,
        elevation = AppBarDefaults.TopAppBarElevation
    ) {
        navigationItems.forEach { item ->
            IconButton(
                onClick = {
                    navController.navigate(item.screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = item.title,
                    tint = if (currentRoute == item.screen.route) {
                        MaterialTheme.colors.primaryVariant
                    } else {
                        Color.Black
                    }
                )
            }
        }
    }
}