package org.memento.data.repositoryimpl

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await
import org.memento.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl
    @Inject
    constructor(
        private val firebaseAuth: FirebaseAuth,
    ) : AuthRepository {
        override suspend fun signInWithGoogle(idToken: String): Result<FirebaseUser> {
            return try {
                val credential = GoogleAuthProvider.getCredential(idToken, null)
                val authResult = firebaseAuth.signInWithCredential(credential).await()
                authResult.user?.let { user ->
                    Result.success(user)
                } ?: Result.failure(Exception())
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

        override suspend fun getCurrentUser(): FirebaseUser? {
            return firebaseAuth.currentUser
        }
    }
