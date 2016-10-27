package de.koessel.docbase.batchimport;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.name.Rename;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by BA23462 on 27.10.2016.
 */
public class Image {

  public static final String THUMBNAIL_FORMAT = "png";
  private File fullImage;
  private File thumbnailImage;

  public Image(File fullImage) {
    this.fullImage = fullImage;
  }

  public File getFullImage() {
    return fullImage;
  }

  public File getThumbnailImage() {
    return thumbnailImage;
  }

  public void createThumbnailImage(int size) throws IOException {
    List<File> thumbnail = Thumbnails.of(fullImage)
          .allowOverwrite(true)
          .size(size, size)
          .outputFormat(THUMBNAIL_FORMAT)
          .asFiles(Rename.PREFIX_DOT_THUMBNAIL);
    if (!thumbnail.isEmpty()) {
      thumbnailImage = thumbnail.get(0);
    }
  }

  public String getName() {
    return fullImage.getName();
  }

  public boolean hasThumbnail() {
    return (thumbnailImage != null && thumbnailImage.exists());
  }
}
