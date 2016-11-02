package de.koessel.myarchive.util;

import org.apache.commons.cli.Options;

/**
 * Base class for all CLI commands
 */
public abstract class DefaultCommand implements Command {

  protected String name;
  protected String description;
  protected Options options;

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public DefaultCommand(String name, String description) {
    this.name = name;
    this.description = description;
    this.options = new Options();
  }

  @Override
  public Options getOptions() {
    return options;
  }

  @Override
  public String getUsage() {
    return name;
  }

  @Override
  public String toString() {
    return getClass().getName();
  }
}
