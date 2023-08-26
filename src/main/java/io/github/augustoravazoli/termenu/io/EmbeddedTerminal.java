package io.github.augustoravazoli.termenu.io;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * This is the embedded implementation of the terminal, using the provided I/O stream.
 *
 * @author Augusto Ravazoli
 * @since 3.0.0
 */
final class EmbeddedTerminal extends Terminal {

  private final Scanner input;
  private final PrintStream output;

  EmbeddedTerminal(InputStream input, OutputStream output) {
    this.input = new Scanner(input);
    this.output = new PrintStream(output);
  }

  @Override
  public int readInteger() {
    while (true) {
      try {
        return Integer.parseInt(input.nextLine().trim());
      } catch (NumberFormatException ex) {}
    }
  }

  @Override
  public double readDecimal() {
    while (true) {
      try {
        return Double.parseDouble(input.nextLine().trim());
      } catch (NumberFormatException ex) {}
    }
  }

  @Override
  public String readLine() {
    return input.nextLine();
  }

  @Override
  public char[] readPassword() {
    return input.nextLine().toCharArray();
  }

  @Override
  public void writeLine(String format, Object... args) {
    output.printf(format + "%n", args);
  }

  @Override
  public void write(String format, Object... args) {
    output.printf(format, args);
  }

  @Override
  public boolean clear() {
    return false;
  }

}
