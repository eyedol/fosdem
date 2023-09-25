// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.detail.di

import com.addhen.fosdem.core.api.di.ActivityScope
import com.addhen.fosdem.ui.session.detail.SessionDetailUiFactory
import com.addhen.fosdem.ui.session.detail.SessionDetailUiPresenterFactory
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.ui.Ui
import me.tatarka.inject.annotations.IntoSet
import me.tatarka.inject.annotations.Provides

interface SessionDetailComponent {
  @IntoSet
  @Provides
  @ActivityScope
  fun bindSessionDetailPresenterFactory(
    factory: SessionDetailUiPresenterFactory,
  ): Presenter.Factory = factory

  @IntoSet
  @Provides
  @ActivityScope
  fun bindSessionDetailFactoryFactory(
    factory: SessionDetailUiFactory,
  ): Ui.Factory = factory
}
