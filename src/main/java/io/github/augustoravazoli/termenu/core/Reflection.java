package io.github.augustoravazoli.termenu.core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A utility class responsible for doing the reflection needed for {@link Menu} class.
 *
 * @author Augusto Ravazoli
 * @since 3.0.0
 */
final class Reflection {

  private Reflection() {}

  static String getTitle(Class<? extends Menu> clazz) {
    if (!clazz.isAnnotationPresent(Title.class)) {
      throw new IllegalArgumentException("Missing menu's title");
    }
    return clazz.getAnnotation(Title.class).value();
  }

  static List<Choice> getOptions(Class<? extends Menu> clazz) {
    var set = new HashSet<Integer>();
    return Arrays.stream(clazz.getDeclaredMethods())
      .filter(method -> method.isAnnotationPresent(Option.class))
      .peek(method -> method.setAccessible(true))
      .peek(method -> validateOption(method, set))
      .map(method -> new Choice(
        method.getAnnotation(Option.class).number(),
        method.getAnnotation(Option.class).name(),
        method
      ))
      .sorted(Comparator.comparingInt(Choice::number))
      .toList();
  }

  private static void validateOption(Method method, Set<Integer> set) {
    if (!set.add(method.getAnnotation(Option.class).number())) {
      throw new IllegalArgumentException("Menu's options should be unique");
    }
    if (method.getParameterCount() != 0) {
      throw new IllegalArgumentException("Menu's options should have zero parameters");
    }
    if (method.getReturnType() != Void.TYPE) {
      throw new IllegalArgumentException("Menu's options should have void return type");
    }
  }

  static <T extends Menu> void executeAction(Method method, T menu) {
    try {
      method.invoke(menu);
    } catch (InvocationTargetException | IllegalAccessException ex) {
      throw new IllegalStateException(ex);
    }
  }

}
