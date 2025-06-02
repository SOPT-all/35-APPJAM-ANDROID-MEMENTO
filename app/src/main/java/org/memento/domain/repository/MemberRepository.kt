package org.memento.domain.repository

import org.memento.domain.entity.Login
import org.memento.domain.entity.LoginInfo

interface MemberRepository {
    suspend fun postLogin(login: Login): Result<LoginInfo>

    suspend fun deleteMember(): Result<Unit>
}
