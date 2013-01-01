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
package com.phloc.holiday.mgr;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.joda.time.LocalDate;

import com.phloc.commons.collections.ArrayHelper;
import com.phloc.holiday.HolidayManagerFactory;
import com.phloc.holiday.HolidayMap;
import com.phloc.holiday.IHolidayManager;
import com.phloc.holiday.ISingleHoliday;

/**
 * @author svdi1de
 */
public abstract class AbstractCountryTestBase
{
  /**
   * Compares two hierarchy structure by traversing down.
   * 
   * @param expected
   *        This is the test structure which is how it should be.
   * @param found
   *        This is the real live data structure.
   */
  protected void compareHierarchies (final CalendarHierarchy expected, final CalendarHierarchy found)
  {
    assertNotNull ("Null description", found.getDescription (Locale.getDefault ()));
    assertEquals ("Wrong hierarchy id.", expected.getID (), found.getID ());
    assertEquals ("Number of children wrong.", expected.getChildren ().size (), found.getChildren ().size ());
    for (final String id : expected.getChildren ().keySet ())
    {
      assertTrue ("Missing " + id + " within " + found.getID (), found.getChildren ().containsKey (id));
      compareHierarchies (expected.getChildren ().get (id), found.getChildren ().get (id));
    }
  }

  protected void compareData (final AbstractHolidayManager expected, final IHolidayManager found, final int year)
  {
    final CalendarHierarchy expectedHierarchy = expected.getHierarchy ();
    final ArrayList <String> args = new ArrayList <String> ();
    _compareDates (expected, found, expectedHierarchy, args, year);
  }

  private void _compareDates (final IHolidayManager aExpected,
                             final IHolidayManager aFound,
                             final CalendarHierarchy aHierarchy,
                             final List <String> args,
                             final int nYear)
  {
    final HolidayMap aExpectedHolidays = aExpected.getHolidays (nYear, ArrayHelper.newArray (args, String.class));
    final HolidayMap aFoundHolidays = aFound.getHolidays (nYear, ArrayHelper.newArray (args, String.class));
    for (final Map.Entry <LocalDate, ISingleHoliday> aEntry : aExpectedHolidays.getMap ().entrySet ())
    {
      assertNotNull ("Description is null.", aEntry.getValue ().getHolidayName (Locale.getDefault ()));
      if (!aFoundHolidays.containsHolidayForDate (aEntry.getKey ()))
      {
        fail ("Could not find " +
              aEntry.getKey () +
              " // " +
              aEntry.getValue () +
              " in " +
              aHierarchy.getDescription (Locale.getDefault ()) +
              " - " +
              aFoundHolidays);
      }
    }
    for (final String id : aHierarchy.getChildren ().keySet ())
    {
      final ArrayList <String> newArgs = new ArrayList <String> (args);
      newArgs.add (id);
      _compareDates (aExpected, aFound, aHierarchy.getChildren ().get (id), newArgs, nYear);
    }
  }

  protected void validateCalendarData (final String countryCode, final int year) throws Exception
  {
    final AbstractHolidayManager dataManager = (AbstractHolidayManager) HolidayManagerFactory.getHolidayManager (countryCode);
    final AbstractHolidayManager testManager = (AbstractHolidayManager) HolidayManagerFactory.getHolidayManager ("test_" +
                                                                                                                 countryCode +
                                                                                                                 "_" +
                                                                                                                 Integer.toString (year));

    final CalendarHierarchy dataHierarchy = dataManager.getHierarchy ();
    final CalendarHierarchy testHierarchy = dataManager.getHierarchy ();

    compareHierarchies (testHierarchy, dataHierarchy);
    compareData (testManager, dataManager, year);
  }
}
