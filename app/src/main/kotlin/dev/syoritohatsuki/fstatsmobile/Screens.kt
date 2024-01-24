package dev.syoritohatsuki.fstatsmobile

sealed class Screens(val route: String, val argument: String = "") {
    object Favorite : Screens("favorite")
    object Projects : Screens("projects")
    object Profile : Screens("profile")
    object Project : Screens("project/{projectId}", "projectId")
}