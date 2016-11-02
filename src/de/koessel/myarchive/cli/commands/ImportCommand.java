package de.koessel.myarchive.cli.commands;

import de.koessel.myarchive.util.DefaultCommand;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;

/**
 * Image Importer
 * Creates thumbnails, uploads documents and files
 */
public class ImportCommand extends DefaultCommand {

  private Option keepImages = new Option("keep", "don`t delete files after succcessful import");
  private Option date = Option.builder("date")
        .hasArg()
        .argName("value")
        .desc("reference date in the format [[DD.]MM.]YYYY]")
        .numberOfArgs(1)
        .build();

  public ImportCommand() {
    super("import", "imports given files into database");
    options.addOption(CommonOptions.database);
    options.addOption(keepImages);
    options.addOption(date);
  }

  @Override
  public String getUsage() {
    return "import [options] [file [file2 [file3] ...]]";
  }

  @Override
  public int run(CommandLine commandLine) {
    return 0;
  }
}
