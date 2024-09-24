package com.ead.lib.nomoreadsonmywebviewplayer

import android.content.Context
import android.webkit.WebResourceRequest
import android.webkit.WebView
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ead.lib.nomoreadsonmywebviewplayer.core.Blocker
import com.ead.lib.nomoreadsonmywebviewplayer.model.FakeBlockerClient
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@Suppress("DEPRECATION")
@RunWith(AndroidJUnit4::class)
class BlockerClientTest {

    private lateinit var webView: WebView
    private lateinit var request: WebResourceRequest

    private lateinit var context: Context

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        runBlocking {
            Blocker.init(context)
            Blocker.awaitKeywordsLoaded()
        }

        webView = mockk(relaxed = true)
        request = mockk(relaxed = true)
    }

    @Test
    fun `request returns unknown url, so let him override as normal, isOverrideUrlLoadingCalled hasn't to be called`() {

        //given
        val client = FakeBlockerClient("https://www.unknown.com")
        every { request.url.toString() } returns "https://www.unknown-test.com"

        //when
        client.onPassingOverrideUrl(webView, request)

        //then
        assert(!client.isOverrideUrlLoadingCalled)
        assert(!client.isInterceptRequestCalled)
    }

    @Test
    fun `request returns known url, so verify that isOverrideUrlLoadingCalled, to let the option to override`() {

        //given
        val client = FakeBlockerClient("https://www.example.com")
        every { request.url.toString() } returns
                "https://www.something.that.contain.known.keyword.hls.example.com"

        //when
        client.onPassingOverrideUrl(webView, request)

        //then
        assert(client.isOverrideUrlLoadingCalled)
        assert(!client.isInterceptRequestCalled)
    }

    @Test
    fun `request returns unknown url, so let him override as normal, onInterceptRequest has to be called`() {

        //given
        val client = FakeBlockerClient("https://www.unknown.com")
        every { request.url.toString() } returns "https://www.unknown-test.com"

        //when
        client.onPassingInterceptRequest(webView, request)

        //then
        assert(client.isInterceptRequestCalled)
        assert(!client.isOverrideUrlLoadingCalled)
    }

    @Test
    fun `request returns known url, so verify that onInterceptRequest isn't called to block the source and let him intercept`() {

        //given
        val client = FakeBlockerClient("https://www.example.com")
        every { request.url.toString() } returns
                "https://www.something.that.contain.known.keyword.jwplayer.example.com"

        //when
        client.onPassingInterceptRequest(webView, request)

        //then
        assert(!client.isInterceptRequestCalled)
        assert(!client.isOverrideUrlLoadingCalled)
    }
}