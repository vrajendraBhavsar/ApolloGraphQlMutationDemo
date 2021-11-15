package com.example.apollographqlmutationdemo.data.network

import android.content.Context
import android.content.SharedPreferences
import android.os.Looper
import com.apollographql.apollo.ApolloClient
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import javax.inject.Inject

//this class creates the ApolloClient
//does the network call to the Rick and Morty API endpoint.

class CharApiService() {  //GraphQL client
    fun getApolloClient(context: Context): ApolloClient {   //using hilt we will inject it wherever we need to make Api call.
        check(Looper.myLooper() == Looper.getMainLooper()) {
            "Only the main thread can get the apolloClient instance"
        }

//        val okHttpClient = OkHttpClient.Builder().build()
        return ApolloClient.builder()
            .serverUrl("https://apollo-fullstack-tutorial.herokuapp.com/")
            .okHttpClient(OkHttpClient.Builder()
                .addInterceptor(AuthorizationInterceptor(context))
                .build()
            )
            .build()
    }
}

private class AuthorizationInterceptor @Inject constructor(val context: Context): Interceptor {
    private var PRIVATE_MODE = Context.MODE_PRIVATE
    private val PREF_NAME = "bearer-token"
    private val TOKEN_KEY: String = "bearer-token-key"

    //getting sharedPreference data(Bearer Token) from WebView
    val sharedPref: SharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
    val token = sharedPref.getString(TOKEN_KEY, "default_value")

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization", token ?: "")
            .build()

        return chain.proceed(request)
    }
}