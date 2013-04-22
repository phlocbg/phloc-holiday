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

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.joda.time.LocalDate;

import com.phloc.commons.collections.ArrayHelper;
import com.phloc.datetime.PDTFactory;
import com.phloc.holiday.HolidayMap;
import com.phloc.holiday.IHolidayManager;
import com.phloc.holiday.ISingleHoliday;

/**
 * Abstract base class for all holiday manager implementations.
 *
 * @author Sven Diedrichsen
 * @author Philip Helger
 */
public abstract class AbstractHolidayManager implements IHolidayManager
{
  /**
   * Caches the holidays for a given year.
   */
  private final Map <String, HolidayMap> m_aHolidaysPerYear = new HashMap <String, HolidayMap> ();

  protected AbstractHolidayManager ()
  {}

  /**
   * Calls isHoliday with JODA time object.
   *
   * @see #isHoliday(LocalDate c, String... args)
   */
  public boolean isHoliday (@Nonnull final Calendar aCalendar, final String... args)
  {
    return isHoliday (PDTFactory.createLocalDate (aCalendar), args);
  }

  public boolean isHoliday (@Nonnull final LocalDate aDate, @Nullable final String... aArgs)
  {
    return getHoliday (aDate, aArgs) != null;
  }

  @Nullable
  public ISingleHoliday getHoliday (@Nonnull final LocalDate aDate, @Nullable final String... aArgs)
  {
    if (aDate == null)
      throw new NullPointerException ("date");

    String sKey = Integer.toString (aDate.getYear ());
    if (ArrayHelper.isNotEmpty (aArgs))
    {
      final StringBuilder aKey = new StringBuilder (sKey);
      for (final String sArg : aArgs)
        aKey.append ('_').append (sArg);
      sKey = aKey.toString ();
    }
    HolidayMap aHolidayMap = m_aHolidaysPerYear.get (sKey);
    if (aHolidayMap == null)
    {
      aHolidayMap = getHolidays (aDate.getYear (), aArgs);
      m_aHolidaysPerYear.put (sKey, aHolidayMap);
    }
    return aHolidayMap.getHolidayForDate (aDate);
  }

  /**
   * Returns the configured hierarchy structure for the specific manager. This
   * hierarchy shows how the configured holidays are structured and can be
   * retrieved.
   *
   * @return The hierarchy
   */
  protected abstract CalendarHierarchy getHierarchy ();
}
