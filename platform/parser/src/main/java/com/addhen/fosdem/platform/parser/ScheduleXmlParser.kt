package com.addhen.fosdem.platform.parser

import android.util.Xml
import com.addhen.fosdem.data.model.*
import com.addhen.fosdem.platform.extension.toDate
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException
import java.io.InputStream
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import java.util.concurrent.atomic.AtomicLong

class ScheduleXmlParser(private val parser: XmlPullParser = Xml.newPullParser()) : Parser<Schedule> {

    private val calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.US)

    private lateinit var currentDayDate: Date

    private val linId = AtomicLong()

    override fun parse(inputStream: InputStream): Schedule {
        inputStream.use { input ->
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
            parser.setInput(input, null)
            parser.nextTag()
            return readSchedule()
        }
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun readSchedule(): Schedule {
        requireStartTag("schedule")
        val days = mutableListOf<Schedule.Day>()
        while (!isEndTag()) {
            if (!isStartTag()) {
                continue
            }
            // Look for day tag
            if (parser.name == "day") {
                days.add(readDay())
            } else {
                skipToEndTag()
            }
        }
        return Schedule(days)
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun readDay(): Schedule.Day {
        requireStartTag("day")
        // Look for day attributes, index and date
        val index = parser.getAttributeValue(null, "index").toInt()
        currentDayDate = parser.getAttributeValue(null, "date").toDate()
        val sessions = mutableListOf<Session>()
        while (!isEndTag()) {
            if (!isStartTag()) {
                continue
            }
            // Look for room tag
            if (parser.name == "room") {
                sessions.addAll(readRoom(index))
            } else {
                skipToEndTag()
            }
        }

        return Schedule.Day(index, currentDayDate, sessions)
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun readRoom(day: Int): List<Session> {
        requireStartTag("room")
        val sessions = mutableListOf<Session>()
        while (!isEndTag()) {
            if (!isStartTag()) {
                continue
            }

            // Look for event
            if (parser.name == "event") {
                sessions.add(readEvent(day))
            } else {
                skipToEndTag()
            }
        }
        return sessions
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun readEvent(day: Int): Session {
        requireStartTag("event")
        var id = parser.getAttributeValue(null, "id").toLong()
        var startTime = Date()
        var durationTime = Date()
        var title = ""
        var description = ""
        var abstractText = ""
        var room = ""
        var track = ""
        var type = ""
        var speakers = mutableListOf<Speaker>()
        val links = mutableListOf<Link>()
        while (!isEndTag()) {
            if (!isStartTag()) {
                continue
            }
            // Look for room tag
            when (parser.name) {
                "start" -> {
                    resetCalendar(parser.nextText())
                    startTime = calendar.time
                }
                "duration" -> {
                    resetCalendar(parser.nextText())
                    durationTime = calendar.time
                }
                "description" -> description = parser.nextText()
                "abstractText" -> abstractText = parser.nextText()
                "title" -> title = parser.nextText()
                "persons" -> speakers.addAll(readPersons(id))
                "links" -> links.addAll(readLinks(id))
                "room" -> room = parser.nextText()
                "track" -> track = parser.nextText()
                "type" -> type = parser.nextText()
                else -> skipToEndTag()
            }
        }
        return Session(
            id,
            day,
            startTime,
            durationTime,
            title,
            description,
            abstractText,
            Room(name = room, building = Room.Building.fromRoomName(room).name),
            Track(name = track, type = Track.Type.valueOf(type)),
            links,
            speakers.toList()
        )
    }

    @Throws(IOException::class, XmlPullParserException::class)
    private fun readPersons(sessionId: Long): List<Speaker> {
        requireStartTag("persons")
        val speakers = mutableListOf<Speaker>()
        while (!isEndTag()) {
            if (!isStartTag()) {
                continue
            }
            // Look for person tag
            if (parser.name == "person") {
                speakers.add(readPerson(sessionId))
            } else {
                skipToEndTag()
            }
        }
        return speakers
    }

    private fun readPerson(sessionId: Long): Speaker {
        requireStartTag("person")
        val id = parser.getAttributeValue(null, "id").toLong()
        val name = parser.nextText()
        return Speaker(id, name, sessionId)
    }

    @Throws(IOException::class, XmlPullParserException::class)
    private fun readLinks(sessionId: Long): List<Link> {
        requireStartTag("links")
        val links = mutableListOf<Link>()
        while (!isEndTag()) {
            if (!isStartTag()) {
                continue
            }
            // Look for person tag
            if (parser.name == "link") {
                links.add(readLink(sessionId))
            } else {
                skipToEndTag()
            }
        }
        return links
    }

    private fun readLink(sessionId: Long): Link {
        requireStartTag("link")
        val href = parser.getAttributeValue(null, "href")
        val text = parser.nextText()
        return Link(linId.incrementAndGet(), href, text, sessionId)
    }

    @Throws(XmlPullParserException::class)
    private fun isEndTag(): Boolean {
        return parser.next() == XmlPullParser.END_TAG
    }

    @Throws(XmlPullParserException::class)
    private fun isStartTag(): Boolean {
        return parser.eventType == XmlPullParser.START_TAG
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun skipToEndTag() {
        var type = 0
        while ({ type = parser.next(); type }() != XmlPullParser.END_TAG) {
            if (type == XmlPullParser.START_TAG)
                skipToEndTag()
        }
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun requireStartTag(tag: String) {
        parser.require(XmlPullParser.START_TAG, null, tag)
    }

    private fun resetCalendar(time: String) {
        calendar.time = currentDayDate
        calendar.set(Calendar.HOUR_OF_DAY, getHours(time))
        calendar.set(Calendar.MINUTE, getMinutes(time))
    }

    private fun getHours(time: String): Int {
        return Character.getNumericValue(time[0]) * 10 + Character.getNumericValue(time[1])
    }

    private fun getMinutes(time: String): Int {
        return Character.getNumericValue(time[3]) * 10 + Character.getNumericValue(time[4])
    }
}
