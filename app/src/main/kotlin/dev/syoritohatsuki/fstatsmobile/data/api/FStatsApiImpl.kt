package dev.syoritohatsuki.fstatsmobile.data.api

import dev.syoritohatsuki.fstatsmobile.data.dto.ApiMessage
import dev.syoritohatsuki.fstatsmobile.data.dto.AuthToken
import dev.syoritohatsuki.fstatsmobile.data.dto.Metric
import dev.syoritohatsuki.fstatsmobile.data.dto.Project
import dev.syoritohatsuki.fstatsmobile.data.dto.ProjectLine
import dev.syoritohatsuki.fstatsmobile.data.dto.ProjectPie
import dev.syoritohatsuki.fstatsmobile.data.dto.User
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpHeaders

class FStatsApiImpl(private val httpClient: HttpClient) : FStatsApi {
    override suspend fun login(username: String, password: String): AuthToken =
        httpClient.post("auth/login") {
            setBody(User(username = username, password = password))
        }.body()

    override suspend fun getUsers(): List<User> =
        httpClient.get("users").body()

    override suspend fun getProjects(): List<Project> =
        httpClient.get("projects").body()

    override suspend fun getProjectByUserId(userId: Int): List<Project> =
        httpClient.get("users/$userId/projects").body()

    override suspend fun createProjectById(projectName: String, token: String): ApiMessage =
        httpClient.post("projects") {
            header(HttpHeaders.Authorization, "Bearer $token")
            setBody(Project(name = projectName))
        }.body()

    override suspend fun deleteProjectById(projectId: Int, token: String): ApiMessage =
        httpClient.delete("projects/${projectId}") {
            header(HttpHeaders.Authorization, "Bearer $token")
        }.body()

    override suspend fun getMetrics(projectId: Int): List<Metric> =
        httpClient.get("metrics/$projectId").body()

    override suspend fun getMetricsLine(projectId: Int): ProjectLine =
        httpClient.get("metrics/${projectId}/line").body()

    override suspend fun getMetricsCount(projectId: Int): ProjectPie =
        httpClient.get("metrics/$projectId/pie").body()

    override suspend fun getUserFavorites(userId: Int, token: String): List<Project> =
        httpClient.get("users/${userId}/favorite") {
            header(HttpHeaders.Authorization, "Bearer $token")
        }.body()
}