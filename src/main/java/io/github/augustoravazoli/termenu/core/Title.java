package io.github.augustoravazoli.termenu.core;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Retention;
import java.lang.annotation.Documented;
import java.lang.annotation.Target;

/**
 * The title of a menu to be displayed. Every menu class should be 
 * marked with this annotation to give the menu a displayable title.
 * 
 * @author Augusto Ravazoli
 * @since 2.0.0
 */
@Target(TYPE)
@Retention(RUNTIME)
@Documented
public @interface Title {

  /**
   * The title.
   *
   * @return the title
   */
  String value();

}
