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
package com.phloc.holiday.parser.impl;

import org.joda.time.LocalDate;

import com.phloc.holiday.HolidayMap;
import com.phloc.holiday.IHolidayType;
import com.phloc.holiday.ResourceBundleHoliday;
import com.phloc.holiday.config.Holidays;
import com.phloc.holiday.config.RelativeToFixed;
import com.phloc.holiday.config.When;
import com.phloc.holiday.mgr.XMLUtil;
import com.phloc.holiday.parser.AbstractHolidayParser;

/**
 * @author Sven Diedrichsen
 * @author philip
 */
public class RelativeToFixedParser extends AbstractHolidayParser
{
  private static final RelativeToFixedParser s_aInstance = new RelativeToFixedParser ();

  private RelativeToFixedParser ()
  {}

  public static RelativeToFixedParser getInstance ()
  {
    return s_aInstance;
  }

  public void parse (final int nYear, final HolidayMap aHolidays, final Holidays aConfig)
  {
    for (final RelativeToFixed aRelativeToFixed : aConfig.getRelativeToFixed ())
    {
      if (!isValid (aRelativeToFixed, nYear))
        continue;

      LocalDate aFixed = XMLUtil.create (nYear, aRelativeToFixed.getDate ());
      if (aRelativeToFixed.getWeekday () != null)
      {
        // if weekday is set -> move to weekday
        final int nExpectedWeekday = XMLUtil.getWeekday (aRelativeToFixed.getWeekday ());
        final int nDirection = (aRelativeToFixed.getWhen () == When.BEFORE ? -1 : 1);
        do
        {
          aFixed = aFixed.plusDays (nDirection);
        } while (aFixed.getDayOfWeek () != nExpectedWeekday);
      }
      else
        if (aRelativeToFixed.getDays () != null)
        {
          // if number of days set -> move number of days
          aFixed = aFixed.plusDays (aRelativeToFixed.getWhen () == When.BEFORE ? -aRelativeToFixed.getDays ()
                                                                                                  .intValue ()
                                                                              : aRelativeToFixed.getDays ().intValue ());
        }

      final IHolidayType aType = XMLUtil.getType (aRelativeToFixed.getLocalizedType ());
      final String sPropertyKey = aRelativeToFixed.getDescriptionPropertiesKey ();
      aHolidays.add (aFixed, new ResourceBundleHoliday (aType, sPropertyKey));
    }
  }
}
