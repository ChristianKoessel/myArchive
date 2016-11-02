package de.koessel.myarchive.cli;

import de.koessel.myarchive.ArchiveProperties;
import de.koessel.myarchive.document.ImageDocument;
import de.koessel.myarchive.util.Helper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static de.koessel.myarchive.ArchiveProperties.PROPERTY_REFERENCE_DATE;

/**
 * Image Importer
 * Creates thumbnails, uploads documents and files
 */
public class Importer {

  private List<Image> images;
  private ArchiveProperties properties;

  private static Logger logger = LogManager.getLogger();

  public Importer(List<File> files) throws IOException {
    properties = ArchiveProperties.getInstance();
    images = new ArrayList<>();
    for (File file : files) {
      images.add(new Image(file));
    }
  }

  public void run() throws Exception {
    Helper.logProperties(properties);
    Helper.checkServer(properties);
    uploadImages();
  }

  private void createThumbnail(Image image) {
    try {
      image.createThumbnailImage(properties.getThumbnailSize());
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
    if (!properties.isKeepImages()) {
      logger.info("Deleting image " + image.getFullImage());
      Helper.deleteFile(image.getFullImage());
    }
  }

  private void verifyUpload() {
  }

}
