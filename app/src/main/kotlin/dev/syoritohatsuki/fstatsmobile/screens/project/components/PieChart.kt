package dev.syoritohatsuki.fstatsmobile.screens.project.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.ValueDataEntry

@Composable
fun PieChart(values: Map<String?, Int>, paddingValues: PaddingValues) {
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
                        ValueDataEntry(entry.key?.nameParse(), entry.value)
                    })
                    setChart(pie)
                }
            }
        },
        modifier = Modifier
            .fillMaxHeight()
            .padding(paddingValues)
            .padding(8.dp, 8.dp, 8.dp, 0.dp)
    )
}

private fun String.nameParse(): String {
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