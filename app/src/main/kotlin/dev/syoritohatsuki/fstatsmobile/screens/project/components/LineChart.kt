package dev.syoritohatsuki.fstatsmobile.screens.project.components

import android.util.Log
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.data.Set
import dev.syoritohatsuki.fstatsmobile.data.dto.ProjectLine
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

@Composable
fun LineChart(projectLine: ProjectLine) {
    if (projectLine.metricLine.isEmpty()) return
    AndroidView(
        factory = { context ->
            AnyChartView(context).apply {
                Log.e("LineChart: ", "1")
                setBackgroundColor("#1E1E1E")
                AnyChart.line().let { cartesian ->
                    Log.e("LineChart: ", "2")
                    cartesian.background(false)
                    cartesian.xAxis(0).ticks(false)
                        .labels().width(170).hAlign("center").format(
                            "function() {\n" +
                                    "return this.value.split(' ')[0];\n" +
                                    "}"
                        )
                    cartesian.line(Set.instantiate().let { set ->
                        Log.e("LineChart: ", "3")
                        Log.e("LineChart: ", projectLine.metricLine.size.toString())
                        set.data(projectLine.metricLine.map { entry ->
                            Log.e("LineChart: ", "4")
                            Log.d("LineChart: ", "${entry.key} | ${entry.value}")
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
                        Log.e("LineChart: ", "5")
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
}