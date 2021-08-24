package com.example.practice

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import api.API
import com.example.practice.components.UserListItem
import com.example.practice.model.UserModel

class InfoActivity : ComponentActivity() {

    val TAG: String = "InfoActivity"

    companion object{
        private const val UserModelId = "UserModelId"
            fun intent(context: Context, userModel: UserModel)=
            Intent(context,InfoActivity::class.java).apply {
                putExtra(UserModelId,userModel)
            }
    }
    private val userModel : UserModel by lazy {
        intent?.getSerializableExtra(UserModelId) as UserModel
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var api = API
        var usersList by mutableStateOf(listOf<UserModel>())
        var id = userModel.id.toString()
        Log.d(TAG, "id : ${id}" )

        api.create().fetchUsersList(id, {
            Log.d(TAG, (it as List<UserModel>).size.toString())
            Log.d(TAG, it.get(1).name)
            usersList = it
            this@InfoActivity.runOnUiThread (java.lang.Runnable {
                setContent {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = Color.LightGray,
                        contentColor = Color.Black
                    ) {
                        DisplayUserListShows (usersListDetails = usersList){}

                    }
                }
            })

        }, {
            Log.d(TAG, "fetch fail >>> ${(it as Exception).message}")
        })

       /*
        setContent {

                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
//                    ViewMoreInfo( userModel)
                    DisplayUserListShows (usersListDetails = usersList ){
                        startActivity(InfoActivity.intent(this,it))
                        usersList.toMutableList().remove(it)

                    }
                }

        }*/
    }
}
/*
@Composable
fun ViewMoreInfo(userModel: UserModel) {
    val scrollState = rememberScrollState()
    Card(
        modifier = Modifier.padding(10.dp),
        elevation = 10.dp,
        shape = RoundedCornerShape(corner = CornerSize(10.dp))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState)
                .padding(10.dp)
        ) {

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = userModel.name,
                style = MaterialTheme.typography.h3
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = userModel.username,
                style = MaterialTheme.typography.h5
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Adderss",
                style = MaterialTheme.typography.h5, fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "${userModel.address.street}, ${userModel.address.city} - ${userModel.address.zipcode}",
                style = MaterialTheme.typography.h5, fontWeight = FontWeight.Light
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Email : ${userModel.email}",
                style = MaterialTheme.typography.h5, fontWeight = FontWeight.Light
            )

        }
    }
}


@Composable
fun DisplayUserListShows(usersListDetails: List<UserModel>, selectedItem: (UserModel) -> Unit) {

    val userItems = remember { usersListDetails}
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp,vertical = 8.dp)
    ) {
        items(
            items = userItems,
            itemContent = {
                UserListItem(userModel = it, selectedItem)
            }
        )
    }

}*/

