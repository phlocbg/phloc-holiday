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

import java.util.Map;

import javax.annotation.Nullable;

import org.joda.time.LocalDate;

import com.phloc.commons.locale.country.ECountry;
import com.phloc.holiday.EHolidayType;
import com.phloc.holiday.HolidayMap;
import com.phloc.holiday.ISingleHoliday;
import com.phloc.holiday.ResourceBundleHoliday;

/**
 * @author svdi1de
 * @author philip
 */
public final class XMLHolidayManagerJapan extends XMLHolidayManager
{
  public static final String COUNTRY_ID = ECountry.JP.getID ();
  /**
   * The properties key for Japanese bridging holidays.
   */
  private static final String BRIDGING_HOLIDAY_PROPERTIES_KEY = "BRIDGING_HOLIDAY";

  public XMLHolidayManagerJapan ()
  {
    super (COUNTRY_ID);
  }

  /**
   * Implements the rule which requests if two holidays have one non holiday
   * between each other than this day is also a holiday.
   */
  @Override
  public HolidayMap getHolidays (final int nYear, @Nullable final String... aArgs)
  {
    final HolidayMap aHolidays = super.getHolidays (nYear, aArgs);
    final HolidayMap aAdditionalHolidays = new HolidayMap ();
    for (final Map.Entry <LocalDate, ISingleHoliday> aEntry : aHolidays.getMap ().entrySet ())
    {
      final LocalDate aTwoDaysLater = aEntry.getKey ().plusDays (2);
      if (aHolidays.containsHolidayForDate (aTwoDaysLater))
      {
        final LocalDate aBridgingDate = aTwoDaysLater.minusDays (1);
        aAdditionalHolidays.add (aBridgingDate, new ResourceBundleHoliday (EHolidayType.OFFICIAL_HOLIDAY,
                                                                           BRIDGING_HOLIDAY_PROPERTIES_KEY));
      }
    }
    aHolidays.addAll (aAdditionalHolidays);
    return aHolidays;
  }
}
