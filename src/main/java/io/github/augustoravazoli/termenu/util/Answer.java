package io.github.augustoravazoli.termenu.util;

import io.github.augustoravazoli.termenu.io.Terminal;

/**
 * A helper class to parse user input.
 *
 * @author Augusto Ravazoli
 * @since 3.0.0
 */
final class Answer<T> {

  private final Terminal terminal = Terminal.getInstance();
  private final Class<T> clazz;
  private final Validator<T> validator;

  Answer(Class<T> clazz, Validator<T> validator) {
    this.clazz = clazz;
    this.validator = validator;
  }

  T value() {
    while (true) {
      terminal.write("> ");
      var value = resolveValue();
      if (validator.isValid(value)) {
        return value;
      }
      terminal.writeLine(validator.errorMessage());
    }
  }

  @SuppressWarnings("unchecked")
  private T resolveValue() {
    if (clazz == Integer.class) {
      return (T) (Integer) terminal.readInteger();
    }
    if (clazz == Double.class) {
      return (T) (Double) terminal.readDecimal();
    }
    if (clazz == String.class) {
      return (T) terminal.readLine();
    }
    if (clazz == char[].class) {
      return (T) terminal.readPassword();
    }
    throw new IllegalArgumentException(String.format(
      "Unsupported class %s, the only supported classes are: Integer, Double, String and char[]",
      clazz.getName())
    );
  }

}
