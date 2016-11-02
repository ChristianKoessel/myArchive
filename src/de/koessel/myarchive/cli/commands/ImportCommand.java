package de.koessel.myarchive.cli.commands;

import de.koessel.myarchive.ArchiveProperties;
import de.koessel.myarchive.cli.Importer;
import de.koessel.myarchive.util.Helper;
import de.koessel.myarchive.util.commands.DefaultCommand;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
  private Option tags = Option.builder("tags")
        .hasArg()
        .argName("values")
        .desc("keywords in the format tag[,tag2,...]")
        .valueSeparator(',')
        .numberOfArgs(1)
        .build();

  public ImportCommand() {
    super("import", "imports given files into database");
    options.addOption(CommonOptions.database);
    options.addOption(keepImages);
    options.addOption(date);
    options.addOption(tags);
  }

  @Override
  public String getUsage() {
    return "import [options] file [file2 [file3] ...]]";
  }

  @Override
  public int run(CommandLine commandLine) throws Exception {
    handleOptions(commandLine);
    List<File> files = new ArrayList<>();
    for (String arg : commandLine.getArgs()) {
      files.add(new File(arg));
    }
    Importer importer = new Importer(files);
    importer.run();
    return 0;
  }

  private void handleOptions(CommandLine commandLine) throws IOException {
    ArchiveProperties properties = ArchiveProperties.getInstance();
    properties.setProperty(ArchiveProperties.PROPERTY_DATABASE, commandLine.getOptionValue(CommonOptions.database.getOpt()));
    if (commandLine.hasOption(keepImages.getOpt())) {
      properties.setProperty(ArchiveProperties.PROPERTY_KEEP_IMAGES, Boolean.TRUE.toString());
    }
    if (commandLine.hasOption(date.getOpt())) {
      String refDate = Helper.parsePartialDate(commandLine.getOptionValue(date.getOpt()));
      properties.setProperty(ArchiveProperties.PROPERTY_REFERENCE_DATE, refDate);
    }
    if (commandLine.hasOption(tags.getOpt())) {
      properties.setProperty(ArchiveProperties.PROPERTY_TAGS, commandLine.getOptionValue(tags.getOpt()));
    }
  }
}
