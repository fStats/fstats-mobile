package dev.syoritohatsuki.fstatsmobile.screens.project.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.data.Set
import dev.syoritohatsuki.fstatsmobile.data.dto.MetricLine
import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@Composable
fun LineChart(merticLine: MetricLine) {

    val decodedMetricLine = remember { mutableListOf<TimelineData>() }

    LaunchedEffect(merticLine) {
        decodedMetricLine.clear()

        var prevTimestamp = 0L
        var prevCount = 0

        decodedMetricLine.addAll(merticLine.timestamps.mapIndexed { index, deltaTimestamp ->
            prevTimestamp += deltaTimestamp
            prevCount += merticLine.counts[index]
            TimelineData(x = prevTimestamp * 1000, y = prevCount)
        })
    }

    if (decodedMetricLine.isNotEmpty()) AndroidView(
        factory = { context ->
            AnyChartView(context).apply {
                setBackgroundColor("#1E1E1E")
                AnyChart.line().let { cartesian ->
                    cartesian.background(false)
                    cartesian.xAxis(0).ticks(false).labels().width(170).hAlign("center").format(
                        "function() {\n" + "return this.value.split(' ')[0];\n" + "}"
                        )
                    cartesian.line(Set.instantiate().let { set ->
                        set.data(decodedMetricLine.map { entry ->
                            ValueDataEntry(
                                OffsetDateTime.ofInstant(
                                    Instant.ofEpochMilli(entry.x), ZoneOffset.UTC
                                ).format(
                                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                                ), entry.y
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
}

data class TimelineData(
    val x: Long, val y: Int
)