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

import org.joda.time.LocalDate;

import com.phloc.holiday.HolidayMap;
import com.phloc.holiday.IHolidayType;
import com.phloc.holiday.ResourceBundleHoliday;
import com.phloc.holiday.config.FixedWeekdayRelativeToFixed;
import com.phloc.holiday.config.Holidays;
import com.phloc.holiday.config.When;
import com.phloc.holiday.mgr.XMLUtil;
import com.phloc.holiday.parser.AbstractHolidayParser;

/**
 * Parses fixed weekday relative to fixed date.
 * 
 * @author Sven Diedrichsen
 * @author Philip Helger
 */
public class FixedWeekdayRelativeToFixedParser extends AbstractHolidayParser
{
  private static final FixedWeekdayRelativeToFixedParser s_aInstance = new FixedWeekdayRelativeToFixedParser ();

  private FixedWeekdayRelativeToFixedParser ()
  {}

  public static FixedWeekdayRelativeToFixedParser getInstance ()
  {
    return s_aInstance;
  }

  /**
   * Parses the provided configuration and creates holidays for the provided
   * year.
   */
  public void parse (final int nYear, final HolidayMap aHolidayMap, final Holidays aConfig)
  {
    for (final FixedWeekdayRelativeToFixed aFixedWeekdayRelativeToFixed : aConfig.getFixedWeekdayRelativeToFixed ())
    {
      if (!isValid (aFixedWeekdayRelativeToFixed, nYear))
        continue;

      // parsing fixed day
      final int nExpectedWeekday = XMLUtil.getWeekday (aFixedWeekdayRelativeToFixed.getWeekday ());
      LocalDate aDay = XMLUtil.create (nYear, aFixedWeekdayRelativeToFixed.getDay ());
      do
      {
        // move fixed to first occurrence of weekday
        aDay = aFixedWeekdayRelativeToFixed.getWhen () == When.AFTER ? aDay.plusDays (1) : aDay.minusDays (1);
      } while (aDay.getDayOfWeek () != nExpectedWeekday);
      int nDays = 0;
      switch (aFixedWeekdayRelativeToFixed.getWhich ())
      {
        case FIRST:
          break;
        case SECOND:
          nDays = 7;
          break;
        case THIRD:
          nDays = 14;
          break;
        case FOURTH:
          nDays = 21;
          break;
        case LAST:
          // seems to be unsupported
          break;
      }
      // move day further if it is second, third or fourth weekday
      aDay = aFixedWeekdayRelativeToFixed.getWhen () == When.AFTER ? aDay.plusDays (nDays) : aDay.minusDays (nDays);
      final IHolidayType aType = XMLUtil.getType (aFixedWeekdayRelativeToFixed.getLocalizedType ());
      final String sPropertyKey = aFixedWeekdayRelativeToFixed.getDescriptionPropertiesKey ();
      aHolidayMap.add (aDay, new ResourceBundleHoliday (aType, sPropertyKey));
    }
  }
}
