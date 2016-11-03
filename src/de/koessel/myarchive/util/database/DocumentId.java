package de.koessel.myarchive.util.database;

/**
 * Wrapper for UUID and revision of a document
 */
public class DocumentId {

  private String uuid;
  private String revision;

  public String getUuid() {
    return uuid;
  }

  public String getRevision() {
    return revision;
  }

  public void setRevision(String revision) {
    this.revision = revision;
  }

  public DocumentId(String uuid) {
    this.uuid = uuid;
  }

  public DocumentId(String uuid, String revision) {
    this.uuid = uuid;
    this.revision = revision;
  }
}
