package com.ead.lib.nomoreadsonmywebviewplayer

import kotlin.system.measureNanoTime

/**
 * Utility class for measuring performance metrics in tests
 */
object PerformanceTestUtils {

    /**
     * Performance metrics data class
     */
    data class PerformanceMetrics(
        val operationName: String,
        val executionTimeMs: Long,
        val blockedCount: Int = 0,
        val allowedCount: Int = 0,
        val totalUrls: Int = 0
    ) {
        val blockingEfficiency: Double
            get() = if (totalUrls > 0) (blockedCount.toDouble() / totalUrls) * 100.0 else 0.0

        val avgTimePerUrlMs: Double
            get() = if (totalUrls > 0) executionTimeMs.toDouble() / totalUrls else 0.0

        override fun toString(): String {
            return """
                |Performance Metrics for: $operationName
                |  Execution Time: ${executionTimeMs}ms
                |  Total URLs: $totalUrls
                |  Blocked: $blockedCount
                |  Allowed: $allowedCount
                |  Blocking Efficiency: ${"%.2f".format(blockingEfficiency)}%
                |  Avg Time per URL: ${"%.2f".format(avgTimePerUrlMs)}ms
            """.trimMargin()
        }
    }

    /**
     * Measure execution time of a block of code
     */
    inline fun <T> measurePerformance(
        operationName: String,
        block: () -> T
    ): Pair<T, Long> {
        var result: T
        val executionTimeNs = measureNanoTime {
            result = block()
        }
        val executionTimeMs = executionTimeNs / 1_000_000
        return Pair(result, executionTimeMs)
    }

    /**
     * Measure performance with metrics
     */
    fun measureWithMetrics(
        operationName: String,
        urls: List<String>,
        block: (String) -> Boolean
    ): PerformanceMetrics {
        var blockedCount = 0
        var allowedCount = 0
        
        val executionTimeMs = measureNanoTime {
            urls.forEach { url ->
                if (block(url)) {
                    allowedCount++
                } else {
                    blockedCount++
                }
            }
        } / 1_000_000

        return PerformanceMetrics(
            operationName = operationName,
            executionTimeMs = executionTimeMs,
            blockedCount = blockedCount,
            allowedCount = allowedCount,
            totalUrls = urls.size
        )
    }

    /**
     * Performance thresholds for validation
     */
    object Thresholds {
        const val MAX_URL_CHECK_TIME_MS = 10L // Maximum time to check a single URL
        const val MIN_BLOCKING_EFFICIENCY_PERCENT = 80.0 // Minimum blocking efficiency
        const val MAX_BATCH_CHECK_TIME_MS = 100L // Maximum time to check 10 URLs
    }

    /**
     * Assert that performance metrics meet thresholds
     */
    fun assertPerformanceThresholds(metrics: PerformanceMetrics) {
        require(metrics.avgTimePerUrlMs <= Thresholds.MAX_URL_CHECK_TIME_MS) {
            "Performance issue: Average URL check time (${metrics.avgTimePerUrlMs}ms) " +
            "exceeds threshold (${Thresholds.MAX_URL_CHECK_TIME_MS}ms)\n${metrics}"
        }
    }

    /**
     * Compare two performance metrics
     */
    fun compareMetrics(baseline: PerformanceMetrics, current: PerformanceMetrics): String {
        val timeDiff = current.executionTimeMs - baseline.executionTimeMs
        val timeDiffPercent = (timeDiff.toDouble() / baseline.executionTimeMs) * 100.0
        val efficiencyDiff = current.blockingEfficiency - baseline.blockingEfficiency

        return """
            |Performance Comparison:
            |  Baseline: ${baseline.operationName}
            |  Current: ${current.operationName}
            |  Time Difference: ${timeDiff}ms (${"%.2f".format(timeDiffPercent)}%)
            |  Efficiency Difference: ${"%.2f".format(efficiencyDiff)}%
        """.trimMargin()
    }
}
