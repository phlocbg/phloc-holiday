/**
 * Copyright 2011 Sven Diedrichsen 
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

import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;
import org.joda.time.chrono.GregorianChronology;
import org.joda.time.chrono.JulianChronology;

import com.phloc.datetime.CPDT;
import com.phloc.datetime.config.PDTConfig;
import com.phloc.holiday.CalendarUtil;
import com.phloc.holiday.HolidayMap;
import com.phloc.holiday.IHolidayType;
import com.phloc.holiday.ResourceBundleHoliday;
import com.phloc.holiday.config.ChronologyType;
import com.phloc.holiday.config.HolidayType;
import com.phloc.holiday.config.Holidays;
import com.phloc.holiday.config.RelativeToEasterSunday;
import com.phloc.holiday.mgr.XMLUtil;
import com.phloc.holiday.parser.AbstractHolidayParser;

/**
 * This parser creates holidays relative to easter sunday.
 * 
 * @author Sven Diedrichsen
 * @version $Id: $
 */
public class RelativeToEasterSundayParser extends AbstractHolidayParser
{
  /**
   * Parses relative to Easter Sunday holidays.
   */
  public void parse (final int nYear, final HolidayMap aHolidayMap, final Holidays aConfig)
  {
    for (final RelativeToEasterSunday aDay : aConfig.getRelativeToEasterSunday ())
    {
      if (!isValid (aDay, nYear))
        continue;
      final LocalDate aEasterSunday = getEasterSunday (nYear, aDay.getChronology ());
      aEasterSunday.plusDays (aDay.getDays ());
      final String sPropertiesKey = "christian." + aDay.getDescriptionPropertiesKey ();
      addChrstianHoliday (aEasterSunday, sPropertiesKey, XMLUtil.getType (aDay.getLocalizedType ()), aHolidayMap);
    }
  }

  /**
   * Adds the given day to the list of holidays.
   * 
   * @param aDate
   *        The day
   * @param sPropertiesKey
   *        a {@link java.lang.String} object.
   * @param aHolidayType
   *        a {@link HolidayType} object.
   * @param holidays
   *        a {@link java.util.Set} object.
   */
  protected final void addChrstianHoliday (final LocalDate aDate,
                                           final String sPropertiesKey,
                                           final IHolidayType aHolidayType,
                                           final HolidayMap holidays)
  {
    final LocalDate convertedDate = CalendarUtil.convertToGregorianDate (aDate);
    holidays.add (convertedDate, new ResourceBundleHoliday (aHolidayType, sPropertiesKey));
  }

  /**
   * Returns the easter Sunday for a given year.
   * 
   * @param nYear
   * @return Easter Sunday.
   */
  public static LocalDate getEasterSunday (final int nYear)
  {
    return nYear <= CPDT.LAST_JULIAN_YEAR ? getJulianEasterSunday (nYear) : getGregorianEasterSunday (nYear);
  }

  public static LocalDate getEasterSunday (final int nYear, final ChronologyType eType)
  {
    return eType == ChronologyType.JULIAN ? getJulianEasterSunday (nYear) : getGregorianEasterSunday (nYear);
  }

  /**
   * Returns the easter Sunday within the julian chronology.
   * 
   * @param nYear
   * @return julian easter Sunday
   */
  public static LocalDate getJulianEasterSunday (final int nYear)
  {
    int a, b, c, d, e;
    int x, nMonth, nDay;
    a = nYear % 4;
    b = nYear % 7;
    c = nYear % 19;
    d = (19 * c + 15) % 30;
    e = (2 * a + 4 * b - d + 34) % 7;
    x = d + e + 114;
    nMonth = x / 31;
    nDay = (x % 31) + 1;
    return new LocalDate (nYear,
                          (nMonth == 3 ? DateTimeConstants.MARCH : DateTimeConstants.APRIL),
                          nDay,
                          JulianChronology.getInstance ());
  }

  /**
   * Returns the easter Sunday within the gregorian chronology.
   * 
   * @param nYear
   * @return gregorian easter Sunday.
   */
  public static LocalDate getGregorianEasterSunday (final int nYear)
  {
    int a, b, c, d, e, f, g, h, i, j, k, l;
    int x, nMonth, nDay;
    a = nYear % 19;
    b = nYear / 100;
    c = nYear % 100;
    d = b / 4;
    e = b % 4;
    f = (b + 8) / 25;
    g = (b - f + 1) / 3;
    h = (19 * a + b - d - g + 15) % 30;
    i = c / 4;
    j = c % 4;
    k = (32 + 2 * e + 2 * i - h - j) % 7;
    l = (a + 11 * h + 22 * k) / 451;
    x = h + k - 7 * l + 114;
    nMonth = x / 31;
    nDay = (x % 31) + 1;
    return new LocalDate (nYear,
                          (nMonth == 3 ? DateTimeConstants.MARCH : DateTimeConstants.APRIL),
                          nDay,
                          GregorianChronology.getInstance (PDTConfig.getDefaultDateTimeZone ()));
  }
}
