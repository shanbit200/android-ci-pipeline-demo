package com.shan.pipelinedemo

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val rule = createAndroidComposeRule<MainActivity>()

    @Test
    fun greeting_is_shown_from_scaffold_content_lambda() {
        rule.onNodeWithText("Hello Android!").assertIsDisplayed()
    }

    @Test
    fun greeting_survives_activity_recreate() {
        // Covers lifecycle + recomposition (helps coverage inside MainActivityKt)
        rule.activityRule.scenario.recreate()
        rule.onNodeWithText("Hello Android!").assertIsDisplayed()
    }
}