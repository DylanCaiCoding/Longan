package com.dylanc.longan

import org.junit.Assert
import org.junit.Test

/**
 * @author Dylan Cai
 */
class StringTest {

  @Test
  fun isPhone() {
    Assert.assertTrue("13888888888".isPhone())
  }

  @Test
  fun isEmail() {
    Assert.assertTrue("dylancai@qq.com".isEmail())
  }

  @Test
  fun isIDCard18() {
    Assert.assertTrue("440881199901014554".isIDCard18())
  }
}