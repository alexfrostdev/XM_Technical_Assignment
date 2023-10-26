@file:JvmName("MockResponseUtil")

package com.example.xmtechnicalassignment.data.remote

import okhttp3.mockwebserver.MockResponse
import okio.buffer
import okio.source
import java.io.IOException
import java.nio.charset.StandardCharsets

@Throws(IOException::class)
fun createMockResponse(
    classLoader: ClassLoader,
    fileName: String,
    headers: Map<String, String>,
    responseCode: Int
): MockResponse {

    val inputStream = classLoader.getResourceAsStream("api-response/$fileName")
    val source = inputStream.source().buffer()

    val mockResponse = MockResponse()
    for ((key, value) in headers) {
        mockResponse.addHeader(key, value)
    }
    if (responseCode != -1) {
        mockResponse.setResponseCode(responseCode)
    }
    mockResponse.setBody(source.readString(StandardCharsets.UTF_8))

    return mockResponse
}

@Throws(IOException::class)
fun createMockResponse(
    response: String,
    headers: Map<String, String>,
    responseCode: Int
): MockResponse {

    val mockResponse = MockResponse()
    for ((key, value) in headers) {
        mockResponse.addHeader(key, value)
    }
    if (responseCode != -1) {
        mockResponse.setResponseCode(responseCode)
    }
    mockResponse.setBody(response)

    return mockResponse
}

@Throws(IOException::class)
fun requestAsString(classLoader: ClassLoader, fileName: String): String {
    return resourceAsString(classLoader, fileName, "api-request")
}

@Throws(IOException::class)
fun responseAsString(classLoader: ClassLoader, fileName: String): String {
    return resourceAsString(classLoader, fileName, "api-response")
}

@Throws(IOException::class)
fun requestAsStringWithoutFormatting(classLoader: ClassLoader, fileName: String): String {
    return requestAsString(classLoader, fileName)
        //remove formatting
        .replace("\n", "")
        .replace("\r", "")
        .replace("  ", "")
        .replace(": ", ":")
}

private fun resourceAsString(
    classLoader: ClassLoader,
    fileName: String,
    resource: String = "api-request"
): String {
    val inputStream = classLoader.getResourceAsStream("$resource/$fileName")
    val source = inputStream.source().buffer()

    return source.readString(StandardCharsets.UTF_8)
}
