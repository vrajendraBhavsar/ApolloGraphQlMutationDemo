package com.example.apollographqlmutationdemo.domain.login

import com.apollographql.apollo.api.Response
import com.example.apollographqldemozz.UserLoginMutation

interface LoginRepository {
    suspend fun getLoginResult(email : String): Response<UserLoginMutation.Data>
}