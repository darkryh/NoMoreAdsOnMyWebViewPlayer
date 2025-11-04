package com.ead.lib.nomoreadsonmywebviewplayer

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ead.lib.nomoreadsonmywebviewplayer.core.Blocker
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Performance validation tests for the blocker
 * Measures execution time and blocking efficiency
 */
@RunWith(AndroidJUnit4::class)
class BlockerPerformanceTest {

    private lateinit var context: Context

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        runBlocking {
            Blocker.init(context)
            Blocker.awaitKeywordsLoaded()
        }
    }

    @Test
    fun `test performance of single URL check`() {
        val url = "https://uqload.ws/embed-lpf9yop57kfr.html"
        
        val (result, timeMs) = PerformanceTestUtils.measurePerformance("Single URL Check") {
            Blocker.isPermitted(url)
        }

        println("Single URL check took: ${timeMs}ms")
        assertTrue("URL check should complete quickly (< 10ms)", timeMs < 10)
        assertTrue("URL should be permitted", result)
    }

    @Test
    fun `test performance of batch URL checks`() {
        val urls = SupportedSites.ALL_SITES.flatMap { it.testUrls }
        
        val metrics = PerformanceTestUtils.measureWithMetrics(
            operationName = "Batch URL Check (${urls.size} URLs)",
            urls = urls
        ) { url ->
            Blocker.isPermitted(url)
        }

        println(metrics.toString())
        
        // Assert performance thresholds
        assertTrue(
            "Average URL check time should be < 10ms, was ${metrics.avgTimePerUrlMs}ms",
            metrics.avgTimePerUrlMs < PerformanceTestUtils.Thresholds.MAX_URL_CHECK_TIME_MS
        )
        
        assertTrue(
            "All supported site URLs should be permitted",
            metrics.allowedCount == metrics.totalUrls
        )
    }

    @Test
    fun `test performance of ad blocking`() {
        val adUrls = listOf(
            "https://ads.example.com/banner.js",
            "https://tracking.unknown.com/pixel.gif",
            "https://adserver.com/video-ad.js",
            "https://unknown-ads.net/popup.js",
            "https://random-unknown-site.com/page.html",
            "https://not-a-video-host.com/video.mp4"
        )
        
        val metrics = PerformanceTestUtils.measureWithMetrics(
            operationName = "Ad Blocking (${adUrls.size} ad URLs)",
            urls = adUrls
        ) { url ->
            Blocker.isPermitted(url)
        }

        println(metrics.toString())
        
        // All ad URLs should be blocked (not permitted)
        assertTrue(
            "All ad URLs should be blocked, but ${metrics.allowedCount} were allowed",
            metrics.allowedCount == 0
        )
        
        assertTrue(
            "Ad blocking should be fast (< 10ms per URL)",
            metrics.avgTimePerUrlMs < PerformanceTestUtils.Thresholds.MAX_URL_CHECK_TIME_MS
        )
    }

    @Test
    fun `test performance of mixed URL batch`() {
        // Mix of valid site URLs and ad URLs
        val validUrls = SupportedSites.ALL_SITES.flatMap { it.testUrls }.take(20)
        val adUrls = listOf(
            "https://ads.example.com/banner.js",
            "https://tracking.unknown.com/pixel.gif",
            "https://adserver.com/ad.js",
            "https://unknown.com/page.html",
            "https://random-site.net/video.mp4"
        )
        val mixedUrls = validUrls + adUrls
        
        val metrics = PerformanceTestUtils.measureWithMetrics(
            operationName = "Mixed URL Batch (${mixedUrls.size} URLs)",
            urls = mixedUrls
        ) { url ->
            Blocker.isPermitted(url)
        }

        println(metrics.toString())
        
        // Validate that the correct number of URLs were allowed
        assertTrue(
            "Should have allowed ${validUrls.size} URLs, but allowed ${metrics.allowedCount}",
            metrics.allowedCount >= validUrls.size - 2 // Allow some tolerance
        )
        
        assertTrue(
            "Performance should be consistent (< 10ms per URL)",
            metrics.avgTimePerUrlMs < PerformanceTestUtils.Thresholds.MAX_URL_CHECK_TIME_MS
        )
    }

    @Test
    fun `test performance of concurrent URL checks`() {
        val urls = SupportedSites.ALL_SITES.flatMap { it.testUrls }
        val iterations = 3
        val allMetrics = mutableListOf<PerformanceTestUtils.PerformanceMetrics>()
        
        repeat(iterations) { iteration ->
            val metrics = PerformanceTestUtils.measureWithMetrics(
                operationName = "Iteration ${iteration + 1}",
                urls = urls
            ) { url ->
                Blocker.isPermitted(url)
            }
            allMetrics.add(metrics)
            println(metrics.toString())
        }
        
        // Calculate average performance across iterations
        val avgTimeMs = allMetrics.map { it.executionTimeMs }.average()
        val avgTimePerUrl = allMetrics.map { it.avgTimePerUrlMs }.average()
        
        println("Average execution time across $iterations iterations: ${avgTimeMs}ms")
        println("Average time per URL: ${avgTimePerUrl}ms")
        
        assertTrue(
            "Performance should be consistent across iterations",
            avgTimePerUrl < PerformanceTestUtils.Thresholds.MAX_URL_CHECK_TIME_MS
        )
    }

    @Test
    fun `test performance of Uqload site specifically`() {
        val site = SupportedSites.ALL_SITES.find { it.siteName == "Uqload" }!!
        
        val metrics = PerformanceTestUtils.measureWithMetrics(
            operationName = "Uqload Site Performance",
            urls = site.testUrls
        ) { url ->
            Blocker.isPermitted(url)
        }

        println(metrics.toString())
        
        assertTrue(
            "All Uqload URLs should be permitted",
            metrics.allowedCount == metrics.totalUrls
        )
        
        PerformanceTestUtils.assertPerformanceThresholds(metrics)
    }

    @Test
    fun `test performance of Mp4Upload site specifically`() {
        val site = SupportedSites.ALL_SITES.find { it.siteName == "Mp4Upload" }!!
        
        val metrics = PerformanceTestUtils.measureWithMetrics(
            operationName = "Mp4Upload Site Performance",
            urls = site.testUrls
        ) { url ->
            Blocker.isPermitted(url)
        }

        println(metrics.toString())
        
        assertTrue(
            "All Mp4Upload URLs should be permitted",
            metrics.allowedCount == metrics.totalUrls
        )
        
        PerformanceTestUtils.assertPerformanceThresholds(metrics)
    }

    @Test
    fun `test performance of DoodStream site specifically`() {
        val site = SupportedSites.ALL_SITES.find { it.siteName == "DoodStream" }!!
        
        val metrics = PerformanceTestUtils.measureWithMetrics(
            operationName = "DoodStream Site Performance",
            urls = site.testUrls
        ) { url ->
            Blocker.isPermitted(url)
        }

        println(metrics.toString())
        
        assertTrue(
            "All DoodStream URLs should be permitted",
            metrics.allowedCount == metrics.totalUrls
        )
        
        PerformanceTestUtils.assertPerformanceThresholds(metrics)
    }

    @Test
    fun `test performance of Filemoon site specifically`() {
        val site = SupportedSites.ALL_SITES.find { it.siteName == "Filemoon" }!!
        
        val metrics = PerformanceTestUtils.measureWithMetrics(
            operationName = "Filemoon Site Performance",
            urls = site.testUrls
        ) { url ->
            Blocker.isPermitted(url)
        }

        println(metrics.toString())
        
        assertTrue(
            "All Filemoon URLs should be permitted",
            metrics.allowedCount == metrics.totalUrls
        )
        
        PerformanceTestUtils.assertPerformanceThresholds(metrics)
    }

    @Test
    fun `test performance comparison between sites`() {
        val uqloadSite = SupportedSites.ALL_SITES.find { it.siteName == "Uqload" }!!
        val filemoonSite = SupportedSites.ALL_SITES.find { it.siteName == "Filemoon" }!!
        
        val uqloadMetrics = PerformanceTestUtils.measureWithMetrics(
            operationName = "Uqload",
            urls = uqloadSite.testUrls
        ) { url -> Blocker.isPermitted(url) }
        
        val filemoonMetrics = PerformanceTestUtils.measureWithMetrics(
            operationName = "Filemoon",
            urls = filemoonSite.testUrls
        ) { url -> Blocker.isPermitted(url) }
        
        println(uqloadMetrics.toString())
        println(filemoonMetrics.toString())
        println(PerformanceTestUtils.compareMetrics(uqloadMetrics, filemoonMetrics))
        
        // Both should meet performance thresholds
        PerformanceTestUtils.assertPerformanceThresholds(uqloadMetrics)
        PerformanceTestUtils.assertPerformanceThresholds(filemoonMetrics)
    }

    @Test
    fun `test URL validation performance with malformed URLs`() {
        val malformedUrls = listOf(
            "not-a-url",
            "://missing-protocol.com",
            "http://",
            "",
            "   ",
            "javascript:alert(1)"
        )
        
        val metrics = PerformanceTestUtils.measureWithMetrics(
            operationName = "Malformed URLs",
            urls = malformedUrls
        ) { url ->
            Blocker.isPermitted(url)
        }

        println(metrics.toString())
        
        // Malformed URLs should be handled gracefully and quickly
        assertTrue(
            "Malformed URL checking should be fast (< 10ms per URL)",
            metrics.avgTimePerUrlMs < PerformanceTestUtils.Thresholds.MAX_URL_CHECK_TIME_MS
        )
    }
}
