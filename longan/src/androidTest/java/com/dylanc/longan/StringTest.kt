package com.dylanc.longan

import org.junit.Assert
import org.junit.Test

/**
 * @author Dylan Cai
 */
class StringTest {

  @Test
  fun isPhone() {
    // China mobile:
    Assert.assertTrue("13488888888".isPhone())
    Assert.assertTrue("13588888888".isPhone())
    Assert.assertTrue("13688888888".isPhone())
    Assert.assertTrue("13788888888".isPhone())
    Assert.assertTrue("13888888888".isPhone())
    Assert.assertTrue("13988888888".isPhone())
    Assert.assertTrue("14788888888".isPhone())
    Assert.assertTrue("14888888888".isPhone())
    Assert.assertTrue("15088888888".isPhone())
    Assert.assertTrue("15188888888".isPhone())
    Assert.assertTrue("15288888888".isPhone())
    Assert.assertTrue("15788888888".isPhone())
    Assert.assertTrue("15888888888".isPhone())
    Assert.assertTrue("15988888888".isPhone())
    Assert.assertTrue("17288888888".isPhone())
    Assert.assertTrue("17888888888".isPhone())
    Assert.assertTrue("18288888888".isPhone())
    Assert.assertTrue("18388888888".isPhone())
    Assert.assertTrue("18488888888".isPhone())
    Assert.assertTrue("18788888888".isPhone())
    Assert.assertTrue("18888888888".isPhone())
    Assert.assertTrue("19588888888".isPhone())
    Assert.assertTrue("19888888888".isPhone())

    // China unicom:
    Assert.assertTrue("13088888888".isPhone())
    Assert.assertTrue("13188888888".isPhone())
    Assert.assertTrue("13288888888".isPhone())
    Assert.assertTrue("14588888888".isPhone())
    Assert.assertTrue("14688888888".isPhone())
    Assert.assertTrue("15588888888".isPhone())
    Assert.assertTrue("15688888888".isPhone())
    Assert.assertTrue("16688888888".isPhone())
    Assert.assertTrue("17588888888".isPhone())
    Assert.assertTrue("18588888888".isPhone())
    Assert.assertTrue("18688888888".isPhone())
    Assert.assertTrue("19688888888".isPhone())

    // China telecom:
    Assert.assertTrue("13388888888".isPhone())
    Assert.assertTrue("14988888888".isPhone())
    Assert.assertTrue("15388888888".isPhone())
    Assert.assertTrue("17388888888".isPhone())
    Assert.assertTrue("17488888888".isPhone())
    Assert.assertTrue("17788888888".isPhone())
    Assert.assertTrue("18088888888".isPhone())
    Assert.assertTrue("18188888888".isPhone())
    Assert.assertTrue("18988888888".isPhone())
    Assert.assertTrue("19188888888".isPhone())
    Assert.assertTrue("19388888888".isPhone())
    Assert.assertTrue("19988888888".isPhone())

    // China nrta:
    Assert.assertTrue("19088888888".isPhone())
    Assert.assertTrue("19288888888".isPhone())
    Assert.assertTrue("19788888888".isPhone())

    // China telecom virtual
    Assert.assertTrue("16288888888".isPhone())
    Assert.assertTrue("17008888888".isPhone())
    Assert.assertTrue("17018888888".isPhone())
    Assert.assertTrue("17028888888".isPhone())

    // China unicom virtual
    Assert.assertTrue("16788888888".isPhone())
    Assert.assertTrue("17048888888".isPhone())
    Assert.assertTrue("17078888888".isPhone())
    Assert.assertTrue("17088888888".isPhone())
    Assert.assertTrue("17098888888".isPhone())
    Assert.assertTrue("17188888888".isPhone())

    // China mobile virtual
    Assert.assertTrue("16588888888".isPhone())
    Assert.assertTrue("17038888888".isPhone())
    Assert.assertTrue("17058888888".isPhone())
    Assert.assertTrue("17068888888".isPhone())

    Assert.assertFalse("138888".isPhone())
    Assert.assertFalse("28888888888".isPhone())
  }

  @Test
  fun isEmail() {
    Assert.assertTrue("dylancai@qq.com".isEmail())
    Assert.assertFalse("dylancai.com".isEmail())
  }

  @Test
  fun isIDCard18() {
    Assert.assertTrue("440881199901014554".isIDCard18())
    Assert.assertFalse("44088119990101".isIDCard18())
  }
}