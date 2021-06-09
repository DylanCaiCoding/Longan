@file:Suppress("unused", "NOTHING_TO_INLINE")

package com.dylanc.longan

import org.threeten.bp.*
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.temporal.TemporalAdjusters
import java.util.concurrent.TimeUnit

/**
 * @author Dylan Cai
 */

inline val nowLocalDateTime: LocalDateTime get() = LocalDateTime.now()

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

inline fun LocalDateTime.toEpochSecond() = toEpochSecond(ZoneOffset.ofHours(8))

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

inline fun LocalDateTime.dayOfWeekInMonth(ordinal: Int, dayOfWeek: DayOfWeek): LocalDateTime =
  with(TemporalAdjusters.dayOfWeekInMonth(ordinal, dayOfWeek))

inline fun LocalDateTime.lastDayOfMonth(): LocalDateTime =
  with(TemporalAdjusters.lastDayOfMonth())

inline fun LocalDateTime.lastDayOfYear(): LocalDateTime =
  with(TemporalAdjusters.lastDayOfYear())

inline fun LocalDateTime.lastDayOfMonth(dayOfWeek: DayOfWeek): LocalDateTime =
  with(TemporalAdjusters.lastInMonth(dayOfWeek))

val Int.nanoseconds: Duration
  get() = Duration.ofNanos(toLong())

val Int.microseconds: Duration
  get() = Duration.ofNanos(toLong() * 1000L)

val Int.milliseconds: Duration
  get() = Duration.ofMillis(toLong())

val Int.seconds: Duration
  get() = Duration.ofSeconds(toLong())

val Int.minutes: Duration
  get() = Duration.ofMinutes(toLong())

val Int.hours: Duration
  get() = Duration.ofHours(toLong())

val Duration.ago: LocalDateTime
  get() = nowLocalDateTime - this

val Duration.fromNow: LocalDateTime
  get() = nowLocalDateTime + this