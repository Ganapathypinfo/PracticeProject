package com.example.practice

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import api.*
import com.example.practice.model.UserModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory

import retrofit2.Retrofit




class MainViewModel( ): ViewModel(){
    val TAG : String = this.javaClass.simpleName
    private var usersList: MutableLiveData<List<UserModel>>? = null

    fun getUseLivedata():MutableLiveData<List<UserModel>>?{
        return usersList
    }
    fun setLivedata(listUser:MutableLiveData<List<UserModel>>?){
        usersList = listUser
    }
    //we will call this method to get the data
    fun getUsers(excludingUserWithID: String): MutableLiveData<List<UserModel>>? {
        //if the list is null

            usersList = MutableLiveData<List<UserModel>>()
            //we will load it asynchronously from server in this method
            viewModelScope.launch {
                RemoteService.remoteCall(API.usersListURL).apply {
                    var userItems = fetchUsers()
                    userItems = userItems.reversed()

                    if(excludingUserWithID != null && excludingUserWithID.isNotEmpty()){

                        val myUser = userItems.find  {
                                user -> user.id.toString().equals(excludingUserWithID)
                        }

                       /* var usersMinus = userItems.toMutableList().minus(myUser)
                        usersList?.value = usersMinus*/
                        Log.d(TAG, "before userItems.size : ${userItems.size}")
                        userItems.minus(myUser)
                        Log.d(TAG, "after userItems.size : ${userItems.size}")
                        usersList?.value = userItems
                        setLivedata(usersList)

                    }else{
                        usersList?.value = userItems
                        setLivedata(usersList)
                    }
                }
            }


        //finally we will return the list
        return usersList
    }



}