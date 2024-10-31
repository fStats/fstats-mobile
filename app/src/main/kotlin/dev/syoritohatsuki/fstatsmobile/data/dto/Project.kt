package dev.syoritohatsuki.fstatsmobile.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Project(
    val id: Int = -1,
    val name: String = "",
    @SerialName("is_visible")
    val isVisible: Boolean = true,
    val owner: User = User()
)