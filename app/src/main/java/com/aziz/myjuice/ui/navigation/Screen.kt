package com.aziz.myjuice.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Splash : Screen("splash")
    object Favorite : Screen("favorite")
    object Profile : Screen("profile")
    object Detail : Screen("home/{myjuiceId}") {
        fun createRoute(myjuiceId: Int) = "home/$myjuiceId"
    }
}
