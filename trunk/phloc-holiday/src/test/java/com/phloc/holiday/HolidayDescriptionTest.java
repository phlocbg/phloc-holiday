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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.junit.Test;

/**
 * @author sven
 */
public class HolidayDescriptionTest
{
  @Test
  public void testHolidayDescriptionsCompleteness () throws Exception
  {
    final File folder = new File ("src/main/resources/descriptions");
    assertTrue (folder.isDirectory ());
    final File [] descriptions = folder.listFiles (new FilenameFilter ()
    {
      public boolean accept (final File dir, final String name)
      {
        return name.startsWith ("holiday_descriptions") && name.endsWith (".properties");
      }
    });
    assertNotNull (descriptions);
    assertTrue (descriptions.length > 0);

    final Set <String> propertiesNames = new HashSet <String> ();
    final Map <String, Properties> descriptionProperties = new HashMap <String, Properties> ();

    for (final File descriptionFile : descriptions)
    {
      final Properties props = new Properties ();
      props.load (new FileInputStream (descriptionFile));
      propertiesNames.addAll (props.stringPropertyNames ());
      descriptionProperties.put (descriptionFile.getName (), props);
    }

    final Map <String, Set <String>> missingProperties = new HashMap <String, Set <String>> ();

    for (final Map.Entry <String, Properties> entry : descriptionProperties.entrySet ())
    {
      if (!entry.getValue ().stringPropertyNames ().containsAll (propertiesNames))
      {
        final Set <String> remainingProps = new HashSet <String> (propertiesNames);
        remainingProps.removeAll (entry.getValue ().stringPropertyNames ());
        missingProperties.put (entry.getKey (), remainingProps);
      }
    }

    assertTrue ("Following files are lacking properties: " + missingProperties, missingProperties.isEmpty ());
  }
}
