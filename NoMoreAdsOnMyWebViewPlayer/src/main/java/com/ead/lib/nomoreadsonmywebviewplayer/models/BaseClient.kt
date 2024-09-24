package com.ead.lib.nomoreadsonmywebviewplayer.models

import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.VisibleForTesting
import java.io.ByteArrayInputStream
import java.net.MalformedURLException
import java.net.URL

/**
 * Blocker client that controls the event
 * to validate the loading of a url
 */
open class BaseClient(
    /**
     * @param *url to validate is called when a url is loading
     */
    var url : String
    /**
     * Inherited class from WebViewClient
     */
) :  WebViewClient() {


    /**
     * Empty resource for the web view to block the request
     */
    internal val emptyResource : WebResourceResponse =
        WebResourceResponse(
            "text/plain",
            "utf-8",
            ByteArrayInputStream(
                "".toByteArray()
            )
        )


    /**
     * @return domain of the url to intercept and validate the loading
     */
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    private val domain: String? get() = run {

        /**
         * Getting the url to validated isn't malformed
         */
        val url = try { URL(this@BaseClient.url) } catch (e: MalformedURLException) { null }


        /**
         * Getting the host of the url by splitting the domain
         */
        val hostParts = url?.host?.split('.')


        /**
         * if the host parts are more than 2 return the second last part, that is consider the domain
         */
        return if (hostParts != null && hostParts.size >= 2) {
            hostParts[hostParts.size - 2]
        } else {

            /**
             * if the host parts are less than 2 return the host
             */
            url?.host
        }
    }




    /**
     * @return regex to validate the url by the domain in the loading url
     */
    private val permittedRegexByDomain : Regex? get() = run {
        val domain = domain ?: return null
        ".*${domain}.*".toRegex()
    }


    /**
     * @return regex to validate the url of embed sites
     */
    private val permittedRegexes : List<Regex> = listOf(
        """https://[a-zA-Z0-9.-]+/(e|embed|watch|player)/[a-zA-Z0-9]+""".toRegex()
    )


    /**
     * Used to validate the overriding of the url
     * in basic way just checking by domain
     * in case its passes the regex
     * call onPassingOverrideUrl
     * to check the next layer of security [onPassingInterceptRequest]
     */
    @Deprecated("use OnPassingOverrideUrl Instead", ReplaceWith("OnPassingOverrideUrl(view, request)"))
    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        return if (onPassingFirstLayerOfSecurity(request,true))
            false
        else
            onPassingOverrideUrl(view, request)
    }



    /**
     * Used to validate the intercepting of the url
     * in basic way just checking by domain
     * in case its passes the regex
     * call onPassingInterceptRequest
     * to check the next layer of security [onPassingInterceptRequest]
     */
    @Deprecated("use onPassingInterceptRequest Instead", ReplaceWith("onPassingInterceptRequest(view, request)"))
    override fun shouldInterceptRequest(
        view: WebView?,
        request: WebResourceRequest?
    ): WebResourceResponse? {


        /**
         * check the structure of the url using the regex
         */
        return if (onPassingFirstLayerOfSecurity(request))
            super.shouldInterceptRequest(view, request)
        else
            onPassingInterceptRequest(view, request)
    }


    protected fun onPassingFirstLayerOfSecurity(request: WebResourceRequest?, isOverriding : Boolean = false) : Boolean {
        return permittedRegexByDomain?.matches(request?.url.toString()) == true ||
                permittedRegexes.any {
                    val urlMatchToRedirect = it.matches(request?.url.toString())
                    if (isOverriding && urlMatchToRedirect) url = request?.url.toString()
                    urlMatchToRedirect
                }
    }

    /**
     * Used to validate the overriding of the url on the next layer of security
     */
    open fun onPassingOverrideUrl(view: WebView?, request: WebResourceRequest?) : Boolean = true



    /**
     * Used to validate the intercepting of the url on the next layer of security
     */
    open fun onPassingInterceptRequest(view: WebView?, request: WebResourceRequest?) : WebResourceResponse? = null
}