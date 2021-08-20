package com.example.practice

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.practice.model.UserModel

class InfoActivity : ComponentActivity() {
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
        setContent {

                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    ViewMoreInfo( userModel)
                }

        }
    }
}

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
