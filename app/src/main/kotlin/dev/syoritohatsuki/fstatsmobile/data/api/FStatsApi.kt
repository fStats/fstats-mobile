package dev.syoritohatsuki.fstatsmobile.data.api

import dev.syoritohatsuki.fstatsmobile.data.dto.ApiMessage
import dev.syoritohatsuki.fstatsmobile.data.dto.AuthToken
import dev.syoritohatsuki.fstatsmobile.data.dto.MetricLine
import dev.syoritohatsuki.fstatsmobile.data.dto.MetricPie
import dev.syoritohatsuki.fstatsmobile.data.dto.Project
import dev.syoritohatsuki.fstatsmobile.data.dto.User

interface FStatsApi {
    suspend fun login(username: String, password: String): AuthToken
    suspend fun getUsers(): List<User>
    suspend fun getProjects(): List<Project>
    suspend fun getProjectByUserId(userId: Int): List<Project>
    suspend fun createProjectById(projectName: String, token: String): ApiMessage
    suspend fun deleteProjectById(projectId: Int, token: String): ApiMessage
    suspend fun getMetricsLine(projectId: Int): MetricLine
    suspend fun getMetricsCount(projectId: Int): MetricPie
    suspend fun getUserFavorites(userId: Int, token: String): List<Project>
}