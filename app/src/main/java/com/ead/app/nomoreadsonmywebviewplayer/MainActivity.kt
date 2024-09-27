package com.ead.app.nomoreadsonmywebviewplayer

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.ead.app.nomoreadsonmywebviewplayer.presentation.main.MainEvent
import com.ead.app.nomoreadsonmywebviewplayer.presentation.main.MainViewModel
import com.ead.app.nomoreadsonmywebviewplayer.presentation.theme.NoMoreAdsOnMyWebViewPlayerTheme
import com.ead.app.nomoreadsonmywebviewplayer.util.TestTags
import com.ead.lib.nomoreadsonmywebviewplayer.NoMoreAdsWebView
import com.ead.lib.nomoreadsonmywebviewplayer.core.Blocker
import com.ead.lib.nomoreadsonmywebviewplayer.models.BlockerClient

class MainActivity : ComponentActivity() {

    private val viewModel : MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Blocker.init(this)
        enableEdgeToEdge()
        setContent {
            NoMoreAdsOnMyWebViewPlayerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NoMoreAdsWebView(
                        modifier = Modifier.padding(innerPadding),
                        event = viewModel::onEvent
                    )

                    LaunchedEffect(key1 = true) {
                        viewModel.onEvent(
                            MainEvent.LoadingUrl(
                                url = "https://adblock-tester.com/"
                            )
                        )
                    }
                }
            }
        }
    }
}

@SuppressLint("ResourceType")
@Composable
fun NoMoreAdsWebView(modifier: Modifier = Modifier, event: (MainEvent) -> Unit) {
    AndroidView(
        modifier = modifier
            .fillMaxSize()
            .testTag(TestTags.WEB_VIEW_CONTAINER),
        factory = { context ->
            NoMoreAdsWebView(context).apply {
                id = R.id.test_id_no_more_ads_web_view
                event(MainEvent.InitializeWebView(this))
                webViewClient = object : BlockerClient() {
                    override val exceptionWordKeys: List<String>
                        get() = listOf(
                            /**
                             * Your Key words
                             */
                        )

                    /**
                     * Replacement option for ShouldOverrideUrlLoading(view, request)
                     */
                    override fun onOverrideUrlLoading(
                        view: WebView?,
                        request: WebResourceRequest?
                    ): Boolean {
                        print("What amazing replacement :)")
                        return super.onOverrideUrlLoading(view, request)
                    }

                    /**
                     * Replacement option for ShouldInterceptRequest(view, request)
                     */
                    override fun onInterceptRequest(
                        view: WebView?,
                        request: WebResourceRequest?
                    ): WebResourceResponse? {
                        print("What amazing replacement :)")
                        return super.onInterceptRequest(view, request)
                    }
                }
            }
        }
    )
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NoMoreAdsOnMyWebViewPlayerTheme {
        NoMoreAdsWebView(
            event = {}
        )
    }
}