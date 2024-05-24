package com.ead.app.nomoreadsonmywebviewplayer

import android.os.Bundle
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.ead.app.nomoreadsonmywebviewplayer.presentation.main.MainEvent
import com.ead.app.nomoreadsonmywebviewplayer.presentation.main.MainViewModel
import com.ead.app.nomoreadsonmywebviewplayer.presentation.theme.NoMoreAdsOnMyWebViewPlayerTheme
import com.ead.lib.nomoreadsonmywebviewplayer.NoMoreAdsWebView
import com.ead.lib.nomoreadsonmywebviewplayer.core.Blocker

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
                                url = "https://uqload.to/embed-b0airhfed337.html"
                            )
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun NoMoreAdsWebView(modifier: Modifier = Modifier, event: (MainEvent) -> Unit) {
    AndroidView(
        modifier = modifier.fillMaxSize(),
        factory = { context ->
            NoMoreAdsWebView(context).apply {
                event(MainEvent.InitializeWebView(this))
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