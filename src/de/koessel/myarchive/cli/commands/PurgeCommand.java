package de.koessel.myarchive.cli.commands;

import de.koessel.myarchive.util.DefaultCommand;

/**
 * Database Purger
 * Deletes all documents marked as 'canceled' from the database
 */
public class PurgeCommand extends DefaultCommand {
  public PurgeCommand() {
    name = "purge";
    description = "removes canceled documents from a given database";
  }

  @Override
  public int run(String[] args) {
    throw new IllegalArgumentException("Command not yet implemented!");
  }
}
