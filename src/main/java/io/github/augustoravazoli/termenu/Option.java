package io.github.augustoravazoli.termenu;

import static java.lang.annotation.ElementType.METHOD;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Inherited;
import java.lang.annotation.Target;

/**
 * Indicates that an annotated method is an "option". Such methods are used to define an 
 * action to be perfomed when the given option is chosed
 * @author Augusto Ravazoli
 */
@Inherited
@Target(METHOD)
@Retention(RUNTIME)
public @interface Option {

  /**
   * The number readed from keyboard to map this option
   * @return {}
   */
  int number();

  /**
   * The option name to be displayed in the menu
   * @return {}
   */
  String name();

}
