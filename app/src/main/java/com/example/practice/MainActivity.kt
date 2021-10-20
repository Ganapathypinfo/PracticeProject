package com.example.practice

import android.content.Context
import android.net.ConnectivityManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.practice.components.UserListItem
import com.example.practice.model.UsersList
import com.example.practice.model.UsersListItem
import kotlinx.coroutines.*


// TODO: Display list of users with the user information mentioned in the assignment
// Note: A nice looking UI is appreciated but clean code is more important
@ExperimentalCoroutinesApi
class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!isNetworkConnected()) {
            showNativeAlertDialog()
        } else {
            mainViewModel.fetchUserList("")
             setContent{
                val userDataState by mainViewModel.userDataState.observeAsState(MainViewModel.UserDataState.Loading)
                 Surface(
                     modifier = Modifier.fillMaxSize(),
                     color = Color.LightGray,
                     contentColor = Color.Black
                 ){
                     userDataState.let {
                         when(it){
                             is MainViewModel.UserDataState.Loading ->{
                                 loadingUi()
                             }
                             is MainViewModel.UserDataState.ShowUsers ->{
                                 it.usersList.let { items ->
                                     if (items != null) {
                                         DisplayUserListShows(usersListDetails = items.usersList!!){
                                             mainViewModel.fetchUserList(excludingUserId = it.id.toString())
                                         }
                                     }

                                 }

                             }

                         }
                     }
                 }
        }
        }
    }

    private fun showNativeAlertDialog() {
        AlertDialog.Builder(this).setTitle(getString(R.string.title_network_info))
            .setMessage(getString(R.string.network_info))
            .setPositiveButton(android.R.string.ok) { _, _ -> }
            .setIcon(android.R.drawable.ic_dialog_alert).show()
    }

    private fun isNetworkConnected(): Boolean {
        var result = false
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val actNw =
                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
            result = when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.activeNetworkInfo?.run {
                result = when (type) {
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return result
    }
}

@Composable
fun DisplayUserListShows(usersListDetails: List<UsersListItem?>?, selectedItem: (UsersListItem) -> Unit) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        items(
            items = usersListDetails!!,
            itemContent = {
                if (it != null) {
                    UserListItem(userModel = it, selectedItem)
                }
            },
        )
    }
}


/**
 * Composable function show loading ui screen
 */
@Composable
fun loadingUi(){
    Column(modifier = Modifier
        .fillMaxSize()
        .wrapContentSize(Alignment.Center)) {
        Text(text = "Loading...",
            style = MaterialTheme.typography.h4, textAlign = TextAlign.Center)
    }
}