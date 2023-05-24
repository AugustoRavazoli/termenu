package io.github.augustoravazoli.termenu;

import static java.lang.annotation.ElementType.TYPE;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;

/**
 * The title of a menu to be displayed.
 * @author Augusto Ravazoli
 * @since 2.0.0
 */
@Target(TYPE)
@Retention(RUNTIME)
public @interface Title {

  /**
   * Value.
   * @return the value
   */
  String value();

}
