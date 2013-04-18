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

import org.joda.time.LocalDate;

import com.phloc.holiday.HolidayMap;
import com.phloc.holiday.IHolidayType;
import com.phloc.holiday.ResourceBundleHoliday;
import com.phloc.holiday.config.Fixed;
import com.phloc.holiday.config.Holidays;
import com.phloc.holiday.mgr.XMLUtil;
import com.phloc.holiday.parser.AbstractHolidayParser;

/**
 * @author Sven Diedrichsen
 * @author philip
 */
public final class FixedParser extends AbstractHolidayParser
{
  private static final FixedParser s_aInstance = new FixedParser ();

  private FixedParser ()
  {}

  public static FixedParser getInstance ()
  {
    return s_aInstance;
  }

  public void parse (final int nYear, final HolidayMap aHolidays, final Holidays aConfig)
  {
    for (final Fixed aFixed : aConfig.getFixed ())
    {
      if (!isValid (aFixed, nYear))
        continue;

      final LocalDate aDate = XMLUtil.create (nYear, aFixed);
      final LocalDate aMovedDate = moveDate (aFixed, aDate);
      final IHolidayType aType = XMLUtil.getType (aFixed.getLocalizedType ());
      final String sPropertyKey = aFixed.getDescriptionPropertiesKey ();
      aHolidays.add (aMovedDate, new ResourceBundleHoliday (aType, sPropertyKey));
    }
  }
}
