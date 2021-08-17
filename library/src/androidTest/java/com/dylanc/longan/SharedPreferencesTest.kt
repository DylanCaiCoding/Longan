package com.dylanc.longan

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * @author Dylan Cai
 */

var i: Int by sharedPreferences("i", default = -1)
var b: Boolean by sharedPreferences("b", default = false)
var l: Long by sharedPreferences("l", default = -1L)
var f: Float by sharedPreferences("f", default = -1f)
var s1: String by sharedPreferences("s1", default = "")
var s2: String? by sharedPreferences("s2")
var set: Set<String> by sharedPreferences("ss", default = emptySet())

@RunWith(AndroidJUnit4::class)
class SharedPreferencesTest {

  @Before
  fun clear() {
    DataRepository.clearAll()
  }

  @Test
  fun putInt() {
    Assert.assertEquals(-1, i)
    i = 5
    Assert.assertEquals(5, i)
  }

  @Test
  fun putBoolean() {
    Assert.assertEquals(false, b)
    b = true
    Assert.assertEquals(true, b)
  }

  @Test
  fun putString() {
    Assert.assertEquals("", s1)
    s1 = "test"
    Assert.assertEquals("test", s1)
  }

  @Test
  fun putString2() {
    Assert.assertEquals(null, s2)
    s2 = "test"
    Assert.assertEquals("test", s2)
  }

  @Test
  fun putLong() {
    Assert.assertEquals(-1L, l)
    l = 3L
    Assert.assertEquals(3L, l)
  }

  @Test
  fun putFloat() {
    Assert.assertEquals(-1f, f)
    f = 0.5f
    Assert.assertEquals(0.5f, f)
  }

  @Test
  fun putSet() {
    Assert.assertEquals(emptySet<String>(), set)
    set = setOf("22", "33")
    Assert.assertEquals(setOf("22", "33"), set)
  }

  @Test
  fun putDataRepositoryInt() {
    Assert.assertEquals(-1, DataRepository.i1)
    DataRepository.i1 = 5
    Assert.assertEquals(5, DataRepository.i1)
  }

  @Test
  fun putDataRepositoryInt2() {
    Assert.assertEquals(-1, DataRepository.i2)
    DataRepository.i2 = 5
    Assert.assertEquals(5, DataRepository.i2)
  }

  @Test
  fun putDataRepositoryBoolean() {
    Assert.assertEquals(false, DataRepository.b1)
    DataRepository.b1 = true
    Assert.assertEquals(true, DataRepository.b1)
  }

  @Test
  fun putDataRepositoryBoolean2() {
    Assert.assertEquals(false, DataRepository.b2)
    DataRepository.b2 = true
    Assert.assertEquals(true, DataRepository.b2)
  }

  @Test
  fun putDataRepositoryString() {
    Assert.assertEquals("", DataRepository.s1)
    DataRepository.s1 = "test"
    Assert.assertEquals("test", DataRepository.s1)
  }

  @Test
  fun putDataRepositoryString2() {
    Assert.assertEquals("", DataRepository.s2)
    DataRepository.s2 = "test"
    Assert.assertEquals("test", DataRepository.s2)
  }

  @Test
  fun putDataRepositoryString3() {
    Assert.assertEquals(null, DataRepository.s3)
    DataRepository.s3 = "??"
    Assert.assertEquals("??", DataRepository.s3)
  }

  @Test
  fun putDataRepositoryString4() {
    Assert.assertEquals(null, DataRepository.s4)
    DataRepository.s4 = "??"
    Assert.assertEquals("??", DataRepository.s4)
  }

  @Test
  fun putDataRepositoryLong() {
    Assert.assertEquals(-1L, DataRepository.l1)
    DataRepository.l1 = 3L
    Assert.assertEquals(3L, DataRepository.l1)
  }

  @Test
  fun putDataRepositoryLong2() {
    Assert.assertEquals(-1L, DataRepository.l2)
    DataRepository.l2 = 3L
    Assert.assertEquals(3L, DataRepository.l2)
  }

  @Test
  fun putDataRepositoryFloat() {
    Assert.assertEquals(-1f, DataRepository.f1)
    DataRepository.f1 = 0.5f
    Assert.assertEquals(0.5f, DataRepository.f1)
  }

  @Test
  fun putDataRepositoryFloat2() {
    Assert.assertEquals(-1f, DataRepository.f2)
    DataRepository.f2 = 0.5f
    Assert.assertEquals(0.5f, DataRepository.f2)
  }

  @Test
  fun putDataRepositorySet() {
    Assert.assertEquals(emptySet<String>(), DataRepository.set1)
    DataRepository.set1 = setOf("22", "33")
    Assert.assertEquals(setOf("22", "33"), DataRepository.set1)
  }

  @Test
  fun putDataRepositorySet2() {
    Assert.assertEquals(emptySet<String>(), DataRepository.set2)
    DataRepository.set2 = setOf("22", "33")
    Assert.assertEquals(setOf("22", "33"), DataRepository.set2)
  }
}

object DataRepository : SharedPreferencesOwner {
  var i1: Int by sharedPreferences(default = -1)
  var i2: Int by sharedPreferences("int", default = -1)

  var b1: Boolean by sharedPreferences(default = false)
  var b2: Boolean by sharedPreferences("boolean", default = false)

  var s1: String by sharedPreferences(default = "")
  var s2: String by sharedPreferences("string", default = "")
  var s3: String? by sharedPreferences()
  var s4: String? by sharedPreferences(key = "string?")

  var l1: Long by sharedPreferences(default = -1L)
  var l2: Long by sharedPreferences("long", default = -1L)

  var f1: Float by sharedPreferences(default = -1f)
  var f2: Float by sharedPreferences("float", default = -1f)

  var set1: Set<String> by sharedPreferences(default = emptySet())
  var set2: Set<String> by sharedPreferences("string_set", default = emptySet())

  fun clearAll() {
    sharedPreferences.clear()
  }
}