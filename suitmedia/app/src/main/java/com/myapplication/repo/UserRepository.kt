package com.myapplication.repo

import com.myapplication.Result
import com.myapplication.api.response.DataResponse
import com.myapplication.api.retrofit.ApiService

class UserRepository private constructor(
    private val apiService: ApiService
) {

    suspend fun getUsers(page: Int, perPage: Int): Result<DataResponse> {
        return try {
            val response = apiService.getUsers(page, perPage)
            if (response.isSuccessful) {
                Result.Success(response.body()!!)
            } else {
                Result.Error(Exception("Failed to fetch users"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            apiService: ApiService
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(apiService)
            }.also { instance = it }
    }
}