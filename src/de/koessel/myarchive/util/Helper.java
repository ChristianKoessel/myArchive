package de.koessel.myarchive.util;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import de.koessel.myarchive.ArchiveProperties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static de.koessel.myarchive.ArchiveProperties.PROPERTY_SERVER;

/**
 * Helper class for utility methods
 */
public class Helper {

  //todo: Auftrennen in CouchDbHelper und Helper

  private static Logger logger = LogManager.getLogger();

  public static void checkServer() throws Exception {
    ArchiveProperties properties = ArchiveProperties.getInstance();
    HttpResponse<JsonNode> response = Unirest.get(properties.getProperty(PROPERTY_SERVER)).asJson();
    checkResponseCode(response);
    JSONObject json = response.getBody().getObject();
    if (json.has("couchdb")) {
      logger.info("Connnected to CouchDB Version " + json.get("version"));
    } else {
      throw new IllegalArgumentException("Connection to CouchDB failed!");
    }
  }

  private static void checkResponseCode(HttpResponse<JsonNode> response) throws IOException {
    if (response.getStatus() >= 400) {
      logger.error("HTTP error: ", response.getStatusText());
      throw new IOException("Request to CouchDB failed");
    }
  }

  public static void checkDatabase() throws Exception {
    ArchiveProperties properties = ArchiveProperties.getInstance();
    String database = properties.getProperty(ArchiveProperties.PROPERTY_DATABASE);
    HttpResponse<JsonNode> response = Unirest.get(properties.getProperty(PROPERTY_SERVER) + "/_all_dbs").asJson();
    checkResponseCode(response);
    JsonNode body = response.getBody();
    if (body.isArray()) {
      JSONArray array = body.getArray();
      for (int i = 0; i < array.length(); i++) {
        String db = array.getString(i);
        if (database.equals(db)) {
          return;
        }
      }
    }
    throw new IllegalArgumentException("Database '" + database + "' not found!");
  }

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

  public static void createDocument(JSONObject jsonObject) {
    //todo: UUID anfordern
    //todo: Dokument mit PUT erzeugen
    //todo: Dokument mit UUID wieder anfordern und prüfen
  }
}
