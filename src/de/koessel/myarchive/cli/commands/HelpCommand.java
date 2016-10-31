package de.koessel.myarchive.cli.commands;

import de.koessel.myarchive.util.CommandRunner;

/**
 * Just prints usage string
 */
public class HelpCommand implements CommandRunner {
  @Override
  public int run(String[] args) {
    System.out.println("Usage: myarchive [command] [options] [file [file2 [file3] ...]]");
    System.out.println(args[0]);
    return 0;
  }
}
