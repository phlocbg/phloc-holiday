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

import org.joda.time.LocalDate;
import org.joda.time.ReadableInterval;

/**
 * Base interface for a holiday manager for one country.
 * 
 * @author Philip Helger
 */
public interface IHolidayManager
{
  /**
   * Check if the requested date is a holiday.
   * 
   * @param aDate
   *        The potential holiday.
   * @param aArgs
   *        Hierarchy to request the holidays for. i.e. args = {'ny'} -> New
   *        York holidays
   * @return is a holiday in the state/region
   */
  boolean isHoliday (@Nonnull LocalDate aDate, @Nullable String... aArgs);

  /**
   * Get the holiday information for the requested date.
   * 
   * @param aDate
   *        The potential holiday.
   * @param aArgs
   *        Hierarchy to request the holidays for. i.e. args = {'ny'} -> New
   *        York holidays
   * @return The respective holiday.
   */
  @Nullable
  ISingleHoliday getHoliday (@Nonnull LocalDate aDate, @Nullable String... aArgs);

  /**
   * Returns the holidays for the requested year and hierarchy structure.
   * 
   * @param nYear
   *        i.e. 2010
   * @param aArgs
   *        i.e. args = {'ny'}. returns US/New York holidays. No args ->
   *        holidays common to whole country
   * @return the list of holidays for the requested year
   */
  @Nonnull
  HolidayMap getHolidays (int nYear, @Nullable String... aArgs);

  /**
   * Returns the holidays for the requested interval and hierarchy structure.
   * 
   * @param aInterval
   *        the interval in which the holidays lie.
   * @param aArgs
   *        a {@link java.lang.String} object.
   * @return list of holidays within the interval
   */
  @Nonnull
  HolidayMap getHolidays (@Nonnull ReadableInterval aInterval, @Nullable String... aArgs);
}
