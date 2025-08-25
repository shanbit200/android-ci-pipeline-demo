package com.shan.pipelinedemo
import androidx.compose.runtime.Composable
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.assertIsDisplayed
import com.shan.pipelinedemo.ui.theme.AndroidcipipelineTheme
import org.junit.Rule
import org.junit.Test

class GreetingComposableTest {
    @get:Rule
    val compose = createComposeRule()

    @Test
    fun greeting_renders_dynamic_name() {
        compose.setContent { GreetingHost("Kotlin") }
        compose.onNodeWithText("Hello Kotlin!").assertIsDisplayed()
    }

    @Composable
    private fun GreetingHost(name: String) {
        AndroidcipipelineTheme {
            Greeting(name = name)
        }
    }


}