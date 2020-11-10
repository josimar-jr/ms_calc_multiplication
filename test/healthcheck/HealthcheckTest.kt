package org.josimarjr.msmulti.healthcheck

import com.google.gson.Gson
import io.ktor.http.*
import kotlin.test.*
import io.ktor.server.testing.*
import org.josimarjr.msmulti.module
import kotlin.test.assertEquals

class HealthcheckTest {
    @Test
    fun testHealthcheckService() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Get, "/healthcheck").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                val responseType = assertNotNull(response.headers[HttpHeaders.ContentType])
                assertEquals(ContentType.Application.Json.withCharset(Charsets.UTF_8), ContentType.parse(responseType))
                val map: Map<String, Any> = HashMap()
                val body = Gson().fromJson(response.content, map.javaClass)
                assertEquals("ok", body["status"].toString())
            }
        }
    }
}
