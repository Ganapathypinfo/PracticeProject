package com.example.practice.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.practice.model.UserModel

@Composable
fun UserListItem(userModel: UserModel, selectedItem: (UserModel) -> Unit) {
    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
        elevation = 10.dp,
        shape = RoundedCornerShape(corner = CornerSize(7.dp))
    ) {
        Row(
            modifier = Modifier
                .padding(7.dp)
                .fillMaxWidth()
                .clickable { selectedItem(userModel) },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = userModel.name, fontSize = 18.sp, fontWeight = FontWeight.ExtraBold)
                Spacer(modifier = Modifier.height(6.dp))
                Text(text = "Company : ${userModel.company.name}", fontSize = 16.sp, fontWeight = FontWeight.Light)
                Spacer(modifier = Modifier.height(3.dp))
                Text(text = "UserId : ${userModel.id.toString()}", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(3.dp))
                Text(text = "UserName : ${userModel.username}", fontSize = 16.sp, fontWeight = FontWeight.Light)
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = "Email : ${userModel.email}",
                    fontSize = 16.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis, fontWeight = FontWeight.Light
                )
                Spacer(modifier = Modifier.height(3.dp))
                Text(text = "City : ${userModel.address.city}",fontSize = 16.sp, fontWeight = FontWeight.Light)


            }

        }

    }
}