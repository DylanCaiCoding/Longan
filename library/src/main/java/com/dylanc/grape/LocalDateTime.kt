@file:Suppress("unused")

package com.dylanc.grape

import org.threeten.bp.DayOfWeek
import org.threeten.bp.Instant
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneOffset
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.temporal.TemporalAdjusters
import java.util.concurrent.TimeUnit

/**
 * @author Dylan Cai
 */

fun Long.toLocalDateTime(
  timeUnit: TimeUnit = TimeUnit.SECONDS,
  zoneOffset: ZoneOffset = ZoneOffset.ofHours(8)
): LocalDateTime =
  when (timeUnit) {
    TimeUnit.SECONDS -> LocalDateTime.ofEpochSecond(this, 0, zoneOffset)
    TimeUnit.MILLISECONDS -> LocalDateTime.ofInstant(Instant.ofEpochMilli(this), zoneOffset)
    else -> throw IllegalArgumentException("This time unit is not supported")
  }

fun String.toLocalDateTime(pattern: String): LocalDateTime =
  LocalDateTime.parse(this, DateTimeFormatter.ofPattern(pattern))

fun LocalDateTime.toEpochMilli(zoneOffset: ZoneOffset = ZoneOffset.ofHours(8)) =
  toInstant(zoneOffset).toEpochMilli()

fun LocalDateTime.format(pattern: String): String =
  format(DateTimeFormatter.ofPattern(pattern))

fun LocalDateTime.firstDayOfMonth(): LocalDateTime =
  with(TemporalAdjusters.firstDayOfMonth())

fun LocalDateTime.firstDayOfNextMonth(): LocalDateTime =
  with(TemporalAdjusters.firstDayOfNextMonth())

fun LocalDateTime.firstDayOfNextYear(): LocalDateTime =
  with(TemporalAdjusters.firstDayOfNextYear())

fun LocalDateTime.firstDayOfYear(): LocalDateTime =
  with(TemporalAdjusters.firstDayOfYear())

fun LocalDateTime.firstInMonth(dayOfWeek: DayOfWeek): LocalDateTime =
  with(TemporalAdjusters.firstInMonth(dayOfWeek))

fun LocalDateTime.next(dayOfWeek: DayOfWeek): LocalDateTime =
  with(TemporalAdjusters.next(dayOfWeek))

fun LocalDateTime.nextOrSame(dayOfWeek: DayOfWeek): LocalDateTime =
  with(TemporalAdjusters.nextOrSame(dayOfWeek))

fun LocalDateTime.previous(dayOfWeek: DayOfWeek): LocalDateTime =
  with(TemporalAdjusters.previous(dayOfWeek))

fun LocalDateTime.previousOrSame(dayOfWeek: DayOfWeek): LocalDateTime =
  with(TemporalAdjusters.previousOrSame(dayOfWeek))

fun LocalDateTime.firstDayOfYear(ordinal: Int, dayOfWeek: DayOfWeek): LocalDateTime =
  with(TemporalAdjusters.dayOfWeekInMonth(ordinal, dayOfWeek))

fun LocalDateTime.lastDayOfMonth(): LocalDateTime =
  with(TemporalAdjusters.lastDayOfMonth())

fun LocalDateTime.lastDayOfYear(): LocalDateTime =
  with(TemporalAdjusters.lastDayOfYear())

fun LocalDateTime.lastDayOfMonth(dayOfWeek: DayOfWeek): LocalDateTime =
  with(TemporalAdjusters.lastInMonth(dayOfWeek))
