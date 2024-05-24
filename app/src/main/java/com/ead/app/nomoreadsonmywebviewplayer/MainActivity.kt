package com.ead.app.nomoreadsonmywebviewplayer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.ead.app.nomoreadsonmywebviewplayer.ui.theme.NoMoreAdsOnMyWebViewPlayerTheme
import com.ead.lib.nomoreadsonmywebviewplayer.NoMoreAdsWebView
import com.ead.lib.nomoreadsonmywebviewplayer.core.Blocker

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Blocker.init(this)
        enableEdgeToEdge()
        setContent {
            NoMoreAdsOnMyWebViewPlayerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TestWebView(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun TestWebView(modifier: Modifier = Modifier) {
    AndroidView(
        modifier = modifier.fillMaxSize(),
        factory = { context ->
            NoMoreAdsWebView(context).apply {
                loadUrl("https://asnwish.com/e/ktqgf3vcaniy")
            }
        }
    )
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NoMoreAdsOnMyWebViewPlayerTheme {
        TestWebView()
    }
}