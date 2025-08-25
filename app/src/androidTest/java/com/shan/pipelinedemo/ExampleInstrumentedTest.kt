import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.shan.pipelinedemo.MainActivity
import org.junit.Rule
import org.junit.Test

class ExampleInstrumentedTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testHelloWorldIsDisplayed() {
        composeTestRule.onNodeWithText("Hello Android!").assertExists()
    }
}