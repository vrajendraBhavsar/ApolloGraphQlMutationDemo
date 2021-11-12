package com.example.apollographqlmutationdemo.ui.login
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.example.apollographqlmutationdemo.data.model.state.ViewState
import com.example.apollographqldemozz.UserLoginMutation
import com.example.apollographqlmutationdemo.domain.login.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginRepository: LoginRepository) : ViewModel() {
    private val _userLoginResult by lazy { MutableLiveData<ViewState<Response<UserLoginMutation.Data>>>() }
    val userLoginResult: LiveData<ViewState<Response<UserLoginMutation.Data>>> = _userLoginResult

    fun getUserLoginResult(email: String) = viewModelScope.launch {
//        _userLoginResult.postValue(ViewState.Loading()) //started loading..
        try {
            val response = loginRepository.getLoginResult(email = email)
            _userLoginResult.postValue(ViewState.Success(response))
        } catch (e: ApolloException) {
            Log.d("ApolloException", "userLoginResult Failure", e)
            _userLoginResult.postValue(ViewState.Error("Error fetching single character"))
        }
    }
}