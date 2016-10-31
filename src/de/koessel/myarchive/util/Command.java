package de.koessel.myarchive.util;

import org.apache.commons.cli.ParseException;

/**
 * Common Interface for all commands of the CLI
 */
public interface Command {

  String getName();

  String getDescription();

  void setDescription(String description);

  int run(String[] args) throws ParseException;
}
