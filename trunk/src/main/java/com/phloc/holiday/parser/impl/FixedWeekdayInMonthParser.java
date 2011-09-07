/**
 * Original Copyright 2010 Sven Diedrichsen
 * This work is based on jollyday library trunk 2010/10/20
 * http://jollyday.sourceforge.net/
 * Originally licensed under the Apache License, Version 2.0
 *
 * =========
 *
 * Copyright 2010-2011 phloc systems
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

import com.phloc.datetime.PDTFactory;
import com.phloc.holiday.HolidayMap;
import com.phloc.holiday.IHolidayType;
import com.phloc.holiday.ResourceBundleHoliday;
import com.phloc.holiday.config.FixedWeekdayInMonth;
import com.phloc.holiday.config.Holidays;
import com.phloc.holiday.config.Which;
import com.phloc.holiday.mgr.XMLUtil;
import com.phloc.holiday.parser.AbstractHolidayParser;

/**
 * @author Sven Diedrichsen
 * @author philip
 */
public class FixedWeekdayInMonthParser extends AbstractHolidayParser
{
  private static final FixedWeekdayInMonthParser s_aInstance = new FixedWeekdayInMonthParser ();

  protected FixedWeekdayInMonthParser ()
  {}

  public static FixedWeekdayInMonthParser getInstance ()
  {
    return s_aInstance;
  }

  public void parse (final int nYear, final HolidayMap aHolidays, final Holidays aConfig)
  {
    for (final FixedWeekdayInMonth aFWM : aConfig.getFixedWeekday ())
    {
      if (!isValid (aFWM, nYear))
        continue;
      final LocalDate aDate = parse (nYear, aFWM);
      final IHolidayType aType = XMLUtil.getType (aFWM.getLocalizedType ());
      final String sPropertyKey = aFWM.getDescriptionPropertiesKey ();
      aHolidays.add (aDate, new ResourceBundleHoliday (aType, sPropertyKey));
    }
  }

  protected static LocalDate parse (final int nYear, final FixedWeekdayInMonth aFixedWeekdayInMonth)
  {
    LocalDate aDate = PDTFactory.createLocalDate (nYear, XMLUtil.getMonth (aFixedWeekdayInMonth.getMonth ()), 1);
    int nDirection = 1;
    if (aFixedWeekdayInMonth.getWhich () == Which.LAST)
    {
      aDate = aDate.withDayOfMonth (aDate.dayOfMonth ().getMaximumValue ());
      nDirection = -1;
    }
    final int nWeekDay = XMLUtil.getWeekday (aFixedWeekdayInMonth.getWeekday ());
    while (aDate.getDayOfWeek () != nWeekDay)
    {
      aDate = aDate.plusDays (nDirection);
    }
    switch (aFixedWeekdayInMonth.getWhich ())
    {
      case SECOND:
        aDate = aDate.plusDays (7);
        break;
      case THIRD:
        aDate = aDate.plusDays (14);
        break;
      case FOURTH:
        aDate = aDate.plusDays (21);
    }
    return aDate;
  }
}
