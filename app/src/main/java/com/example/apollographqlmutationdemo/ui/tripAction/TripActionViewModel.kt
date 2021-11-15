package com.example.apollographqlmutationdemo.ui.tripAction

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.example.apollographqldemozz.BookTripMutation
import com.example.apollographqldemozz.CancelTripMutation
import com.example.apollographqldemozz.TotalBookedTripsQuery
import com.example.apollographqlmutationdemo.data.model.state.ViewState
import com.example.apollographqlmutationdemo.domain.tripAction.TripActionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TripActionViewModel @Inject constructor(private val tripActionRepository: TripActionRepository) :
    ViewModel() {
    private val _bookTripResult by lazy { MutableLiveData<ViewState<Response<BookTripMutation.Data>>>() }
    val bookTripResult: LiveData<ViewState<Response<BookTripMutation.Data>>> = _bookTripResult

    private val _cancelTripResult by lazy { MutableLiveData<ViewState<Response<CancelTripMutation.Data>>>() }
    val cancelTripResult: LiveData<ViewState<Response<CancelTripMutation.Data>>> = _cancelTripResult

    private val _totalBookedTripResult by lazy { MutableLiveData<ViewState<Response<TotalBookedTripsQuery.Data>>>() }
    val totalBookedTripResult: LiveData<ViewState<Response<TotalBookedTripsQuery.Data>>> =
        _totalBookedTripResult

    fun getBookTripResult(id: ArrayList<String>, context: Context) = viewModelScope.launch {
//        _bookTripResult.postValue(ViewState.Loading()) //started loading..
        try {
            val response = tripActionRepository.bookTrip(ids = id, context = context)
            _bookTripResult.postValue(ViewState.Success(response))
        } catch (e: ApolloException) {
            Log.d("ApolloException", "bookTripResult Failure", e)
            _bookTripResult.postValue(ViewState.Error("Error while booking Trip"))
        }
    }

    fun getCancelTripResult(id: String, context: Context) = viewModelScope.launch {
//        _cancelTripResult.postValue(ViewState.Loading()) //started loading..
        try {
            val response = tripActionRepository.cancelTrip(id = id, context = context)
            _cancelTripResult.postValue(ViewState.Success(response))
        } catch (e: ApolloException) {
            Log.d("ApolloException", "cancelTripResult Failure", e)
            _cancelTripResult.postValue(ViewState.Error("Error while cancelling Trip"))
        }
    }

    fun getTotalBookedTripResult(context: Context) = viewModelScope.launch {
//        _totalBookedTripResult.postValue(ViewState.Loading()) //started loading..
        try {
            val response = tripActionRepository.totalBookedTrips(context)
            _totalBookedTripResult.postValue(ViewState.Success(response))
        } catch (e: ApolloException) {
            Log.d("ApolloException", "cancelTripResult Failure", e)
            _totalBookedTripResult.postValue(ViewState.Error("Error while cancelling Trip"))
        }
    }
}