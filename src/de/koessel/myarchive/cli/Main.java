package de.koessel.myarchive.cli;

import de.koessel.myarchive.cli.commands.HelpCommand;
import de.koessel.myarchive.cli.commands.ImportCommand;
import de.koessel.myarchive.util.Command;
import de.koessel.myarchive.util.Commands;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

  private static final String WELCOME_MESSAGE = "myArchiveCLI v1.0 (Command Line Interface for myArchive)\n";
  private static Logger logger = LogManager.getLogger();

  public static void main(String[] args) {
    System.out.println(WELCOME_MESSAGE);
    try {
      Commands commands = new Commands(new Command("help", "prints out available commands", new HelpCommand()));
      commands.add(new Command("import", "imports given files into database", new ImportCommand()));
      int returnValue = commands.run(args);
      System.exit(returnValue);
    } catch (Exception e) {
      logger.fatal(e);
      System.exit(-1);
    }
  }
}
