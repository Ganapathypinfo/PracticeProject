package api

import com.example.practice.model.UserModel
import retrofit2.http.GET

interface RemoteInterface {
    @GET("users/")
    suspend fun fetchUsers():List<UserModel>
}