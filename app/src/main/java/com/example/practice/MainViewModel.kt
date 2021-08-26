package com.example.practice


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import api.*
import com.example.practice.model.UserModel
import kotlinx.coroutines.launch

class MainViewModel: ViewModel(){
    private var usersList: MutableLiveData<List<UserModel>>? = null

    fun getUseLivedata():MutableLiveData<List<UserModel>>?{
        return usersList
    }

    fun getUsers(excludingUserWithID: String): MutableLiveData<List<UserModel>>? {
            usersList = MutableLiveData<List<UserModel>>()
            viewModelScope.launch {
                RemoteService.remoteCall(API.usersListURL).apply {
                    var userItems = fetchUsers()
                    userItems = userItems.reversed()
                    if(excludingUserWithID.isNotEmpty()){
                        for(user in userItems){
                            if((user.id.toString()) == excludingUserWithID){
                                userItems = userItems.minus(user)
                            }
                        }
                    }
                    usersList?.value = userItems
                }
            }
        return usersList
    }
}