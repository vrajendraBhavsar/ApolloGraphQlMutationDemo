package com.example.apollographqlmutationdemo.domain.tripAction

import android.content.Context
import com.apollographql.apollo.api.Response
import com.example.apollographqldemozz.BookTripMutation
import com.example.apollographqldemozz.CancelTripMutation
import com.example.apollographqldemozz.TotalBookedTripsQuery

interface TripActionRepository {
    suspend fun bookTrip(context: Context, ids : ArrayList<String>): Response<BookTripMutation.Data>
    suspend fun cancelTrip(context: Context, id : String): Response<CancelTripMutation.Data>
    suspend fun totalBookedTrips(context: Context): Response<TotalBookedTripsQuery.Data>
}