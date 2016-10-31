package de.koessel.myarchive.util;

/**
 * Base class for all CLI commands
 */
public abstract class DefaultCommand implements Command {

  protected String name;
  protected String description;

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public DefaultCommand() {
  }

  public DefaultCommand(String name, String description) {
    this.name = name;
    this.description = description;
  }

  @Override
  public String toString() {
    return getClass().getName();
  }
}
