package de.koessel.myarchive.cli.commands;

import de.koessel.myarchive.util.DefaultCommand;
import org.apache.commons.cli.CommandLine;

/**
 * Database Purger
 * Deletes all documents marked as 'canceled' from the database
 */
public class PurgeCommand extends DefaultCommand {
  public PurgeCommand() {
    super("purge", "removes canceled documents from a given database");
  }

  @Override
  public int run(CommandLine commandLine) {
    throw new IllegalArgumentException("Command not yet implemented!");
  }
}
