package org.memento.domain.repository

import org.memento.domain.entity.Login
import org.memento.domain.entity.LoginInfo

interface LoginRepository {
    suspend fun postLogin(login: Login): Result<LoginInfo>
}
