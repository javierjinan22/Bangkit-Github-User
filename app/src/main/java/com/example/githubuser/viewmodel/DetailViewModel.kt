package com.example.githubuser.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.data.response.DetailUserResponse
import com.example.githubuser.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailViewModel : ViewModel() {

    private val DatadetailUser = MutableLiveData<DetailUserResponse>()
    val detailUser: LiveData<DetailUserResponse> = DatadetailUser

    private val Loading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = Loading

    init {
        getUserDetail()
    }
    companion object {
        private const val TAG = "DetailViewModel"
    }

    fun getUserDetail(username: String = "") {
        Loading.value = true
        val client = ApiConfig.getApiService().getDetailUser(username)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                Loading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        DatadetailUser.value =response.body()
                        Log.d(TAG, "onResponse: ")
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                Loading.value = false
                Log.e("TAG", "onFailure: ${t.message}")
            }

        })
    }
    }



