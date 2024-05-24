package com.ead.lib.nomoreadsonmywebviewplayer

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebSettings
import android.webkit.WebView
import com.ead.lib.nomoreadsonmywebviewplayer.core.Blocker
import com.ead.lib.nomoreadsonmywebviewplayer.models.BlockerClient
import java.io.ByteArrayInputStream

/**
 * Base Class to configure the blocker for the web view
 */
@SuppressLint("SetJavaScriptEnabled")
open class BaseWebView @JvmOverloads constructor(
    /**
     * param context for the web view
     */
    context: Context,
    /**
     * param attributeSet for the web view
     */
    attrs: AttributeSet?= null,
    /**
     * param defStyle for the web view
     */
    defStyle : Int=0,
    /**
     * param defStyleRes for the web view
     */
    defStylerRes: Int=0)

    : WebView(context,attrs,defStyle,defStylerRes) {

    /**
     * blocker client to handle the operations
     */
    private var client : BlockerClient

    /**
     * urls as filter in the loading
     */
    private val loadedUrls: Map<String, Boolean> = HashMap()


    /**
     * Initializing the setup for the web view
     */
    init {


        /**
         * Setup the web view settings
         */
        settings.setupRequirements()


        /**
         * Adding the blocker client to the web view client
         */
        webViewClient = object : BlockerClient(url ?:"about:blank") {


            override fun onPassingOverrideUrl(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                /**
                 * Getting the url
                 */
                val url = request?.url.toString()



                /**
                 * Initializing is permitted
                 */
                val isPermitted: Boolean



                /**
                 * Checking if the url is already loaded
                 */
                if (!loadedUrls.containsKey(url)) {


                    /**
                     * Checking if the url is permitted
                     */
                    isPermitted = Blocker.isPermitted(url)



                    /**
                     * Adding the url to the loaded urls
                     */
                    loadedUrls.containsValue(isPermitted)
                } else {


                    /**
                     * Getting the value of the url
                     * if it is permitted in case its on loaded
                     */
                    isPermitted = loadedUrls[url] == true
                }


                /**
                 * Returning the response in case the url is permitted
                 * return a normal resource
                 * if not just an empty resource
                 */
                return !isPermitted
            }

            /**
             * Intercept the request for the url
             * that pass the first filter
             * verifying if the url has a key word
             */
            override fun onPassingInterceptRequest(
                view: WebView?,
                request: WebResourceRequest?
            ): WebResourceResponse? {

                /**
                 * Getting the url
                 */
                val url = request?.url.toString()



                /**
                 * Initializing is permitted
                 */
                val isPermitted: Boolean



                /**
                 * Checking if the url is already loaded
                 */
                if (!loadedUrls.containsKey(url)) {


                    /**
                     * Checking if the url is permitted
                     */
                    isPermitted = Blocker.isPermitted(url)



                    /**
                     * Adding the url to the loaded urls
                     */
                    loadedUrls.containsValue(isPermitted)
                } else {


                    /**
                     * Getting the value of the url
                     * if it is permitted in case its on loaded
                     */
                    isPermitted = loadedUrls[url] == true
                }


                /**
                 * Returning the response in case the url is permitted
                 * return a normal resource
                 * if not just an empty resource
                 */
                return if (isPermitted) super.onPassingInterceptRequest(view, request)
                else emptyResource
            }
        }


        /**
         * Getting the blocker client reference to handle operations
         */
        client = webViewClient as BlockerClient
    }

    /**
     * Setup the web view settings requirements to work properly
     */
    private fun WebSettings.setupRequirements() {


        /**
         * Enable Javascript
         */
        javaScriptEnabled = true


        /**
         * Enable DOM Storage
         */
        domStorageEnabled = true


        /**
         * Completing the layout params
         * to see the render on screen
         */
        layoutParams = LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
    }


    /**
     * Load the url in the web view
     * overriding to update the blocker client
     */
    override fun loadUrl(url: String) {


        /**
         * Updating the blocker client url
         */
        client.url = url



        /**
         * Loading the url
         */
        super.loadUrl(url)
    }



    /**
     * Empty resource for the web view to block the request
     */
    private val emptyResource : WebResourceResponse =
        WebResourceResponse(
            "text/plain",
            "utf-8",
            ByteArrayInputStream(
                "".toByteArray()
            )
        )
}

/**
 * No more ads web view player to block the ads, its works in many embed player sites
 */
class NoMoreAdsWebView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?= null,
    defStyle : Int=0,
    defStylerRes: Int=0) : BaseWebView(context,attrs,defStyle,defStylerRes)