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
import com.phloc.holiday.config.FixedWeekdayBetweenFixed;
import com.phloc.holiday.config.Holidays;
import com.phloc.holiday.mgr.XMLUtil;
import com.phloc.holiday.parser.AbstractHolidayParser;

/**
 * Parses the configuration for fixed weekdays between two fixed dates.
 * 
 * @author Sven Diedrichsen
 * @author Philip Helger
 */
public class FixedWeekdayBetweenFixedParser extends AbstractHolidayParser
{
  private static final FixedWeekdayBetweenFixedParser s_aInstance = new FixedWeekdayBetweenFixedParser ();

  private FixedWeekdayBetweenFixedParser ()
  {}

  public static FixedWeekdayBetweenFixedParser getInstance ()
  {
    return s_aInstance;
  }

  /**
   * Parses the provided configuration and creates holidays for the provided
   * year.
   */
  public void parse (final int nYear, final HolidayMap aHolidayMap, final Holidays aConfig)
  {
    for (final FixedWeekdayBetweenFixed aFixedWeekdayBetweenFixed : aConfig.getFixedWeekdayBetweenFixed ())
    {
      if (!isValid (aFixedWeekdayBetweenFixed, nYear))
        continue;

      final int nExpectedWeekday = XMLUtil.getWeekday (aFixedWeekdayBetweenFixed.getWeekday ());
      LocalDate aFrom = XMLUtil.create (nYear, aFixedWeekdayBetweenFixed.getFrom ());
      final LocalDate aTo = XMLUtil.create (nYear, aFixedWeekdayBetweenFixed.getTo ());
      LocalDate aResult = null;
      while (!aFrom.isAfter (aTo))
      {
        if (aFrom.getDayOfWeek () == nExpectedWeekday)
        {
          aResult = aFrom;
          break;
        }
        aFrom = aFrom.plusDays (1);
      }

      if (aResult != null)
      {
        final IHolidayType aType = XMLUtil.getType (aFixedWeekdayBetweenFixed.getLocalizedType ());
        final String sPropertyKey = aFixedWeekdayBetweenFixed.getDescriptionPropertiesKey ();
        aHolidayMap.add (aResult, new ResourceBundleHoliday (aType, sPropertyKey));
      }
    }
  }
}
