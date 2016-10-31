package de.koessel.myarchive.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Container for the supported commands of the CLI
 */
public class Commands {

  private Map<String, Command> commands;
  private Command defaultCommand;

  public Commands(Command defaultCommand) {
    commands = new HashMap<>();
    this.defaultCommand = defaultCommand;
    add(defaultCommand);
  }

  public void add(Command command) {
    commands.put(command.getName(), command);
  }

  public int run(String[] args) {
    if (args.length == 0) {
      return runDefaultCommand(args);
    }
    String commandString = args[0];
    if (commands.containsKey(commandString)) {
      return commands.get(commandString).getRunner().run(args);
    } else {
      return runDefaultCommand(args);
    }
  }

  private int runDefaultCommand(String[] args) {
    defaultCommand.getRunner().run(getCommandsDescription());
    return -1;
  }

  private String getCommandsDescription() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Valid commands are:");
    for (Command command : commands.values()) {
      stringBuilder.append("\n\t").append(command);
      for (int i = 0; i < 15 - command.getName().length(); i++) {
        stringBuilder.append(' ');
      }
      stringBuilder.append(command.getDescription());
    }
    return stringBuilder.toString();
  }
}
