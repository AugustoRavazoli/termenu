package io.github.augustoravazoli.termenu.util;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import io.github.augustoravazoli.termenu.io.Terminal;

/**
 * A helper class to parse user input.
 *
 * @author Augusto Ravazoli
 * @since 3.0.0
 */
final class Answer<T> {

  private final Terminal terminal = Terminal.getInstance();
  private final Map<Class<?>, Supplier<?>> suppliers = new HashMap<>();
  private final Class<T> clazz;
  private final Validator<T> validator;

  {
    suppliers.put(Integer.class, terminal::readInteger);
    suppliers.put(Double.class, terminal::readDecimal);
    suppliers.put(String.class, terminal::readLine);
    suppliers.put(char[].class, terminal::readPassword);
  }

  Answer(Class<T> clazz, Validator<T> validator) {
    this.clazz = clazz;
    this.validator = validator;
  }

  @SuppressWarnings("unchecked")
  T value() {
    var supplier = suppliers.get(clazz);
    if (supplier == null) {
      throw new IllegalArgumentException("Unsupported class " + clazz.getName());
    }
    while (true) {
      terminal.write("> ");
      var value = (T) supplier.get(); 
      if (validator.isValid(value)) {
        return value;
      }
      terminal.writeLine(validator.errorMessage());
    }
  }

}
