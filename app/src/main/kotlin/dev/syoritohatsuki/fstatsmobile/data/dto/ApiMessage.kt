package dev.syoritohatsuki.fstatsmobile.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class ApiMessage(
    val code: Int,
    val message: String
)