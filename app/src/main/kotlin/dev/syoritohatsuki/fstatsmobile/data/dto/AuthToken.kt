package dev.syoritohatsuki.fstatsmobile.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class AuthToken(
    val token: String
)