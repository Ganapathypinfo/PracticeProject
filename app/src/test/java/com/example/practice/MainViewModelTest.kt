package com.example.practice

import androidx.lifecycle.MutableLiveData
import com.example.practice.Utils.Companion.getJson
import com.example.practice.model.UsersList
import com.example.practice.model.UsersListItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.junit.Assert
import org.junit.Before

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.lang.reflect.Type

/**
 * Main Activity Test Class is responsible to test main activity logic
 */
@RunWith(JUnit4::class)
class MainViewModelTest {
    /**
     * Declare Main Activity View Model Class variable
     */
    lateinit var mainViewModel:MainViewModel
    /**
     * Declare Reference user list item list
     */
    lateinit var mReferenceUserListItems: List<UsersListItem>

    private var usersList: MutableLiveData<List<UsersList>>? = null

    @Before
    fun setup(){
        // Initialize MainActivityViewModel Class to access class function
        mainViewModel = MainViewModel()
        // Create reference user list items list, we have to parse set of user data
        // from 'userlist_items.json' file which is placed in resoucres directory.
        val userListJsonResponse = getJson("userlist_items.json")
        val parsedUserListItems: Type = object : TypeToken<List<UsersListItem?>?>(){}.type

        // Set parsed user list to 'mReferenceUserListItems'
        mReferenceUserListItems = Gson().fromJson(userListJsonResponse, parsedUserListItems)
    }


    /**
     * Function test uses case when no user is excluded
     * Expected Result : 'mReferenceUserListItems' List should have 10 items
     * Actual Result : User List from 'excludeByUserId' api should have 10 items
     */
    @Test
    fun test_main_activity_view_model_exclude_no_user(){
        mainViewModel.excludeByUserId("", mReferenceUserListItems){ userListItems ->
            Assert.assertEquals(mReferenceUserListItems, userListItems)
        }
    }

    /**
     * Function test use case when we exclude user by id
     * Expected Result : 'mReferenceUserListItems' List should have 9 items
     * Actual Result : User List from 'excludeByUserId' api should have 9 items
     */
    @Test
    fun test_main_activity_view_model_exclude_user_by_id(){
        val usersListExcludeUserOfIdOne = mReferenceUserListItems.filterNot { it?.id == 1 }
        mainViewModel.excludeByUserId("1", mReferenceUserListItems){ userListItems ->
            Assert.assertEquals(usersListExcludeUserOfIdOne, userListItems)
        }
    }

}