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
package com.phloc.holiday.mgr;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.WillClose;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.phloc.commons.annotations.Nonempty;
import com.phloc.commons.annotations.ReturnsMutableCopy;
import com.phloc.commons.collections.ArrayHelper;
import com.phloc.commons.io.resource.ClassPathResource;
import com.phloc.commons.io.streams.StreamUtils;
import com.phloc.commons.jaxb.JAXBContextCache;
import com.phloc.commons.lang.GenericReflection;
import com.phloc.commons.locale.country.ECountry;
import com.phloc.commons.string.StringHelper;
import com.phloc.holiday.HolidayMap;
import com.phloc.holiday.config.Configuration;
import com.phloc.holiday.config.Holidays;
import com.phloc.holiday.parser.IHolidayParser;
import com.phloc.holiday.parser.impl.ChristianHolidayParser;
import com.phloc.holiday.parser.impl.EthiopianOrthodoxHolidayParser;
import com.phloc.holiday.parser.impl.FixedParser;
import com.phloc.holiday.parser.impl.FixedWeekdayBetweenFixedParser;
import com.phloc.holiday.parser.impl.FixedWeekdayInMonthParser;
import com.phloc.holiday.parser.impl.FixedWeekdayRelativeToFixedParser;
import com.phloc.holiday.parser.impl.HinduHolidayParser;
import com.phloc.holiday.parser.impl.IslamicHolidayParser;
import com.phloc.holiday.parser.impl.RelativeToFixedParser;
import com.phloc.holiday.parser.impl.RelativeToWeekdayInMonthParser;

/**
 * Manager implementation for reading data from XML files. The files with the
 * name pattern Holidays_[country].xml will be read from the system classpath.
 * It uses a list a parsers for parsing the different type of XML nodes.
 * 
 * @author Sven Diedrichsen
 * @author Philip Helger
 */
public class XMLHolidayManager extends AbstractHolidayManager
{
  private static final Logger s_aLogger = LoggerFactory.getLogger (XMLHolidayManager.class);

  /**
   * Unmarshals the configuration from the stream. Uses <code>JAXB</code> for
   * this.
   * 
   * @param aIS
   * @return The unmarshalled configuration.
   */
  private static Configuration _unmarshallConfiguration (@WillClose @Nonnull final InputStream aIS)
  {
    if (aIS == null)
      throw new IllegalArgumentException ("Stream is NULL. Cannot read XML.");

    try
    {
      final JAXBContext ctx = JAXBContextCache.getInstance ()
                                              .getFromCache (com.phloc.holiday.config.ObjectFactory.class.getPackage ());
      final Unmarshaller um = ctx.createUnmarshaller ();
      final JAXBElement <Configuration> aElement = GenericReflection.<Object, JAXBElement <Configuration>> uncheckedCast (um.unmarshal (aIS));
      return aElement.getValue ();
    }
    catch (final JAXBException ex)
    {
      throw new IllegalArgumentException ("Cannot parse holidays XML.", ex);
    }
    finally
    {
      StreamUtils.close (aIS);
    }
  }

  /**
   * Configuration parsed on initialization.
   */
  private final Configuration m_aConfiguration;

  public XMLHolidayManager (@Nonnull @Nonempty final String sCountryCode)
  {
    if (StringHelper.hasNoText (sCountryCode))
      throw new IllegalArgumentException ("countryCode is empty");

    final String sFileName = "holidays/Holidays_" + sCountryCode.toLowerCase () + ".xml";
    m_aConfiguration = _unmarshallConfiguration (ClassPathResource.getInputStream (sFileName));
    _validateConfigurationHierarchy (m_aConfiguration);
  }

  /**
   * Calls
   * <code>Set&lt;LocalDate&gt; getHolidays(int year, Configuration c, String... args)</code>
   * with the configuration from initialization.
   */
  public HolidayMap getHolidays (final int nYear, @Nullable final String... aArgs)
  {
    return _getHolidays (nYear, m_aConfiguration, aArgs);
  }

