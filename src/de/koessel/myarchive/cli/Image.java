package de.koessel.myarchive.cli;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.name.Rename;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static de.koessel.myarchive.ArchiveProperties.THUMBNAIL_FORMAT;

/**
 * A container for image files and their thumbnails
 */
class Image {

  private File fullImage;
  private File thumbnailImage;

  Image(File fullImage) {
    this.fullImage = fullImage;
  }

  File getFullImage() {
    return fullImage;
  }

  File getThumbnailImage() {
    return thumbnailImage;
  }

  void createThumbnailImage(int size) throws IOException {
    List<File> thumbnail = Thumbnails.of(fullImage)
          .allowOverwrite(true)
          .size(size, size)
          .outputFormat(THUMBNAIL_FORMAT)
          .asFiles(Rename.PREFIX_DOT_THUMBNAIL);
    if (!thumbnail.isEmpty()) {
      thumbnailImage = thumbnail.get(0);
    }
  }

  String getName() {
    return fullImage.getName();
  }

  boolean hasThumbnail() {
    return (thumbnailImage != null && thumbnailImage.exists());
  }
}
