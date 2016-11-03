package de.koessel.myarchive.cli.commands;

import de.koessel.myarchive.ArchiveProperties;
import de.koessel.myarchive.cli.Initializer;
import de.koessel.myarchive.util.commands.DefaultCommand;
import org.apache.commons.cli.CommandLine;

import java.io.IOException;

/**
 * Database Initializer
 * Creates new database and loads design documents
 */
public class InitCommand extends DefaultCommand {
  public InitCommand() {
    super("init", "initializes new database");
    options.addOption(CommonOptions.database);

  }

  @Override
  public String getUsage() {
    return "init [options]";
  }

  @Override
  public int run(CommandLine commandLine) throws Exception {
    handleOptions(commandLine);
    Initializer initializer = new Initializer();
    initializer.run();
    return 0;
  }

  private void handleOptions(CommandLine commandLine) throws IOException {
    ArchiveProperties properties = ArchiveProperties.getInstance();
    properties.setProperty(ArchiveProperties.PROPERTY_DATABASE, commandLine.getOptionValue(CommonOptions.database.getOpt()));
  }

}
