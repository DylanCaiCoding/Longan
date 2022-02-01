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
import java.time.temporal.TemporalAdjusters
import java.time.ZonedDateTime as jtZonedDateTime
import java.time.LocalDateTime as jtLocalDateTime
import java.time.LocalDate as jtLocalDate

fun Instant.Companion.parse(text: String, pattern: String, timeZone: TimeZone): Instant =
  jtZonedDateTime.parse(text, DateTimeFormatter.ofPattern(pattern).withZone(timeZone.toJavaZoneId())).toInstant().toKotlinInstant()

fun LocalDateTime.Companion.parse(text: String, pattern: String): LocalDateTime =
  jtLocalDateTime.parse(text, DateTimeFormatter.ofPattern(pattern)).toKotlinLocalDateTime()

fun LocalDate.Companion.parse(text: String, pattern: String): LocalDate =
  jtLocalDate.parse(text, DateTimeFormatter.ofPattern(pattern)).toKotlinLocalDate()

fun String.toInstant(pattern: String, timeZone: TimeZone): Instant = Instant.parse(this, pattern, timeZone)

fun String.toLocalDateTime(pattern: String): LocalDateTime = LocalDateTime.parse(this, pattern)

fun String.toLocalDate(pattern: String): LocalDate = LocalDate.parse(this, pattern)

fun Instant.format(pattern: String, timeZone: TimeZone): String =
  DateTimeFormatter.ofPattern(pattern).withZone(timeZone.toJavaZoneId()).format(toJavaInstant())

fun LocalDate.format(pattern: String): String =
  DateTimeFormatter.ofPattern(pattern).format(toJavaLocalDate())

fun LocalDateTime.format(pattern: String): String =
  DateTimeFormatter.ofPattern(pattern).format(toJavaLocalDateTime())

fun LocalDateTime.isToday(timeZone: TimeZone): Boolean = date.isToday(timeZone)

fun LocalDate.isToday(timeZone: TimeZone): Boolean = this == Clock.System.todayAt(timeZone)

fun LocalDateTime.isYesterday(timeZone: TimeZone): Boolean = date.isYesterday(timeZone)

fun LocalDate.isYesterday(timeZone: TimeZone): Boolean = this == Clock.System.todayAt(timeZone).minus(1, DateTimeUnit.DAY)

fun LocalDate.firstDayOfYear(): LocalDate =
  toJavaLocalDate().with(TemporalAdjusters.firstDayOfYear()).toKotlinLocalDate()

fun LocalDate.firstDayOfMonth(): LocalDate =
  toJavaLocalDate().with(TemporalAdjusters.firstDayOfMonth()).toKotlinLocalDate()

fun LocalDate.firstDayOfNextMonth(): LocalDate =
  toJavaLocalDate().with(TemporalAdjusters.firstDayOfNextMonth()).toKotlinLocalDate()

fun LocalDate.firstDayOfNextYear(): LocalDate =
  toJavaLocalDate().with(TemporalAdjusters.firstDayOfNextYear()).toKotlinLocalDate()

fun LocalDate.lastDayOfMonth(): LocalDate =
  toJavaLocalDate().with(TemporalAdjusters.lastDayOfMonth()).toKotlinLocalDate()

fun LocalDate.lastDayOfYear(): LocalDate =
  toJavaLocalDate().with(TemporalAdjusters.lastDayOfYear()).toKotlinLocalDate()

fun LocalDate.firstInMonth(dayOfWeek: DayOfWeek): LocalDate =
  toJavaLocalDate().with(TemporalAdjusters.firstInMonth(dayOfWeek)).toKotlinLocalDate()

fun LocalDate.lastInMonth(dayOfWeek: DayOfWeek): LocalDate =
  toJavaLocalDate().with(TemporalAdjusters.lastInMonth(dayOfWeek)).toKotlinLocalDate()

fun LocalDate.dayOfWeekInMonth(ordinal: Int, dayOfWeek: DayOfWeek): LocalDate =
  toJavaLocalDate().with(TemporalAdjusters.dayOfWeekInMonth(ordinal, dayOfWeek)).toKotlinLocalDate()

fun LocalDate.next(dayOfWeek: DayOfWeek): LocalDate =
  toJavaLocalDate().with(TemporalAdjusters.next(dayOfWeek)).toKotlinLocalDate()

fun LocalDate.nextOrSame(dayOfWeek: DayOfWeek): LocalDate =
  toJavaLocalDate().with(TemporalAdjusters.nextOrSame(dayOfWeek)).toKotlinLocalDate()

fun LocalDate.previous(dayOfWeek: DayOfWeek): LocalDate =
  toJavaLocalDate().with(TemporalAdjusters.previous(dayOfWeek)).toKotlinLocalDate()

fun LocalDate.previousOrSame(dayOfWeek: DayOfWeek): LocalDate =
  toJavaLocalDate().with(TemporalAdjusters.previousOrSame(dayOfWeek)).toKotlinLocalDate()
