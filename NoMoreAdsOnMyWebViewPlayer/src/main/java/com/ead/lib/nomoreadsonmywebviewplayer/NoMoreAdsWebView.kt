package com.ead.lib.nomoreadsonmywebviewplayer

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.webkit.WebSettings
import android.webkit.WebView
import com.ead.lib.nomoreadsonmywebviewplayer.models.BlockerClient

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
        webViewClient = object : BlockerClient() {

            /**
             * Getting the blocker client reference to handle operations
             */
            init {
                this@BaseWebView.client = this
            }

            override val exceptionWordKeys: List<String>
                get() = listOf(
                    "your key word"
                )
        }
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
}

/**
 * No more ads web view player to block the ads, its works in many embed player sites
 */
open class NoMoreAdsWebView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?= null,
    defStyle : Int=0,
    defStylerRes: Int=0) : BaseWebView(context,attrs,defStyle,defStylerRes)