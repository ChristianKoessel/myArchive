package de.koessel.myarchive.cli;

import de.koessel.myarchive.MyArchiveProperties;
import de.koessel.myarchive.document.ImageDocument;
import de.koessel.myarchive.util.Helper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static de.koessel.myarchive.MyArchiveProperties.*;

/**
 * Image Importer
 * Creates thumbnails, uploads documents and files
 */
class Importer {

  private static final String THUMBNAIL_SIZE_DEFAULT = "80";

  private List<Image> images;
  private MyArchiveProperties properties;

  private static Logger logger = LogManager.getLogger();

  Importer(MyArchiveProperties properties, List<File> files) {
    this.properties = properties;
    images = new ArrayList<>();
    for (File file : files) {
      images.add(new Image(file));
    }
  }

  void run() throws Exception {
    Helper.logProperties(properties);
    Helper.checkServer(properties);
    uploadImages();
  }

  private void createThumbnail(Image image) {
    Integer size = Integer.parseInt(properties.getProperty(PROPERTY_THUMBNAIL_SIZE, THUMBNAIL_SIZE_DEFAULT));
    try {
      image.createThumbnailImage(size);
      logger.info("Created thumbnail " + image.getThumbnailImage());
    } catch (IOException e) {
      logger.warn(e.getMessage());
    }
  }

  private void uploadImages() {
    for (Image image : images) {
      logger.info("---- " + image.getFullImage() + " ----");
      createThumbnail(image);
      ImageDocument document = new ImageDocument();
      document.setTitle(image.getName());
      if (properties.containsKey(PROPERTY_REFERENCE_DATE)) {
        document.setRefDate(properties.getProperty(PROPERTY_REFERENCE_DATE));
      }
      JSONObject jsonObject = new JSONObject(document);
      String jsonString = jsonObject.toString();

      deleteImage(image);
    }
  }

  private void deleteImage(Image image) {
    if (image.hasThumbnail()) {
      logger.info("Deleting thumbnail " + image.getThumbnailImage());
      Helper.deleteFile(image.getThumbnailImage());
    }
    if ("false".equals(properties.getProperty(PROPERTY_KEEP_IMAGES, "true"))) {
      logger.info("Deleting image " + image.getFullImage());
      Helper.deleteFile(image.getFullImage());
    }
  }

  private void verifyUpload() {
  }

}
