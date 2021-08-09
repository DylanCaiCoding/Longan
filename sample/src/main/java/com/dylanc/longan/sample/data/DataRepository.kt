package com.dylanc.longan.sample.data

import com.dylanc.longan.SharedPreferencesOwner
import com.dylanc.longan.sharedPreferences

/**
 * @author Dylan Cai
 */

var i: Int by sharedPreferences("key", default = -1)
var b: Boolean by sharedPreferences("key", default = false)
var s1: String by sharedPreferences("key", default = "")
var s2: String? by sharedPreferences("key")
var l: Long by sharedPreferences("key", default = -1)
var f: Float by sharedPreferences("key", default = -1f)

object DataRepository : SharedPreferencesOwner {
  var i1: Int by sharedPreferences(default = -1)
  var i2: Int by sharedPreferences("key", default = -1)

  var b1: Boolean by sharedPreferences(default = false)
  var b2: Boolean by sharedPreferences("key", default = false)

  var s1: String by sharedPreferences(default = "")
  var s2: String by sharedPreferences("key", default = "")
  var s3: String? by sharedPreferences()
  var s4: String? by sharedPreferences("key")

  var l1: Long by sharedPreferences(default = -1)
  var l2: Long by sharedPreferences("key", default = -1)

  var f1: Float by sharedPreferences(default = -1f)
  var f2: Float by sharedPreferences("key", default = -1f)
}

object DataRepository2 {
  var i: Int by sharedPreferences("key", default = -1)
  var b: Boolean by sharedPreferences("key", default = false)
  var s1: String by sharedPreferences("key", default = "")
  var s2: String? by sharedPreferences("key")
  var l: Long by sharedPreferences("key", default = -1)
  var f: Float by sharedPreferences("key", default = -1f)
}