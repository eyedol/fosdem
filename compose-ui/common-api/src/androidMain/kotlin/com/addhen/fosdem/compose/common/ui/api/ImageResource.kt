package com.addhen.fosdem.compose.common.ui.api

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import kotlinx.parcelize.Parcelize

@Parcelize
actual class ImageResource(
  @DrawableRes val drawableResId: Int
) : Parcelable {

  fun getDrawable(context: Context): Drawable? = ContextCompat.getDrawable(context, drawableResId)
}
