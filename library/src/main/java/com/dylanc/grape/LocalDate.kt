@file:Suppress("unused")

package com.dylanc.grape

import org.threeten.bp.DayOfWeek
import org.threeten.bp.Instant
import org.threeten.bp.LocalDate
import org.threeten.bp.ZoneOffset
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.temporal.TemporalAdjusters
import java.util.concurrent.TimeUnit

/**
 * @author Dylan Cai
 */

fun Long.toLocalDate(
  timeUnit: TimeUnit = TimeUnit.SECONDS,
  zoneOffset: ZoneOffset = ZoneOffset.ofHours(8)
): LocalDate =
  when (timeUnit) {
    TimeUnit.SECONDS -> Instant.ofEpochSecond(this).atOffset(zoneOffset).toLocalDate()
    TimeUnit.MILLISECONDS -> Instant.ofEpochMilli(this).atOffset(zoneOffset).toLocalDate()
    else -> throw IllegalArgumentException("This time unit is not supported")
  }

fun String.toLocalDate(pattern: String): LocalDate =
  LocalDate.parse(this, org.threeten.bp.format.DateTimeFormatter.ofPattern(pattern))

fun LocalDate.toEpochSecond(zoneOffset: ZoneOffset = ZoneOffset.ofHours(8)) =
  atStartOfDay(zoneOffset).toInstant().epochSecond

fun LocalDate.toEpochMilli(zoneOffset: ZoneOffset = ZoneOffset.ofHours(8)) =
  atStartOfDay(zoneOffset).toInstant().toEpochMilli()

fun LocalDate.format(pattern: String): String =
  format(DateTimeFormatter.ofPattern(pattern))

fun LocalDate.firstDayOfMonth(): LocalDate =
  with(TemporalAdjusters.firstDayOfMonth())

fun LocalDate.firstDayOfNextMonth(): LocalDate =
  with(TemporalAdjusters.firstDayOfNextMonth())

fun LocalDate.firstDayOfNextYear(): LocalDate =
  with(TemporalAdjusters.firstDayOfNextYear())

fun LocalDate.firstDayOfYear(): LocalDate =
  with(TemporalAdjusters.firstDayOfYear())

fun LocalDate.firstInMonth(dayOfWeek: DayOfWeek): LocalDate =
  with(TemporalAdjusters.firstInMonth(dayOfWeek))

fun LocalDate.next(dayOfWeek: DayOfWeek): LocalDate =
  with(TemporalAdjusters.next(dayOfWeek))

fun LocalDate.nextOrSame(dayOfWeek: DayOfWeek): LocalDate =
  with(TemporalAdjusters.nextOrSame(dayOfWeek))

fun LocalDate.previous(dayOfWeek: DayOfWeek): LocalDate =
  with(TemporalAdjusters.previous(dayOfWeek))

fun LocalDate.previousOrSame(dayOfWeek: DayOfWeek): LocalDate =
  with(TemporalAdjusters.previousOrSame(dayOfWeek))

fun LocalDate.firstDayOfYear(ordinal: Int, dayOfWeek: DayOfWeek): LocalDate =
  with(TemporalAdjusters.dayOfWeekInMonth(ordinal, dayOfWeek))

fun LocalDate.lastDayOfMonth(): LocalDate =
  with(TemporalAdjusters.lastDayOfMonth())

fun LocalDate.lastDayOfYear(): LocalDate =
  with(TemporalAdjusters.lastDayOfYear())

fun LocalDate.lastDayOfMonth(dayOfWeek: DayOfWeek): LocalDate =
  with(TemporalAdjusters.lastInMonth(dayOfWeek))