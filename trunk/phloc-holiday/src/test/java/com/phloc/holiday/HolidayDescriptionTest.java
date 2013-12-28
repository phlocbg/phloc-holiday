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
