package com.example.practice.model

import api.model.Address
import api.model.Company
import java.io.Serializable

data class UserModel(val id:Int, val name:String, val username:String, val email:String,
                     val address: Address, val phoneval :String, val website :String, val company: Company
):Serializable
