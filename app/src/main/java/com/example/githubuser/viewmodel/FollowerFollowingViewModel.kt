package com.example.githubuser.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.data.response.DetailUserResponse
import com.example.githubuser.data.response.ItemsItem
import com.example.githubuser.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowerFollowingViewModel : ViewModel(){
    private val daftarFollower = MutableLiveData<List<ItemsItem>>()
    val _daftarFollower: LiveData<List<ItemsItem>> = daftarFollower

    private val Loading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = Loading

    companion object {
        val TAG = "Follower View Model"
    }

    fun getFollower(username: String = "") {
        Loading.value = true
        val client = ApiConfig.getApiService().getFollower(username)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                Loading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        daftarFollower.value =response.body()
                        Log.d(FollowerFollowingViewModel.TAG, "onResponse: ")
                    }
                } else {
                    Log.e(FollowerFollowingViewModel.TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                Loading.value = false
                Log.e("TAG", "onFailure: ${t.message}")
            }

        })
    }

    fun getFollowing(username: String = "") {
        Loading.value = true
        val client = ApiConfig.getApiService().getFollowing(username)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                Loading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        daftarFollower.value =response.body()
                        Log.d(FollowerFollowingViewModel.TAG, "onResponse: ")
                    }
                } else {
                    Log.e(FollowerFollowingViewModel.TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                Loading.value = false
                Log.e("TAG", "onFailure: ${t.message}")
            }

        })
    }
}