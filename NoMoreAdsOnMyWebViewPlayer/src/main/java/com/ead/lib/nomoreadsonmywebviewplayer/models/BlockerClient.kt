package com.ead.lib.nomoreadsonmywebviewplayer.models

import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import com.ead.lib.nomoreadsonmywebviewplayer.core.Blocker

open class BlockerClient(
    /**
     * @param *url to validate is called when a url is loading
     */
    url : String ?= null,
    /**
     * @param *exceptionWorkKeys to validate is called when a url is loading
     */
    protected open val exceptionWordKeys : List<String> = listOf()


    /**
     * inherited class from base and
     */
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
         * Checking if the url is permitted
         */
        val isPermitted = isPermitted(url)

        /**
         * Returning the response in case the url is permitted
         * get redirected to acceptable page
         */
        return if (isPermitted) super.onPassingOverrideUrl(view, request)
        else onOverrideUrlLoading(view, request)
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
         * Returning the response in case the url is permitted
         * return a normal resource
         * if not just an empty resource
         */

        return if (isPermitted(url)) super.onPassingInterceptRequest(view, request)
        else onInterceptRequest(view, request)
    }



    /**
     * Check if the url is permitted
     */
    private fun isPermitted(url: String): Boolean {

        /**
         * Checking if the url is permitted
         * by customise wordKeys List
         */
        if (exceptionWordKeys.any { url.contains(it, ignoreCase = true) }) return true


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

        return isPermitted
    }

    /**
     * Used to validate, to replace shouldOverrideUrlLoading
     * his value by default is false
     * if want to accept redirected override
     * and return true
     */
    protected open fun onOverrideUrlLoading(view: WebView?, request: WebResourceRequest?) : Boolean = false

    /**
     * Used to validate, to replace shouldInterceptRequest
     */
    protected open fun onInterceptRequest(view: WebView?, request: WebResourceRequest?) : WebResourceResponse? = emptyResource
}