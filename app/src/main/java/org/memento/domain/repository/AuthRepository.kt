package org.memento.domain.repository

import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    suspend fun signInWithGoogle(idToken: String): Result<FirebaseUser>

    suspend fun getCurrentUser(): FirebaseUser?
}
