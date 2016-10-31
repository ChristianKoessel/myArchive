package de.koessel.myarchive.cli.commands;

import de.koessel.myarchive.util.DefaultCommand;

/**
 * Just prints usage string
 */
public class HelpCommand extends DefaultCommand {

  public HelpCommand() {
    name = "help";
    description = "";
  }

  @Override
  public int run(String[] args) {
    System.out.println("Usage: myarchive [command] [options] [file [file2 [file3] ...]]");
    System.out.println(description);
    System.out.println("Type 'myarchive <command> -help' for help about a specific command");
    return 0;
  }
}
