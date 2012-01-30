/**
 * Copyright 2011 Sven Diedrichsen 
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 * 
 * http://www.apache.org/licenses/LICENSE-2.0 
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an 
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either 
 * express or implied. See the License for the specific language 
 * governing permissions and limitations under the License. 
 */
package com.phloc.holiday.parser.impl;

import org.junit.Assert;
import org.junit.Test;

import com.phloc.holiday.HolidayMap;
import com.phloc.holiday.config.FixedWeekdayInMonth;
import com.phloc.holiday.config.Holidays;

/**
 * @author svdi1de
 */
public final class FixedWeekdayInMonthParserTest
{
  private static final FixedWeekdayInMonthParser parser = FixedWeekdayInMonthParser.getInstance ();

  @Test
  public void testEmpty ()
  {
    final HolidayMap aHolidays = new HolidayMap ();
    final Holidays config = new Holidays ();
    parser.parse (2010, aHolidays, config);
    Assert.assertTrue ("Expected to be empty.", aHolidays.isEmpty ());
  }

  @Test
  public void testInvalid ()
  {
    final HolidayMap aHolidays = new HolidayMap ();
    final Holidays config = new Holidays ();
    final FixedWeekdayInMonth e = new FixedWeekdayInMonth ();
    e.setValidFrom (Integer.valueOf (2011));
    config.getFixedWeekday ().add (e);
    parser.parse (2010, aHolidays, config);
    Assert.assertEquals ("Expected to be empty.", 0, aHolidays.size ());
  }
}