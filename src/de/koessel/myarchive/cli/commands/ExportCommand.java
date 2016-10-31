package de.koessel.myarchive.cli.commands;

import de.koessel.myarchive.util.DefaultCommand;

/**
 * Database Exporter
 * Exports all images and document metadata from a database
 */
public class ExportCommand extends DefaultCommand {
  public ExportCommand() {
    name = "export";
    description = "exports images and metadata from a given database";
  }

  @Override
  public int run(String[] args) {
    throw new IllegalArgumentException("Command not yet implemented!");
  }
}
