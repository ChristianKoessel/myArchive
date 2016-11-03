package de.koessel.myarchive.document;

/**
 * JSON-Object for CouchDB-Returncode-Responses
 */
public class Returncode {

  private boolean ok;
  private String id;
  private String rev;
  private String error;
  private String reason;

  public Returncode() {
  }

  public boolean getOk() {
    return ok;
  }

  public void setOk(boolean ok) {
    this.ok = ok;
  }

  public String getError() {
    return error;
  }

  public void setError(String error) {
    this.error = error;
  }

  public String getReason() {
    return reason;
  }

  public void setReason(String reason) {
    this.reason = reason;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getRev() {
    return rev;
  }

  public void setRev(String rev) {
    this.rev = rev;
  }

  public boolean isError() {
    return error != null;
  }

  @Override
  public String toString() {
    if (ok) {
      return "OK";
    }
    return error + ": " + reason;
  }
}
