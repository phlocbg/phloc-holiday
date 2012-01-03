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
package com.phloc.holiday.mgr;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.phloc.commons.annotations.ReturnsImmutableObject;
import com.phloc.commons.collections.ContainerHelper;
import com.phloc.commons.hash.HashCodeGenerator;
import com.phloc.commons.id.IHasID;
import com.phloc.commons.locale.country.ECountry;
import com.phloc.commons.string.ToStringGenerator;

/**
 * Bean class for describing the configuration hierarchy.
 *
 * @author Sven Diedrichsen
 * @author philip
 */
public final class CalendarHierarchy implements IHasID <String>
{
  private final String m_sID;
  private final ECountry m_eCountry;
  private final Map <String, CalendarHierarchy> m_aChildren = new HashMap <String, CalendarHierarchy> ();

  /**
   * Constructor which takes a eventually existing parent hierarchy node and the
   * ID of this hierarchy.
   *
   * @param aParent
   * @param eCountry
   */
  public CalendarHierarchy (@Nullable final CalendarHierarchy aParent,
                           @Nonnull final String sID,
                           @Nullable final ECountry eCountry)
  {
    if (sID == null)
      throw new NullPointerException ("id");
    m_sID = aParent == null ? sID : aParent.getID () + "_" + sID;
    m_eCountry = eCountry;
  }

  @Nonnull
  public String getID ()
  {
    return m_sID;
  }

  /**
   * Returns the hierarchies description text from the resource bundle.
   *
   * @param aContentLocale
   *        Locale to return the description text for.
   * @return Description text
   */
  @Nonnull
  public String getDescription (final Locale aContentLocale)
  {
    final String ret = m_eCountry == null ? null : m_eCountry.getDisplayText (aContentLocale);
    return ret != null ? ret : "undefined";
  }

  public void addChild (@Nonnull final CalendarHierarchy aChild)
  {
    m_aChildren.put (aChild.getID (), aChild);
  }

  @Nonnull
  @ReturnsImmutableObject
  public Map <String, CalendarHierarchy> getChildren ()
  {
    return ContainerHelper.makeUnmodifiable (m_aChildren);
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (!(o instanceof CalendarHierarchy))
      return false;
    final CalendarHierarchy rhs = (CalendarHierarchy) o;
    return m_sID.equals (rhs.m_sID);
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_sID).getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("ID", m_sID).append ("country", m_eCountry).toString ();
  }
}
