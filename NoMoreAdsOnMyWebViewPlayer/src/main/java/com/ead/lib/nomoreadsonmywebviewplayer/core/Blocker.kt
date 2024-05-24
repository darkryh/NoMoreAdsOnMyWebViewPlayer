package com.ead.lib.nomoreadsonmywebviewplayer.core

import android.content.Context
import android.util.Log
import androidx.annotation.WorkerThread
import okio.BufferedSource
import okio.buffer
import okio.source
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * Object blocker to handle adBlocker for web view
 */
object Blocker {



    /**
     * The file name of the keywords and reference.
     */
    private const val KEYWORDS_FILE = "keywords.txt"



    /**
     * The set of keywords to save in the memory.
     */
    private val KEYWORDS: MutableSet<String> = HashSet()

    /**
     * The executor service to load the keywords on the background thread.
     */
    private val executor: ExecutorService = Executors.newSingleThreadExecutor()

    /**
     * Initialize the keywords from the assets.
     *
     * example:
     *
     * Application() {
     *
     *      fun onCreate() {
     *
     *          Blocker.init(this)
     *
     *      }
     *
     * }
     */
    fun init(context: Context) {
        executor.execute {
            try {
                loadFromAssets(context)
            } catch (e: IOException) {
                /*noop*/
            }
        }
    }


    /**
     * Load the keywords from the assets keywords.txt.
     */
    @WorkerThread
    @Throws(IOException::class)
    private fun loadFromAssets(context: Context) {

        /**
         * Getting the stream from the assets for the keywords
         */
        val stream = context.assets.open(KEYWORDS_FILE)



        /**
         * Getting the buffer from the stream for the keywords
         */
        val buffer: BufferedSource = stream.source().buffer()


        /**
         * Getting the line that store the keywords
         * and initialize
         */
        var line = ""


        /**
         * Read the keywords line by line
         * from the keywords.txt
         */
        while ((buffer.readUtf8Line().also {



                /**
                 * Getting the line that store the keywords
                 */

                if (it != null) {

                    /**
                     * Assigning the line to the keywords
                     */
                    line = it

                }

                /**
                 * Validating the line being not null
                 */
            }) != null) {


            /**
             * Finally adding the line to the keywords into KEYWORDS
             */
            KEYWORDS.add(line)
        }


        /**
         * Closing the buffer and the stream
         */
        buffer.close()
        stream.close()
    }

    /**
     * Check if the url is permitted validating  by try catch if the structure
     * its ok to use the url.
     */
    fun isPermitted(url: String?): Boolean {

        /**
         * Validating if the url is null
         */
        return if (url == null) {



            /**
             * return false in case is null
             * son that means the url is not permitted
             */
            false
        } else {

            /**
             * Using try catch to check the structure of the url
             * in case of throw MalformedURLException
             * return no permitted url
             */
            try {



                /**
                 * Getting the url content
                 * and validating structure
                 */
                val urlContent = getUrlContent(url)



                /**
                 * Check if the content is permitted
                 * and returning the boolean result
                 */
                isPermittedContent(urlContent)

            } catch (e: MalformedURLException) {
                Log.e("error url..", e.toString())
                false
            }
        }
    }



    /**
     * Check if the content is permitted.
     */
    private fun isPermittedContent(content: String): Boolean {

        /**
         * Validating if the content is empty
         */
        if (content.isEmpty()) {
            return false
        }



        /**
         * Validating if the content contains reference
         * from the keywords
         */
        return KEYWORDS.any { content.contains(it, ignoreCase = true) }
    }



    /**
     * Get the url content and check the structure of the url
     * if not throw MalformedURLException.
     */
    @Throws(MalformedURLException::class)
    fun getUrlContent(url: String): String {
        /**
         * reference the url Into URL from java
         * that validates the structure
         */
        return URL(url).toString()
    }
}