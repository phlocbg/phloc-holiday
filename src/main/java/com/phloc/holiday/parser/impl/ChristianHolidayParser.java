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
import com.phloc.holiday.config.ChristianHoliday;
import com.phloc.holiday.config.ChronologyType;
import com.phloc.holiday.config.Holidays;
import com.phloc.holiday.mgr.XMLUtil;
import com.phloc.holiday.parser.AbstractHolidayParser;

/**
 * This parser creates christian holidays for the given year relative to easter
 * sunday.
 * 
 * @author Sven Diedrichsen
 * @author philip
 */
public class ChristianHolidayParser extends AbstractHolidayParser
{
  private static final ChristianHolidayParser s_aInstance = new ChristianHolidayParser ();

  private ChristianHolidayParser ()
  {}

  public static ChristianHolidayParser getInstance ()
  {
    return s_aInstance;
  }

  /**
   * Parses all christian holidays relative to eastern.
   */
  public void parse (final int nYear, final HolidayMap aHolidayMap, final Holidays aConfig)
  {
    for (final ChristianHoliday aChristianHoliday : aConfig.getChristianHoliday ())
    {
      if (!isValid (aChristianHoliday, nYear))
        continue;

      LocalDate aEasterSunday;
      if (aChristianHoliday.getChronology () == ChronologyType.JULIAN)
        aEasterSunday = getJulianEasterSunday (nYear);
      else
        if (aChristianHoliday.getChronology () == ChronologyType.GREGORIAN)
          aEasterSunday = getGregorianEasterSunday (nYear);
        else
          aEasterSunday = getEasterSunday (nYear);

      switch (aChristianHoliday.getType ())
      {
        case EASTER:
          break;
        case CLEAN_MONDAY:
        case SHROVE_MONDAY:
          aEasterSunday = aEasterSunday.minusDays (48);
          break;
        case MARDI_GRAS:
        case CARNIVAL:
          aEasterSunday = aEasterSunday.minusDays (47);
          break;
        case ASH_WEDNESDAY:
          aEasterSunday = aEasterSunday.minusDays (46);
          break;
        case MAUNDY_THURSDAY:
          aEasterSunday = aEasterSunday.minusDays (3);
          break;
        case GOOD_FRIDAY:
          aEasterSunday = aEasterSunday.minusDays (2);
          break;
        case EASTER_SATURDAY:
          aEasterSunday = aEasterSunday.minusDays (1);
          break;
        case EASTER_MONDAY:
          aEasterSunday = aEasterSunday.plusDays (1);
          break;
        case EASTER_TUESDAY:
          aEasterSunday = aEasterSunday.plusDays (2);
          break;
        case GENERAL_PRAYER_DAY:
          aEasterSunday = aEasterSunday.plusDays (26);
          break;
        case ASCENSION_DAY:
          aEasterSunday = aEasterSunday.plusDays (39);
          break;
        case PENTECOST:
          aEasterSunday = aEasterSunday.plusDays (49);
          break;
        case WHIT_MONDAY:
        case PENTECOST_MONDAY:
          aEasterSunday = aEasterSunday.plusDays (50);
          break;
        case CORPUS_CHRISTI:
          aEasterSunday = aEasterSunday.plusDays (60);
          break;
        case SACRED_HEART:
          aEasterSunday = aEasterSunday.plusDays (68);
          break;
        default:
          throw new IllegalArgumentException ("Unknown christian holiday type " + aChristianHoliday.getType ());
      }
      final LocalDate aConvertedDate = CalendarUtil.convertToGregorianDate (aEasterSunday);
      final IHolidayType aType = XMLUtil.getType (aChristianHoliday.getLocalizedType ());
      final String sPropertiesKey = "christian." + aChristianHoliday.getType ().name ();
      aHolidayMap.add (aConvertedDate, new ResourceBundleHoliday (aType, sPropertiesKey));
    }
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
