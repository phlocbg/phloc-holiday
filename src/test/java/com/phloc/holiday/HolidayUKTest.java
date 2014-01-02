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
package com.phloc.holiday;

import static org.junit.Assert.assertTrue;

import org.joda.time.LocalDate;
import org.junit.Test;

import com.phloc.commons.locale.country.ECountry;
import com.phloc.datetime.PDTFactory;
import com.phloc.holiday.mgr.AbstractCountryTestBase;

public class HolidayUKTest extends AbstractCountryTestBase
{

  private static final int YEAR = 2010;
  private static final String ISO_CODE = "uk";

  @Test
  public void testManagerUKStructure () throws Exception
  {
    validateCalendarData (ISO_CODE, YEAR);
  }

  @Test
  public void testManagerUKChristmasMovingDaysWhenChristimasOnSunday ()
  {
    doChristmasContainmentTest (2011, 26, 27);
  }

  @Test
  public void testManagerUKChristmasMovingDaysWhenChristimasOnSaturday ()
  {
    doChristmasContainmentTest (2010, 27, 28);
  }

  @Test
  public void testManagerUKChristmasMovingDaysWhenChristimasOnFriday ()
  {
    doChristmasContainmentTest (2009, 25, 28);
  }

  private void doChristmasContainmentTest (final int year, final int dayOfChristmas, final int dayOfBoxingday)
  {
    final LocalDate christmas = PDTFactory.createLocalDate (year, 12, dayOfChristmas);
    final LocalDate boxingday = PDTFactory.createLocalDate (year, 12, dayOfBoxingday);
    final IHolidayManager holidayManager = HolidayManagerFactory.getHolidayManager (ECountry.UK);
    final HolidayMap holidays = holidayManager.getHolidays (year);
    assertTrue ("There should be christmas on " + christmas, holidays.containsHolidayForDate (christmas));
    assertTrue ("There should be boxing day on " + boxingday, holidays.containsHolidayForDate (boxingday));
  }
}
