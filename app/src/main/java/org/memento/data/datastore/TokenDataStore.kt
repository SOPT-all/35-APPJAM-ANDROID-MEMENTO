package org.memento.data.datastore

interface TokenDataStore {
    var accessToken: String
    var refreshToken: String

    fun clearInfo()
}
