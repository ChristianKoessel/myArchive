package de.koessel.myarchive.util;

import org.apache.commons.cli.ParseException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Container for all supported commands of the CLI
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
    defaultCommand.setDescription(getDescriptionForAllCommand());
  }

  public int run(String[] args) throws ParseException {
    if (args.length == 0) {
      return defaultCommand.run(args);
    }
    String commandString = args[0];
    if (commands.containsKey(commandString)) {
      return commands.get(commandString).run(removeFirstArg(args));
    } else {
      return defaultCommand.run(args);
    }
  }

  private String[] removeFirstArg(String[] args) {
    return Arrays.copyOfRange(args, 1, args.length);
  }

  private String getDescriptionForAllCommand() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("where command is one of:");
    for (Command command : commands.values()) {
      stringBuilder.append("\n\t").append(command.getName());
      for (int i = 0; i < 15 - command.getName().length(); i++) {
        stringBuilder.append(' ');
      }
      if (command.getName().equals(defaultCommand.getName())) {
        stringBuilder.append("prints out available commands");
      } else {
        stringBuilder.append(command.getDescription());
      }
    }
    return stringBuilder.toString();
  }
}
