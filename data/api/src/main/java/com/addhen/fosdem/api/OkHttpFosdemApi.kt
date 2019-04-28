package com.addhen.fosdem.api

import com.addhen.fosdem.platform.parser.Schedule
import com.addhen.fosdem.platform.parser.ScheduleXmlParser
import okhttp3.OkHttpClient
import okhttp3.Request


class OkHttpFosdemApi(val client: OkHttpClient, val xmlParser: ScheduleXmlParser) : FosdemApi {

    private val requestBuilder: Request.Builder
        get() {
            return Request.Builder()
                .addHeader("Content-Type", "application/xml; charset=utf-8")
                .addHeader("User-Agent", "Fosdem mobile app")
                .addHeader("Accept-Encoding", "gzip")
        }

    override suspend fun getSessions(): Schedule {
        val request = requestBuilder
            .url(SCHEDULE_URL)
            .build()

        client.newCall(request).execute().use { return xmlParser.parse(it?.body()!!.byteStream()) }
    }

    companion object {

        private const val SCHEDULE_URL = "https://fosdem.org/schedule/xml"
    }
}