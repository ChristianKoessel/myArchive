package de.koessel.myarchive.cli.commands;

import de.koessel.myarchive.util.DefaultCommand;
import org.apache.commons.cli.CommandLine;

/**
 * Database Exporter
 * Exports all images and document metadata from a database
 */
public class ExportCommand extends DefaultCommand {
  public ExportCommand() {
    super("export", "exports images and metadata from a given database");
  }

  @Override
  public int run(CommandLine commandLine) {
    throw new IllegalArgumentException("Command not yet implemented!");
  }
}
