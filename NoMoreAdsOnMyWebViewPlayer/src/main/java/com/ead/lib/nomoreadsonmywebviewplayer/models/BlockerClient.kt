package com.ead.lib.nomoreadsonmywebviewplayer.models

import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import com.ead.lib.nomoreadsonmywebviewplayer.core.Blocker

open class BlockerClient(
    /**
     * @param *url to validate is called when a url is loading
     */
    url : String ?= null
) : BaseClient(url ?:"about:blank") {

    private val loadedUrls: Map<String, Boolean> = HashMap()

    @Deprecated("use onByPassOverridingUrl Instead", ReplaceWith("false"))
    override fun onPassingOverrideUrl(
        view: WebView?,
        request: WebResourceRequest?
    ): Boolean {
        /**
         * Getting the url
         */
        val url = request?.url.toString()

        /**
         * Getting onInterceptingOverridingUrl to have the possibility to
         * in case the they need to customize
         */
        val onBeforeOverridingUrl = onBeforeOverridingUrl(view, request)
        if (onBeforeOverridingUrl != null) return onBeforeOverridingUrl


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
    @Deprecated("use onByPassInterceptRequest Instead", ReplaceWith("false"))
    override fun onPassingInterceptRequest(
        view: WebView?,
        request: WebResourceRequest?
    ): WebResourceResponse? {

        /**
         * Getting the url
         */
        val url = request?.url.toString()


        /**
         * Getting onInterceptingInterceptRequest to have the possibility to
         * in case the they need to customize
         */
        val onBeforeInterceptRequest = onBeforeInterceptRequest(view, request)

        if (onBeforeInterceptRequest != null) return onBeforeInterceptRequest



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

    /**
     * Used to validate, to replace shouldOverrideUrlLoading
     */
    protected open fun onBeforeOverridingUrl(view: WebView?, request: WebResourceRequest?) : Boolean? = null

    /**
     * Used to validate, to replace shouldInterceptRequest
     */
    protected open fun onBeforeInterceptRequest(view: WebView?, request: WebResourceRequest?) : WebResourceResponse? = null
}