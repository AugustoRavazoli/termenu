package io.github.augustoravazoli.termenu;

import static java.lang.annotation.ElementType.METHOD;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;

/**
 * Indicates that an annotated method is an "option". Such methods are used to define an 
 * action to be performed when the given option is chose.
 * @author Augusto Ravazoli
 * @since 1.0.0
 */
@Target(METHOD)
@Retention(RUNTIME)
public @interface Option {

  /**
   * The number read from keyboard to map this option.
   * @return the number
   */
  int number();

  /**
   * The option name to be displayed in the menu.
   * @return the name
   */
  String name();

}
