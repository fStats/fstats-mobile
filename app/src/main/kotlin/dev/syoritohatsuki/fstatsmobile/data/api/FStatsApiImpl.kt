package dev.syoritohatsuki.fstatsmobile.data.api

import dev.syoritohatsuki.fstatsmobile.data.dto.Metric
import dev.syoritohatsuki.fstatsmobile.data.dto.Project
import dev.syoritohatsuki.fstatsmobile.data.dto.User
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post

class FStatsApiImpl(private val httpClient: HttpClient) : FStatsApi {

    override suspend fun login(username: String, password: String): Map<String, String> =
        httpClient.post("auth/login").body()

    override suspend fun register(username: String, password: String): Boolean =
        httpClient.post("auth/registration").body()

    override suspend fun getUsers(): List<User> =
        httpClient.get("users").body()

    override suspend fun getProjects(): List<Project> =
        httpClient.get("projects").body()

    override suspend fun getProjectByUserId(userId: Int): List<Project> =
        httpClient.get("users/$userId/projects").body()

    override suspend fun getMetrics(projectId: Int): List<Metric> =
        httpClient.get("metrics/$projectId").body()

    override suspend fun getMetricsCount(projectId: Int): Map<String, Map<String, Int>> =
        httpClient.get("metrics/$projectId/count").body()
}