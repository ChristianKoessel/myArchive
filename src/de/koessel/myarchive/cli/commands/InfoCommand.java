package de.koessel.myarchive.cli.commands;

import de.koessel.myarchive.util.DefaultCommand;
import org.apache.commons.cli.CommandLine;

/**
 * System and Database Information Command
 * Reads and prints out useful information abeout the system and its databases
 */
public class InfoCommand extends DefaultCommand {
  public InfoCommand() {
    super("info", "prints out system and database status information");
  }

  @Override
  public int run(CommandLine commandLine) {
    throw new IllegalArgumentException("Command not yet implemented!");
  }
}
