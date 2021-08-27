
package com.example.practice

import api.API
import api.RemoteService.remoteCall
import com.example.practice.model.UserModel
import kotlinx.coroutines.runBlocking
import org.junit.Test
import strikt.api.expectThat

class RemoteServiceTest {

    @Test
    fun `it should GET with query`() {
    val remoteApi = remoteCall(baseUrl = API.usersListURL)

        runBlocking{
            val call: List<UserModel> = remoteApi.fetchUsers()

            expectThat(call) {
                assertThat("List not Empty") {
                    it.isNotEmpty()
                }
            }
        }

    }

}