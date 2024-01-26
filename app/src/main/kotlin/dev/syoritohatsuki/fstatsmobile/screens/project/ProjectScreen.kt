package dev.syoritohatsuki.fstatsmobile.screens.project

import android.widget.ProgressBar
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
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
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import kotlin.random.Random


val RANDOM = Random(System.currentTimeMillis())

@Composable
fun ProjectScreen(navController: NavController, navBackStackEntry: NavBackStackEntry) {

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

    LazyColumn(content = {
        item {
            AndroidView(factory = { context ->
                AnyChartView(context).apply {
                    AnyChart.line().let { cartesian ->
                        cartesian.xAxis(0).ticks(false)
                            .labels().width(170).hAlign("center").format(
                                "function() {\n" +
                                        "return this.value.split(' ')[0];\n" +
                                        "}"
                            )
                        cartesian.line(Set.instantiate().let {
                            it.data(projectLine!!.metricLine.map { entry ->
                                ValueDataEntry(
                                    OffsetDateTime.parse(
                                        entry.key,
                                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssX")
                                    ).format(
                                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                                    ), entry.value
                                )
                            })
                            it.mapAs("{x: 'x', value: 'value'}")
                        }).let {
                            it.color("#3498db")
                            it.name("Servers: ")
                            it.tooltip().enabled(true)
                            setChart(cartesian)
                        }
                    }
                }
            }, modifier = Modifier.height(150.dp))
        }
        projectPie!!.metricPie.forEach { (type, values) ->
            item {
                AndroidView(factory = { context ->
                    AnyChartView(context).apply {
                        AnyChart.pie().let { pie ->
                            pie.palette(
                                arrayOf(
                                    "#e74c3c",
                                    "#2ecc71",
                                    "#3498db",
                                    "#e67e22",
                                    "#f1c40f",
                                )
                            )
                            pie.title(type.typeToName())
                            pie.data(values.map { entry ->
                                ValueDataEntry(
                                    entry.key?.nameParse(),
                                    entry.value
                                )
                            })
                            setChart(pie)
                        }
                    }
                }, modifier = Modifier.height(250.dp))
            }
        }
    }, modifier = Modifier.fillMaxSize())
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