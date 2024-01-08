package dev.syoritohatsuki.fstatsmobile.data.api

import dev.syoritohatsuki.fstatsmobile.data.dto.Metric
import dev.syoritohatsuki.fstatsmobile.data.dto.Project
import dev.syoritohatsuki.fstatsmobile.data.dto.User

interface FStatsApi {
    suspend fun login(username: String, password: String): Map<String, String>
    suspend fun register(username: String, password: String): Boolean
    suspend fun getUsers(): Array<User>
    suspend fun getProjects(): Array<Project>
    suspend fun getProjectByUserId(userId: Int): Array<Project>
    suspend fun getMetrics(projectId: Int): Array<Metric>
    suspend fun getMetricsCount(projectId: Int): Map<String, Map<String, Int>>
}