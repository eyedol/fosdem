// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.data.events.api

import com.addhen.fosdem.core.api.AppCoroutineDispatchers
import com.addhen.fosdem.data.core.api.AppResult
import com.addhen.fosdem.data.core.api.network.ApiService
import com.addhen.fosdem.data.events.api.api.EventsApi
import com.addhen.fosdem.data.events.api.api.dto.EventDto

class KtorEventsApi(
  private val api: ApiService,
  private val dispatchers: AppCoroutineDispatchers,
) : EventsApi {
  override suspend fun fetchEvents(): AppResult<EventDto> = api.get<EventDto>(dispatchers.io)
}
