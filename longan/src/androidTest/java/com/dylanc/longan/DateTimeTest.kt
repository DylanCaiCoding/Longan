package com.dylanc.longan

import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.DayOfWeek.*
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.TextStyle
import java.time.temporal.ChronoUnit.DAYS
import java.util.*

class DateTimeTest {

  private val epochMillis = 1643673600000
  private val epochSeconds = epochMillis / 1000
  private val localDateTime = LocalDateTime.of(2022, 2, 1, 8, 0, 0)
  private val localDate = localDateTime.toLocalDate()
  private val timeString = "2022-02-01 08:00:00"
  private val timePattern = "yyyy-MM-dd HH:mm:ss"

  @Test
  fun millisecondsToString() {
    assertEquals(timeString, Instant.ofEpochMilli(epochMillis).format(timePattern))
  }

  @Test
  fun millisecondsToLocalDateTime() {
    assertEquals(localDateTime, Instant.ofEpochMilli(epochMillis).toLocalDateTime())
  }

  @Test
  fun secondsToString() {
    assertEquals(timeString, Instant.ofEpochSecond(epochSeconds).format(timePattern))
  }

  @Test
  fun secondsToLocalDateTime() {
    assertEquals(localDateTime, Instant.ofEpochSecond(epochSeconds).toLocalDateTime())
  }

  @Test
  fun stringToMilliseconds() {
    assertEquals(epochMillis, timeString.toEpochMilliseconds(timePattern))
  }

  @Test
  fun stringToSeconds() {
    assertEquals(epochSeconds, timeString.toEpochSeconds(timePattern))
  }

  @Test
  fun stringToLocalDateTime() {
    assertEquals(localDateTime, timeString.toLocalDateTime(timePattern))
  }

  @Test
  fun localDateTimeToString() {
    assertEquals(timeString, localDateTime.format(timePattern))
  }

  @Test
  fun localDateTimeToMilliseconds() {
    assertEquals(epochMillis, localDateTime.toEpochMilli())
  }

  @Test
  fun localDateTimeToSeconds() {
    assertEquals(epochSeconds, localDateTime.toEpochSecond())
  }

  @Test
  fun getWeekString() {
    val dayOfWeek = localDateTime.dayOfWeek

    assertEquals("星期二", dayOfWeek.getDisplayName(TextStyle.FULL, Locale.CHINA))
    assertEquals("周二", dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.CHINA))
    assertEquals("二", dayOfWeek.getDisplayName(TextStyle.NARROW, Locale.CHINA))

    assertEquals("Tuesday", dayOfWeek.getDisplayName(TextStyle.FULL, Locale.ENGLISH))
    assertEquals("Tue", dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.ENGLISH))
    assertEquals("T", dayOfWeek.getDisplayName(TextStyle.NARROW, Locale.ENGLISH))
  }

  @Test
  fun getMonthString() {
    val month = localDateTime.month

    assertEquals("二月", month.getDisplayName(TextStyle.FULL, Locale.CHINA))
    assertEquals("2月", month.getDisplayName(TextStyle.SHORT, Locale.CHINA))
    assertEquals("二", month.getDisplayName(TextStyle.NARROW, Locale.CHINA))

    assertEquals("February", month.getDisplayName(TextStyle.FULL, Locale.ENGLISH))
    assertEquals("Feb", month.getDisplayName(TextStyle.SHORT, Locale.ENGLISH))
    assertEquals("F", month.getDisplayName(TextStyle.NARROW, Locale.ENGLISH))
  }

  @Test
  fun getFirstOrLastDay() {
    assertEquals("2022-02-01", localDate.firstDayOfMonth().toString())
    assertEquals("2022-02-28", localDate.lastDayOfMonth().toString())
    assertEquals("2022-03-01", localDate.firstDayOfNextMonth().toString())
    assertEquals("2022-01-01", localDate.firstDayOfLastMonth().toString())
    assertEquals("2022-01-01", localDate.firstDayOfYear().toString())
    assertEquals("2022-12-31", localDate.lastDayOfYear().toString())
    assertEquals("2023-01-01", localDate.firstDayOfNextYear().toString())
    assertEquals("2021-01-01", localDate.firstDayOfLastYear().toString())
  }

  @Test
  fun getSomeWeek() {
    assertEquals("2022-02-06", localDate.firstInMonth(SUNDAY).toString())
    assertEquals("2022-02-27", localDate.lastInMonth(SUNDAY).toString())
    assertEquals("2022-02-08", localDate.next(TUESDAY).toString())
    assertEquals("2022-01-25", localDate.previous(TUESDAY).toString())
    assertEquals("2022-02-01", localDate.nextOrSame(TUESDAY).toString())
    assertEquals("2022-02-01", localDate.previousOrSame(TUESDAY).toString())
    assertEquals("2022-02-09", localDate.dayOfWeekInMonth(2, WEDNESDAY).toString())
  }

  private fun getFriendlyTimeString(millis: Long): String {
    val localDateTime = Instant.ofEpochMilli(millis).toLocalDateTime()
    val today = LocalDate.now()
    val pattern = when {
      localDateTime.toLocalDate() == today -> "HH:mm"
      localDateTime.toLocalDate() == today.minus(1, DAYS) -> "昨天 HH:mm"
      localDateTime.toLocalDate() >= today.previousOrSame(MONDAY) -> "EE HH:mm"
      localDateTime.year == today.year -> "MM月dd日 ${localDateTime.timeRange} HH:mm"
      else -> "yyyy年MM月dd日 ${localDateTime.timeRange} HH:mm"
    }
    return localDateTime.format(pattern, Locale.CHINA)
  }

  private val LocalDateTime.timeRange: String
    get() = when (hour) {
      in 0..5 -> "凌晨"
      in 6..12 -> "早上"
      in 13..17 -> "下午"
      else -> "晚上"
    }
}