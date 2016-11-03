package de.koessel.myarchive.cli;

import com.mashape.unirest.http.Unirest;
import de.koessel.myarchive.ArchiveProperties;
import de.koessel.myarchive.util.Helper;
import de.koessel.myarchive.util.ObjectMapper;
import de.koessel.myarchive.util.database.CouchDbHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * Database initializer
 */
public class Initializer {

  private ArchiveProperties properties;

  private static Logger logger = LogManager.getLogger();

  public Initializer() throws IOException {
    properties = ArchiveProperties.getInstance();
  }

  public void run() throws Exception {
    Helper.logProperties(properties);
    Unirest.setObjectMapper(new ObjectMapper());
    CouchDbHelper.createDatabase();
    Unirest.shutdown();
  }
}
