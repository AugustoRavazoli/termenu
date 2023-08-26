package io.github.augustoravazoli.termenu.util;

/**
 * A validator to validate user input.
 *
 * @param <T> The type this validator validates.
 *
 * @author Augusto Ravazoli
 * @since 3.0.0
 */
@FunctionalInterface
public interface Validator<T> {

  /**
   * Validate an object.
   *
   * @param object an object
   * @return       <code>true</code> if valid;
   *               <code>false</code> otherwise
   */
  boolean isValid(T object);

  /**
   * The invalid error message.
   *
   * @return the error message
   */
  default String errorMessage() {
    return "Invalid value, try again:";
  }

}
