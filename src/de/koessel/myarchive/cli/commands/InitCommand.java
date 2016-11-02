package de.koessel.myarchive.cli.commands;

import de.koessel.myarchive.util.commands.DefaultCommand;
import org.apache.commons.cli.CommandLine;

/**
 * Database Initializer
 * Creates new database and loads design documents
 */
public class InitCommand extends DefaultCommand {
  public InitCommand() {
    super("init", "initializes new database");
    options.addOption(CommonOptions.database);

  }

  @Override
  public int run(CommandLine commandLine) {
    throw new IllegalArgumentException("Command not yet implemented!");
  }
}
