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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import com.phloc.commons.locale.country.ECountry;

/**
 * The Class ISOCodesTest.
 * 
 * @author svdi1de
 */
public final class ISOCodesTest
{
  private static final int NUMBER_OF_ISOCOUNTRIES = 245;

  /**
   * Returns a list of ISO codes.
   * 
   * @return 2-digit ISO codes.
   */
  public static final Set <String> getISOCodes ()
  {
    final Set <String> codes = new HashSet <String> ();
    for (final ECountry eCountry : ECountry.values ())
      codes.add (eCountry.getISOCountryCode ());
    return codes;
  }

  /**
   * Test iso codes.
   */
  @Test
  public void testISOCodes ()
  {
    final Set <String> isoCodes = getISOCodes ();
    assertNotNull (isoCodes);
    assertEquals ("Wrong number of ISO codes.", NUMBER_OF_ISOCOUNTRIES, isoCodes.size ());
  }
}
