package com.ead.app.nomoreadsonmywebviewplayer

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.web.assertion.WebViewAssertions.webContent
import androidx.test.espresso.web.matcher.DomMatchers.hasElementWithXpath
import androidx.test.espresso.web.model.Atoms
import androidx.test.espresso.web.sugar.Web.onWebView
import androidx.test.espresso.web.webdriver.DriverAtoms
import androidx.test.espresso.web.webdriver.Locator
import com.ead.app.nomoreadsonmywebviewplayer.util.TestTags
import com.ead.app.nomoreadsonmywebviewplayer.util.TestTags.MINIMUM_SCORE
import com.ead.lib.nomoreadsonmywebviewplayer.NoMoreAdsWebView
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

class WebViewImplementationTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        onWebView().forceJavascriptEnabled()
    }

    @Test
    fun checkWebViewInAdBlockTesterByCheckingFinalScoreAboveTheMinimum() {
        composeTestRule.onNodeWithTag(TestTags.WEB_VIEW_CONTAINER).assertIsDisplayed()

        val webView = composeTestRule.activity.findViewById<NoMoreAdsWebView>(R.id.test_id_no_more_ads_web_view)
        val idlingResource = WebViewIdlingResource(webView, "final-score-value")

        val latch = CountDownLatch(1)

        idlingResource.waitForClassExists(latch)

        runBlocking {
            if (!latch.await(10, TimeUnit.SECONDS)) {
                throw TimeoutException("Timeout waiting for WebView to check final score class")
            }
        }

        val score = onWebView(withId(R.id.test_id_no_more_ads_web_view))
            .check(webContent(hasElementWithXpath("//h1/span[contains(text(), 'AdBlock Tester')]")))
            .check(webContent(hasElementWithXpath("//h3[contains(@class, 'final-score')]")))
            .withElement(DriverAtoms.findElement(Locator.CLASS_NAME, "final-score-value"))
            .perform(Atoms.script(
                "return parseInt(document.querySelector('.final-score-value').innerText)"
            ))
            .get()
            .value as Int

        assert(score > MINIMUM_SCORE)
    }
}
