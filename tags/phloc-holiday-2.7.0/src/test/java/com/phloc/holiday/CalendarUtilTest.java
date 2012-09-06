/**
 * Original Copyright 2010 Sven Diedrichsen
 * This work is based on jollyday library trunk 2010/10/20
 * http://jollyday.sourceforge.net/
 * Originally licensed under the Apache License, Version 2.0
 *
 * =========
 *
 * Copyright 2010-2012 phloc systems
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
package com.phloc.holiday;

import java.util.HashSet;
import java.util.Set;

import junit.framework.Assert;

import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;
import org.junit.Test;

import com.phloc.datetime.PDTFactory;
import com.phloc.datetime.PDTUtils;

/**
 * @author svdi1de
 */
public final class CalendarUtilTest
{
  @Test
  public void testWeekend ()
  {
    final LocalDate dateFriday = PDTFactory.createLocalDate (2010, DateTimeConstants.MARCH, 12);
    final LocalDate dateSaturday = PDTFactory.createLocalDate (2010, DateTimeConstants.MARCH, 13);
    final LocalDate dateSunday = PDTFactory.createLocalDate (2010, DateTimeConstants.MARCH, 14);
    final LocalDate dateMonday = PDTFactory.createLocalDate (2010, DateTimeConstants.MARCH, 15);
    Assert.assertFalse (PDTUtils.isWeekend (dateFriday));
    Assert.assertTrue (PDTUtils.isWeekend (dateSaturday));
    Assert.assertTrue (PDTUtils.isWeekend (dateSunday));
    Assert.assertFalse (PDTUtils.isWeekend (dateMonday));
  }

  @Test
  public void testCalendarIslamicNewYear ()
  {
    final Set <LocalDate> expected = new HashSet <LocalDate> ();
    expected.add (PDTFactory.createLocalDate (2008, DateTimeConstants.JANUARY, 10));
    expected.add (PDTFactory.createLocalDate (2008, DateTimeConstants.DECEMBER, 29));
    final Set <LocalDate> holidays = CalendarUtil.getIslamicHolidaysInGregorianYear (2008, 1, 1);
    Assert.assertNotNull (holidays);
    Assert.assertEquals ("Wrong number of islamic new years in 2008.", expected.size (), holidays.size ());
    Assert.assertEquals ("Wrong islamic New Year holidays in 2008.", expected, holidays);
  }

  @Test
  public void testCalendarIslamicAschura2008 ()
  {
    final Set <LocalDate> expected = new HashSet <LocalDate> ();
    expected.add (PDTFactory.createLocalDate (2008, DateTimeConstants.JANUARY, 19));
    final Set <LocalDate> holidays = CalendarUtil.getIslamicHolidaysInGregorianYear (2008, 1, 10);
    Assert.assertNotNull (holidays);
    Assert.assertEquals ("Wrong number of islamic Aschura holidays in 2008.", expected.size (), holidays.size ());
    Assert.assertEquals ("Wrong islamic Aschura holidays in 2008.", expected, holidays);
  }

  @Test
  public void testCalendarIslamicAschura2009 ()
  {
    final Set <LocalDate> expected = new HashSet <LocalDate> ();
    expected.add (PDTFactory.createLocalDate (2009, DateTimeConstants.JANUARY, 7));
    expected.add (PDTFactory.createLocalDate (2009, DateTimeConstants.DECEMBER, 27));
    final Set <LocalDate> holidays = CalendarUtil.getIslamicHolidaysInGregorianYear (2009, 1, 10);
    Assert.assertNotNull (holidays);
    Assert.assertEquals ("Wrong number of islamic Aschura holidays in 2009.", expected.size (), holidays.size ());
    Assert.assertEquals ("Wrong islamic Aschura holidays in 2009.", expected, holidays);
  }

  @Test
  public void testCalendarIslamicIdAlFitr2008 ()
  {
    final Set <LocalDate> expected = new HashSet <LocalDate> ();
    expected.add (PDTFactory.createLocalDate (2008, DateTimeConstants.OCTOBER, 2));
    final Set <LocalDate> holidays = CalendarUtil.getIslamicHolidaysInGregorianYear (2008, 10, 1);
    Assert.assertNotNull (holidays);
    Assert.assertEquals ("Wrong number of islamic IdAlFitr holidays in 2008.", expected.size (), holidays.size ());
    Assert.assertEquals ("Wrong islamic IdAlFitr holidays in 2008.", expected, holidays);
  }

  @Test
  public void testCalendarIslamicIdAlFitr2009 ()
  {
    final Set <LocalDate> expected = new HashSet <LocalDate> ();
    expected.add (PDTFactory.createLocalDate (2009, DateTimeConstants.SEPTEMBER, 21));
    final Set <LocalDate> holidays = CalendarUtil.getIslamicHolidaysInGregorianYear (2009, 10, 1);
    Assert.assertNotNull (holidays);
    Assert.assertEquals ("Wrong number of islamic IdAlFitr holidays in 2009.", expected.size (), holidays.size ());
    Assert.assertEquals ("Wrong islamic IdAlFitr holidays in 2009.", expected, holidays);
  }

}
