// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.search.component

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.addhen.fosdem.compose.common.ui.api.LocalStrings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTextFieldAppBar(
  searchQuery: String,
  onSearchQueryChanged: (String) -> Unit,
  onBackClick: () -> Unit,
  testTag: String,
  modifier: Modifier = Modifier,
) {
  TopAppBar(
    modifier = modifier,
    colors = TopAppBarDefaults.topAppBarColors(
      containerColor = MaterialTheme.colorScheme.surfaceVariant,
    ),
    navigationIcon = {
      IconButton(onClick = onBackClick) {
        Icon(
          imageVector = Icons.Default.ArrowBack,
          contentDescription = null,
        )
      }
    },
    title = {
      SearchTextField(
        searchQuery = searchQuery,
        onSearchQueryChanged = onSearchQueryChanged,
        modifier = Modifier
          .testTag(testTag),
      )
    },
  )
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun SearchTextField(
  modifier: Modifier = Modifier,
  searchQuery: String = "",
  onSearchQueryChanged: (String) -> Unit = {},
  enabled: Boolean = true,
  interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
  val focusRequester = remember { FocusRequester() }
  val keyboardController = LocalSoftwareKeyboardController.current
  val appStrings = LocalStrings.current

  BasicTextField(
    value = searchQuery,
    onValueChange = onSearchQueryChanged,
    modifier = modifier
      .fillMaxWidth(1.0f)
      .focusRequester(focusRequester),
    enabled = enabled,
    textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface),
    cursorBrush = SolidColor(MaterialTheme.colorScheme.onSurface),
    singleLine = true,
    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
    keyboardActions = KeyboardActions(onSearch = { keyboardController?.hide() }),
    decorationBox = @Composable { innerTextField ->
      TextFieldDefaults.DecorationBox(
        value = searchQuery,
        innerTextField = innerTextField,
        enabled = enabled,
        singleLine = true,
        visualTransformation = VisualTransformation.None,
        interactionSource = interactionSource,
        placeholder = {
          if (searchQuery.isBlank()) {
            Text(
              text = appStrings.searchTermPlaceHolder,
              style = MaterialTheme.typography.bodyLarge,
              color = MaterialTheme.colorScheme.onSurface,
            )
          }
        },
        trailingIcon = {
          if (searchQuery.isNotEmpty()) {
            Box(modifier = Modifier.offset(x = (-4).dp)) {
              IconButton(
                onClick = { onSearchQueryChanged("") },
              ) {
                Icon(
                  imageVector = Icons.Default.Clear,
                  contentDescription = null,
                  tint = MaterialTheme.colorScheme.onSurface,
                )
              }
            }
          }
        },
        contentPadding = TextFieldDefaults.contentPaddingWithoutLabel(0.dp),
        container = {},
      )
    },
  )
}
