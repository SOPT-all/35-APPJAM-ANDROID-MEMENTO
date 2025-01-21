package org.memento.di

import androidx.core.location.LocationRequestCompat.Quality

@Quality
@Retention(AnnotationRetention.BINARY)
annotation class Auth

@Quality
@Retention(AnnotationRetention.BINARY)
annotation class Memento
