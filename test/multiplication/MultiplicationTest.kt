package org.josimarjr.msmulti.multiplication

import com.google.gson.Gson
import io.ktor.http.*
import kotlin.test.*
import io.ktor.server.testing.*
import org.josimarjr.msmulti.module
import kotlin.test.assertEquals
import org.junit.Assert

class MultiplicationTest {
    @Test
    fun testMultiplicationService() {
        Assert.assertEquals(24.0, multiplicationService(2.0, 12.0), 0.001)
    }

    @Test
    fun testMultiplyWithIntegers() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Get, "/multiply/12/2").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                val responseType = assertNotNull(response.headers[HttpHeaders.ContentType])
                assertEquals(ContentType.Application.Json.withCharset(Charsets.UTF_8), ContentType.parse(responseType))
                val map: Map<String, Double> = HashMap()
                val body = Gson().fromJson(response.content, map.javaClass)
                Assert.assertEquals(24.0, body["result"].toString().toDouble(), 0.001)
            }
        }
    }

    @Test
    fun testMultiplyWithDoubles() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Get, "/multiply/12.3/3.1").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                val responseType = assertNotNull(response.headers[HttpHeaders.ContentType])
                assertEquals(ContentType.Application.Json.withCharset(Charsets.UTF_8), ContentType.parse(responseType))
                val map: Map<String, Double> = HashMap()
                val body = Gson().fromJson(response.content, map.javaClass)
                Assert.assertEquals(38.13, body["result"].toString().toDouble(), 0.001)
            }
        }
    }
}
