# Performance and Deep Testing Documentation

## Overview
This document describes the comprehensive performance validation and deep testing implemented for all 19 supported video hosting sites in the NoMoreAdsOnMyWebViewPlayer library.

## Test Structure

### 1. Test Data (`SupportedSiteTestData.kt`)
- **Purpose**: Centralized test data for all 19 supported sites
- **Content**: 
  - Site names
  - Multiple test URLs per site
  - Expected keywords for validation
  - Known ad URLs for blocking tests
- **Supported Sites**:
  1. Uqload
  2. Mp4Upload
  3. DoodStream
  4. Filelions
  5. Filemoon
  6. VidGuard
  7. LuluStream-Luluvdo
  8. Streamtape
  9. Okru
  10. StreamWish
  11. Voe
  12. Senvid
  13. Anonfiles
  14. Bayfiles
  15. Fembed
  16. Mega
  17. YourUpload
  18. Maru
  19. GoodStream

### 2. Performance Test Utilities (`PerformanceTestUtils.kt`)
- **Purpose**: Measure and validate performance metrics
- **Features**:
  - Execution time measurement (nanosecond precision)
  - Blocking efficiency calculation
  - Average time per URL calculation
  - Performance threshold validation
  - Metrics comparison between test runs

#### Performance Thresholds
- **MAX_URL_CHECK_TIME_MS**: 10ms per URL
- **MIN_BLOCKING_EFFICIENCY_PERCENT**: 80%
- **MAX_BATCH_CHECK_TIME_MS**: 100ms for 10 URLs

### 3. Deep Testing Suite (`SupportedSitesDeepTest.kt`)
- **Purpose**: Validate each supported site individually
- **Test Coverage**:
  - Individual tests for all 19 sites
  - Multiple URL patterns per site
  - Keyword coverage validation
  - Ad blocking validation
  - Comprehensive batch testing

#### Test Cases
- ✅ `test Uqload site URLs should be permitted`
- ✅ `test Mp4Upload site URLs should be permitted`
- ✅ `test DoodStream site URLs should be permitted`
- ✅ `test Filelions site URLs should be permitted`
- ✅ `test Filemoon site URLs should be permitted`
- ✅ `test VidGuard site URLs should be permitted`
- ✅ `test LuluStream-Luluvdo site URLs should be permitted`
- ✅ `test Streamtape site URLs should be permitted`
- ✅ `test Okru site URLs should be permitted`
- ✅ `test StreamWish site URLs should be permitted`
- ✅ `test Voe site URLs should be permitted`
- ✅ `test Senvid site URLs should be permitted`
- ✅ `test Anonfiles site URLs should be permitted`
- ✅ `test Bayfiles site URLs should be permitted`
- ✅ `test Fembed site URLs should be permitted`
- ✅ `test Mega site URLs should be permitted`
- ✅ `test YourUpload site URLs should be permitted`
- ✅ `test Maru site URLs should be permitted`
- ✅ `test GoodStream site URLs should be permitted`
- ✅ `test all supported sites with comprehensive URLs`
- ✅ `test ad URLs should be blocked`
- ✅ `test keywords coverage for all sites`

### 4. Performance Validation Suite (`BlockerPerformanceTest.kt`)
- **Purpose**: Validate performance across all sites
- **Test Coverage**:
  - Single URL check performance
  - Batch URL processing performance
  - Ad blocking performance
  - Mixed content performance
  - Concurrent check performance
  - Site-specific performance tests
  - Malformed URL handling

#### Test Cases
- ✅ `test performance of single URL check`
- ✅ `test performance of batch URL checks`
- ✅ `test performance of ad blocking`
- ✅ `test performance of mixed URL batch`
- ✅ `test performance of concurrent URL checks`
- ✅ `test performance of Uqload site specifically`
- ✅ `test performance of Mp4Upload site specifically`
- ✅ `test performance of DoodStream site specifically`
- ✅ `test performance of Filemoon site specifically`
- ✅ `test performance comparison between sites`
- ✅ `test URL validation performance with malformed URLs`

### 5. Integration Testing Suite (`BlockerClientIntegrationTest.kt`)
- **Purpose**: Test complete blocking flow with realistic scenarios
- **Test Coverage**:
  - BlockerClient with all supported sites
  - Custom exception keywords
  - Mixed content (videos + ads)
  - Same domain navigation
  - URL override behavior
  - Complete integration tests

