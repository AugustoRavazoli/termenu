package io.github.augustoravazoli.termenu;

import static java.lang.annotation.ElementType.TYPE;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Inherited;
import java.lang.annotation.Target;

/**
 * Indicates that an annotated class is a "menu".
 * @author Augusto Ravazoli
 * @since 1.0.0
 */
@Inherited
@Target(TYPE)
@Retention(RUNTIME)
public @interface Header {

  /**
   * The title of this menu to be displayed
   * @return the title
   */
  String title();

  /**
   * How options will be formated in the menu
   * @return the format
   */
  String format() default "%d - %s\n";

  /**
   * The message to be displayed when an invalid option is chosed
   * @return the error message
   */
  String invalidOptionErrorMessage() default "Invalid option, try again";

  /**
   * The message to be displayed when an invalid token is readed from keyboard if an integer is expected
   * @return the error message
   */
  String invalidIntErrorMessage() default "That's not an integer";


  /**
   * The message to be displayed when an invalid token is readed from keyboard if a double is expected
   * @return the error message
   */
  String invalidDoubleErrorMessage() default "That's not a double";

}
