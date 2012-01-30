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
package com.phloc.holiday.mgr;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;

import com.phloc.datetime.PDTFactory;
import com.phloc.holiday.EHolidayType;
import com.phloc.holiday.IHolidayType;
import com.phloc.holiday.config.Fixed;
import com.phloc.holiday.config.HolidayType;
import com.phloc.holiday.config.Month;
import com.phloc.holiday.config.Weekday;

/**
 * @author svdi1de
 * @author philip
 */
public final class XMLUtil
{
  private XMLUtil ()
  {}

  /**
   * Returns the {@link DateTimeConstants} value for the given weekday.
   * 
   * @param eWeekday
   * @return {@link DateTimeConstants} value.
   */
  @Nonnegative
  public static int getWeekday (final Weekday eWeekday)
  {
    switch (eWeekday)
    {
      case MONDAY:
        return DateTimeConstants.MONDAY;
      case TUESDAY:
        return DateTimeConstants.TUESDAY;
      case WEDNESDAY:
        return DateTimeConstants.WEDNESDAY;
      case THURSDAY:
        return DateTimeConstants.THURSDAY;
      case FRIDAY:
        return DateTimeConstants.FRIDAY;
      case SATURDAY:
        return DateTimeConstants.SATURDAY;
      case SUNDAY:
        return DateTimeConstants.SUNDAY;
      default:
        throw new IllegalArgumentException ("Unknown weekday " + eWeekday);
    }
  }

  /**
   * Returns the {@link DateTimeConstants} value for the given month.
   * 
   * @param eMonth
   * @return {@link DateTimeConstants} value.
   */
  @Nonnegative
  public static int getMonth (final Month eMonth)
  {
    switch (eMonth)
    {
      case JANUARY:
        return DateTimeConstants.JANUARY;
      case FEBRUARY:
        return DateTimeConstants.FEBRUARY;
      case MARCH:
        return DateTimeConstants.MARCH;
      case APRIL:
        return DateTimeConstants.APRIL;
      case MAY:
        return DateTimeConstants.MAY;
      case JUNE:
        return DateTimeConstants.JUNE;
      case JULY:
        return DateTimeConstants.JULY;
      case AUGUST:
        return DateTimeConstants.AUGUST;
      case SEPTEMBER:
        return DateTimeConstants.SEPTEMBER;
      case OCTOBER:
        return DateTimeConstants.OCTOBER;
      case NOVEMBER:
        return DateTimeConstants.NOVEMBER;
      case DECEMBER:
        return DateTimeConstants.DECEMBER;
      default:
        throw new IllegalArgumentException ("Unknown month " + eMonth);
    }
  }

  /**
   * Gets the type.
   * 
   * @param aType
   *        the type of holiday in the config
   * @return the type of holiday
   */
  @Nonnull
  public static IHolidayType getType (@Nonnull final HolidayType aType)
  {
    switch (aType)
    {
      case OFFICIAL_HOLIDAY:
        return EHolidayType.OFFICIAL_HOLIDAY;
      case UNOFFICIAL_HOLIDAY:
        return EHolidayType.UNOFFICIAL_HOLIDAY;
      default:
        throw new IllegalArgumentException ("Unknown type " + aType);
    }
  }

  /**
   * Creates the date from the month/day within the specified year.
   * 
   * @param nYear
   * @param aFixed
   * @return A local date instance.
   */
  public static LocalDate create (final int nYear, final Fixed aFixed)
  {
    return PDTFactory.createLocalDate (nYear, getMonth (aFixed.getMonth ()), aFixed.getDay ().intValue ());
  }
}
