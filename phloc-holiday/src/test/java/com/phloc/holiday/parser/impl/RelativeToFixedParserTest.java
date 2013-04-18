/**
 * Original Copyright 2010 Sven Diedrichsen
 * This work is based on jollyday library trunk 2010/10/20
 * http://jollyday.sourceforge.net/
 * Originally licensed under the Apache License, Version 2.0
 *
 * =========
 *
 * Copyright 2010-2013 phloc systems
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

import com.phloc.commons.collections.ContainerHelper;
import com.phloc.datetime.PDTFactory;
import com.phloc.holiday.HolidayMap;
import com.phloc.holiday.config.Fixed;
import com.phloc.holiday.config.Holidays;
import com.phloc.holiday.config.Month;
import com.phloc.holiday.config.RelativeToFixed;
import com.phloc.holiday.config.Weekday;
import com.phloc.holiday.config.When;

/**
 * @author Sven
 */
public final class RelativeToFixedParserTest
{
  private static final RelativeToFixedParser s_aParser = RelativeToFixedParser.getInstance ();

  @Test
  public void testEmpty ()
  {
    final HolidayMap aHolidays = new HolidayMap ();
    final Holidays config = new Holidays ();
    s_aParser.parse (2010, aHolidays, config);
    Assert.assertTrue ("Expected to be empty.", aHolidays.isEmpty ());
  }

  @Test
  public void testInvalid ()
  {
    final HolidayMap aHolidays = new HolidayMap ();
    final Holidays config = new Holidays ();
    final RelativeToFixed rule = new RelativeToFixed ();
    rule.setValidFrom (Integer.valueOf (2011));
    config.getRelativeToFixed ().add (rule);
    s_aParser.parse (2010, aHolidays, config);
    Assert.assertTrue ("Expected to be empty.", aHolidays.isEmpty ());
  }

  @Test
  public void testWeekday ()
  {
    final HolidayMap aHolidays = new HolidayMap ();
    final Holidays config = new Holidays ();
    final RelativeToFixed rule = new RelativeToFixed ();
    rule.setWeekday (Weekday.THURSDAY);
    rule.setWhen (When.AFTER);
    final Fixed date = new Fixed ();
    date.setDay (Integer.valueOf (5));
    date.setMonth (Month.AUGUST);
    rule.setDate (date);
    config.getRelativeToFixed ().add (rule);
    s_aParser.parse (2011, aHolidays, config);
    Assert.assertEquals ("Number of aHolidays wrong.", 1, aHolidays.size ());
    Assert.assertEquals ("Wrong date.",
                         PDTFactory.createLocalDate (2011, 8, 11),
                         ContainerHelper.getFirstElement (aHolidays.getAllDates ()));
  }

  @Test
  public void testNumberOfDays ()
  {
    final HolidayMap aHolidays = new HolidayMap ();
    final Holidays config = new Holidays ();
    final RelativeToFixed rule = new RelativeToFixed ();
    rule.setDays (Integer.valueOf (3));
    rule.setWhen (When.BEFORE);
    final Fixed date = new Fixed ();
    date.setDay (Integer.valueOf (5));
    date.setMonth (Month.AUGUST);
    rule.setDate (date);
    config.getRelativeToFixed ().add (rule);
    s_aParser.parse (2011, aHolidays, config);
    Assert.assertEquals ("Number of aHolidays wrong.", 1, aHolidays.size ());
    Assert.assertEquals ("Wrong date.",
                         PDTFactory.createLocalDate (2011, 8, 2),
                         ContainerHelper.getFirstElement (aHolidays.getAllDates ()));
  }
}
