package com.example.practice.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.practice.model.UserModel
import com.google.android.material.shape.CornerSize
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Composable
fun UserCard(
    userModel: UserModel,
    onClick: ()-> Unit,
){
    val scrollState = rememberScrollState()

    Card(
        shape = MaterialTheme.shapes.medium,

        modifier = Modifier
            .padding(
                bottom = 6.dp,
                top = 6.dp
            )
            .fillMaxWidth()
            .fillMaxHeight(0.3f)
            .clickable(onClick = onClick),
        elevation = 18.dp,

    ) {
        Column {
            userModel.let { user ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = 12.dp,
                            bottom = 12.dp,
                            start = 8.dp,
                            end = 8.dp
                        )
                ) {
                    Text(text = "Name:${user.name}",
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .wrapContentWidth(Alignment.Start),
                        fontSize = 18.sp

                    )
                    Text(text = "UserName:${user.username}",
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(Alignment.Start),
                        fontSize = 18.sp

                    )
                    Text(text = "email:${user.email}",
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(Alignment.Start),
                        fontSize = 12.sp

                    )
                    Text(text = "city:${user.address.city}",
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(Alignment.Start),
                        fontSize = 12.sp

                    )
                    Text(text = "Id:${user.id}",
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(Alignment.Start),
                        fontSize = 12.sp

                    )
                }
            }
        }
    }
}