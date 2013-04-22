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
import javax.annotation.concurrent.Immutable;

import com.phloc.commons.hash.HashCodeGenerator;
import com.phloc.commons.string.StringHelper;
import com.phloc.commons.string.ToStringGenerator;

/**
 * Default implementation of the {@link ISingleHoliday} interface.
 *
 * @author Philip Helger
 */
@Immutable
public final class FixedNameHoliday implements ISingleHoliday
{
  private final boolean m_bIsOfficial;
  private final String m_sHolidayName;

  public FixedNameHoliday (@Nonnull final String sHolidayName)
  {
    this (EHolidayType.OFFICIAL_HOLIDAY, sHolidayName);
  }

  public FixedNameHoliday (@Nonnull final IHolidayType aType, @Nonnull final String sHolidayName)
  {
    if (aType == null)
      throw new NullPointerException ("type");
    if (StringHelper.hasNoText (sHolidayName))
      throw new IllegalArgumentException ("empty name");
    m_bIsOfficial = aType.isOfficialHoliday ();
    m_sHolidayName = sHolidayName;
  }

  public boolean isOfficialHoliday ()
  {
    return m_bIsOfficial;
  }

  public String getHolidayName (final Locale aContentLocale)
  {
    return m_sHolidayName;
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (!(o instanceof FixedNameHoliday))
      return false;
    final FixedNameHoliday rhs = (FixedNameHoliday) o;
    return m_bIsOfficial == rhs.m_bIsOfficial && m_sHolidayName.equals (rhs.m_sHolidayName);
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_bIsOfficial).append (m_sHolidayName).getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("official", m_bIsOfficial)
                                       .append ("holidayName", m_sHolidayName)
                                       .toString ();
  }
}
