package de.koessel.docbase.batchimport;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import de.koessel.docbase.ImageDocument;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static de.koessel.docbase.batchimport.Main.*;

/**
 * Created by BA23462 on 27.10.2016.
 */
public class Importer {

  private static final String THUMBNAIL_SIZE_DEFAULT = "80";

  private List<Image> images;
  private Properties properties;

  private static Logger logger = LogManager.getLogger();

  public Importer(Properties properties, List<File> files) {
    this.properties = properties;
    images = new ArrayList<>();
    for (File file : files) {
      images.add(new Image(file));
    }
  }

  public void run() throws Exception {
    logProperties();
    checkServer();
    uploadImages();
  }

  private void logProperties() {
    logger.info("---- Configured Properties ----");
    for (String key : properties.stringPropertyNames()) {
      logger.info(key + ": " + properties.getProperty(key));
    }
    logger.info("---- Configured Properties ----");
  }

  private void checkServer() throws UnirestException {
    HttpResponse<JsonNode> response = Unirest.get(properties.getProperty(PROPERTY_SERVER)).asJson();
    JSONObject json = response.getBody().getObject();
    if (response.getStatus() == 200 && json.has("couchdb")) {
      logger.info("Connnected to CouchDB Version " + json.get("version"));
    } else {
      throw new IllegalArgumentException("Connection to CouchDB failed!");
    }
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
      deleteFile(image.getThumbnailImage());
    }
    if ("false".equals(properties.getProperty(PROPERTY_KEEP_IMAGES, "true"))) {
      logger.info("Deleting image " + image.getFullImage());
      deleteFile(image.getFullImage());
    }
  }

  private void verifyUpload() {
  }

  private void deleteFile(File file) {
    if (file != null) {
      if (!file.delete()) {
        logger.warn(file + " could not be deleted!");
      }
    }
  }

}
