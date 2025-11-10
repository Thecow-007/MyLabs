package com.example.mylabs

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@RunWith(AndroidJUnit4::class)
class Lab7Tests {
    @get:Rule
    val composeTestRule = createComposeRule()


    @Test
    fun testSendButton_addsMessageToList() {
        val testSizeClass = WindowSizeClass.calculateFromSize(
            DpSize(width = 600.dp, height = 800.dp)
        )

        composeTestRule.setContent {
            LoginPage(size=testSizeClass)
        }

        val testMessage = "Testing testing 1, 2, 3. Is this thing even on?"


        // Find the text field by its tag and type text into it
        composeTestRule
            .onNodeWithTag("messageInput")
            .performTextInput(testMessage)

        // Find the send button by its tag and click it
        composeTestRule
            .onNodeWithTag("sendButton")
            .performClick()


        // Check that the first item in the list now exists
        composeTestRule
            .onNodeWithTag("MessageRow0")
            .assertExists()

        // Check the text content of that new row
        composeTestRule
            .onNodeWithTag("MessageText0")
            .assert(hasText(testMessage))

        // And check that the avatar exists for that row
        composeTestRule
            .onNodeWithTag("Avatar0")
            .assertExists()
    }

    @Test
    fun testReceiveButton_addsMessageToList() {
        val testSizeClass = WindowSizeClass.calculateFromSize(
            DpSize(width = 600.dp, height = 800.dp)
        )

        composeTestRule.setContent {
            LoginPage(size=testSizeClass)
        }

        val testMessage = "This is a received message."

        // Find text field and type
        composeTestRule
            .onNodeWithTag("messageInput")
            .performTextInput(testMessage)

        // Find RECEIVE button and click
        composeTestRule
            .onNodeWithTag("receiveButton")
            .performClick()

        // Assert that the row and its text exist
        composeTestRule
            .onNodeWithTag("MessageRow0")
            .assertExists()

        composeTestRule
            .onNodeWithTag("MessageText0")
            .assert(hasText(testMessage))
    }
}