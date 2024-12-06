package com.example.frontend.data

import javax.inject.Inject
import retrofit2.Response
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val userApi : UserApi,
)  {
    suspend fun getUserById(netId : String): Response<User> {
        return userApi.getUserById(netId)
    }
}