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

/**
 * Type of holiday. Each holiday can be placed in a category and this is
 * represented by this type. The categories can mark a holiday as a official
 * holiday or not.
 * 
 * @author tboven
 * @author Philip Helger
 */
public interface IHolidayType
{
  /**
   * Checks if is official holiday.
   * 
   * @return <code>true</code>, if is official holiday
   */
  boolean isOfficialHoliday ();
}
