package com.example.practice

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onRoot
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.practice.model.UsersListItem
import junit.framework.Assert.assertTrue
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MyComposeTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        Assert.assertEquals("com.example.practice", appContext.packageName)
    }
   @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun screentest(){
        var usersList by mutableStateOf(listOf<UsersListItem>()) // State that can cause recompositions
        var lastSeenValue = 0 // Used to track recompositions
        composeTestRule.setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = Color.LightGray,
                contentColor = Color.Black
            ) {

                DisplayUserListShows (usersListDetails = usersList ){
                    var id = it.id.toString()
                    lastSeenValue = 1
                }
            }
        }

        // Fails because nothing triggered a recomposition
        assertTrue(lastSeenValue == 0)
        assertTrue(usersList.size == 0)

    }
    @Test
    fun initial_zero() {
        composeTestRule.onRoot().assertIsDisplayed()
    }

}