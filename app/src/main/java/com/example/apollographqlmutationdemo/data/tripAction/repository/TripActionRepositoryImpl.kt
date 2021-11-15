package com.example.apollographqlmutationdemo.data.tripAction.repository

import android.content.Context
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.coroutines.await
import com.example.apollographqldemozz.BookTripMutation
import com.example.apollographqldemozz.CancelTripMutation
import com.example.apollographqldemozz.TotalBookedTripsQuery
import com.example.apollographqlmutationdemo.data.network.CharApiService
import com.example.apollographqlmutationdemo.domain.tripAction.TripActionRepository
import javax.inject.Inject

class TripActionRepositoryImpl @Inject constructor(private val charApiService: CharApiService) : TripActionRepository {

    override suspend fun bookTrip(context: Context, ids: ArrayList<String>): Response<BookTripMutation.Data> {
        return charApiService.getApolloClient(context).mutate(BookTripMutation(id = ids)).await()
    }

    override suspend fun cancelTrip(context: Context, id: String): Response<CancelTripMutation.Data> {
        return charApiService.getApolloClient(context).mutate(CancelTripMutation(id = id)).await()
    }

    override suspend fun totalBookedTrips(context: Context): Response<TotalBookedTripsQuery.Data> {
        return charApiService.getApolloClient(context).query(TotalBookedTripsQuery()).await()
    }
}