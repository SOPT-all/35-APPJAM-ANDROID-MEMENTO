package org.memento.data.datastore

interface TokenDataStore {
    var accessToken: String
    var refreshToken: String
    var isNewUser: Boolean
    var userEmail: String

    fun clearInfo()
}
