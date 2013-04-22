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
package com.phloc.holiday;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import org.joda.time.LocalDate;

import com.phloc.datetime.PDTFactory;
import com.phloc.datetime.PDTUtils;

/**
 * Some holiday utility methods.
 * 
 * @author Philip Helger
 */
@Immutable
public final class HolidayUtils
{
  private HolidayUtils ()
  {}

  public static boolean isWorkDay (@Nonnull final LocalDate aDate, @Nonnull final IHolidayManager aHolidayMgr)
  {
    return PDTUtils.isWorkDay (aDate) && !aHolidayMgr.isHoliday (aDate);
  }

  /**
   * Get the number of working days between start date (incl.) and end date
   * (incl.). An optional holiday calculator can be used as well.
   * 
   * @param aStartDate
   *        The start date. May not be <code>null</code>.
   * @param aEndDate
   *        The end date. May not be <code>null</code>.
   * @param aHolidayMgr
   *        The holiday calculator to use. May not be <code>null</code>.
   * @return The number of working days. If start date is after end date, the
   *         value will be negative! If start date equals end date the return
   *         will be 1 if it is a working day.
   */
  public static int getWorkingDays (@Nonnull final LocalDate aStartDate,
                                    @Nonnull final LocalDate aEndDate,
                                    @Nonnull final IHolidayManager aHolidayMgr)
  {
    if (aStartDate == null)
      throw new NullPointerException ("startDate");
    if (aEndDate == null)
      throw new NullPointerException ("endDate");
    if (aHolidayMgr == null)
      throw new NullPointerException ("holidayCalc");

    final boolean bFlip = aStartDate.isAfter (aEndDate);
    LocalDate aCurDate = bFlip ? aEndDate : aStartDate;
    final LocalDate aRealEndDate = bFlip ? aStartDate : aEndDate;

    int ret = 0;
    while (!aRealEndDate.isBefore (aCurDate))
    {
      if (isWorkDay (aCurDate, aHolidayMgr))
        ret++;
      aCurDate = aCurDate.plusDays (1);
    }
    return bFlip ? -1 * ret : ret;
  }

  /**
   * Get the next working day based on the current day. If the current day is a
   * working day, the current day is returned. A working day is determined by:
   * it's not a weekend day (usually Saturday and Sunday) and it's not a holiday
   * (based on the holiday manager).
   * 
   * @param aHolidayMgr
   *        An optional holiday calculator to be used. May be <code>null</code>.
   * @return The next matching date.
   * @see HolidayUtils#isWorkDay(LocalDate, IHolidayManager)
   * @see CalendarUtil#getCurrentOrNextWorkDay()
   */
  @Nonnull
  public static LocalDate getCurrentOrNextWorkDay (@Nullable final IHolidayManager aHolidayMgr)
  {
    if (aHolidayMgr == null)
      return CalendarUtil.getCurrentOrNextWorkDay ();
    LocalDate aDT = PDTFactory.getCurrentLocalDate ();
    while (!isWorkDay (aDT, aHolidayMgr))
      aDT = aDT.plusDays (1);
    return aDT;
  }
}
