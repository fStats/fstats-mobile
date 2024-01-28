package dev.syoritohatsuki.fstatsmobile.screens.project

import android.widget.ProgressBar
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.data.Set
import dev.syoritohatsuki.fstatsmobile.Screens
import dev.syoritohatsuki.fstatsmobile.screens.project.viewmodel.ProjectViewModel
import dev.syoritohatsuki.fstatsmobile.screens.project.viewmodel.ProjectViewModelFactory
import kotlinx.coroutines.launch
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable

fun ProjectScreen(navController: NavController, navBackStackEntry: NavBackStackEntry) {
    val scope = rememberCoroutineScope()

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


    if (projectPie == null || projectLine == null) {
        AndroidView(factory = {
            ProgressBar(it)
        }, modifier = Modifier.fillMaxSize())
        return
    }

    Scaffold(
        topBar = {
            AndroidView(
                factory = { context ->
                    AnyChartView(context).apply {
                        setBackgroundColor("#1E1E1E")
                        AnyChart.line().let { cartesian ->
                            cartesian.background(false)
                            cartesian.xAxis(0).ticks(false)
                                .labels().width(170).hAlign("center").format(
                                    "function() {\n" +
                                            "return this.value.split(' ')[0];\n" +
                                            "}"
                                )
                            cartesian.line(Set.instantiate().let { set ->
                                set.data(projectLine!!.metricLine.map { entry ->
                                    ValueDataEntry(
                                        OffsetDateTime.parse(
                                            entry.key,
                                            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssX")
                                        ).format(
                                            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                                        ), entry.value
                                    )
                                })
                                set.mapAs("{x: 'x', value: 'value'}")
                            }).let { line ->
                                line.color("#3498db")
                                line.name("Servers: ")
                                line.tooltip().enabled(true)
                                setChart(cartesian)
                            }
                        }
                    }
                }, modifier = Modifier
                    .height(160.dp)
                    .padding(8.dp, 8.dp, 8.dp, 0.dp)
            )
        },
        content = {
            HorizontalPager(state = pagerState) { page ->
                type.value = projectPie!!.metricPie.keys.elementAt(page)
                projectPie!!.metricPie[type.value]?.let { values ->
                    AndroidView(
                        factory = { context ->
                            AnyChartView(context).apply {
                                setBackgroundColor("#1E1E1E")
                                AnyChart.pie().let { pie ->
                                    pie.background("#1E1E1E")
                                    pie.palette(
                                        arrayOf(
                                            "#e74c3c",
                                            "#2ecc71",
                                            "#3498db",
                                            "#e67e22",
                                            "#f1c40f",
                                        )
                                    )
                                    pie.data(values.map { entry ->
                                        ValueDataEntry(
                                            entry.key?.nameParse(),
                                            entry.value
                                        )
                                    })
                                    setChart(pie)
                                }
                            }
                        }, modifier = Modifier
                            .fillMaxHeight()
                            .padding(it)
                            .padding(8.dp, 8.dp, 8.dp, 0.dp)
                    )
                }
            }
        },
        bottomBar = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
                    scope.launch {
                        pagerState.scrollToPage(pagerState.currentPage - 1)
                    }
                }, enabled = pagerState.currentPage > 0) {
                    Icon(Icons.Filled.KeyboardArrowLeft, "")
                }
                Text(
                    text = type.value.typeToName(),
                    modifier = Modifier.padding(bottom = 2.dp),
                    textAlign = TextAlign.Center
                )
                IconButton(onClick = {
                    scope.launch {
                        pagerState.scrollToPage(pagerState.currentPage + 1)
                    }
                }, enabled = pagerState.currentPage < pagerState.pageCount - 1) {
                    Icon(Icons.Filled.KeyboardArrowRight, "")
                }
            }
        }, modifier = Modifier.fillMaxSize()
    )
}

fun String.typeToName(): String {
    return when (this) {
        "minecraft_version" -> "Minecraft Version"
        "online_mode" -> "Online Mode"
        "mod_version" -> "Mod Version"
        "os" -> "Operation System"
        "location" -> "Location"
        "fabric_api_version" -> "Fabric API"
        else -> this
    }
}

fun String.nameParse(): String {
    return when (this) {
        "true" -> "Online"
        "false" -> "Offline"
        "w" -> "Windows"
        "l" -> "Linux"
        "m" -> "Mac"
        "o" -> "Other"
        else -> this
    }
}