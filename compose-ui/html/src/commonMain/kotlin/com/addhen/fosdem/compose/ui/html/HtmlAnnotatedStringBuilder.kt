// Copyright 2024, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.compose.ui.html

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp

internal class HtmlAnnotatedStringBuilder(
  private val linkTextColor: Color,
  private val builder: AnnotatedString.Builder = AnnotatedString.Builder(),
) {

  private var tag: HtmlTag = HtmlTag.NONE
  private var isOlTagOpened = false
  private var olCounter = 0

  fun handleLineBreakOpenTag() {
    tag = HtmlTag.BR
  }

  fun handleUlOpenTag() {
    tag = HtmlTag.UL
  }

  fun handleOlOpenTag() {
    tag = HtmlTag.OL
    isOlTagOpened = true
  }

  fun handleAOpenTag(linkUrl: String) {
    tag = HtmlTag.A
    builder.pushStringAnnotation(tag = linkUrl, annotation = linkUrl)
    builder.pushStyle(
      style = linkSpanStyle(),
    )
  }

  fun handleACloseTag() {
    builder.pop() // pop the link text style
    builder.pop() // pop the link url annotation
  }

  fun handleLiOpenTag() {
    tag = HtmlTag.LI
    var restLine = 1.8.em
    if (isOlTagOpened) {
      restLine = 2.em
    }
    builder.pushStyle(
      ParagraphStyle(
        textAlign = TextAlign.Start,
        textIndent = TextIndent(firstLine = 1.em, restLine = restLine),
        lineBreak = LineBreak.Paragraph,
      ),
    )
  }

  fun handleEmOpenTag() {
    builder.pushStyle(SpanStyle(fontStyle = FontStyle.Italic))
  }

  fun handleStrongOpenTag() {
    builder.pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
  }

  fun handleParagraphOpenTag() {
    tag = HtmlTag.P
    builder.pushStyle(
      ParagraphStyle(
        textAlign = TextAlign.Start,
        lineBreak = LineBreak.Paragraph,
      ),
    )
  }

  fun handleStrongCloseTag() {
    builder.pop()
  }

  fun handleEmCloseTag() {
    builder.pop()
  }

  fun handleParagraphCloseTag() {
    builder.pop()
  }

  fun handleUlCloseTag() {
    builder.append("\r\n")
  }

  fun handleOlCloseTag() {
    isOlTagOpened = false
    olCounter = 0
    builder.append("\r\n")
  }

  fun handleLiCloseTag() {
    builder.pop()
  }

  fun handleH1OpenTag() {
    tag = HtmlTag.H1
    builder.pushStyle(headingSpanStyle(22.sp))
  }

  fun handleH1CloseTag() {
    builder.pop()
  }

  fun handleH2OpenTag() {
    tag = HtmlTag.H2
    builder.pushStyle(headingSpanStyle(20.sp))
  }

  fun handleH3OpenTag() {
    tag = HtmlTag.H3
    builder.pushStyle(headingSpanStyle(18.sp))
  }

  fun handleH4OpenTag() {
    tag = HtmlTag.H4
    builder.pushStyle(headingSpanStyle(16.sp))
  }

  fun handleH5OpenTag() {
    tag = HtmlTag.H5
    builder.pushStyle(headingSpanStyle(14.sp))
  }

  fun handleH6OpenTag() {
    tag = HtmlTag.H6
    builder.pushStyle(headingSpanStyle(12.sp))
  }

  fun handleH2CloseTag() {
    builder.pop()
    builder.append("\r\n")
  }

  fun handleH3CloseTag() {
    builder.pop()
    builder.append("\r\n")
  }

  fun handleH4CloseTag() {
    builder.pop()
    builder.append("\r\n")
  }

  fun handleH5CloseTag() {
    builder.pop()
    builder.append("\r\n")
  }

  fun handleH6CloseTag() {
    builder.pop()
    builder.append("\r\n")
  }

  fun write(text: String) {
    when (tag) {
      HtmlTag.P, HtmlTag.BR -> {
        styleLinksUrlsOrAppend(text)
        builder.append("\r\n")
      }

      HtmlTag.UL, HtmlTag.OL -> {
        builder.append("\r\n")
      }

      HtmlTag.LI -> {
        if (isOlTagOpened) {
          builder.append("${++olCounter}. ")
        } else {
          builder.append("\u2022 ")
        }
        builder.append(text)
      }

      else -> {
        styleLinksUrlsOrAppend(text)
      }
    }
  }

  fun toAnnotatedString() = builder.toAnnotatedString()

  private fun linkSpanStyle() = SpanStyle(
    textDecoration = TextDecoration.Underline,
    color = linkTextColor,
    fontWeight = FontWeight.Bold,
  )

  private fun headingSpanStyle(fontSize: TextUnit) = SpanStyle(
    fontSize = fontSize,
    fontWeight = FontWeight.Bold,
  )

  private fun findLinks(content: String): Sequence<MatchResult> {
    return "(?:https?|ftp)://[\\w+&@#/%?=~_|!:,.;-]*[\\w+&@#/%=~_|]".toRegex().findAll(content)
  }

  private fun styleLinksUrlsOrAppend(text: String) {
    val links = findLinks(text)
    if (links.count() == 0) {
      builder.append(text)
    } else {
      builder.append(getLinksUrlAnnotatedString(text, links))
    }
  }

  /**
   * Use this to style links in text that are not html anchor <a>.
   *
   * This will underline the link as an hyperlink just like it has been done
   * for the <a /> html tags.
   */
  private fun getLinksUrlAnnotatedString(
    content: String,
    links: Sequence<MatchResult>,
  ): AnnotatedString {
    return buildAnnotatedString {
      append(content)

      var lastIndex = 0
      links.forEach { matchResult ->
        val startIndex = content.indexOf(
          string = matchResult.value,
          startIndex = lastIndex,
        )
        val endIndex = startIndex + matchResult.value.length
        addStyle(
          style = SpanStyle(
            color = linkTextColor,
            textDecoration = TextDecoration.Underline,
            fontWeight = FontWeight.Bold,
          ),
          start = startIndex,
          end = endIndex,
        )
        addStringAnnotation(
          tag = matchResult.value,
          annotation = matchResult.value,
          start = startIndex,
          end = endIndex,
        )

        lastIndex = endIndex
      }
    }
  }
}
