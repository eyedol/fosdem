// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.data.sqldelight.api.entities

data class AttachmentEntity(
  val id: Long?,
  val type: String,
  val url: String,
  val name: String,
)
