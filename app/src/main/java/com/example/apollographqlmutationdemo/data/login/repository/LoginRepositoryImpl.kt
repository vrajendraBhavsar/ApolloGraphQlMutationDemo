package com.example.apollographqlmutationdemo.data.login.repository

import com.apollographql.apollo.api.Input
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.coroutines.await
import com.example.apollographqlmutationdemo.data.network.CharApiService
import com.example.apollographqldemozz.UserLoginMutation
import com.example.apollographqlmutationdemo.domain.login.LoginRepository
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(private val charApiService: CharApiService) : LoginRepository {
    override suspend fun getLoginResult(email: String): Response<UserLoginMutation.Data> {
        return charApiService.getApolloClient().mutate(UserLoginMutation(email = Input.fromNullable(email))).await()
    }
}