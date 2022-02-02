package com.dylanc.longan

import kotlinx.datetime.*
import kotlinx.datetime.TimeZone
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.DayOfWeek.*
import java.time.format.TextStyle
import java.util.*

class DateTimeTest {

  private val epochMillis = 1643673600000
  private val epochSeconds = epochMillis / 1000
  private val localDateTime = LocalDateTime(2022, 2, 1, 8, 0, 0)
  private val localDate = localDateTime.date
  private val timeString = "2022-02-01 08:00:00"
  private val timePattern = "yyyy-MM-dd HH:mm:ss"
  private val timeZone = TimeZone.currentSystemDefault()

  @Test
  fun millisecondsToString() {
    assertEquals(timeString, Instant.fromEpochMilliseconds(epochMillis).format(timePattern, timeZone))
  }

  @Test
  fun millisecondsToLocalDateTime() {
    assertEquals(localDateTime, Instant.fromEpochMilliseconds(epochMillis).toLocalDateTime(timeZone))
  }

  @Test
  fun secondsToString() {
    assertEquals(timeString, Instant.fromEpochSeconds(epochSeconds).format(timePattern, timeZone))
  }

  @Test
  fun secondsToLocalDateTime() {
    assertEquals(localDateTime, Instant.fromEpochSeconds(epochSeconds).toLocalDateTime(timeZone))
  }

  @Test
  fun stringToMilliseconds() {
    assertEquals(epochMillis, timeString.toInstant(timePattern, timeZone).toEpochMilliseconds())
  }

  @Test
  fun stringToSeconds() {
    assertEquals(epochSeconds, timeString.toInstant(timePattern, timeZone).epochSeconds)
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
    assertEquals(epochMillis, localDateTime.toInstant(timeZone).toEpochMilliseconds())
  }

  @Test
  fun localDateTimeToSeconds() {
    assertEquals(epochSeconds, localDateTime.toInstant(timeZone).epochSeconds)
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
    assertEquals("2", month.getDisplayName(TextStyle.NARROW, Locale.CHINA))

    assertEquals("February", month.getDisplayName(TextStyle.FULL, Locale.ENGLISH))
    assertEquals("Feb", month.getDisplayName(TextStyle.SHORT, Locale.ENGLISH))
    assertEquals("F", month.getDisplayName(TextStyle.NARROW, Locale.ENGLISH))
  }

  @Test
  fun getFirstDayOrLastDay() {
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
}