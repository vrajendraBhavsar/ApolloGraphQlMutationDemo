package com.example.apollographqlmutationdemo.domain.login

import android.content.Context
import com.apollographql.apollo.api.Response
import com.example.apollographqldemozz.UserLoginMutation

interface LoginRepository {
    suspend fun getLoginResult(email : String, context: Context): Response<UserLoginMutation.Data>
}