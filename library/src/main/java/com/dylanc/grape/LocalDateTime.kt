@file:Suppress("unused", "NOTHING_TO_INLINE")

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

inline fun Long.toLocalDateTime(
  timeUnit: TimeUnit = TimeUnit.SECONDS,
  zoneOffset: ZoneOffset = ZoneOffset.ofHours(8)
): LocalDateTime =
  when (timeUnit) {
    TimeUnit.SECONDS -> LocalDateTime.ofEpochSecond(this, 0, zoneOffset)
    TimeUnit.MILLISECONDS -> LocalDateTime.ofInstant(Instant.ofEpochMilli(this), zoneOffset)
    else -> throw IllegalArgumentException("This time unit is not supported")
  }

inline fun String.toLocalDateTime(pattern: String): LocalDateTime =
  LocalDateTime.parse(this, DateTimeFormatter.ofPattern(pattern))

inline fun LocalDateTime.toEpochMilli(zoneOffset: ZoneOffset = ZoneOffset.ofHours(8)) =
  toInstant(zoneOffset).toEpochMilli()

inline fun LocalDateTime.format(pattern: String): String =
  format(DateTimeFormatter.ofPattern(pattern))

inline fun LocalDateTime.firstDayOfMonth(): LocalDateTime =
  with(TemporalAdjusters.firstDayOfMonth())

inline fun LocalDateTime.firstDayOfNextMonth(): LocalDateTime =
  with(TemporalAdjusters.firstDayOfNextMonth())

inline fun LocalDateTime.firstDayOfNextYear(): LocalDateTime =
  with(TemporalAdjusters.firstDayOfNextYear())

inline fun LocalDateTime.firstDayOfYear(): LocalDateTime =
  with(TemporalAdjusters.firstDayOfYear())

inline fun LocalDateTime.firstInMonth(dayOfWeek: DayOfWeek): LocalDateTime =
  with(TemporalAdjusters.firstInMonth(dayOfWeek))

inline fun LocalDateTime.next(dayOfWeek: DayOfWeek): LocalDateTime =
  with(TemporalAdjusters.next(dayOfWeek))

inline fun LocalDateTime.nextOrSame(dayOfWeek: DayOfWeek): LocalDateTime =
  with(TemporalAdjusters.nextOrSame(dayOfWeek))

inline fun LocalDateTime.previous(dayOfWeek: DayOfWeek): LocalDateTime =
  with(TemporalAdjusters.previous(dayOfWeek))

inline fun LocalDateTime.previousOrSame(dayOfWeek: DayOfWeek): LocalDateTime =
  with(TemporalAdjusters.previousOrSame(dayOfWeek))

inline fun LocalDateTime.firstDayOfYear(ordinal: Int, dayOfWeek: DayOfWeek): LocalDateTime =
  with(TemporalAdjusters.dayOfWeekInMonth(ordinal, dayOfWeek))

inline fun LocalDateTime.lastDayOfMonth(): LocalDateTime =
  with(TemporalAdjusters.lastDayOfMonth())

inline fun LocalDateTime.lastDayOfYear(): LocalDateTime =
  with(TemporalAdjusters.lastDayOfYear())

inline fun LocalDateTime.lastDayOfMonth(dayOfWeek: DayOfWeek): LocalDateTime =
  with(TemporalAdjusters.lastInMonth(dayOfWeek))