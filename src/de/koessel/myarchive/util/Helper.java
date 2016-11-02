package de.koessel.myarchive.util;

import de.koessel.myarchive.ArchiveProperties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Helper class for utility methods
 */
public class Helper {

  private static Logger logger = LogManager.getLogger();

  public static void logProperties(ArchiveProperties properties) {
    logger.info("---- Configured Properties ----");
    for (String key : properties.stringPropertyNames()) {
      logger.info(key + ": " + properties.getProperty(key));
    }
    logger.info("---- Configured Properties ----");
  }

  public static void deleteFile(File file) {
    if (file != null) {
      if (!file.delete()) {
        logger.warn(file + " could not be deleted!");
      }
    }
  }

  public static String parsePartialDate(String dateString) {
    if (dateString.length() == 0) {
      throw new IllegalArgumentException("Date is missing!");
    }
    String text = "";
    String[] token = dateString.split("\\.");
    if (token.length == 1) {
      // muss YYYY sein
      text = "01.01." + dateString;
    } else if (token.length == 2) {
      // muss MM.YYYY sein
      text = "01." + dateString;
    } else if (token.length == 3) {
      // muss DD.MM.YYYY sein
      text = dateString;
    } else {
      throw new IllegalArgumentException("Invalid date: " + dateString);
    }
    LocalDate date = LocalDate.parse(text, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    return date.format(DateTimeFormatter.ISO_DATE);
  }

}
