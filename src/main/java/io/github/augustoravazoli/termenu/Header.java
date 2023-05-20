package io.github.augustoravazoli.termenu;

import static java.lang.annotation.ElementType.TYPE;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Inherited;
import java.lang.annotation.Target;

/**
 * Indicates that an annotated class is a "menu".
 * @author Augusto Ravazoli
 */
@Inherited
@Target(TYPE)
@Retention(RUNTIME)
public @interface Header {

  /**
   * The title of this menu to be displayed
   * @return the title of this annotated menu
   */
  String title();

  /**
   * The message to be displayed when an invalid option is chosed
   * @return the default error message of the annotated menu
   */
  String errorMessage() default "Invalid option";

}
