package de.koessel.myarchive.util;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;

/**
 * Common Interface for all commands of the CLI
 */
public interface Command {

  /**
   * Returns the name of the command
   *
   * @return
   */
  String getName();

  /**
   * Returns the help description of the command
   * @return
   */
  String getDescription();

  /**
   * Sets the help description of the command
   * Only needed for updating the help command
   * @param description
   */
  void setDescription(String description);

  /**
   * Returns the usage string of the command
   *
   * @return
   */
  String getUsage();

  /**
   * Returns configured options of the command
   *
   * @return
   */
  Options getOptions();

  /**
   * Runs the command with the given arguments
   *
   * @param commandLine
   * @return return code (exit code) of the command
   */
  int run(CommandLine commandLine) throws Exception;
}
