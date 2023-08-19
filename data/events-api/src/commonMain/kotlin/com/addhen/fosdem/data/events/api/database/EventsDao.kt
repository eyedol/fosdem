package com.addhen.fosdem.data.events.api.database

import com.addhen.fosdem.data.sqldelight.api.Events
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate

interface EventsDao {

  fun getEvent(localDate: LocalDate): Flow<List<Events>>

  fun getMovie(eventId: Long): Flow<Events>

  fun toggleBookmark(eventId: Long)

  suspend fun deleteAll()

  suspend fun insert(movies: List<Events>)

}
