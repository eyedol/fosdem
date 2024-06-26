// Copyright 2022, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.data.licenses.api.repository

import com.addhen.fosdem.model.api.licenses.License

interface LicensesRepository {

  suspend fun getLicenses(): List<License>
}
