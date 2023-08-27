package io.github.augustoravazoli.termenu.io;

import java.io.Console;
import java.io.IOException;
import java.util.List;

/**
 * This is the native implementation of the terminal, using the operating system console.
 *
 * @author Augusto Ravazoli
 * @since 3.0.0
 */
final class NativeTerminal extends Terminal {
  
  private final Console console;

  NativeTerminal(Console console) {
    this.console = console;
  }

  @Override
  public int readInteger() {
    while (true) {
      try {
        return Integer.parseInt(console.readLine().trim());
      } catch (NumberFormatException ex) {}
    }
  }

  @Override
  public double readDecimal() {
    while (true) {
      try {
        return Double.parseDouble(console.readLine().trim());
      } catch (NumberFormatException ex) {}
    }
  }

  @Override
  public String readLine() {
    return console.readLine();
  } 

  @Override
  public char[] readPassword() {
    return console.readPassword();
  }

  @Override
  public void writeLine(String format, Object... args) {
    console.printf(format + "%n", args);
  }

  @Override
  public void write(String format, Object... args) {
    console.printf(format, args);
  }

  @Override
  public boolean clear() {
    try {
      var windows = System.getProperty("os.name").contains("Windows");
      var commands = windows ? List.of("cmd", "/c", "cls") : List.of("clear");
      new ProcessBuilder(commands).inheritIO().start().waitFor();
      return true;
    } catch (InterruptedException | IOException ex) {
      return false;
    }
  }

}
