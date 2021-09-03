package com.dylanc.longan

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * @author Dylan Cai
 */

@RunWith(AndroidJUnit4::class)
class SharedPreferencesTest {

  @Before
  fun clear() {
    DataRepository.clearAll()
  }

  @Test
  fun putInt() {
    Assert.assertEquals(-1, DataRepository.i)
    DataRepository.i = 5
    Assert.assertEquals(5, DataRepository.i)
  }

  @Test
  fun putBoolean() {
    Assert.assertEquals(false, DataRepository.b)
    DataRepository.b = true
    Assert.assertEquals(true, DataRepository.b)
  }

  @Test
  fun putString1() {
    Assert.assertEquals("", DataRepository.s1)
    DataRepository.s1 = "test"
    Assert.assertEquals("test", DataRepository.s1)
  }

  @Test
  fun putString2() {
    Assert.assertEquals(null, DataRepository.s2)
    DataRepository.s2 = "??"
    Assert.assertEquals("??", DataRepository.s2)
  }

  @Test
  fun putLong() {
    Assert.assertEquals(-1L, DataRepository.l)
    DataRepository.l = 3L
    Assert.assertEquals(3L, DataRepository.l)
  }

  @Test
  fun putFloat() {
    Assert.assertEquals(-1f, DataRepository.f)
    DataRepository.f = 0.5f
    Assert.assertEquals(0.5f, DataRepository.f)
  }

  @Test
  fun putSet() {
    Assert.assertEquals(emptySet<String>(), DataRepository.set)
    DataRepository.set = setOf("22", "33")
    Assert.assertEquals(setOf("22", "33"), DataRepository.set)
  }

}

object DataRepository : SharedPreferencesOwner {
  var i: Int by sharedPreferences(default = -1)
  var b: Boolean by sharedPreferences(default = false)
  var s1: String by sharedPreferences(default = "")
  var s2: String? by sharedPreferences()
  var l: Long by sharedPreferences(default = -1L)
  var f: Float by sharedPreferences(default = -1f)
  var set: Set<String> by sharedPreferences(default = emptySet())

  fun clearAll() {
    sharedPreferences.clear()
  }
}