  /**
   * Creates a list of parsers by reading the configuration and trying to find
   * an <code>HolidayParser</code> implementation for by XML class type.
   * 
   * @param aConfig
   * @return A list of parsers to for this configuration.
   */
  @Nonnull
  @ReturnsMutableCopy
  private static List <IHolidayParser> _getParsers (@Nonnull final Holidays aConfig)
  {
    final List <IHolidayParser> ret = new ArrayList <IHolidayParser> ();
    if (!aConfig.getChristianHoliday ().isEmpty ())
      ret.add (ChristianHolidayParser.getInstance ());
    if (!aConfig.getEthiopianOrthodoxHoliday ().isEmpty ())
      ret.add (EthiopianOrthodoxHolidayParser.getInstance ());
    if (!aConfig.getFixed ().isEmpty ())
      ret.add (FixedParser.getInstance ());
    if (!aConfig.getFixedWeekdayBetweenFixed ().isEmpty ())
      ret.add (FixedWeekdayBetweenFixedParser.getInstance ());
    if (!aConfig.getFixedWeekday ().isEmpty ())
      ret.add (FixedWeekdayInMonthParser.getInstance ());
    if (!aConfig.getFixedWeekdayRelativeToFixed ().isEmpty ())
      ret.add (FixedWeekdayRelativeToFixedParser.getInstance ());
    if (!aConfig.getHinduHoliday ().isEmpty ())
      ret.add (HinduHolidayParser.getInstance ());
    if (!aConfig.getIslamicHoliday ().isEmpty ())
      ret.add (IslamicHolidayParser.getInstance ());
    if (!aConfig.getRelativeToFixed ().isEmpty ())
      ret.add (RelativeToFixedParser.getInstance ());
    if (!aConfig.getRelativeToWeekdayInMonth ().isEmpty ())
      ret.add (RelativeToWeekdayInMonthParser.getInstance ());
    return ret;
  }

  /**
   * Parses the provided configuration for the provided year and fills the list
   * of holidays.
   * 
   * @param nYear
   * @param aConfig
   * @param aArgs
   * @return the holidays
   */
  @Nonnull
  @ReturnsMutableCopy
  private HolidayMap _getHolidays (final int nYear,
                                   @Nonnull final Configuration aConfig,
                                   @Nullable final String... aArgs)
  {
    if (s_aLogger.isDebugEnabled ())
      s_aLogger.debug ("Adding holidays for " + aConfig.getDescription ());

    final HolidayMap aHolidayMap = new HolidayMap ();
    for (final IHolidayParser aParser : _getParsers (aConfig.getHolidays ()))
      aParser.parse (nYear, aHolidayMap, aConfig.getHolidays ());

    if (ArrayHelper.isNotEmpty (aArgs))
    {
      final String sHierarchy = aArgs[0];
      for (final Configuration aSubConfig : aConfig.getSubConfigurations ())
      {
        if (sHierarchy.equalsIgnoreCase (aSubConfig.getHierarchy ()))
        {
          // Recursive call
          final HolidayMap aSubHolidays = _getHolidays (nYear,
                                                        aSubConfig,
                                                        ArrayHelper.getCopy (aArgs, 1, aArgs.length - 1));
          aHolidayMap.addAll (aSubHolidays);
          break;
        }
      }
    }
    return aHolidayMap;
  }

  /**
   * Validates the content of the provided configuration by checking for
   * multiple hierarchy entries within one configuration. It traverses down the
   * configuration tree.
   */
  private static void _validateConfigurationHierarchy (final Configuration aConfig)
  {
    final Set <String> aHierarchySet = new HashSet <String> ();
    for (final Configuration subConfig : aConfig.getSubConfigurations ())
    {
      final String sHierarchy = subConfig.getHierarchy ();
      if (!aHierarchySet.add (sHierarchy))
        throw new IllegalArgumentException ("Configuration for " +
                                            aConfig.getHierarchy () +
                                            " contains multiple SubConfigurations with the same hierarchy id '" +
                                            sHierarchy +
                                            "'. ");
    }

    for (final Configuration aSubConfig : aConfig.getSubConfigurations ())
      _validateConfigurationHierarchy (aSubConfig);
  }

  /**
   * Returns the configurations hierarchy.<br>
   * i.e. Hierarchy 'us' -> Children 'al','ak','ar', ... ,'wv','wy'. Every child
   * might itself have children. The ids be used to call
   * getHolidays()/isHoliday().
   */
  @Override
  protected CalendarHierarchy getHierarchy ()
  {
    return _createConfigurationHierarchy (m_aConfiguration, null);
  }

  /**
   * Creates the configuration hierarchy for the provided configuration.
   * 
   * @param aConfig
   * @return configuration hierarchy
   */
  private static CalendarHierarchy _createConfigurationHierarchy (final Configuration aConfig,
                                                                  final CalendarHierarchy aParent)
  {
    final ECountry eCountry = ECountry.getFromIDOrNull (aConfig.getHierarchy ());
    final CalendarHierarchy aHierarchy = new CalendarHierarchy (aParent, aConfig.getHierarchy (), eCountry);
    for (final Configuration aSubConfig : aConfig.getSubConfigurations ())
    {
      final CalendarHierarchy aSubHierarchy = _createConfigurationHierarchy (aSubConfig, aHierarchy);
      aHierarchy.addChild (aSubHierarchy);
    }
    return aHierarchy;
  }
}
