package com.example.practice

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var api = API
        var usersList by mutableStateOf(listOf<UserModel>())

        api.create().fetchUsersList("", {
            Log.d(TAG, (it as List<UserModel>).size.toString())
            Log.d(TAG, it.get(1).name)
            usersList = it
            this@MainActivity.runOnUiThread (java.lang.Runnable {
                setContent {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = Color.LightGray,
                        contentColor = Color.Black
                    ) {
                        val peoples: List<UserModel> = remember { usersList }

                        DisplayTvShows (usersListDetails = peoples ){
                            startActivity(InfoActivity.intent(this,it))
                        }

                    }
                }
            })
        }, {
            Log.d(TAG, "fetch fail >>> ${(it as Exception).message}")
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
