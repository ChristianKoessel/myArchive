package de.koessel.myarchive.cli;

import de.koessel.myarchive.cli.commands.*;
import de.koessel.myarchive.util.commands.Commands;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

  private static final String WELCOME_MESSAGE = "myArchiveCLI v1.0 (Command Line Interface for myArchive)\n";
  private static Logger logger = LogManager.getLogger();

  public static void main(String[] args) {
    System.out.println(WELCOME_MESSAGE);
    try {
      Commands commands = new Commands("myarchive", new HelpCommand());
      commands.add(new InitCommand());
      commands.add(new InfoCommand());
      commands.add(new ImportCommand());
      commands.add(new ExportCommand());
      commands.add(new PurgeCommand());
      int returnValue = commands.run(args);
      System.exit(returnValue);
    } catch (Exception e) {
      logger.fatal(e);
      System.exit(-1);
    }
  }
}
