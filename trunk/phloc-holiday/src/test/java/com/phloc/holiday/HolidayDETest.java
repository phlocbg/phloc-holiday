package com.phloc.holiday;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.List;

import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.junit.Test;

import com.phloc.commons.locale.country.ECountry;
import com.phloc.datetime.PDTFactory;
import com.phloc.holiday.mgr.AbstractCountryTestBase;

public class HolidayDETest extends AbstractCountryTestBase
{
  private static final int YEAR = 2010;
  private static final String ISO_CODE = "de";

  @Test
  public void testManagerDEStructure () throws Exception
  {
    validateCalendarData (ISO_CODE, YEAR);
  }

  @Test
  public void testManagerDEinterval ()
  {
    try
    {
      final IHolidayManager instance = HolidayManagerFactory.getHolidayManager (ECountry.DE);
      final Interval interval = new Interval (PDTFactory.createLocalDate (2010, 10, 1).toDateTimeAtStartOfDay (),
                                              PDTFactory.createLocalDate (2011, 1, 31).toDateTimeAtStartOfDay ());
      final HolidayMap holidays = instance.getHolidays (interval);
      final List <LocalDate> expected = Arrays.asList (PDTFactory.createLocalDate (2010, 12, 25),
                                                       PDTFactory.createLocalDate (2010, 12, 26),
                                                       PDTFactory.createLocalDate (2010, 10, 3),
                                                       PDTFactory.createLocalDate (2011, 1, 1));
      assertEquals ("Wrong number of holidays", expected.size (), holidays.size ());
      for (final LocalDate d : expected)
      {
        assertTrue ("Expected date " + d + " missing.", holidays.containsHolidayForDate (d));
      }
    }
    catch (final Exception e)
    {
      fail ("Unexpected error occurred: " + e.getClass ().getName () + " - " + e.getMessage ());
    }
  }

}
