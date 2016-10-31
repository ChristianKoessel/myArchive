package de.koessel.myarchive.util;

/**
 * Holder for name and description of a CLI command
 */
public class Command {

  private String name;
  private String description;
  private CommandRunner runner;

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public CommandRunner getRunner() {
    return runner;
  }

  public Command(String name, String description, CommandRunner runner) {
    this.name = name;
    this.description = description;
    this.runner = runner;
  }

  @Override
  public String toString() {
    return name;
  }
}
