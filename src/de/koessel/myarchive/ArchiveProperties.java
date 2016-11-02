package de.koessel.myarchive;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Wrapper for all Properties (Singleton)
 */
public class ArchiveProperties extends Properties {

  private static final String MYARCHIVE_PROPERTIES = "myarchive.properties";

  public static final String PROPERTY_THUMBNAIL_SIZE = "thumbnail.size";
  public static final String PROPERTY_KEEP_IMAGES = "keepImages";
  public static final String PROPERTY_REFERENCE_DATE = "referenceDate";
  public static final String PROPERTY_DATABASE = "database";
  public static final String PROPERTY_TAGS = "tags";
  public static final String PROPERTY_SERVER = "couchdb.url";

  public static final String THUMBNAIL_FORMAT = "png";
  public static final String THUMBNAIL_SIZE_DEFAULT = "80";

  public int getThumbnailSize() {
    return Integer.parseInt(getProperty(PROPERTY_THUMBNAIL_SIZE, THUMBNAIL_SIZE_DEFAULT));
  }

  public boolean isKeepImages() {
    return Boolean.parseBoolean(getProperty(PROPERTY_KEEP_IMAGES, "false"));
  }

  public String[] getTags() {
    String tagString = getProperty(PROPERTY_TAGS, "");
    if (tagString.isEmpty()) {
      return new String[0];
    }
    return tagString.split(",");
  }

  private static ArchiveProperties instance;

  private ArchiveProperties() throws IOException {
    load(new FileInputStream(MYARCHIVE_PROPERTIES));
  }

  public static ArchiveProperties getInstance() throws IOException {
    return (instance == null ? instance = new ArchiveProperties() : instance);
  }
}
