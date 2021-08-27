package com.example.practice

import androidx.lifecycle.MutableLiveData
import com.example.practice.model.UserModel
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test

class MainViewModelTest {
    lateinit var mainViewModel:MainViewModel
    private var usersList: MutableLiveData<List<UserModel>>? = null

    @Before
    fun setup(){
        mainViewModel = MainViewModel()
    }

    @Test
    fun getUseLivedata() {
        assertEquals(mainViewModel.getUseLivedata(), usersList)
    }


}