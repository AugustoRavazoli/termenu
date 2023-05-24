package io.github.augustoravazoli.termenu;

import static java.lang.annotation.ElementType.TYPE;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Target;

/**
 * An annotation to add configurations to a menu.
 * @author Augusto Ravazoli
 * @since 2.0.0
 */
@Documented
@Inherited
@Target(TYPE)
@Retention(RUNTIME)
public @interface Configuration {

  /**
   * How options will be formatted in the menu.
   * @return the format
   */
  String format();

  /**
   * If the terminal should be cleared automatically after an option is chose and executed.
   * @return a boolean
   */
  boolean clearTerminalAutomatically();

  /**
   * The message to be displayed when choosing an option.
   * @return the message
   */
  String chooseOptionMessage();

  /**
   * The message to be displayed when an invalid option is chose.
   * @return the message
   */
  String invalidOptionMessage();

  /**
   * The message to be displayed when an invalid token is read from keyboard if an integer is expected.
   * @return the message
   */
  String invalidIntMessage();

  /**
   * The message to be displayed when an invalid token is read from keyboard if a double is expected.
   * @return the message
   */
  String invalidDoubleMessage();

}
