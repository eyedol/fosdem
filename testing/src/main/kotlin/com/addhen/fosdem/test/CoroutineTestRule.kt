// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.test

import com.addhen.fosdem.core.api.AppCoroutineDispatchers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext

@ExperimentalCoroutinesApi
class CoroutineTestRule(
  private val dispatcher: TestDispatcher = UnconfinedTestDispatcher(TestCoroutineScheduler()),
) : BeforeEachCallback, AfterEachCallback {

  private val scope = TestScope(dispatcher)

  val testDispatcherProvider = AppCoroutineDispatchers(
    io = dispatcher,
    default = dispatcher,
    databaseRead = dispatcher,
    databaseWrite = dispatcher,
    computation = dispatcher,
    main = dispatcher,
  )

  fun runTest(block: suspend TestScope.() -> Unit) {
    scope.runTest {
      block()
    }
  }

  override fun beforeEach(context: ExtensionContext?) {
    Dispatchers.setMain(dispatcher)
  }

  override fun afterEach(context: ExtensionContext?) {
    Dispatchers.resetMain()
  }
}
