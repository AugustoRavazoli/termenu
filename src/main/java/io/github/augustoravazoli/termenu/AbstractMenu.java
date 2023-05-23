package io.github.augustoravazoli.termenu;

import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * A base class to create menus, it also provides utilities methods to perform I/O.
 * @author Augusto Ravazoli
 * @since 1.0.0
 */
public abstract class AbstractMenu {

  private static final Scanner DEFAULT_INPUT = new Scanner(System.in);
  private static final PrintStream DEFAULT_OUTPUT = System.out;

  private final Scanner input;
  private final PrintStream output;
  private final Header header;
  private final List<Method> methods;
  private boolean exited;

  /**
   * Constructs an abstract menu
   * @throws IllegalStateException if {@link io.github.augustoravazoli.termenu.Header} annotation is missing
   */
  protected AbstractMenu() {
    this(DEFAULT_INPUT, DEFAULT_OUTPUT);
  }

  AbstractMenu(Scanner input, PrintStream output) {
    if (!getClass().isAnnotationPresent(Header.class)) {
      throw new IllegalStateException("Missing Header annotation");
    }
    this.input = input;
    this.output = output;
    this.header = getClass().getAnnotation(Header.class);
    this.methods = getMethods();
  }

  private List<Method> getMethods() {
    return Arrays
      .stream(getClass().getDeclaredMethods())
      .filter(m -> m.isAnnotationPresent(Option.class))
      .peek(m -> m.setAccessible(true))
      .sorted((m1, m2) -> Integer.compare(
        m1.getAnnotation(Option.class).number(),
        m2.getAnnotation(Option.class).number()
      ))
      .toList();
  }

  /**
   * Starts the menu and waits for keyboard input to execute an option
   */
  public final void run() {
    exited = false;
    while (!exited) {
      listOptions();
      chooseOption();
    }
  }

  private void listOptions() {
    output.println(header.title());
    for (var method : methods) {
      var option = method.getAnnotation(Option.class);
      output.printf(header.format(), option.number(), option.name());
    }
  }

  private void chooseOption() {
    while (true) {
      var choice = askForInt("");
      var method = methods
        .stream()
        .filter(m -> m.getAnnotation(Option.class).number() == choice)
        .findFirst()
        .orElse(null);
      if (method != null) {
        execute(method);
        break;
      }
      output.println(header.invalidOptionErrorMessage());
    }
  }

  private void execute(Method method) {
    if (!header.clearTerminalAutomatically()) {
      executeHelper(method);
      return;
    }
    clear();
    executeHelper(method);
    if (!method.getName().equals("exit")) {
      clear();
    }
  }

  private void executeHelper(Method method) {
    try {
      method.invoke(this);
    } catch (InvocationTargetException | IllegalAccessException ex) {
      throw new UnsupportedOperationException(ex);
    }
  }

  /**
   * The action to exit this menu
   */
  protected void exit() {
    exited = true;
  }

  /**
   * Clear the terminal output
   */
  protected final void clear() {
    try {
      var osIsWindows = System.getProperty("os.name").contains("Windows");
      var commands = osIsWindows ? List.of("cmd", "/c", "cls") : List.of("clear");
      new ProcessBuilder(commands)
        .inheritIO()
        .start()
        .waitFor();
    } catch (InterruptedException | IOException ex) {
      throw new UnsupportedOperationException(ex);
    }
  }

  /**
   * A helper method to display a message
   * @param message - the message
   */
  protected void print(String message) {
    output.print(message);
  }

  /**
   * A helper method to display a formatted message
   * @param format - the format
   * @param args - the objects
   */
  protected void printf(String format, Object... args) {
    output.printf(format, args);
  }

  /**
   * A helper method to display a message and return an integer from keyboard
   * @param message - the message to display
   * @return an integer
   */
  protected int askForInt(String message) {
    output.print(message);
    checkInt();
    var number = input.nextInt();
    input.nextLine();
    return number;
  }

  private void checkInt() {
    while (!input.hasNextInt()) {
      output.println(header.invalidIntErrorMessage());
      input.next();
    }
  }

  /**
   * A helper method to display a message and return a double from keyboard
   * @param message - the message to display
   * @return a double
   */
  protected double askForDouble(String message) {
    output.print(message);
    checkDouble();
    var number = input.nextDouble();
    input.nextLine();
    return number;
  }

  private void checkDouble() {
    while (!input.hasNextDouble()) {
      output.println(header.invalidDoubleErrorMessage());
      input.next();
    }
  }

  /**
   * A helper method to display a message and return a word from the keyboard
   * @param message - the message to display
   * @return a word
   */
  protected String askForWord(String message) {
    output.print(message);
    var word = input.next();
    input.nextLine();
    return word;
  }

  /**
   * A helper method to display a message and return a line from the keyboard
   * @param message - the message to display
   * @return a line
   */
  protected String askForPhrase(String message) {
    output.print(message);
    return input.nextLine();
  }

}
