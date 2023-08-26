package io.github.augustoravazoli.termenu.core;

import java.lang.reflect.Method;

/**
 * This class encapsulates an menu's option.
 *
 * @author Augusto Ravazoli
 * @since 3.0.0
 */
record Choice(int number, String name, Method action) {}
