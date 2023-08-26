package io.github.augustoravazoli.termenu.io;

/**
 * Terminal is the abstract class that provides an abstraction layer for the I/O 
 * system, it's implemented using the singleton design pattern since it's a shared resource.
 *
 * @author Augusto Ravazoli
 * @since 3.0.0
 */
public abstract sealed class Terminal permits NativeTerminal, EmbeddedTerminal {
  
  private static Terminal instance;

  Terminal() {}
  
  /**
   * Gets the available terminal instance of the current environment.
   *
   * @return the terminal
   */
  public static Terminal getInstance() {
    if (instance == null) {
      instance = System.console() != null
        ? new NativeTerminal(System.console())
        : new EmbeddedTerminal(System.in, System.out);
    }
    return instance;
  }

  /**
   * Reads a primitive integer from the terminal input.
   *
   * @return an int
   */
  public abstract int readInteger();
  
  /**
   * Reads a primitive double from the terminal input.
   *
   * @return a double
   */
  public abstract double readDecimal();

  /**
   * Reads a line from the terminal input.
   *
   * @return a string
   */
  public abstract String readLine();

  /**
   * Reads a char array from the terminal input. 
   * If the environment supports echoing, it will be enabled. 
   *
   * @return a char array
   */
  public abstract char[] readPassword();

  /**
   * Writes a formatted string to the terminal output, breaking a line at the end.
   *
   * @param format the format
   * @param args   the args
   */
  public abstract void writeLine(String format, Object... args);

  /**
   * Writes a formatted string to the terminal output.
   *
   * @param format the format
   * @param args   the args
   */
  public abstract void write(String format, Object... args);

  /**
   * Clears the terminal output if the current environment supports it.
   *
   * @return <code>true</code> if the terminal was cleared;
   *         <code>false</code> otherwise.
   */
  public abstract boolean clear();

  /**
   * Pauses the execution until ENTER key is pressed.
   */
  public void pause() {
    writeLine("Press ENTER to continue...");
    readLine();  
  }

  /**
   * Displays a message and ask confirmation.
   *
   * @param message the message
   * @return <code>true</code> if yes;
   *         <code>false</code> if no.
   */
  public boolean confirm(String message) {
    write(message + " (y/n) ");
    while (true) {
      var value = readLine().trim().toLowerCase();
      if (value.equals("y")) {
        return true;
      }
      if (value.equals("n")) {
        return false;
      }
    }
  }

  /**
   * Exits the program. 
   */
  public void exit() {
    System.exit(0);
  }

}
