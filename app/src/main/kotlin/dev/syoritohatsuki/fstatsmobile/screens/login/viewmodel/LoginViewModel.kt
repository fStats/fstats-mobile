package dev.syoritohatsuki.fstatsmobile.screens.login.viewmodel

import androidx.lifecycle.ViewModel
import dev.syoritohatsuki.fstatsmobile.data.api.FStatsApi
import kotlinx.coroutines.flow.flow
import org.koin.java.KoinJavaComponent

class LoginViewModel : ViewModel() {
    private val api by KoinJavaComponent.inject<FStatsApi>(FStatsApi::class.java)

    fun login(username: String, password: String) = flow {
        val login = api.login(username, password)
        emit(login.token)
    }
}