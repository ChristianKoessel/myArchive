package de.koessel.myarchive.util;

/**
 * Common Interface for all commands of the CLI
 */
public interface CommandRunner {

  int run(String[] args);
}
