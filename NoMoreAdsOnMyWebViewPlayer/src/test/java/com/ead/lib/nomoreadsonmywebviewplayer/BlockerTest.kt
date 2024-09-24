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

@RunWith(AndroidJUnit4::class)
class BlockerTest {

    private lateinit var context: Context

    @Before
    fun onSetup() {
        context = ApplicationProvider.getApplicationContext()
        runBlocking {
            Blocker.init(context)
            Blocker.awaitKeywordsLoaded()
        }
    }

    @Test
    fun `test using and unknown keywords, should return false`() {
        assertTrue(!Blocker.isPermitted("http://example.com"))
        assertTrue(!Blocker.isPermitted("https://google.com"))
        assertTrue(!Blocker.isPermitted("https://unknown.com"))
        assertTrue(!Blocker.isPermitted("https://anotherunknown.com"))
    }

    @Test
    fun `test using a known keywords cases, should return true`() {
        assertTrue(Blocker.isPermitted("https://uqload.ws/embed-lpf9yop57kfr.html"))
        assertTrue(Blocker.isPermitted("https://mp4upload.com/embed-lpf9yop57kfr.html"))
        assertTrue(Blocker.isPermitted("https://filemoon.sx/e/hvfk8xj9n0r5"))
        assertTrue(Blocker.isPermitted("https://luluvdo.com/videothumbs/o8p2wu6ralic"))
        assertTrue(Blocker.isPermitted("https://cdn.luluvdo.com/embed/o8p2wu6ralic"))
        assertTrue(Blocker.isPermitted("https://cloudflare.luluvdo.com/e/o8p2wu6ralic"))
        assertTrue(Blocker.isPermitted("https://jwplayer.filemoon.com/f/o8p2wu6ralic"))
    }
}