package org.interview.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Utility class for date/time management
 *
 * @author Fabio Bozzo
 */
public class DateTimeUtils {

  public static String formatDateTime(Date date) {
    return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
  }

  public static String formatDate(Date date) {
    return new SimpleDateFormat("yyyy-MM-dd").format(date);
  }

  public static SimpleDateFormat twitterDateFormat() {
    return new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.ENGLISH);
  }
}
