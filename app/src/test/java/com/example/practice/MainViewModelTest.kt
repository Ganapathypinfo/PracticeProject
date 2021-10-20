package com.example.practice

import androidx.lifecycle.MutableLiveData
import com.example.practice.model.UsersList
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test

class MainViewModelTest {
    lateinit var mainViewModel:MainViewModel
    private var usersList: MutableLiveData<List<UsersList>>? = null

    @Before
    fun setup(){
        mainViewModel = MainViewModel()
    }

    @Test
    fun getUseLivedata() {
        assertEquals(mainViewModel.fetchUserList(""), usersList)
    }


}