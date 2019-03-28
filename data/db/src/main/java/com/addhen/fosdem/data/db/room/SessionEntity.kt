package com.addhen.fosdem.data.db.room

interface SessionEntity {
    val id: Long
    val start: String
    val duration: String
    val title: String
    val room: String
    val track: String
    val description: String
}