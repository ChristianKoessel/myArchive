package de.koessel.myarchive.cli.commands;

import de.koessel.myarchive.util.DefaultCommand;

/**
 * Database Initializer
 * Creates new database and loads design documents
 */
public class InitCommand extends DefaultCommand {
  public InitCommand() {
    name = "init";
    description = "initializes new database";
  }

  @Override
  public int run(String[] args) {
    throw new IllegalArgumentException("Command not yet implemented!");
  }
}
