package com.example.practice

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.practice.components.UserListItem
import com.example.practice.model.UserModel
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
            mainViewModel.getUsers("")?.observe(this, {
                setContent {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = Color.LightGray,
                        contentColor = Color.Black
                    ) {
                        mainViewModel.getUseLivedata()?.value?.let { it1 ->
                            DisplayUserListShows(usersListDetails = it1) {
                                if (!isNetworkConnected()) {
                                    showNativeAlertDialog()
                                } else {
                                    val id = it.id.toString()
                                    mainViewModel.getUsers(id)?.observe(this, {
                                        simulateHotReload(this)
                                    })
                                }
                            }
                        }
                    }
                }
            })
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
fun DisplayUserListShows(usersListDetails: List<UserModel>, selectedItem: (UserModel) -> Unit) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        items(
            items = usersListDetails,
            itemContent = { UserListItem(userModel = it, selectedItem) },
        )
    }
}
