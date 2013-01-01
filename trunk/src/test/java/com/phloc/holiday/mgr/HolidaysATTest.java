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
package com.phloc.holiday.mgr;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Locale;

import org.joda.time.DateTimeConstants;
import org.junit.Test;

import com.phloc.commons.locale.country.ECountry;
import com.phloc.datetime.PDTFactory;
import com.phloc.holiday.HolidayManagerFactory;
import com.phloc.holiday.IHolidayManager;

public final class HolidaysATTest
{
  @Test
  public void testAT ()
  {
    final IHolidayManager aMgr = HolidayManagerFactory.getHolidayManager (ECountry.AT);
    assertTrue (aMgr.isHoliday (PDTFactory.createLocalDate (2010, DateTimeConstants.DECEMBER, 25)));
    assertEquals ("Heilige Drei Könige",
                  aMgr.getHoliday (PDTFactory.createLocalDate (2010, DateTimeConstants.JANUARY, 6))
                      .getHolidayName (Locale.GERMAN));
  }
}
