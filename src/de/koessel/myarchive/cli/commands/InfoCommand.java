package de.koessel.myarchive.cli.commands;

import de.koessel.myarchive.util.DefaultCommand;

/**
 * System and Database Information Command
 * Reads and prints out useful information abeout the system and its databases
 */
public class InfoCommand extends DefaultCommand {
  public InfoCommand() {
    name = "info";
    description = "prints out system and database status information";
  }

  @Override
  public int run(String[] args) {
    throw new IllegalArgumentException("Command not yet implemented!");
  }
}
