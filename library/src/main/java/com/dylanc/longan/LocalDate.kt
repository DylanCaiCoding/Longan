@file:Suppress("unused", "NOTHING_TO_INLINE")

package com.dylanc.longan

import org.threeten.bp.*
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.temporal.TemporalAdjusters
import java.util.concurrent.TimeUnit

/**
 * @author Dylan Cai
 */

inline val nowLocalDate: LocalDate get() = LocalDate.now()

inline fun Long.toLocalDate(
  timeUnit: TimeUnit = TimeUnit.SECONDS,
  zoneOffset: ZoneOffset = ZoneOffset.ofHours(8)
): LocalDate =
  when (timeUnit) {
    TimeUnit.SECONDS -> Instant.ofEpochSecond(this).atOffset(zoneOffset).toLocalDate()
    TimeUnit.MILLISECONDS -> Instant.ofEpochMilli(this).atOffset(zoneOffset).toLocalDate()
    else -> throw IllegalArgumentException("This time unit is not supported")
  }

inline fun String.toLocalDate(pattern: String): LocalDate =
  LocalDate.parse(this, DateTimeFormatter.ofPattern(pattern))

inline fun LocalDate.toEpochSecond(zoneOffset: ZoneOffset = ZoneOffset.ofHours(8)) =
  atStartOfDay(zoneOffset).toInstant().epochSecond

inline fun LocalDate.toEpochMilli(zoneOffset: ZoneOffset = ZoneOffset.ofHours(8)) =
  atStartOfDay(zoneOffset).toInstant().toEpochMilli()

inline fun LocalDate.format(pattern: String): String =
  format(DateTimeFormatter.ofPattern(pattern))

inline fun LocalDate.firstDayOfMonth(): LocalDate =
  with(TemporalAdjusters.firstDayOfMonth())

inline fun LocalDate.firstDayOfNextMonth(): LocalDate =
  with(TemporalAdjusters.firstDayOfNextMonth())

inline fun LocalDate.firstDayOfNextYear(): LocalDate =
  with(TemporalAdjusters.firstDayOfNextYear())

inline fun LocalDate.firstDayOfYear(): LocalDate =
  with(TemporalAdjusters.firstDayOfYear())

inline fun LocalDate.firstInMonth(dayOfWeek: DayOfWeek): LocalDate =
  with(TemporalAdjusters.firstInMonth(dayOfWeek))

inline fun LocalDate.next(dayOfWeek: DayOfWeek): LocalDate =
  with(TemporalAdjusters.next(dayOfWeek))

inline fun LocalDate.nextOrSame(dayOfWeek: DayOfWeek): LocalDate =
  with(TemporalAdjusters.nextOrSame(dayOfWeek))

inline fun LocalDate.previous(dayOfWeek: DayOfWeek): LocalDate =
  with(TemporalAdjusters.previous(dayOfWeek))

inline fun LocalDate.previousOrSame(dayOfWeek: DayOfWeek): LocalDate =
  with(TemporalAdjusters.previousOrSame(dayOfWeek))

inline fun LocalDate.dayOfWeekInMonth(ordinal: Int, dayOfWeek: DayOfWeek): LocalDate =
  with(TemporalAdjusters.dayOfWeekInMonth(ordinal, dayOfWeek))

inline fun LocalDate.lastDayOfMonth(): LocalDate =
  with(TemporalAdjusters.lastDayOfMonth())

inline fun LocalDate.lastDayOfYear(): LocalDate =
  with(TemporalAdjusters.lastDayOfYear())

inline fun LocalDate.lastDayOfMonth(dayOfWeek: DayOfWeek): LocalDate =
  with(TemporalAdjusters.lastInMonth(dayOfWeek))

val Int.days: Period
  get() = Period.ofDays(this)

val Int.weeks: Period
  get() = Period.ofWeeks(this)

val Int.months: Period
  get() = Period.ofMonths(this)

val Int.years: Period
  get() = Period.ofYears(this)

val Period.ago: LocalDate
  get() = nowLocalDate - this

val Period.fromNow: LocalDate
  get() = nowLocalDate + this