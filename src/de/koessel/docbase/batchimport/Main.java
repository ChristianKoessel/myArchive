package de.koessel.docbase.batchimport;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

public class Main {

  private static final String WELCOME_MESSAGE = "BatchImport v0.9 (Importer for DocBase)";
  private static final String USAGE = "Usage: batchimport [-date=[[DD.]MM.]YYYY] -database=<database> <path-with-wildcards>";
  private static final String PROPERTIES = "batchimport.properties";
  private static final String ARG_DATE = "-date=";

  private static Logger logger = LogManager.getLogger();

  public static void main(String[] args) {
    System.out.println(WELCOME_MESSAGE);
    try {
      Properties properties = readArgsAndProperties(args);
    } catch (Exception e) {
      logger.fatal(e);
      System.exit(-1);
    }
  }

  private static Properties readArgsAndProperties(String[] args) throws IOException {
    Properties properties = new Properties();
    properties.load(new FileInputStream(PROPERTIES));
    if (args.length < 2) {
      System.out.println(USAGE);
    }
    for (String arg : args) {
      if (arg.startsWith(ARG_DATE)) {
        String date = arg.substring(ARG_DATE.length());
        properties.setProperty("referenceDate", readDate(date));
      }
      //todo: Pfade mit Wildcards werden bereits in einzelne Datei-Argumente aufgelöst!
    }
    return properties;
  }

  private static String readDate(String dateString) {
    if (dateString.length() == 0) {
      throw new IllegalArgumentException("Reference date is missing!");
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
