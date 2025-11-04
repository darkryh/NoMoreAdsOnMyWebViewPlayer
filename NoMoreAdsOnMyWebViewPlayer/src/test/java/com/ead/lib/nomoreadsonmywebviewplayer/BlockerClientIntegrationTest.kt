package com.ead.lib.nomoreadsonmywebviewplayer

import android.content.Context
import android.webkit.WebResourceRequest
import android.webkit.WebView
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ead.lib.nomoreadsonmywebviewplayer.core.Blocker
import com.ead.lib.nomoreadsonmywebviewplayer.models.BlockerClient
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Integration tests for BlockerClient with all supported sites
 * Tests the full blocking flow with realistic scenarios
 */
@Suppress("DEPRECATION")
@RunWith(AndroidJUnit4::class)
class BlockerClientIntegrationTest {

    private lateinit var context: Context
    private lateinit var webView: WebView
    private lateinit var request: WebResourceRequest

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
    fun `test BlockerClient with Uqload URLs should allow video content`() {
        val site = SupportedSites.ALL_SITES.find { it.siteName == "Uqload" }!!
        val client = BlockerClient(site.testUrls.first())

        site.testUrls.forEach { url ->
            every { request.url.toString() } returns url
            
            val shouldBlock = client.shouldInterceptRequest(webView, request)
            
            assertNull(
                "Uqload video URL should not be blocked: $url",
                shouldBlock
            )
        }
    }

    @Test
    fun `test BlockerClient with Mp4Upload URLs should allow video content`() {
        val site = SupportedSites.ALL_SITES.find { it.siteName == "Mp4Upload" }!!
        val client = BlockerClient(site.testUrls.first())

        site.testUrls.forEach { url ->
            every { request.url.toString() } returns url
            
            val shouldBlock = client.shouldInterceptRequest(webView, request)
            
            assertNull(
                "Mp4Upload video URL should not be blocked: $url",
                shouldBlock
            )
        }
    }

    @Test
    fun `test BlockerClient with DoodStream URLs should allow video content`() {
        val site = SupportedSites.ALL_SITES.find { it.siteName == "DoodStream" }!!
        val client = BlockerClient(site.testUrls.first())

        site.testUrls.forEach { url ->
            every { request.url.toString() } returns url
            
            val shouldBlock = client.shouldInterceptRequest(webView, request)
            
            assertNull(
                "DoodStream video URL should not be blocked: $url",
                shouldBlock
            )
        }
    }

    @Test
    fun `test BlockerClient with Filemoon URLs should allow video content`() {
        val site = SupportedSites.ALL_SITES.find { it.siteName == "Filemoon" }!!
        val client = BlockerClient(site.testUrls.first())

        site.testUrls.forEach { url ->
            every { request.url.toString() } returns url
            
            val shouldBlock = client.shouldInterceptRequest(webView, request)
            
            assertNull(
                "Filemoon video URL should not be blocked: $url",
                shouldBlock
            )
        }
    }

    @Test
    fun `test BlockerClient blocks ad URLs`() {
        val client = BlockerClient("https://example.com")
        val adUrls = listOf(
            "https://ads.example.com/banner.js",
            "https://tracking.unknown.com/pixel.gif",
            "https://adserver.com/video-ad.js"
        )

        adUrls.forEach { url ->
            every { request.url.toString() } returns url
            
            val response = client.shouldInterceptRequest(webView, request)
            
            assertNotNull(
                "Ad URL should be blocked: $url",
                response
            )
        }
    }

    @Test
    fun `test BlockerClient with custom exception keywords`() {
        val customClient = object : BlockerClient("https://example.com") {
            override val exceptionWordKeys: List<String>
                get() = listOf("custom-keyword", "special-domain")
        }

        // URL with custom keyword should be allowed
        val customUrl = "https://custom-keyword.example.com/video.mp4"
        every { request.url.toString() } returns customUrl
        
        val response = customClient.shouldInterceptRequest(webView, request)
        
        assertNull(
            "URL with custom exception keyword should not be blocked",
            response
        )
    }

