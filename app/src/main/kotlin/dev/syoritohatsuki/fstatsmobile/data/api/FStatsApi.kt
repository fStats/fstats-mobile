package dev.syoritohatsuki.fstatsmobile.data.api

import dev.syoritohatsuki.fstatsmobile.data.dto.Metric
import dev.syoritohatsuki.fstatsmobile.data.dto.Project
import dev.syoritohatsuki.fstatsmobile.data.dto.ProjectLine
import dev.syoritohatsuki.fstatsmobile.data.dto.ProjectPie
import dev.syoritohatsuki.fstatsmobile.data.dto.User

interface FStatsApi {
    suspend fun login(username: String, password: String): Map<String, String>
    suspend fun register(username: String, password: String): Boolean
    suspend fun getUsers(): List<User>
    suspend fun getProjects(): List<Project>
    suspend fun getProjectByUserId(userId: Int): List<Project>
    suspend fun getMetrics(projectId: Int): List<Metric>
    suspend fun getMetricsLine(projectId: Int): ProjectLine
    suspend fun getMetricsCount(projectId: Int): ProjectPie
}