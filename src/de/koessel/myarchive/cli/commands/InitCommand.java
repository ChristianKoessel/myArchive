package de.koessel.myarchive.cli.commands;

import de.koessel.myarchive.util.DefaultCommand;
import org.apache.commons.cli.CommandLine;

/**
 * Database Initializer
 * Creates new database and loads design documents
 */
public class InitCommand extends DefaultCommand {
  public InitCommand() {
    super("init", "initializes new database");
  }

  @Override
  public int run(CommandLine commandLine) {
    throw new IllegalArgumentException("Command not yet implemented!");
  }
}
