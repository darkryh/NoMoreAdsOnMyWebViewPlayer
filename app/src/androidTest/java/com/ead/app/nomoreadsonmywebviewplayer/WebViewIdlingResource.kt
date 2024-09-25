package com.ead.app.nomoreadsonmywebviewplayer

import android.webkit.WebView
import androidx.test.espresso.IdlingResource
import java.util.concurrent.CountDownLatch

class WebViewIdlingResource(
    private val webView: WebView,
    private val className: String,
    private val maxWaitTime: Long = 10L,
    private val interval: Long = 500L
) : IdlingResource {

    @Volatile
    private var callback: IdlingResource.ResourceCallback? = null
    private var isIdle = false

    override fun getName(): String = this.javaClass.name

    override fun isIdleNow(): Boolean {
        return isIdle
    }

    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback?) {
        this.callback = callback
    }

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

        if (!isIdle) {
            isIdle = true
            callback?.onTransitionToIdle()
        }
    }

    private fun checkIfClassExists(latch: CountDownLatch) {
        webView.evaluateJavascript(
            "document.getElementsByClassName('$className').length > 0"
        ) { result ->
            isIdle = result?.toBoolean() == true

            if (isIdle) {
                callback?.onTransitionToIdle()
                latch.countDown()
            }
        }
    }
}

