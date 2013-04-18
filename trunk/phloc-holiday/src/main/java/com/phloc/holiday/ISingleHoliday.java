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

import java.util.Locale;

import javax.annotation.Nonnull;

/**
 * Descriptor interface for a single holiday.
 * 
 * @author philip
 */
public interface ISingleHoliday extends IHolidayType
{
  /**
   * @param aContentLocale
   *        The locale to use. May not be <code>null</code>.
   * @return The name of this holiday. Never <code>null</code>.
   */
  @Nonnull
  String getHolidayName (@Nonnull Locale aContentLocale);
}