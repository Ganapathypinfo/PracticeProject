package api

import com.example.practice.model.UsersListItem
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface RemoteInterface {
    @GET("users/")
    fun fetchUsers(): Deferred<List<UsersListItem?>?>
}