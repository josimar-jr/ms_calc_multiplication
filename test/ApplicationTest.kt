package org.josimarjr.msmulti

import com.google.gson.Gson
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.features.*
import org.slf4j.event.*
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.gson.*
import kotlin.test.*
import io.ktor.server.testing.*

class ApplicationTest {
    @Test
    fun testRoot() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Get, "/").apply {
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
