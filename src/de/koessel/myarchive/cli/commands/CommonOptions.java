package de.koessel.myarchive.cli.commands;

import org.apache.commons.cli.Option;

/**
 * Set of common options as static fields
 */
class CommonOptions {
  static Option database = Option.builder("database")
        .hasArg()
        .required()
        .argName("name")
        .desc("use given database")
        .numberOfArgs(1)
        .build();

}
