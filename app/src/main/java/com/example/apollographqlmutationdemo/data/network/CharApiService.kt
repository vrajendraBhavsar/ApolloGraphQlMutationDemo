package com.example.apollographqlmutationdemo.data.network

import android.os.Looper
import com.apollographql.apollo.ApolloClient
import okhttp3.OkHttpClient
//this class creates the ApolloClient
//does the network call to the Rick and Morty API endpoint.

class CharApiService {  //GraphQL client
    fun getApolloClient(): ApolloClient {   //using hilt we will inject it wherever we need to make Api call.
        check(Looper.myLooper() == Looper.getMainLooper()) {
            "Only the main thread can get the apolloClient instance"
        }

        val okHttpClient = OkHttpClient.Builder().build()
        return ApolloClient.builder()
            .serverUrl("https://apollo-fullstack-tutorial.herokuapp.com/graphql")
            .okHttpClient(okHttpClient)
            .build()
    }
}