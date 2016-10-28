package de.koessel.myarchive;

import java.util.Properties;

/**
 * A wrapper for Properties
 */
public class MyArchiveProperties extends Properties {

  public static final String MYARCHIVE_PROPERTIES = "myarchive.properties";

  public static final String PROPERTY_THUMBNAIL_SIZE = "thumbnail.size";
  public static final String PROPERTY_KEEP_IMAGES = "keepImages";
  public static final String PROPERTY_REFERENCE_DATE = "referenceDate";
  public static final String PROPERTY_DATABASE = "database";
  public static final String PROPERTY_SERVER = "couchdb.url";

  public static final String THUMBNAIL_FORMAT = "png";
  public static final String THUMBNAIL_SIZE_DEFAULT = "80";

  public int getThumbnailSize() {
    return Integer.parseInt(getProperty(PROPERTY_THUMBNAIL_SIZE, THUMBNAIL_SIZE_DEFAULT));
  }

  public boolean isKeepImages() {
    return Boolean.parseBoolean(getProperty(PROPERTY_KEEP_IMAGES, "false"));
  }
}
