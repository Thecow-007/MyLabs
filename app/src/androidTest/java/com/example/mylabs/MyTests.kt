package com.example.mylabs

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MyTests {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testLightSensor(){
        composeTestRule.setContent {
            DisplayLighting()
        }
        Thread.sleep(1000)
        composeTestRule.onNodeWithTag("light_text")
            .assertExists()
            .assertIsDisplayed()
    }

    @Test
    fun testAccelSensor(){
        composeTestRule.setContent {
            DisplayLighting()
        }
        Thread.sleep(1000)
        composeTestRule.onNodeWithTag("accelerometer_text")
            .assertExists()
            .assertIsDisplayed()
    }

    @Test
    fun testStepCounterSensor(){
        composeTestRule.setContent {
            DisplayLighting()
        }
        Thread.sleep(1000)
        composeTestRule.onNodeWithTag("step_count_text")
            .assertExists()
            .assertIsDisplayed()
    }



}