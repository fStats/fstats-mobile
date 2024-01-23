package dev.syoritohatsuki.fstatsmobile

sealed class Screens(val route: String) {
    object Favorite : Screens("favorite")
    object Projects : Screens("projects")
    object Profile : Screens("profile")
}