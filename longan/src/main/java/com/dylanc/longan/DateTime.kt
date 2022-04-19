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

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import java.time.*
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoField.DAY_OF_MONTH
import java.time.temporal.ChronoField.DAY_OF_YEAR
import java.time.temporal.ChronoUnit
import java.time.temporal.ChronoUnit.MONTHS
import java.time.temporal.ChronoUnit.YEARS
import java.time.temporal.TemporalAdjusters
import java.util.*
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

val systemZoneId: ZoneId by object : ReadOnlyProperty<Any?, ZoneId> {
  private lateinit var zoneId: ZoneId

  override fun getValue(thisRef: Any?, property: KProperty<*>): ZoneId {
    if (!::zoneId.isInitialized) {
      zoneId = ZoneId.systemDefault()
      application.registerReceiver(object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent?) {
          if (intent?.action == Intent.ACTION_TIMEZONE_CHANGED) {
            TimeZone.setDefault(null)
            zoneId = ZoneId.systemDefault()
          }
        }
      }, IntentFilter(Intent.ACTION_TIMEZONE_CHANGED))
    }
    return zoneId
  }
}

fun Instant.format(pattern: String, zone: ZoneId = systemZoneId, locale: Locale? = null): String =
  dateTimeFormatterOf(pattern, locale).withZone(zone).format(this)

fun LocalDateTime.format(pattern: String, locale: Locale? = null): String =
  dateTimeFormatterOf(pattern, locale).format(this)

fun LocalDate.format(pattern: String, locale: Locale? = null): String =
  dateTimeFormatterOf(pattern, locale).format(this)

fun LocalDateTime.toInstant(zone: ZoneId = systemZoneId): Instant =
  atZone(zone).toInstant()

fun Instant.toLocalDateTime(zone: ZoneId = systemZoneId): LocalDateTime =
  LocalDateTime.ofInstant(this, zone)

fun LocalDateTime.toEpochSecond(zone: ZoneId = systemZoneId): Long =
  atZone(zone).toEpochSecond()

fun LocalDateTime.toEpochMilli(zone: ZoneId = systemZoneId): Long =
  toEpochSecond(zone) * 1000 + toLocalTime().nano / 1000000

fun String.toInstant(pattern: String, zone: ZoneId = systemZoneId): Instant =
  ZonedDateTime.parse(this, DateTimeFormatter.ofPattern(pattern).withZone(zone)).toInstant()

fun String.toLocalDateTime(pattern: String): LocalDateTime =
  LocalDateTime.parse(this, DateTimeFormatter.ofPattern(pattern))

fun String.toLocalDate(pattern: String): LocalDate =
  LocalDate.parse(this, DateTimeFormatter.ofPattern(pattern))

fun String.toEpochMilliseconds(pattern: String, zone: ZoneId = systemZoneId): Long =
  toInstant(pattern, zone).toEpochMilli()

fun String.toEpochSeconds(pattern: String, zone: ZoneId = systemZoneId): Long =
  toInstant(pattern, zone).epochSecond

fun LocalDateTime.isToday(zone: ZoneId = systemZoneId): Boolean = toLocalDate().isToday(zone)

fun LocalDate.isToday(zone: ZoneId = systemZoneId): Boolean = this == LocalDate.now(zone)

fun LocalDateTime.isYesterday(zone: ZoneId = systemZoneId): Boolean = toLocalDate().isYesterday(zone)

fun LocalDate.isYesterday(zone: ZoneId = systemZoneId): Boolean =
  this == LocalDate.now(zone).minus(1, ChronoUnit.DAYS)

fun LocalDateTime.firstDayOfYear(): LocalDateTime = with(TemporalAdjusters.firstDayOfYear())

fun LocalDateTime.lastDayOfYear(): LocalDateTime = with(TemporalAdjusters.lastDayOfYear())

fun LocalDateTime.firstDayOfNextYear(): LocalDateTime = with(TemporalAdjusters.firstDayOfNextYear())

fun LocalDateTime.firstDayOfLastYear(): LocalDateTime = with { it.with(DAY_OF_YEAR, 1).minus(1, YEARS) }

