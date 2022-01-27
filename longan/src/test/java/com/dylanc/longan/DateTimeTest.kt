package com.dylanc.longan

import kotlinx.datetime.*
import kotlinx.datetime.TimeZone
import org.junit.Assert.*
import org.junit.Test
import java.time.format.TextStyle
import java.util.*

class DateTimeTest {

  private val epochMillis = 1643738522000
  private val epochSeconds = epochMillis / 1000
  private val localDateTime = LocalDateTime(2022, 2, 2, 2, 2, 2)
  private val timeString = "2022-02-02 02:02:02"
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

    assertEquals("星期三", dayOfWeek.getDisplayName(TextStyle.FULL, Locale.CHINA))
    assertEquals("周三", dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.CHINA))
    assertEquals("三", dayOfWeek.getDisplayName(TextStyle.NARROW, Locale.CHINA))

    assertEquals("Wednesday", dayOfWeek.getDisplayName(TextStyle.FULL, Locale.ENGLISH))
    assertEquals("Wed", dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.ENGLISH))
    assertEquals("W", dayOfWeek.getDisplayName(TextStyle.NARROW, Locale.ENGLISH))
    DayOfWeek.MONDAY
    Month.JANUARY
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
}