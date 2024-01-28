package dev.syoritohatsuki.fstatsmobile.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

typealias MetricPie = Map<String, Map<String?, Int>>

@Serializable
data class ProjectPie(
    val project: Project,
    @SerialName("metric_pie")
    val metricPie: MetricPie = emptyMap()
)