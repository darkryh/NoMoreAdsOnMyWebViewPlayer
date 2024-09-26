package com.ead.app.nomoreadsonmywebviewplayer

import android.webkit.WebView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.CountDownLatch

class WebViewIdlingResource(
    private val webView: WebView,
    private val className: String,
    private val interval: Long = 500L,
    val latch: CountDownLatch = CountDownLatch(1)
)  {

    private var result = false

    val verifier = CoroutineScope(Dispatchers.IO).launch {
        while (true) {

            TestingThread.onUi { checkIfClassExists() }


            if (result) {
                if (latch.count > 0) {
                    latch.countDown()
                }
                break
            }

            delay(interval)
        }
    }


    private fun checkIfClassExists() {
        webView.evaluateJavascript(
            "document.getElementsByClassName('$className').length > 0"
        ) { resultString ->
            this@WebViewIdlingResource.result = resultString?.toBoolean() == true
            if (this@WebViewIdlingResource.result) {
                if (latch.count > 0) latch.countDown()
            }
        }
    }
}

