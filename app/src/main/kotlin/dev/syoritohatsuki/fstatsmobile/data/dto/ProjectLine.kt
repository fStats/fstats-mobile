package dev.syoritohatsuki.fstatsmobile.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

typealias MetricLine = Map<String, Int>

@Serializable
data class ProjectLine(
    val project: Project = Project(),
    @SerialName("metric_line")
    val metricLine: MetricLine = emptyMap()
)