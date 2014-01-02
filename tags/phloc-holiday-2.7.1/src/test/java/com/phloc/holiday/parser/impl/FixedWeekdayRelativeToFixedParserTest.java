/**
 * Original Copyright 2010 Sven Diedrichsen
 * This work is based on jollyday library trunk 2010/10/20
 * http://jollyday.sourceforge.net/
 * Originally licensed under the Apache License, Version 2.0
 *
 * =========
 *
 * Copyright 2010-2014 phloc systems
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.phloc.commons.collections.ContainerHelper;
import com.phloc.datetime.PDTFactory;
import com.phloc.holiday.HolidayMap;
import com.phloc.holiday.config.Fixed;
import com.phloc.holiday.config.FixedWeekdayRelativeToFixed;
import com.phloc.holiday.config.Holidays;
import com.phloc.holiday.config.Month;
import com.phloc.holiday.config.Weekday;
import com.phloc.holiday.config.When;
import com.phloc.holiday.config.Which;

/**
 * @author Sven
 */
public final class FixedWeekdayRelativeToFixedParserTest
{
  private static final FixedWeekdayRelativeToFixedParser s_aParser = FixedWeekdayRelativeToFixedParser.getInstance ();

  @Test
  public void testEmpty ()
  {
    final HolidayMap aHolidays = new HolidayMap ();
    final Holidays config = new Holidays ();
    s_aParser.parse (2011, aHolidays, config);
    assertTrue ("Result is not empty.", aHolidays.isEmpty ());
  }

  @Test
  public void testInvalid ()
  {
    final HolidayMap aHolidays = new HolidayMap ();
    final Holidays config = new Holidays ();
    final FixedWeekdayRelativeToFixed rule = new FixedWeekdayRelativeToFixed ();
    rule.setWhich (Which.FIRST);
    rule.setWeekday (Weekday.MONDAY);
    rule.setWhen (When.BEFORE);
    final Fixed fixed = new Fixed ();
    fixed.setDay (Integer.valueOf (29));
    fixed.setMonth (Month.JANUARY);
    rule.setDay (fixed);
    config.getFixedWeekdayRelativeToFixed ().add (rule);
    rule.setValidTo (Integer.valueOf (2010));
    s_aParser.parse (2011, aHolidays, config);
    assertTrue ("Result is not empty.", aHolidays.isEmpty ());
  }

  @Test
  public void testParserFirstBefore ()
  {
    final HolidayMap aHolidays = new HolidayMap ();
    final Holidays config = new Holidays ();
    final FixedWeekdayRelativeToFixed rule = new FixedWeekdayRelativeToFixed ();
    rule.setWhich (Which.FIRST);
    rule.setWeekday (Weekday.MONDAY);
    rule.setWhen (When.BEFORE);
    final Fixed fixed = new Fixed ();
    fixed.setDay (Integer.valueOf (29));
    fixed.setMonth (Month.JANUARY);
    rule.setDay (fixed);
    config.getFixedWeekdayRelativeToFixed ().add (rule);
    s_aParser.parse (2011, aHolidays, config);
    assertEquals ("Wrong number of dates.", 1, aHolidays.size ());
    assertEquals ("Wrong date.",
                  PDTFactory.createLocalDate (2011, 1, 24),
                  ContainerHelper.getFirstElement (aHolidays.getAllDates ()));
  }

  @Test
  public void testParserSecondBefore ()
  {
    final HolidayMap aHolidays = new HolidayMap ();
    final Holidays config = new Holidays ();
    final FixedWeekdayRelativeToFixed rule = new FixedWeekdayRelativeToFixed ();
    rule.setWhich (Which.SECOND);
    rule.setWeekday (Weekday.MONDAY);
    rule.setWhen (When.BEFORE);
    final Fixed fixed = new Fixed ();
    fixed.setDay (Integer.valueOf (29));
    fixed.setMonth (Month.JANUARY);
    rule.setDay (fixed);
    config.getFixedWeekdayRelativeToFixed ().add (rule);
    s_aParser.parse (2011, aHolidays, config);
    assertEquals ("Wrong number of dates.", 1, aHolidays.size ());
    assertEquals ("Wrong date.",
                  PDTFactory.createLocalDate (2011, 1, 17),
                  ContainerHelper.getFirstElement (aHolidays.getAllDates ()));
  }

  @Test
  public void testParserThirdAfter ()
  {
    final HolidayMap aHolidays = new HolidayMap ();
    final Holidays config = new Holidays ();
    final FixedWeekdayRelativeToFixed rule = new FixedWeekdayRelativeToFixed ();
    rule.setWhich (Which.THIRD);
    rule.setWeekday (Weekday.MONDAY);
    rule.setWhen (When.AFTER);
    final Fixed fixed = new Fixed ();
    fixed.setDay (Integer.valueOf (29));
    fixed.setMonth (Month.JANUARY);
    rule.setDay (fixed);
    config.getFixedWeekdayRelativeToFixed ().add (rule);
    s_aParser.parse (2011, aHolidays, config);
    assertEquals ("Wrong number of dates.", 1, aHolidays.size ());
    assertEquals ("Wrong date.",
                  PDTFactory.createLocalDate (2011, 2, 14),
                  ContainerHelper.getFirstElement (aHolidays.getAllDates ()));
  }

  @Test
  public void testParserFourthAfter ()
  {
    final HolidayMap aHolidays = new HolidayMap ();
    final Holidays config = new Holidays ();
    final FixedWeekdayRelativeToFixed rule = new FixedWeekdayRelativeToFixed ();
    rule.setWhich (Which.FOURTH);
    rule.setWeekday (Weekday.TUESDAY);
    rule.setWhen (When.AFTER);
    final Fixed fixed = new Fixed ();
    fixed.setDay (Integer.valueOf (15));
    fixed.setMonth (Month.MARCH);
    rule.setDay (fixed);
    config.getFixedWeekdayRelativeToFixed ().add (rule);
    s_aParser.parse (2011, aHolidays, config);
    assertEquals ("Wrong number of dates.", 1, aHolidays.size ());
    assertEquals ("Wrong date.",
                  PDTFactory.createLocalDate (2011, 4, 12),
                  ContainerHelper.getFirstElement (aHolidays.getAllDates ()));
  }
}
