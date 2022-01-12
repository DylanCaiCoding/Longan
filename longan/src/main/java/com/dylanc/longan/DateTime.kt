/*
 * Copyright (c) 2021. Dylan Cai
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

@file:Suppress("unused")

package com.dylanc.longan

import kotlinx.datetime.*
import java.time.format.DateTimeFormatter
import java.time.ZonedDateTime as jtZonedDateTime
import java.time.LocalDateTime as jtLocalDateTime
import java.time.LocalDate as jtLocalDate

fun Instant.Companion.parse(text: String, pattern: String, timeZone: TimeZone = TimeZone.currentSystemDefault()): Instant =
  jtZonedDateTime.parse(text, DateTimeFormatter.ofPattern(pattern).withZone(timeZone.toJavaZoneId())).toInstant().toKotlinInstant()

fun LocalDateTime.Companion.parse(text: String, pattern: String, timeZone: TimeZone = TimeZone.currentSystemDefault()): LocalDateTime =
  jtLocalDateTime.parse(text, DateTimeFormatter.ofPattern(pattern).withZone(timeZone.toJavaZoneId())).toKotlinLocalDateTime()

fun LocalDate.Companion.parse(text: String, pattern: String, timeZone: TimeZone = TimeZone.currentSystemDefault()): LocalDate =
  jtLocalDate.parse(text, DateTimeFormatter.ofPattern(pattern).withZone(timeZone.toJavaZoneId())).toKotlinLocalDate()

fun String.toInstant(pattern: String, timeZone: TimeZone = TimeZone.currentSystemDefault()) =
  Instant.parse(this, pattern, timeZone)

fun String.toLocalDateTime(pattern: String, timeZone: TimeZone = TimeZone.currentSystemDefault()) =
  LocalDateTime.parse(this, pattern, timeZone)

fun String.toLocalDate(pattern: String, timeZone: TimeZone = TimeZone.currentSystemDefault()) =
  LocalDate.parse(this, pattern, timeZone)

fun Instant.format(pattern: String, timeZone: TimeZone = TimeZone.currentSystemDefault()): String =
  DateTimeFormatter.ofPattern(pattern).withZone(timeZone.toJavaZoneId()).format(toJavaInstant())

fun LocalDate.format(pattern: String, timeZone: TimeZone = TimeZone.currentSystemDefault()): String =
  DateTimeFormatter.ofPattern(pattern).withZone(timeZone.toJavaZoneId()).format(toJavaLocalDate())

fun LocalDateTime.format(pattern: String, timeZone: TimeZone = TimeZone.currentSystemDefault()): String =
  DateTimeFormatter.ofPattern(pattern).withZone(timeZone.toJavaZoneId()).format(toJavaLocalDateTime())

fun LocalDateTime.toInstant(): Instant = toInstant(TimeZone.currentSystemDefault())
