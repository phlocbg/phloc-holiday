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

import com.phloc.holiday.HolidayMap;
import com.phloc.holiday.config.HinduHoliday;
import com.phloc.holiday.config.Holidays;
import com.phloc.holiday.parser.AbstractHolidayParser;

/**
 * @author Sven Diedrichsen
 * @author philip
 */
public class HinduHolidayParser extends AbstractHolidayParser
{
  private static final HinduHolidayParser s_aInstance = new HinduHolidayParser ();

  private HinduHolidayParser ()
  {}

  public static HinduHolidayParser getInstance ()
  {
    return s_aInstance;
  }

  public void parse (final int nYear, final HolidayMap aHolidayMap, final Holidays aConfig)
  {
    for (final HinduHoliday aHinduHoliday : aConfig.getHinduHoliday ())
    {
      if (!isValid (aHinduHoliday, nYear))
        continue;
      switch (aHinduHoliday.getType ())
      {
        case HOLI:
          // 20 February and ending on 21 March (20th march in leap years)
          // TODO: Calculate with hindu calendar.
          break;
        default:
          throw new IllegalArgumentException ("Unknown hindu holiday " + aHinduHoliday.getType ());
      }
    }
  }
}
