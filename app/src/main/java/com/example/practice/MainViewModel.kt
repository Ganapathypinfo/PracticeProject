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




class MainViewModel( ): ViewModel(),API{
    val TAG : String = this.javaClass.simpleName
    private var usersList: MutableLiveData<List<UserModel>>? = null

    //we will call this method to get the data
    fun getUsers(): MutableLiveData<List<UserModel>>? {
        //if the list is null
        if (usersList == null) {
            usersList = MutableLiveData<List<UserModel>>()
            //we will load it asynchronously from server in this method
//            loadusersList()

        }

        //finally we will return the list
        return usersList
    }

    override fun fetchUsersList(
        excludingUserWithID: String?,
        success: (UsersList) -> Unit,
        failure: (FetchError) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val users = RemoteService.remoteCall(API.usersListURL).fetchUsers()
                if(excludingUserWithID != null && excludingUserWithID.isNotEmpty()){
                    val myUser = users.find  {
                            user -> user.id.toString().equals(excludingUserWithID)
                    }

                    var usersMinus = users.toMutableList().minus( myUser)
                    success(usersMinus.reversed())
                }else{
                    success(users.reversed())
                }
            }catch (ex:Exception){
                failure(ex)
            }
        }
    }


}