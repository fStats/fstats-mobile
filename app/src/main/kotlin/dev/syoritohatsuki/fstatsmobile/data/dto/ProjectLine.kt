package dev.syoritohatsuki.fstatsmobile.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class MetricLine(
    val timestamps: List<Long> = emptyList(),
    val counts: List<Int> = emptyList(),
)