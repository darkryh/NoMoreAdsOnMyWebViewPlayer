package com.ead.lib.nomoreadsonmywebviewplayer

/**
 * Test data for supported video hosting sites
 */
data class SupportedSiteTestData(
    val siteName: String,
    val testUrls: List<String>,
    val expectedKeywords: List<String>,
    val adsUrls: List<String> = listOf()
)

object SupportedSites {
    /**
     * All supported sites with their test URLs
     * Based on README.md supported websites list
     */
    val ALL_SITES = listOf(
        SupportedSiteTestData(
            siteName = "Uqload",
            testUrls = listOf(
                "https://uqload.ws/embed-lpf9yop57kfr.html",
                "https://uqload.com/embed-abc123.html",
                "https://uqload.io/e/test123"
            ),
            expectedKeywords = listOf("uqload"),
            adsUrls = listOf(
                "https://ads.example.com/banner.js",
                "https://tracking.example.com/pixel.gif"
            )
        ),
        SupportedSiteTestData(
            siteName = "Mp4Upload",
            testUrls = listOf(
                "https://mp4upload.com/embed-lpf9yop57kfr.html",
                "https://www.mp4upload.com/embed-test123.html"
            ),
            expectedKeywords = listOf("mp4"),
            adsUrls = listOf("https://ads.example.com/video-ad.js")
        ),
        SupportedSiteTestData(
            siteName = "DoodStream",
            testUrls = listOf(
                "https://dood.watch/e/abc123",
                "https://doodstream.com/e/test456",
                "https://dood.to/d/video789"
            ),
            expectedKeywords = listOf("d000d", "dood"),
            adsUrls = listOf("https://ads.doodstream.com/ad.js")
        ),
        SupportedSiteTestData(
            siteName = "Filelions",
            testUrls = listOf(
                "https://filelions.com/e/abc123",
                "https://filelions.live/embed/test456"
            ),
            expectedKeywords = listOf("stream", "hls"),
            adsUrls = listOf()
        ),
        SupportedSiteTestData(
            siteName = "Filemoon",
            testUrls = listOf(
                "https://filemoon.sx/e/hvfk8xj9n0r5",
                "https://filemoon.com/embed/test123",
                "https://jwplayer.filemoon.com/f/o8p2wu6ralic"
            ),
            expectedKeywords = listOf(".sx", "jwplayer"),
            adsUrls = listOf("https://ads.filemoon.sx/banner.js")
        ),
        SupportedSiteTestData(
            siteName = "VidGuard",
            testUrls = listOf(
                "https://vidguard.to/e/abc123",
                "https://vidguard.com/embed/test456"
            ),
            expectedKeywords = listOf("guardstorage"),
            adsUrls = listOf()
        ),
        SupportedSiteTestData(
            siteName = "LuluStream-Luluvdo",
            testUrls = listOf(
                "https://luluvdo.com/videothumbs/o8p2wu6ralic",
                "https://cdn.luluvdo.com/embed/o8p2wu6ralic",
                "https://cloudflare.luluvdo.com/e/o8p2wu6ralic"
            ),
            expectedKeywords = listOf("videothumbs", "cdn", "cloudflare"),
            adsUrls = listOf("https://ads.luluvdo.com/ad.js")
        ),
        SupportedSiteTestData(
            siteName = "Streamtape",
            testUrls = listOf(
                "https://streamtape.com/e/abc123",
                "https://streamtape.net/embed/test456"
            ),
            expectedKeywords = listOf("tapecontent", "stream"),
            adsUrls = listOf("https://ads.streamtape.com/banner.js")
        ),
        SupportedSiteTestData(
            siteName = "Okru",
            testUrls = listOf(
                "https://ok.ru/videoembed/abc123",
                "https://ok.ru/video/test456"
            ),
            expectedKeywords = listOf("mail.ru"),
            adsUrls = listOf()
        ),
        SupportedSiteTestData(
            siteName = "StreamWish",
            testUrls = listOf(
                "https://streamwish.to/e/abc123",
                "https://streamwish.com/embed/test456"
            ),
            expectedKeywords = listOf("stream", "hls"),
            adsUrls = listOf("https://ads.streamwish.com/ad.js")
        ),
        SupportedSiteTestData(
            siteName = "Voe",
            testUrls = listOf(
                "https://voe.sx/e/abc123",
                "https://voe.com/embed/test456"
            ),
            expectedKeywords = listOf("voe"),
            adsUrls = listOf("https://ads.voe.sx/banner.js")
        ),
        SupportedSiteTestData(
            siteName = "Senvid",
            testUrls = listOf(
                "https://senvid.com/e/abc123",
                "https://senvid.net/embed/test456"
            ),
            expectedKeywords = listOf("stream", "hls"),
            adsUrls = listOf()
        ),
        SupportedSiteTestData(
            siteName = "Anonfiles",
            testUrls = listOf(
                "https://anonfiles.com/abc123/file",
                "https://cdn.anonfiles.com/file/test456"
            ),
            expectedKeywords = listOf("cdn"),
            adsUrls = listOf("https://ads.anonfiles.com/ad.js")
        ),
        SupportedSiteTestData(
            siteName = "Bayfiles",
            testUrls = listOf(
                "https://bayfiles.com/abc123/file",
                "https://cdn.bayfiles.com/file/test456"
            ),
            expectedKeywords = listOf("cdn"),
            adsUrls = listOf()
        ),
        SupportedSiteTestData(
            siteName = "Fembed",
            testUrls = listOf(
                "https://fembed.com/v/abc123",
                "https://fembed.net/embed/test456"
            ),
            expectedKeywords = listOf("stream", "hls"),
            adsUrls = listOf("https://ads.fembed.com/banner.js")
        ),
        SupportedSiteTestData(
            siteName = "Mega",
            testUrls = listOf(
                "https://mega.nz/file/abc123",
                "https://mega.co.nz/embed/test456"
            ),
            expectedKeywords = listOf("video-delivery"),
            adsUrls = listOf()
        ),
        SupportedSiteTestData(
            siteName = "YourUpload",
            testUrls = listOf(
                "https://yourupload.com/embed/abc123",
                "https://yourupload.net/watch/test456"
            ),
            expectedKeywords = listOf("stream", "hls"),
            adsUrls = listOf("https://ads.yourupload.com/ad.js")
        ),
        SupportedSiteTestData(
            siteName = "Maru",
            testUrls = listOf(
                "https://maru.video/e/abc123",
                "https://maru.live/embed/test456"
            ),
            expectedKeywords = listOf("stream", "hls"),
            adsUrls = listOf()
        ),
        SupportedSiteTestData(
            siteName = "GoodStream",
            testUrls = listOf(
                "https://goodstream.net/e/abc123",
                "https://goodstream.com/embed/test456"
            ),
            expectedKeywords = listOf("stream", "hls"),
            adsUrls = listOf("https://ads.goodstream.net/banner.js")
        )
    )
}
