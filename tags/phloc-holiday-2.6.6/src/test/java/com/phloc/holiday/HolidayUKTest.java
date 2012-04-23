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
package com.phloc.holiday;

import org.junit.Test;

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

}
