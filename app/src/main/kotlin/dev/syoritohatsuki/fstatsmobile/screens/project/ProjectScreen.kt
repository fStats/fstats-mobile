package dev.syoritohatsuki.fstatsmobile.screens.project

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import dev.syoritohatsuki.fstatsmobile.Screens
import dev.syoritohatsuki.fstatsmobile.screens.project.components.LineChart
import dev.syoritohatsuki.fstatsmobile.screens.project.components.PagerNavigator
import dev.syoritohatsuki.fstatsmobile.screens.project.components.PieChart
import dev.syoritohatsuki.fstatsmobile.screens.project.viewmodel.ProjectViewModel
import dev.syoritohatsuki.fstatsmobile.screens.project.viewmodel.ProjectViewModelFactory

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable

fun ProjectScreen(navController: NavController, navBackStackEntry: NavBackStackEntry) {


    val pagerState = rememberPagerState(pageCount = { 6 })

    val type = remember { mutableStateOf("") }

    val projectId: Int? = navBackStackEntry.arguments?.getInt(Screens.Project.argument)

    if (projectId == null) {
        navController.navigateUp()
        return
    }

    val projectViewModel: ProjectViewModel = viewModel(factory = ProjectViewModelFactory(projectId))

    val projectPie by projectViewModel.projectPie.collectAsState()
    val projectLine by projectViewModel.projectLine.collectAsState()

    Scaffold(
        topBar = {
            LineChart(projectLine)
        },
        content = {
            HorizontalPager(state = pagerState) { page ->
                type.value = projectPie.metricPie.keys.elementAtOrElse(page) { "" }
                projectPie.metricPie[type.value]?.let { values ->
                    PieChart(values, it)
                }
            }
        },
        bottomBar = {
            PagerNavigator(type, pagerState)
        }, modifier = Modifier.fillMaxSize()
    )
}