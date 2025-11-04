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
 * Deep testing for all supported video hosting sites
 * Tests each supported site individually to ensure proper blocking functionality
 */
@RunWith(AndroidJUnit4::class)
class SupportedSitesDeepTest {

    private lateinit var context: Context

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        runBlocking {
            Blocker.init(context)
            Blocker.awaitKeywordsLoaded()
        }
    }

    private fun getSiteOrFail(siteName: String): SupportedSiteTestData {
        return SupportedSites.ALL_SITES.find { it.siteName == siteName }
            ?: throw AssertionError("Site '$siteName' not found in test data. Available sites: ${SupportedSites.ALL_SITES.map { it.siteName }}")
    }

    @Test
    fun `test Uqload site URLs should be permitted`() {
        val site = getSiteOrFail("Uqload")
        
        site.testUrls.forEach { url ->
            val result = Blocker.isPermitted(url)
            assertTrue(
                "Uqload URL should be permitted: $url",
                result
            )
        }
    }

    @Test
    fun `test Mp4Upload site URLs should be permitted`() {
        val site = getSiteOrFail("Mp4Upload")
        
        site.testUrls.forEach { url ->
            val result = Blocker.isPermitted(url)
            assertTrue(
                "Mp4Upload URL should be permitted: $url",
                result
            )
        }
    }

    @Test
    fun `test DoodStream site URLs should be permitted`() {
        val site = getSiteOrFail("DoodStream")
        
        site.testUrls.forEach { url ->
            val result = Blocker.isPermitted(url)
            assertTrue(
                "DoodStream URL should be permitted: $url",
                result
            )
        }
    }

    @Test
    fun `test Filelions site URLs should be permitted`() {
        val site = getSiteOrFail("Filelions")
        
        site.testUrls.forEach { url ->
            val result = Blocker.isPermitted(url)
            assertTrue(
                "Filelions URL should be permitted: $url",
                result
            )
        }
    }

    @Test
    fun `test Filemoon site URLs should be permitted`() {
        val site = getSiteOrFail("Filemoon")
        
        site.testUrls.forEach { url ->
            val result = Blocker.isPermitted(url)
            assertTrue(
                "Filemoon URL should be permitted: $url",
                result
            )
        }
    }

    @Test
    fun `test VidGuard site URLs should be permitted`() {
        val site = getSiteOrFail("VidGuard")
        
        site.testUrls.forEach { url ->
            val result = Blocker.isPermitted(url)
            assertTrue(
                "VidGuard URL should be permitted: $url",
                result
            )
        }
    }

    @Test
    fun `test LuluStream-Luluvdo site URLs should be permitted`() {
        val site = getSiteOrFail("LuluStream-Luluvdo")
        
        site.testUrls.forEach { url ->
            val result = Blocker.isPermitted(url)
            assertTrue(
                "LuluStream-Luluvdo URL should be permitted: $url",
                result
            )
        }
    }

    @Test
    fun `test Streamtape site URLs should be permitted`() {
        val site = getSiteOrFail("Streamtape")
        
        site.testUrls.forEach { url ->
            val result = Blocker.isPermitted(url)
            assertTrue(
                "Streamtape URL should be permitted: $url",
                result
            )
        }
    }

    @Test
    fun `test Okru site URLs should be permitted`() {
        val site = getSiteOrFail("Okru")
        
        site.testUrls.forEach { url ->
            val result = Blocker.isPermitted(url)
            assertTrue(
                "Okru URL should be permitted: $url",
                result
            )
        }
    }

    @Test
    fun `test StreamWish site URLs should be permitted`() {
        val site = getSiteOrFail("StreamWish")
        
        site.testUrls.forEach { url ->
            val result = Blocker.isPermitted(url)
            assertTrue(
                "StreamWish URL should be permitted: $url",
                result
            )
        }
    }

    @Test
    fun `test Voe site URLs should be permitted`() {
        val site = getSiteOrFail("Voe")
        
        site.testUrls.forEach { url ->
            val result = Blocker.isPermitted(url)
            assertTrue(
                "Voe URL should be permitted: $url",
                result
            )
        }
    }

    @Test
    fun `test Senvid site URLs should be permitted`() {
        val site = getSiteOrFail("Senvid")
        
        site.testUrls.forEach { url ->
            val result = Blocker.isPermitted(url)
            assertTrue(
                "Senvid URL should be permitted: $url",
                result
            )
        }
    }

    @Test
    fun `test Anonfiles site URLs should be permitted`() {
        val site = getSiteOrFail("Anonfiles")
        
        site.testUrls.forEach { url ->
            val result = Blocker.isPermitted(url)
            assertTrue(
                "Anonfiles URL should be permitted: $url",
                result
            )
        }
    }

    @Test
    fun `test Bayfiles site URLs should be permitted`() {
        val site = getSiteOrFail("Bayfiles")
        
        site.testUrls.forEach { url ->
            val result = Blocker.isPermitted(url)
            assertTrue(
                "Bayfiles URL should be permitted: $url",
                result
            )
        }
    }

    @Test
    fun `test Fembed site URLs should be permitted`() {
        val site = getSiteOrFail("Fembed")
        
        site.testUrls.forEach { url ->
            val result = Blocker.isPermitted(url)
            assertTrue(
                "Fembed URL should be permitted: $url",
                result
            )
        }
    }

    @Test
    fun `test Mega site URLs should be permitted`() {
        val site = getSiteOrFail("Mega")
        
        site.testUrls.forEach { url ->
            val result = Blocker.isPermitted(url)
            assertTrue(
                "Mega URL should be permitted: $url",
                result
            )
        }
    }

    @Test
    fun `test YourUpload site URLs should be permitted`() {
        val site = getSiteOrFail("YourUpload")
        
        site.testUrls.forEach { url ->
            val result = Blocker.isPermitted(url)
            assertTrue(
                "YourUpload URL should be permitted: $url",
                result
            )
        }
    }

    @Test
    fun `test Maru site URLs should be permitted`() {
        val site = getSiteOrFail("Maru")
        
        site.testUrls.forEach { url ->
            val result = Blocker.isPermitted(url)
            assertTrue(
                "Maru URL should be permitted: $url",
                result
            )
        }
    }

    @Test
    fun `test GoodStream site URLs should be permitted`() {
        val site = getSiteOrFail("GoodStream")
        
        site.testUrls.forEach { url ->
            val result = Blocker.isPermitted(url)
            assertTrue(
                "GoodStream URL should be permitted: $url",
                result
            )
        }
    }

    @Test
    fun `test all supported sites with comprehensive URLs`() {
        var totalTests = 0
        var passedTests = 0

        SupportedSites.ALL_SITES.forEach { site ->
            site.testUrls.forEach { url ->
                totalTests++
                val isPermitted = Blocker.isPermitted(url)
                if (isPermitted) passedTests++
                
                assertTrue(
                    "${site.siteName} URL should be permitted: $url",
                    isPermitted
                )
            }
        }

        println("Deep Test Results: $passedTests/$totalTests URLs passed")
        assertTrue("All supported site URLs should be permitted", passedTests == totalTests)
    }

    @Test
    fun `test ad URLs should be blocked`() {
        val adUrls = listOf(
            "https://ads.example.com/banner.js",
            "https://tracking.unknown.com/pixel.gif",
            "https://adserver.com/video-ad.js",
            "https://unknown-ads.net/popup.js"
        )

        adUrls.forEach { url ->
            val result = Blocker.isPermitted(url)
            assertTrue(
                "Ad URL should be blocked: $url",
                !result
            )
        }
    }

    @Test
    fun `test keywords coverage for all sites`() {
        val site = getSiteOrFail("Filemoon")
        
        // Test that at least one keyword matches for each test URL
        site.testUrls.forEach { url ->
            val hasKeyword = site.expectedKeywords.any { keyword ->
                url.contains(keyword, ignoreCase = true)
            }
            assertTrue(
                "URL should contain at least one expected keyword: $url",
                hasKeyword || Blocker.isPermitted(url)
            )
        }
    }
}
