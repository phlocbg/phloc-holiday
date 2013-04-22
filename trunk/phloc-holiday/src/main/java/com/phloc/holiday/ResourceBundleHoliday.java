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
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import com.phloc.commons.equals.EqualsUtils;
import com.phloc.commons.hash.HashCodeGenerator;
import com.phloc.commons.lang.ClassHelper;
import com.phloc.commons.string.ToStringGenerator;
import com.phloc.commons.text.resource.ResourceBundleKey;
import com.phloc.commons.text.resource.Utf8ResourceBundle;

/**
 * Represents the holiday and contains the actual date and an localized
 * description.
 * 
 * @author Sven Diedrichsen
 * @author Philip Helger
 */
@Immutable
public final class ResourceBundleHoliday implements ISingleHoliday
{
  private final boolean m_bIsOfficial;

  /** The properties key to retrieve the description with. */
  private final ResourceBundleKey m_aRBKey;

  /** Cached hashCode result */
  private Integer m_aHashCode;

  /**
   * Constructs a holiday for a date using the provided properties key to
   * retrieve the description with.
   */
  public ResourceBundleHoliday (@Nonnull final IHolidayType aType, @Nullable final String sPropertiesKey)
  {
    if (aType == null)
      throw new NullPointerException ("type");
    m_bIsOfficial = aType.isOfficialHoliday ();
    m_aRBKey = com.phloc.commons.string.StringHelper.hasNoText (sPropertiesKey)
                                                                               ? null
                                                                               : new ResourceBundleKey ("descriptions.holiday_descriptions",
                                                                                                        "holiday.description." +
                                                                                                            sPropertiesKey);
  }

  public boolean isOfficialHoliday ()
  {
    return m_bIsOfficial;
  }

  /**
   * @param aLocale
   *        The locale to use.
   * @return The description read with the provided locale.
   */
  public String getHolidayName (final Locale aLocale)
  {
    String ret = null;
    if (m_aRBKey != null)
    {
      try
      {
        final ResourceBundle aBundle = Utf8ResourceBundle.getBundle (m_aRBKey.getBundleName (),
                                                                     aLocale,
                                                                     ClassHelper.getDefaultClassLoader ());
        ret = aBundle.getString (m_aRBKey.getKey ());
      }
      catch (final MissingResourceException ex)
      {
        // fall through
      }
    }
    return ret == null ? "undefined" : ret;
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (!(o instanceof ResourceBundleHoliday))
      return false;
    final ResourceBundleHoliday rhs = (ResourceBundleHoliday) o;
    return m_bIsOfficial == rhs.m_bIsOfficial && EqualsUtils.equals (m_aRBKey, rhs.m_aRBKey);
  }

  @Override
  public int hashCode ()
  {
    // We need a cached one!
    if (m_aHashCode == null)
      m_aHashCode = new HashCodeGenerator (this).append (m_bIsOfficial).append (m_aRBKey).getHashCodeObj ();
    return m_aHashCode.intValue ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("official", m_bIsOfficial).append ("propsKey", m_aRBKey).toString ();
  }
}
