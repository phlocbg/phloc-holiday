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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;

import com.phloc.commons.collections.ContainerHelper;
import com.phloc.datetime.PDTFactory;
import com.phloc.holiday.HolidayMap;
import com.phloc.holiday.config.ChristianHoliday;
import com.phloc.holiday.config.ChristianHolidayType;
import com.phloc.holiday.config.ChronologyType;
import com.phloc.holiday.config.Holidays;
import com.phloc.holiday.config.RelativeToEasterSunday;
import com.phloc.holiday.parser.AbstractHolidayParser;

/**
 * @author svdi1de
 */
public final class ChristianHolidayParserTest
{
  private static final AbstractHolidayParser s_aParser = ChristianHolidayParser.getInstance ();

  @Test
  public void testEmpty ()
  {
    final HolidayMap aHolidays = new HolidayMap ();
    final Holidays aConfig = new Holidays ();
    s_aParser.parse (2010, aHolidays, aConfig);
    Assert.assertTrue ("Expected to be empty.", aHolidays.isEmpty ());
  }

  @Test
  public void testEaster ()
  {
    final HolidayMap aHolidays = new HolidayMap ();
    final Holidays aConfig = _createConfig (ChristianHolidayType.EASTER);
    s_aParser.parse (2011, aHolidays, aConfig);
    Assert.assertEquals ("Wrong number of aHolidays.", 1, aHolidays.size ());
    final LocalDate aEasterDate = ContainerHelper.getFirstElement (aHolidays.getAllDates ());
    final LocalDate aEndDate = PDTFactory.createLocalDate (2011, 4, 24);
    Assert.assertEquals ("Wrong easter date.", aEndDate, aEasterDate);
  }

  @Test
  public void testChristianInvalidDate ()
  {
    final HolidayMap aHolidays = new HolidayMap ();
    final Holidays aConfig = _createConfig (ChristianHolidayType.EASTER);
    aConfig.getChristianHoliday ().get (0).setValidTo (Integer.valueOf (2010));
    s_aParser.parse (2011, aHolidays, aConfig);
    Assert.assertEquals ("Wrong number of aHolidays.", 0, aHolidays.size ());
  }

  @Test
  public void testRelativeToEasterSunday ()
  {
    final HolidayMap aHolidays = new HolidayMap ();
    final Holidays aConfig = _createConfig (1);
    final RelativeToEasterSundayParser aParser = new RelativeToEasterSundayParser ();
    aParser.parse (2011, aHolidays, aConfig);
    final List <LocalDate> aExpected = new ArrayList <LocalDate> ();
    aExpected.add (PDTFactory.createLocalDate (2011, 4, 24));
    Assert.assertEquals ("Wrong number of aHolidays.", aExpected.size (), aHolidays.size ());
    Assert.assertEquals ("Wrong holiday.",
                         aExpected.get (0),
                         ContainerHelper.getFirstElement (aHolidays.getAllDates ()));
  }

  @Test
  public void testChristianDates ()
  {
    final HolidayMap aHolidays = new HolidayMap ();
    final Holidays aConfig = _createConfig (ChristianHolidayType.EASTER,
                                           ChristianHolidayType.CLEAN_MONDAY,
                                           ChristianHolidayType.EASTER_SATURDAY,
                                           ChristianHolidayType.EASTER_TUESDAY,
                                           ChristianHolidayType.GENERAL_PRAYER_DAY,
                                           ChristianHolidayType.PENTECOST,
                                           ChristianHolidayType.SACRED_HEART);
    s_aParser.parse (2011, aHolidays, aConfig);
    final List <LocalDate> expected = new ArrayList <LocalDate> ();
    expected.add (PDTFactory.createLocalDate (2011, 3, 7));
    expected.add (PDTFactory.createLocalDate (2011, 4, 23));
    expected.add (PDTFactory.createLocalDate (2011, 4, 24));
    expected.add (PDTFactory.createLocalDate (2011, 4, 26));
    expected.add (PDTFactory.createLocalDate (2011, 5, 20));
    expected.add (PDTFactory.createLocalDate (2011, 6, 12));
    expected.add (PDTFactory.createLocalDate (2011, 7, 1));

    Assert.assertEquals ("Wrong number of aHolidays.", expected.size (), aHolidays.size ());

    Collections.sort (expected);

    final List <LocalDate> found = ContainerHelper.getSorted (aHolidays.getAllDates ());

    for (int i = 0; i < expected.size (); i++)
    {
      Assert.assertEquals ("Wrong date.", expected.get (i), found.get (i));
    }
  }

  private static Holidays _createConfig (final int... days)
  {
    final Holidays aConfig = new Holidays ();
    for (final int day : days)
    {
      final RelativeToEasterSunday d = new RelativeToEasterSunday ();
      d.setDays (day);
      d.setChronology (ChronologyType.GREGORIAN);
      aConfig.getRelativeToEasterSunday ().add (d);
    }
    return aConfig;
  }

  private static Holidays _createConfig (final ChristianHolidayType... types)
  {
    final Holidays aConfig = new Holidays ();
    for (final ChristianHolidayType type : types)
    {
      final ChristianHoliday h = new ChristianHoliday ();
      h.setType (type);
      aConfig.getChristianHoliday ().add (h);
    }
    return aConfig;
  }
}
