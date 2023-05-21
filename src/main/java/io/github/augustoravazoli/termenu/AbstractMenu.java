package io.github.augustoravazoli.termenu;

import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * A menu should extend this class to work through the {@link #run() Run} method. 
 * This class also provides utilities methods to perform terminal I/O.
 * @author Augusto Ravazoli
 */
public abstract class AbstractMenu {

  private static final Scanner DEFAULT_INPUT = new Scanner(System.in);
  private static final PrintStream DEFAULT_OUTPUT = System.out;

  private final Scanner input;
  private final PrintStream output;
  private final List<Method> actions;
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
    this.actions = getActions();
  }

  private List<Method> getActions() {
    return Arrays
      .stream(getClass().getDeclaredMethods())
      .filter(method -> method.isAnnotationPresent(Option.class))
      .peek(method -> method.setAccessible(true))
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
    while (!exited) {
      listOptions();
      chooseOption();
    }
  }

  private void listOptions() {
    output.println(getClass().getAnnotation(Header.class).title());
    for (var action : actions) {
      var option = action.getAnnotation(Option.class);
      output.printf("%d - %s\n", option.number(), option.name());
    }
  }

  private void chooseOption() {
    var choice = input.nextInt();
    actions
      .stream()
      .filter(action -> action.getAnnotation(Option.class).number() == choice)
      .findFirst()
      .ifPresentOrElse(
        action -> invoke(action),
        () -> output.println(getClass().getAnnotation(Header.class).errorMessage())
      );
  }

  private void invoke(Method method) {
    try {
      method.invoke(this);
    } catch (InvocationTargetException | IllegalAccessException ex) {
      throw new IllegalStateException(ex);
    }
  }

  /**
   * An action to exit the menu
   */
  protected void exit() {
    exited = true;
  }

  /**
   * A helper method to display a message
   * @param message - the message
   */
  protected void print(String message) {
    output.print(message);
  }

  /**
   * A helper method to display a formated message
   * @param format - the the format
   * @param args - the objects
   */
  protected void printf(String format, Object... args) {
    output.printf(format, args);
  }

  /**
   * A helper method to display a message and return an integer from the keyboard
   * @param message - the message to display
   * @return the integer readed from the keyboard
   */
  protected int askForInt(String message) {
    output.print(message);
    return input.nextInt();
  }

  /**
   * A helper method to display a message and return a double from the keyboard
   * @param message - the message to display
   * @return the double readed from the keyboard
   */
  protected double askForDouble(String message) {
    output.print(message);
    return input.nextDouble();
  }

  /**
   * A helper method to display a message and return a string from the keyboard
   * @param message - the message to display
   * @return the string readed from the keyboard
   */
  protected String askForWord(String message) {
    output.print(message);
    return input.next();
  }

  /**
   * A helper method to display a message and return a string line from the keyboard
   * @param message - the message to display
   * @return the string line readed from the keyboard
   */
  protected String askForPhrase(String message) {
    output.print(message);
    return input.nextLine();
  }

}
