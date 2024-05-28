package com.mira.mira.di

import android.content.Context
import com.mira.mira.data.pref.UserPreference
import com.mira.mira.data.pref.dataStore
import com.mira.mira.data.repository.UserRepository

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        return UserRepository.getInstance(pref)
    }
}