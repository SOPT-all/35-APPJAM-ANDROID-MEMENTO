package org.memento.data.repositoryimpl

import org.memento.data.datastore.LoginDataSource
import org.memento.domain.entity.Login
import org.memento.domain.entity.UserInfo
import org.memento.domain.repository.LoginRepository
import javax.inject.Inject

class LoginRepositoryImpl
@Inject
constructor(
    private val loginDataSource: LoginDataSource,
) : LoginRepository
{
    override suspend fun postLogin(login: Login): Result<UserInfo>
    =
        runCatching {
           // loginDataSource.postLogin(requestLoginDto = login.toData())
        }
}