fun LocalDateTime.firstDayOfMonth(): LocalDateTime = with(TemporalAdjusters.firstDayOfMonth())

fun LocalDateTime.lastDayOfMonth(): LocalDateTime = with(TemporalAdjusters.lastDayOfMonth())

fun LocalDateTime.firstDayOfNextMonth(): LocalDateTime = with(TemporalAdjusters.firstDayOfNextMonth())

fun LocalDateTime.firstDayOfLastMonth(): LocalDateTime = with { it.with(DAY_OF_MONTH, 1).minus(1, MONTHS) }

fun LocalDateTime.firstInMonth(dayOfWeek: DayOfWeek): LocalDateTime = with(TemporalAdjusters.firstInMonth(dayOfWeek))

fun LocalDateTime.lastInMonth(dayOfWeek: DayOfWeek): LocalDateTime = with(TemporalAdjusters.lastInMonth(dayOfWeek))

fun LocalDateTime.dayOfWeekInMonth(ordinal: Int, dayOfWeek: DayOfWeek): LocalDateTime = with(TemporalAdjusters.dayOfWeekInMonth(ordinal, dayOfWeek))

fun LocalDateTime.next(dayOfWeek: DayOfWeek): LocalDateTime = with(TemporalAdjusters.next(dayOfWeek))

fun LocalDateTime.nextOrSame(dayOfWeek: DayOfWeek): LocalDateTime = with(TemporalAdjusters.nextOrSame(dayOfWeek))

fun LocalDateTime.previous(dayOfWeek: DayOfWeek): LocalDateTime = with(TemporalAdjusters.previous(dayOfWeek))

fun LocalDateTime.previousOrSame(dayOfWeek: DayOfWeek): LocalDateTime = with(TemporalAdjusters.previousOrSame(dayOfWeek))

fun LocalDate.firstDayOfYear(): LocalDate = with(TemporalAdjusters.firstDayOfYear())

fun LocalDate.lastDayOfYear(): LocalDate = with(TemporalAdjusters.lastDayOfYear())

fun LocalDate.firstDayOfNextYear(): LocalDate = with(TemporalAdjusters.firstDayOfNextYear())

fun LocalDate.firstDayOfLastYear(): LocalDate = with { it.with(DAY_OF_YEAR, 1).minus(1, YEARS) }

fun LocalDate.firstDayOfMonth(): LocalDate = with(TemporalAdjusters.firstDayOfMonth())

fun LocalDate.lastDayOfMonth(): LocalDate = with(TemporalAdjusters.lastDayOfMonth())

fun LocalDate.firstDayOfNextMonth(): LocalDate = with(TemporalAdjusters.firstDayOfNextMonth())

fun LocalDate.firstDayOfLastMonth(): LocalDate = with { it.with(DAY_OF_MONTH, 1).minus(1, MONTHS) }

fun LocalDate.firstInMonth(dayOfWeek: DayOfWeek): LocalDate = with(TemporalAdjusters.firstInMonth(dayOfWeek))

fun LocalDate.lastInMonth(dayOfWeek: DayOfWeek): LocalDate = with(TemporalAdjusters.lastInMonth(dayOfWeek))

fun LocalDate.dayOfWeekInMonth(ordinal: Int, dayOfWeek: DayOfWeek): LocalDate = with(TemporalAdjusters.dayOfWeekInMonth(ordinal, dayOfWeek))

fun LocalDate.next(dayOfWeek: DayOfWeek): LocalDate = with(TemporalAdjusters.next(dayOfWeek))

fun LocalDate.nextOrSame(dayOfWeek: DayOfWeek): LocalDate = with(TemporalAdjusters.nextOrSame(dayOfWeek))

fun LocalDate.previous(dayOfWeek: DayOfWeek): LocalDate = with(TemporalAdjusters.previous(dayOfWeek))

fun LocalDate.previousOrSame(dayOfWeek: DayOfWeek): LocalDate = with(TemporalAdjusters.previousOrSame(dayOfWeek))

private fun dateTimeFormatterOf(pattern: String, locale: Locale?): DateTimeFormatter =
  if (locale != null) {
    DateTimeFormatter.ofPattern(pattern, locale)
  } else {
    DateTimeFormatter.ofPattern(pattern)
  }
