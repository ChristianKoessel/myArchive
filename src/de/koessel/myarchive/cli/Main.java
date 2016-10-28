package de.koessel.myarchive.cli;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Main {

  private static final String WELCOME_MESSAGE = "myArchiveCLI v1.0 (Command Line Interface for myArchive)\n";
  private static final String USAGE = "Usage: myarchive [-date=[[DD.]MM.]YYYY] -database=<database> <path-with-wildcards>";
  private static final String PROPERTIES = "myarchive.properties";
  private static final String OPTION_DATE = "-date=";
  private static final String OPTION_DATABASE = "-database=";
  private static final String OPTION_KEEP_IMAGES = "-keep";

  public static final String PROPERTY_THUMBNAIL_SIZE = "thumbnail.size";
  public static final String PROPERTY_KEEP_IMAGES = "keepImages";
  public static final String PROPERTY_REFERENCE_DATE = "referenceDate";
  public static final String PROPERTY_DATABASE = "database";
  public static final String PROPERTY_SERVER = "couchdb.url";

  private static Logger logger = LogManager.getLogger();

  public static void main(String[] args) {
    System.out.println(WELCOME_MESSAGE);
    try {
      Properties properties = new Properties();
      properties.load(new FileInputStream(PROPERTIES));
      List<File> files = readOptionsAndFiles(args, properties);
      Importer importer = new Importer(properties, files);
      importer.run();
    } catch (Exception e) {
      logger.fatal(e);
      System.exit(-1);
    }
  }

  private static List<File> readOptionsAndFiles(String[] args, Properties properties) throws IOException {
    if (args.length < 2) {
      System.out.println(USAGE);
    }
    List<File> files = new ArrayList<>();
    for (String arg : args) {
      if (arg.startsWith(OPTION_DATE)) {
        properties.setProperty(PROPERTY_REFERENCE_DATE, parseDate(arg.substring(OPTION_DATE.length())));
      } else if (arg.startsWith(OPTION_DATABASE)) {
        properties.setProperty(PROPERTY_DATABASE, arg.substring(OPTION_DATABASE.length()));
      } else if (arg.equals(OPTION_KEEP_IMAGES)) {
        properties.setProperty(PROPERTY_KEEP_IMAGES, "true");
      } else {
        // Alle anderen Argumente werden als Dateipfade interpretiert
        files.add(new File(arg));
      }
    }
    return files;
  }

  private static String parseDate(String dateString) {
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