#### Test Cases
- ✅ `test BlockerClient with Uqload URLs should allow video content`
- ✅ `test BlockerClient with Mp4Upload URLs should allow video content`
- ✅ `test BlockerClient with DoodStream URLs should allow video content`
- ✅ `test BlockerClient with Filemoon URLs should allow video content`
- ✅ `test BlockerClient blocks ad URLs`
- ✅ `test BlockerClient with custom exception keywords`
- ✅ `test BlockerClient performance with all supported sites`
- ✅ `test BlockerClient with mixed content - videos and ads`
- ✅ `test BlockerClient with LuluStream-Luluvdo CDN variations`
- ✅ `test BlockerClient with same domain navigation`
- ✅ `test BlockerClient shouldOverrideUrlLoading with video sites`
- ✅ `test BlockerClient with all 19 supported sites`

## Test Execution

### Running Unit Tests
```bash
./gradlew test
```

### Running Specific Test Suites
```bash
# Run deep tests
./gradlew test --tests "SupportedSitesDeepTest"

# Run performance tests
./gradlew test --tests "BlockerPerformanceTest"

# Run integration tests
./gradlew test --tests "BlockerClientIntegrationTest"
```

### Running All Tests
```bash
./gradlew clean test
```

## Performance Metrics

### Expected Results
- **Single URL Check**: < 10ms
- **Batch Processing**: < 10ms per URL average
- **Ad Blocking**: 100% efficiency for known ad patterns
- **Site Support**: 100% of supported sites pass validation
- **Malformed URLs**: Handled gracefully without crashes

### Measuring Performance
Each test suite outputs detailed performance metrics:
```
Performance Metrics for: Batch URL Check (57 URLs)
  Execution Time: 234ms
  Total URLs: 57
  Blocked: 0
  Allowed: 57
  Blocking Efficiency: 100.00%
  Avg Time per URL: 4.11ms
```

## Test Coverage

### Keyword Coverage
All keywords from `keywords.txt` are validated:
- video-delivery
- tapecontent
- jwplayer
- uqload
- mp4
- jpg
- cast
- voe
- guardstorage
- cloudflare
- videothumbs
- cdn
- roseimgs
- vidcache
- images
- d000d
- dood
- .sx
- stream
- hls
- mail.ru

### URL Pattern Coverage
Each site is tested with:
- Standard embed URLs
- CDN variations
- Alternative domains
- Path variations

## Continuous Integration

These tests are integrated into the CI pipeline:
- Run on every pull request
- Run on push to develop/master
- Performance regression detection
- Automated test reporting

## Maintenance

### Adding New Sites
1. Add site data to `SupportedSiteTestData.kt`
2. Add keywords to `keywords.txt`
3. Create site-specific test in `SupportedSitesDeepTest.kt`
4. Add performance test in `BlockerPerformanceTest.kt`
5. Update this documentation

### Performance Threshold Adjustments
Update thresholds in `PerformanceTestUtils.kt`:
```kotlin
object Thresholds {
    const val MAX_URL_CHECK_TIME_MS = 10L
    const val MIN_BLOCKING_EFFICIENCY_PERCENT = 80.0
    const val MAX_BATCH_CHECK_TIME_MS = 100L
}
```

## Best Practices

1. **Always run tests locally** before pushing
2. **Review performance metrics** for regressions
3. **Update test data** when sites change
4. **Document new test patterns** in this file
5. **Keep keywords.txt synchronized** with test data

## Troubleshooting

### Test Failures
- Check if keywords.txt is loaded properly
- Verify URL patterns match actual site URLs
- Review performance threshold settings
- Check for network-related issues in CI

### Performance Issues
- Profile individual URL checks
- Check keyword loading performance
- Review regex matching efficiency
- Optimize keyword set structure

## Future Enhancements

1. **Automated URL Discovery**: Scrape real site URLs for testing
2. **Performance Benchmarking**: Track metrics over time
3. **Coverage Reports**: Generate detailed test coverage reports
4. **Real-World Testing**: Add instrumentation tests with actual WebViews
5. **Load Testing**: Test with high-volume URL batches
