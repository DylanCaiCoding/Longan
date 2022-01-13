package com.dylanc.longan

import org.junit.Assert
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * @author Dylan Cai
 */
class StringTest {

  @Test
  fun isPhone() {
    // China mobile:
    assertTrue("13488888888".isPhone())
    assertTrue("13588888888".isPhone())
    assertTrue("13688888888".isPhone())
    assertTrue("13788888888".isPhone())
    assertTrue("13888888888".isPhone())
    assertTrue("13988888888".isPhone())
    assertTrue("14788888888".isPhone())
    assertTrue("14888888888".isPhone())
    assertTrue("15088888888".isPhone())
    assertTrue("15188888888".isPhone())
    assertTrue("15288888888".isPhone())
    assertTrue("15788888888".isPhone())
    assertTrue("15888888888".isPhone())
    assertTrue("15988888888".isPhone())
    assertTrue("17288888888".isPhone())
    assertTrue("17888888888".isPhone())
    assertTrue("18288888888".isPhone())
    assertTrue("18388888888".isPhone())
    assertTrue("18488888888".isPhone())
    assertTrue("18788888888".isPhone())
    assertTrue("18888888888".isPhone())
    assertTrue("19588888888".isPhone())
    assertTrue("19888888888".isPhone())

    // China unicom:
    assertTrue("13088888888".isPhone())
    assertTrue("13188888888".isPhone())
    assertTrue("13288888888".isPhone())
    assertTrue("14588888888".isPhone())
    assertTrue("14688888888".isPhone())
    assertTrue("15588888888".isPhone())
    assertTrue("15688888888".isPhone())
    assertTrue("16688888888".isPhone())
    assertTrue("17588888888".isPhone())
    assertTrue("18588888888".isPhone())
    assertTrue("18688888888".isPhone())
    assertTrue("19688888888".isPhone())

    // China telecom:
    assertTrue("13388888888".isPhone())
    assertTrue("14988888888".isPhone())
    assertTrue("15388888888".isPhone())
    assertTrue("17388888888".isPhone())
    assertTrue("17488888888".isPhone())
    assertTrue("17788888888".isPhone())
    assertTrue("18088888888".isPhone())
    assertTrue("18188888888".isPhone())
    assertTrue("18988888888".isPhone())
    assertTrue("19188888888".isPhone())
    assertTrue("19388888888".isPhone())
    assertTrue("19988888888".isPhone())

    // China nrta:
    assertTrue("19088888888".isPhone())
    assertTrue("19288888888".isPhone())
    assertTrue("19788888888".isPhone())

    // China telecom virtual
    assertTrue("16288888888".isPhone())
    assertTrue("17008888888".isPhone())
    assertTrue("17018888888".isPhone())
    assertTrue("17028888888".isPhone())

    // China unicom virtual
    assertTrue("16788888888".isPhone())
    assertTrue("17048888888".isPhone())
    assertTrue("17078888888".isPhone())
    assertTrue("17088888888".isPhone())
    assertTrue("17098888888".isPhone())
    assertTrue("17188888888".isPhone())

    // China mobile virtual
    assertTrue("16588888888".isPhone())
    assertTrue("17038888888".isPhone())
    assertTrue("17058888888".isPhone())
    assertTrue("17068888888".isPhone())

    assertFalse("138888".isPhone())
    assertFalse("28888888888".isPhone())
  }

  @Test
  fun isEmail() {
    assertTrue("dylancai@qq.com".isEmail())
    assertFalse("dylancai.com".isEmail())
  }

  @Test
  fun isIDCard18() {
    assertTrue("440881199901014554".isIDCard18())
    assertFalse("44088119990101".isIDCard18())
  }
}