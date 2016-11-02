package de.koessel.myarchive.util.database;

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

import static de.koessel.myarchive.ArchiveProperties.PROPERTY_SERVER;

/**
 * Static helper functions for the CouchDB
 */
public class CouchDbHelper {
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

  public static void createDatabase() throws Exception {
    ArchiveProperties properties = ArchiveProperties.getInstance();
    String database = properties.getProperty(ArchiveProperties.PROPERTY_DATABASE);
    HttpResponse<JsonNode> response = Unirest.put(properties.getProperty(PROPERTY_SERVER) + '/' + database).asJson();
    checkResponseCode(response);
    JSONObject json = response.getBody().getObject();
    if ("true".equals(json.get("ok"))) {
      logger.info("Created new database '" + database + "'");
      return;
    }
    logger.error(json.toString());
  }

  public static DocumentId createDocument(JSONObject jsonObject) {
    //todo: UUID anfordern
    //todo: Dokument mit PUT erzeugen
    //todo: Dokument mit UUID wieder anfordern und prüfen

    return null;
  }

  public static void uploadAttachment(DocumentId documentId, File fullImage) {
    //todo
  }
}
