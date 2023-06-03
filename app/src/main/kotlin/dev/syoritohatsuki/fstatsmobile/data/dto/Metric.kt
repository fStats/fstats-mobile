package dev.syoritohatsuki.fstatsmobile.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class Metric(
    val timestampSeconds: Long? = null,
    val projectId: Int? = null,
    val minecraftVersion: String = "unknown",
    val isOnlineMode: Boolean,
    val modVersion: String = "",
    val os: Char,
    val location: String = "unknown"
)