package de.koessel.myarchive.util;

import org.apache.commons.cli.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Container for all supported commands of the CLI
 */
public class Commands {

  private Map<String, Command> commands;
  private Command defaultCommand;
  private String applicationName;

  public Commands(String applicationName, Command defaultCommand) {
    commands = new HashMap<>();
    this.defaultCommand = defaultCommand;
    add(defaultCommand);
    this.applicationName = applicationName;
  }

  public void add(Command command) {
    commands.put(command.getName(), command);
    command.getOptions().addOption(new Option("help", "print help for the command"));
    defaultCommand.setDescription(getDescriptionForAllCommand());
  }

  public int run(String[] args) {
    if (args.length == 0) {
      return runDefaultCommand(args);
    }
    String commandString = args[0];
    if (commands.containsKey(commandString)) {
      return runCommand(commands.get(commandString), args);
    } else {
      return runDefaultCommand(args);
    }
  }

  private int runCommand(Command command, String[] args) {
    args = removeFirstArg(args);
    CommandLineParser parser = new DefaultParser();
    try {
      CommandLine cmdLine = parser.parse(command.getOptions(), args);
      if (cmdLine.hasOption("help")) {
        printCommandHelp(command);
        return -1;
      }
      return command.run(cmdLine);
    } catch (ParseException e) {
      printCommandHelp(command);
      return -1;
    }
  }

  private int runDefaultCommand(String[] args) {
    CommandLineParser parser = new DefaultParser();
    CommandLine cmdLine = null;
    try {
      cmdLine = parser.parse(new Options(), args);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return defaultCommand.run(cmdLine);
  }

  private void printCommandHelp(Command command) {
    HelpFormatter formatter = new HelpFormatter();
    formatter.printHelp(applicationName + ' ' + command.getUsage(), command.getOptions());
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
