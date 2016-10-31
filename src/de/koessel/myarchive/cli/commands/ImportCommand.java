package de.koessel.myarchive.cli.commands;

import de.koessel.myarchive.util.DefaultCommand;
import org.apache.commons.cli.*;

/**
 * Image Importer
 * Creates thumbnails, uploads documents and files
 */
public class ImportCommand extends DefaultCommand {

  private Options options = new Options();
  private Option help = new Option("help", "print help for the command");
  private Option keepImages = new Option("keep", "don`t delete files after succcessful import");
  private Option database = Option.builder("database")
        .hasArg()
        .required()
        .argName("name")
        .desc("use given database")
        .numberOfArgs(1)
        .build();
  private Option date = Option.builder("date")
        .hasArg()
        .argName("value")
        .desc("reference date in the format [[DD.]MM.]YYYY]")
        .numberOfArgs(1)
        .build();

  public ImportCommand() {
    name = "import";
    description = "imports given files into database";
    options.addOption(help);
    options.addOption(database);
    options.addOption(keepImages);
    options.addOption(date);
  }

  @Override
  public int run(String[] args) throws ParseException {
    //todo: Parser rausziehen, Getter für Options, ParseException weg!
    CommandLineParser parser = new DefaultParser();
    try {
      parser.parse(options, args);
    } catch (ParseException e) {
      HelpFormatter formatter = new HelpFormatter();
      formatter.printHelp("myarchive import [options] file ...", options);
    }
    return 0;
  }
}
