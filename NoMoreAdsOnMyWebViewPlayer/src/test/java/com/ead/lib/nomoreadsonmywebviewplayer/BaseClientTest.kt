package com.ead.lib.nomoreadsonmywebviewplayer

import android.webkit.WebResourceRequest
import android.webkit.WebView
import com.ead.lib.nomoreadsonmywebviewplayer.model.FakeBaseClient
import com.ead.lib.nomoreadsonmywebviewplayer.models.BaseClient
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.lang.reflect.Method

@Suppress("DEPRECATION")
class BaseClientTest {

    private lateinit var webView: WebView
    private lateinit var request: WebResourceRequest

    @Before
    fun setUp() {
        webView = mockk(relaxed = true)
        request = mockk(relaxed = true)
    }

    @Test
    fun `test domain variable logic with reflection,using normal url, should return domain name when called`() {

        //given
        val client = BaseClient("https://www.example.com")
        val expectedDomain = "example"
        val domainMethod = getDomainMethodAndMakeAccessible()

        //when
        val domainValue = domainMethod.invoke(client) as? String

        //then
        assertEquals(expectedDomain, domainValue)
    }

    @Test
    fun `test domain variable logic with reflection, using url with sub domains, should return domain name when called`() {

        //given
        val client = BaseClient("https://www.sub.sub2.sub3.example.com")
        val expectedDomain = "example"
        val domainMethod = getDomainMethodAndMakeAccessible()

        //when
        val domainValue = domainMethod.invoke(client) as? String

        //then
        assertEquals(expectedDomain, domainValue)
    }

    @Test
    fun `test domain variable logic with reflection, using malformed url, should return null when called`() {

        //given
        val client = BaseClient(".sub.sub2.sub3.example.")
        val expectedDomain = null
        val domainMethod = getDomainMethodAndMakeAccessible()

        //when
        val domainValue = domainMethod.invoke(client) as? String

        //then
        assertEquals(expectedDomain, domainValue)
    }


    @Test
    fun `request url returns a normal url and unknown url to navigate, so shouldOverrideUrlLoading has to call onPassingOverrideUrl(view, request)`() {

        //given
        val client = FakeBaseClient("https://www.example.com")
        every { request.url.toString() } returns "https://www.google.com"

        //when
        client.shouldOverrideUrlLoading(webView, request)

        //then
        assert(client.isPassingOverrideUrlCalled)
        assertTrue(!client.isPassingInterceptRequestCalled)
    }

    @Test
    fun `request url returns a normal url and known url from the same domain, so shouldOverrideUrlLoading has to return false and avoid onPassingOverrideUrl(view, request)`() {

        //given
        val client = FakeBaseClient("https://www.example.com")
        every { request.url.toString() } returns "https://www.example.com/test"

        //when
        val result = client.shouldOverrideUrlLoading(webView, request)

        //then
        assertTrue(!result)
        assertTrue(!client.isPassingOverrideUrlCalled)
        assertTrue(!client.isPassingInterceptRequestCalled)
    }

    @Test
    fun `request url returns a normal url and known url from the same domain, so shouldInterceptRequest has to return null and avoid onPassingInterceptRequest(view, request)`() {

        //given
        val client = FakeBaseClient("https://www.example.com")
        every { request.url.toString() } returns "https://www.example.com/test"

        //when
        val result = client.shouldInterceptRequest(webView, request)

        //then
        assertTrue(result == null)
        assertTrue(!client.isPassingInterceptRequestCalled)
        assertTrue(!client.isPassingOverrideUrlCalled)
    }

    @Test
    fun `request url returns a normal url and unknown url from other domain, so shouldInterceptRequest has to call onPassingInterceptRequest(view, request)`() {

        //given
        val client = FakeBaseClient("https://www.example.com")
        every { request.url.toString() } returns "https://www.google.com/test"

        //when
        client.shouldInterceptRequest(webView, request)

        //then
        assert(client.isPassingInterceptRequestCalled)
        assertTrue(!client.isPassingOverrideUrlCalled)
    }

    private fun getDomainMethodAndMakeAccessible(classToAccess: Class<*> = BaseClient::class.java, nameOfMethod: String = "getDomain"): Method {
        val domainMethod = classToAccess.getDeclaredMethod(nameOfMethod)
        domainMethod.isAccessible = true
        return domainMethod
    }
}