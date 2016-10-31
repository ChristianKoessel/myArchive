package de.koessel.myarchive.cli.commands;

import de.koessel.myarchive.util.CommandRunner;
import org.apache.commons.cli.Option;

/**
 * Image Importer
 * Creates thumbnails, uploads documents and files
 */
public class ImportCommand implements CommandRunner {
  private Option help = new Option("help", "print help for a command");
  private Option keepImages = new Option("keep", "don`t delete files after succcessful import");
  private Option database = Option.builder("database")
        .hasArg()
        .argName("name")
        .desc("use given database")
        .valueSeparator()
        .build();
  private Option date = Option.builder("date")
        .hasArg()
        .argName("value")
        .desc("reference date in the format [[DD.]MM.]YYYY]")
        .valueSeparator()
        .build();

  public ImportCommand() {
  }

  @Override
  public int run(String[] args) {
    return 0;
  }
}
