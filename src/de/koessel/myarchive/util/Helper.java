package de.koessel.myarchive.util;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import de.koessel.myarchive.MyArchiveProperties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import java.io.File;

import static de.koessel.myarchive.MyArchiveProperties.PROPERTY_SERVER;

/**
 * Helper class for utility methods
 */
public class Helper {

  private static Logger logger = LogManager.getLogger();

  public static void checkServer(MyArchiveProperties properties) throws UnirestException {
    HttpResponse<JsonNode> response = Unirest.get(properties.getProperty(PROPERTY_SERVER)).asJson();
    JSONObject json = response.getBody().getObject();
    if (response.getStatus() == 200 && json.has("couchdb")) {
      logger.info("Connnected to CouchDB Version " + json.get("version"));
    } else {
      throw new IllegalArgumentException("Connection to CouchDB failed!");
    }
  }

  public static void logProperties(MyArchiveProperties properties) {
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

}
