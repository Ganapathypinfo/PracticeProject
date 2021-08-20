package api

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


// TODO: Implement a simple networking API using retrofit or any library you're familiar with 🙌
// Note: Fetching users-list is the *only* use-case therefore avoid implementing unrelated,
// extra, features no matter how common or useful they might be for potential future cases.
//
class RetrofitAPI(private val usersListURL: String) : API {


    override fun fetchUsersList(
        excludingUserWithID: String?,
        success: (UsersList) -> Unit,
        failure: (FetchError) -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val users = RemoteService.remoteCall(usersListURL).fetchUsers()
                success(users.reversed())
            }catch (ex:Exception){
                failure(ex)
            }
        }
    }

}

