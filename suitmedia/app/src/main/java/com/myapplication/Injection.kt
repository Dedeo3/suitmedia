package com.myapplication

import android.content.Context
import com.myapplication.api.retrofit.ApiConfig
import com.myapplication.repo.UserRepository

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val apiService= ApiConfig.getApiService()
        return UserRepository.getInstance(apiService)
    }
}