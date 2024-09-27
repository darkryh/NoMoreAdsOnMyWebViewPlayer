[![](https://jitpack.io/v/darkryh/NoMoreAdsOnMyWebViewPlayer.svg)](https://jitpack.io/#darkryh/NoMoreAdsOnMyWebViewPlayer)
# NoMoreAdsOnMyWebViewPlayer

It's an Android Library that permit custom properties and remove ads from embed websites using WebView:
- Removed ads from the webview ✔
- Add a custom blocker for the user ✔
- Add a metadata capture for every website that blocks (To-do)

## Supported Websites

1. **Uqload**
2. **Mp4Upload**
3. **DoodStream**
4. **Filelions**
5. **Filemoon**
6. **VidGuard**
7. **LuluStream-Luluvdo**
8. **Streamtape**
9. **Okru**
10. **StreamWish**
11. **Voe**
12. **Senvid**
13. **Anonfiles**
14. **Bayfiles**
15. **Fembed**
16. **Mega**
17. **YourUpload**
18. **Maru**
19. **GoodStream**

## Installation - Gradle
```groovy  
repositories {   
    maven { url 'https://jitpack.io' }  
}

dependencies {  
    implementation("com.github.darkryh:NoMoreAdsOnMyWebViewPlayer:$version")
} 
```  
# Example of Configuration with a custom Blocker
This example is to make compatible with a custom site that contains this media players.
```kotlin
import com.ead.lib.nomoreadsonmywebviewplayer.core.Blocker

class MainActivity : ComponentActivity() {

    fun onCreate() {
        setContent {
            AndroidView(
                modifier = modifier.fillMaxSize(),
                factory = { context ->
                    NoMoreAdsWebView(context).apply {
                        webViewClient = object : BlockerClient() {
                            /**
                             * Exceptions key words that let known
                             * the blocker doesn't have to block
                             */
                            override val exceptionWordKeys: List<String>
                                get() = listOf(
                                    "meta",
                                    "subdomain.meta",
                                    "subdomain.related.to.site"
                                )
                        }
                        loadUrl("https://www.facebook.com/")
                    }
                }
            )
        }    
    }
}
```

# Example configuration
```kotlin
@Composable
fun NoMoreAdsWebView(modifier: Modifier = Modifier) {
    AndroidView(
        modifier = modifier.fillMaxSize(),
        factory = { context ->
            NoMoreAdsWebView(context).apply {
                loadUrl("your embed url")
            }
        }
    )
}
```

# Example of Implementation witch Jetpack Compose
```kotlin
@Composable
fun NoMoreAdsWebView(modifier: Modifier = Modifier) {
    AndroidView(
        modifier = modifier.fillMaxSize(),
        factory = { context ->
            NoMoreAdsWebView(context).apply {
                loadUrl("your embed url")
            }
        }
    )
}
```
# Example of Implementation witch XML file
And loading as a normal webview.
```xml
    <com.ead.lib.nomoreadsonmywebviewplayer.NoMoreAdsWebView
        android:id="@+id/web_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>

```

# Want to collaborate
f you want to help or collaborate, feel free to contact me on X account @Darkryh or just make a request.
