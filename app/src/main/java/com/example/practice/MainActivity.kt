package com.example.practice

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.AdapterListUpdateCallback
import api.API
import com.example.practice.components.UserListItem
import com.example.practice.model.UserModel
import kotlinx.coroutines.*


// TODO: Display list of users with the user information mentioned in the assignment
// Note: A nice looking UI is appreciated but clean code is more important
@ExperimentalCoroutinesApi
class MainActivity : ComponentActivity() {
    val TAG: String = "MainActivity"

    val mainViewModel:MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var usersList by mutableStateOf(listOf<UserModel>())

        mainViewModel.getUsers("")?.observe(this, Observer {
            Log.d(TAG, (it as List<UserModel>).size.toString())
            usersList = mainViewModel.getUseLivedata()?.value!!
            setContent {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.LightGray,
                    contentColor = Color.Black
                ) {

                    DisplayTvShows (usersListDetails = usersList ){
                        var id = it.id.toString()
                        mainViewModel.getUsers(id)?.observe(this, Observer {
                            Log.d(TAG, "second : ${(it as List<UserModel>).size.toString()}")
                            usersList = mainViewModel.getUseLivedata()?.value!!
                        })

                    }

                }
            }
        })
    }
}

@Composable
fun DisplayTvShows(usersListDetails: List<UserModel>, selectedItem: (UserModel) -> Unit) {

    val tvShows = remember { usersListDetails}
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp,vertical = 8.dp)
    ) {
        items(
            items = tvShows,
            itemContent = {
                UserListItem(userModel = it, selectedItem)
            }
        )
    }

}
