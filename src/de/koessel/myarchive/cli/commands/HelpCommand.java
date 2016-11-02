package de.koessel.myarchive.cli.commands;

import de.koessel.myarchive.util.DefaultCommand;
import org.apache.commons.cli.CommandLine;

/**
 * Just prints usage string
 */
public class HelpCommand extends DefaultCommand {

  public HelpCommand() {
    super("help", "");
  }

  @Override
  public int run(CommandLine commandLine) {
    System.out.println("usage: myarchive [command] [options] [file [file2 [file3] ...]]");
    System.out.println(description);
    System.out.println("Type 'myarchive <command> -help' for help about a specific command");
    return 0;
  }
}
