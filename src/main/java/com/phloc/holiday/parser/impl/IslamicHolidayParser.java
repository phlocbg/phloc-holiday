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

import java.util.Set;

import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;

import com.phloc.holiday.CalendarUtil;
import com.phloc.holiday.HolidayMap;
import com.phloc.holiday.IHolidayType;
import com.phloc.holiday.ResourceBundleHoliday;
import com.phloc.holiday.config.Holidays;
import com.phloc.holiday.config.IslamicHoliday;
import com.phloc.holiday.mgr.XMLUtil;
import com.phloc.holiday.parser.AbstractHolidayParser;

/**
 * This parser calculates gregorian dates for the different islamic holidays.
 * 
 * @author Sven Diedrichsen
 * @author philip
 */
public class IslamicHolidayParser extends AbstractHolidayParser
{
  private static final IslamicHolidayParser s_aInstance = new IslamicHolidayParser ();

  private IslamicHolidayParser ()
  {}

  public static IslamicHolidayParser getInstance ()
  {
    return s_aInstance;
  }

  public void parse (final int nYear, final HolidayMap aHolidayMap, final Holidays aConfig)
  {
    for (final IslamicHoliday aIslamicHoliday : aConfig.getIslamicHoliday ())
    {
      if (!isValid (aIslamicHoliday, nYear))
        continue;

      Set <LocalDate> aIslamicHolidays;
      switch (aIslamicHoliday.getType ())
      {
        case NEWYEAR:
          aIslamicHolidays = CalendarUtil.getIslamicHolidaysInGregorianYear (nYear, DateTimeConstants.JANUARY, 1);
          break;
        case ASCHURA:
          aIslamicHolidays = CalendarUtil.getIslamicHolidaysInGregorianYear (nYear, DateTimeConstants.JANUARY, 10);
          break;
        case ID_AL_FITR:
          aIslamicHolidays = CalendarUtil.getIslamicHolidaysInGregorianYear (nYear, DateTimeConstants.OCTOBER, 1);
          break;
        case ID_UL_ADHA:
          aIslamicHolidays = CalendarUtil.getIslamicHolidaysInGregorianYear (nYear, DateTimeConstants.DECEMBER, 10);
          break;
        case LAILAT_AL_BARAT:
          aIslamicHolidays = CalendarUtil.getIslamicHolidaysInGregorianYear (nYear, DateTimeConstants.AUGUST, 15);
          break;
        case LAILAT_AL_MIRAJ:
          aIslamicHolidays = CalendarUtil.getIslamicHolidaysInGregorianYear (nYear, DateTimeConstants.JULY, 27);
          break;
        case LAILAT_AL_QADR:
          aIslamicHolidays = CalendarUtil.getIslamicHolidaysInGregorianYear (nYear, DateTimeConstants.SEPTEMBER, 27);
          break;
        case MAWLID_AN_NABI:
          aIslamicHolidays = CalendarUtil.getIslamicHolidaysInGregorianYear (nYear, DateTimeConstants.MARCH, 12);
          break;
        case RAMADAN:
          aIslamicHolidays = CalendarUtil.getIslamicHolidaysInGregorianYear (nYear, DateTimeConstants.SEPTEMBER, 1);
          break;
        default:
          throw new IllegalArgumentException ("Unknown islamic holiday " + aIslamicHoliday.getType ());
      }

      final IHolidayType aType = XMLUtil.getType (aIslamicHoliday.getLocalizedType ());
      final String sPropertiesKey = "islamic." + aIslamicHoliday.getType ().name ();
      for (final LocalDate aDate : aIslamicHolidays)
        aHolidayMap.add (aDate, new ResourceBundleHoliday (aType, sPropertiesKey));
    }
  }
}
