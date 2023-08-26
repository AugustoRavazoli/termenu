package io.github.augustoravazoli.termenu.core;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Retention;
import java.lang.annotation.Documented;
import java.lang.annotation.Target;

/**
 * Indicates that an annotated method is a menu option, such methods are used to define an 
 * action to be performed when the given option is chose. To be a valid option, the method annotated 
 * with this annotation must have zero parameters and void return type. 
 *
 * @author Augusto Ravazoli
 * @since 1.0.0
 */
@Target(METHOD)
@Retention(RUNTIME)
@Documented
public @interface Option {

  /**
   * The option number to be readed from keyboard.
   *
   * @return the number
   */
  int number();

  /**
   * The option name to be displayed in the menu.
   *
   * @return the name
   */
  String name();

}
