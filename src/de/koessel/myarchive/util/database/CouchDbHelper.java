package de.koessel.myarchive.util.database;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import de.koessel.myarchive.ArchiveProperties;
import de.koessel.myarchive.document.Returncode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static de.koessel.myarchive.ArchiveProperties.*;

/**
 * Static helper functions for the CouchDB
 */
public class CouchDbHelper {
  private static Logger logger = LogManager.getLogger();
  private static MimetypesFileTypeMap fileTypeMap = (MimetypesFileTypeMap) MimetypesFileTypeMap.getDefaultFileTypeMap();

  static {
    fileTypeMap.addMimeTypes("application/pdf pdf PDF\nimage/png png PNG");
  }

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

  private static void checkResponseCode(HttpResponse response) throws IOException {
    if (response.getStatus() >= 400) {
      logger.error("HTTP error: " + response.getStatusText());
      if (response.getBody() instanceof Returncode) {
        Returncode returncode = ((HttpResponse<Returncode>) response).getBody();
        if (returncode.isError()) {
          logger.error("CouchDB error: " + returncode);
        }
      }
      throw new IOException("Request to CouchDB failed");
    }
  }

  public static void checkDatabase() throws Exception {
    ArchiveProperties properties = ArchiveProperties.getInstance();
    String database = properties.getProperty(ArchiveProperties.PROPERTY_DATABASE);
    HttpResponse<JsonNode> response = Unirest
          .get(properties.getProperty(PROPERTY_SERVER) + "/_all_dbs")
          .asJson();
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
    logger.info("Creating database '" + database + "' ...");
    HttpResponse<Returncode> response = Unirest
          .put(properties.getProperty(PROPERTY_SERVER) + "/{database}")
          .routeParam("database", database)
          .basicAuth(properties.getProperty(PROPERTY_USERNAME), properties.getProperty(PROPERTY_PASSWORD))
          .asObject(Returncode.class);
    checkResponseCode(response);
    logger.info("OK!");
  }

  private static String getUUID() throws Exception {
    ArchiveProperties properties = ArchiveProperties.getInstance();
    HttpResponse<JsonNode> response = Unirest
          .get(properties.getProperty(PROPERTY_SERVER) + "/_uuids")
          .asJson();
    checkResponseCode(response);
    JSONObject json = response.getBody().getObject();
    return json.getJSONArray("uuids").getString(0);
  }

  public static DocumentId createDocument(Object document) throws Exception {
    ArchiveProperties properties = ArchiveProperties.getInstance();
    logger.info("Creating document");
    HttpResponse<Returncode> response = Unirest
          .put(properties.getProperty(PROPERTY_SERVER) + "/{database}/{uuid}")
          .routeParam("database", properties.getProperty(ArchiveProperties.PROPERTY_DATABASE))
          .routeParam("uuid", getUUID())
          .basicAuth(properties.getProperty(PROPERTY_USERNAME), properties.getProperty(PROPERTY_PASSWORD))
          .body(document)
          .asObject(Returncode.class);
    checkResponseCode(response);
    Returncode returncode = response.getBody();
    return new DocumentId(returncode.getId(), returncode.getRev());
  }

  public static void uploadAttachment(DocumentId documentId, File file) throws Exception {
    ArchiveProperties properties = ArchiveProperties.getInstance();
    logger.info("Uploading attachment " + file.getName());
    byte[] content = Files.readAllBytes(Paths.get(file.toURI()));
    HttpResponse<Returncode> response = Unirest
          .put(properties.getProperty(PROPERTY_SERVER) + "/{database}/{uuid}/{file}")
          .header("Content-Type", fileTypeMap.getContentType(file))
          .routeParam("database", properties.getProperty(ArchiveProperties.PROPERTY_DATABASE))
          .routeParam("uuid", documentId.getUuid())
          .routeParam("file", file.getName())
          .queryString("rev", documentId.getRevision())
          .basicAuth(properties.getProperty(PROPERTY_USERNAME), properties.getProperty(PROPERTY_PASSWORD))
          .body(content)
          .asObject(Returncode.class);
    checkResponseCode(response);
    Returncode returncode = response.getBody();
    documentId.setRevision(returncode.getRev());
  }
}
