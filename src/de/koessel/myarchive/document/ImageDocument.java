package de.koessel.myarchive.document;

import java.time.LocalDateTime;
import java.util.Arrays;

import static de.koessel.myarchive.document.ImageDocument.State.*;

/**
 * JSON-Object for the database document
 */
public class ImageDocument {

  public enum State {
    New, Active, Canceled;
  }

  private String id;
  private String title;
  private String[] tags;
  private State state;
  private String refDate;
  private LocalDateTime uploaded;

  public ImageDocument() {
    state = New;
    tags = new String[0];
    uploaded = LocalDateTime.now();
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String[] getTags() {
    return tags;
  }

  public void setTags(String[] tags) {
    this.tags = tags;
  }

  public String getState() {
    switch (state) {
      case New:
        return "N";
      case Active:
        return "A";
      case Canceled:
        return "C";
    }
    return "";
  }

  public void setState(String state) {
    switch (state) {
      case "N":
        this.state = New;
      case "A":
        this.state = Active;
      case "C":
        this.state = Canceled;
    }
  }

  public String getRefDate() {
    return refDate;
  }

  public void setRefDate(String refDate) {
    this.refDate = refDate;
  }

  public LocalDateTime getUploaded() {
    return uploaded;
  }

  public void setUploaded(LocalDateTime uploaded) {
    this.uploaded = uploaded;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    ImageDocument that = (ImageDocument) o;

    if (!title.equals(that.title)) return false;
    // Probably incorrect - comparing Object[] arrays with Arrays.equals
    if (!Arrays.equals(tags, that.tags)) return false;
    if (state != that.state) return false;
    if (refDate != null ? !refDate.equals(that.refDate) : that.refDate != null) return false;
    return uploaded.equals(that.uploaded);

  }

  @Override
  public int hashCode() {
    int result = title.hashCode();
    result = 31 * result + uploaded.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "ImageDocument{" +
          "title='" + title + '\'' +
          ", tags=" + Arrays.toString(tags) +
          ", state=" + state +
          ", refDate=" + refDate +
          ", uploaded=" + uploaded +
          ", id=" + id +
          '}';
  }
}
