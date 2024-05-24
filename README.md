# NoMoreAdsOnMyWebViewPlayer

It's an Android Library that permit custom properties and remove ads from embed websites using WebView:
- Removed ads from the webview
- Add a custom blocker for the user (To-do)
- Add a metadata capture for every website that blocks (To-do)

## Supported Websites

1. **Uqload**
2. **Mp4Upload**
3. **DoodStream**
4. **Filelions**
5. **Filemoon**
6. **VidGuard**
7. **LuluStream**
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

## Installation - Gradle
```groovy  
repositories {   
    maven { url 'https://jitpack.io' }  
}

dependencies {  
    implementation("com.github.darkryh:NoMoreAdsOnMyWebViewPlayer:$version")
} 
```  
# Example of Configuration
```kotlin
import com.ead.lib.nomoreadsonmywebviewplayer.core.Blocker

class application : Application() {

    fun onCreate() {
        Blocker.init(this)
    }
}
```

# Example of Use Case Kotlin
```kotlin
class MainActivity : ComponentActivity() {

    fun loadingUrl(context : Context) {
        val webView = NoMoreAdsWebView(context)

        webView.load("your embed Url")
    }
}
```

# Want to colaborate
f you want to help or collaborate, feel free to contact me on X account @Darkryh or just make a request.
