package io.github.augustoravazoli.termenu.util;

import io.github.augustoravazoli.termenu.io.Terminal;

/**
 * Comunicator is an utility class to perform high-level I/O.
 *
 * @author Augusto Ravazoli
 * @since 3.0.0
 */
public final class Comunicator {

  private Comunicator() {}

  /**
   * Displays a message and return a parsed value of the provided type. 
   * The following types are supported: Integer, Double, String, and char[].
   *
   * @param  <T>     the return type
   * @param  message the message to be displayed
   * @param  clazz   the type to be parsed
   * @return         the parsed value
   * @throws IllegalArgumentException if the provided type is not supported.
   */
  public static <T> T ask(String message, Class<T> clazz) {
    return ask(message, clazz, object -> true);
  }

  /**
   * Displays a message and return a validated parsed value of the provided type. 
   * The following types are supported: Integer, Double, String, and char[].
   *
   * @param  <T>       the return type
   * @param  message   the message to be displayed
   * @param  clazz     the type to be parsed
   * @param  validator the validator used
   * @return           the parsed value
   * @throws IllegalArgumentException if the provided type is not supported.
   */
  public static <T> T ask(String message, Class<T> clazz, Validator<T> validator) {
    Terminal.getInstance().writeLine(message);
    return new Answer<>(clazz, validator).value();
  }

  /**
   * Displays a formatted message.
   *
   * @param format the format
   * @param args   the args
   */
  public static void say(String format, Object... args) {
    Terminal.getInstance().writeLine(format, args);
  }

  /**
   * Displays an object.
   *
   * @param object the object
   */
  public static void say(Object object) {
    Terminal.getInstance().writeLine(object.toString());
  }

}
