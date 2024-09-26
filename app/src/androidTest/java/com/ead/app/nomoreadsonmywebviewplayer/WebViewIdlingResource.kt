package com.ead.app.nomoreadsonmywebviewplayer

import android.webkit.WebView
import java.util.concurrent.CountDownLatch

class WebViewIdlingResource(
    private val webView: WebView,
    private val className: String,
    private val maxWaitTime: Long = 10L,
    private val interval: Long = 500L
)  {


    fun waitForClassExists(latch: CountDownLatch) {
        val startTime = System.currentTimeMillis()

        while (System.currentTimeMillis() - startTime < maxWaitTime * 1000) {
            TestingThread.onUi {
                checkIfClassExists(latch)
            }

            if (latch.count == 0L) {
                return
            }

            Thread.sleep(interval)
        }


    }

    private fun checkIfClassExists(latch: CountDownLatch) {
        webView.evaluateJavascript(
            "document.getElementsByClassName('$className').length > 0"
        ) { result ->
            if (result?.toBoolean() == true) {
                latch.countDown()
            }
        }
    }
}