    @Test
    fun `test BlockerClient performance with all supported sites`() {
        val allUrls = SupportedSites.ALL_SITES.flatMap { it.testUrls }
        val client = BlockerClient(allUrls.first())
        
        val metrics = PerformanceTestUtils.measureWithMetrics(
            operationName = "BlockerClient with all sites",
            urls = allUrls
        ) { url ->
            every { request.url.toString() } returns url
            val response = client.shouldInterceptRequest(webView, request)
            response == null // null means not blocked
        }

        println(metrics.toString())
        
        // Most URLs should be allowed (video content)
        assertTrue(
            "Most supported site URLs should be allowed, got ${metrics.allowedCount}/${metrics.totalUrls}",
            metrics.allowedCount >= metrics.totalUrls * 0.8 // At least 80% should be allowed
        )
        
        PerformanceTestUtils.assertPerformanceThresholds(metrics)
    }

    @Test
    fun `test BlockerClient with mixed content - videos and ads`() {
        val videoUrls = SupportedSites.ALL_SITES.flatMap { it.testUrls }.take(10)
        val adUrls = listOf(
            "https://ads.example.com/banner.js",
            "https://tracking.com/pixel.gif"
        )
        val mixedUrls = videoUrls + adUrls
        
        val client = BlockerClient(videoUrls.first())
        
        var videoAllowed = 0
        var adsBlocked = 0
        
        mixedUrls.forEach { url ->
            every { request.url.toString() } returns url
            val response = client.shouldInterceptRequest(webView, request)
            
            if (url in videoUrls && response == null) {
                videoAllowed++
            } else if (url in adUrls && response != null) {
                adsBlocked++
            }
        }
        
        println("Videos allowed: $videoAllowed/${videoUrls.size}")
        println("Ads blocked: $adsBlocked/${adUrls.size}")
        
        assertTrue(
            "Most video URLs should be allowed",
            videoAllowed >= videoUrls.size * 0.7
        )
        assertTrue(
            "All ad URLs should be blocked",
            adsBlocked == adUrls.size
        )
    }

    @Test
    fun `test BlockerClient with LuluStream-Luluvdo CDN variations`() {
        val site = SupportedSites.ALL_SITES.find { it.siteName == "LuluStream-Luluvdo" }!!
        val client = BlockerClient(site.testUrls.first())

        site.testUrls.forEach { url ->
            every { request.url.toString() } returns url
            
            val shouldBlock = client.shouldInterceptRequest(webView, request)
            
            assertNull(
                "LuluStream CDN URL should not be blocked: $url",
                shouldBlock
            )
        }
    }

    @Test
    fun `test BlockerClient with same domain navigation`() {
        val baseUrl = "https://uqload.ws/embed-test.html"
        val client = BlockerClient(baseUrl)
        
        // Same domain URLs should be allowed
        val sameDomainUrls = listOf(
            "https://uqload.ws/player.js",
            "https://uqload.ws/styles.css",
            "https://cdn.uqload.ws/video.mp4"
        )
        
        sameDomainUrls.forEach { url ->
            every { request.url.toString() } returns url
            
            val response = client.shouldInterceptRequest(webView, request)
            
            assertNull(
                "Same domain URL should not be blocked: $url",
                response
            )
        }
    }

    @Test
    fun `test BlockerClient shouldOverrideUrlLoading with video sites`() {
        val site = SupportedSites.ALL_SITES.find { it.siteName == "Uqload" }!!
        val client = BlockerClient(site.testUrls.first())

        site.testUrls.forEach { url ->
            every { request.url.toString() } returns url
            
            val shouldOverride = client.shouldOverrideUrlLoading(webView, request)
            
            // Video site URLs should not be overridden (return false)
            assertFalse(
                "Video site URL should not be overridden: $url",
                shouldOverride
            )
        }
    }

    @Test
    fun `test BlockerClient with all 19 supported sites`() {
        var totalTests = 0
        var passedTests = 0
        
        SupportedSites.ALL_SITES.forEach { site ->
            val client = BlockerClient(site.testUrls.first())
            
            site.testUrls.forEach { url ->
                totalTests++
                every { request.url.toString() } returns url
                
                val response = client.shouldInterceptRequest(webView, request)
                if (response == null) {
                    passedTests++
                }
            }
        }
        
        println("BlockerClient Integration Test: $passedTests/$totalTests URLs allowed")
        
        // At least 80% of video URLs should be allowed
        val successRate = passedTests.toDouble() / totalTests * 100.0
        assertTrue(
            "Success rate should be at least 80%, was ${"%.2f".format(successRate)}%",
            successRate >= 80.0
        )
    }
}
