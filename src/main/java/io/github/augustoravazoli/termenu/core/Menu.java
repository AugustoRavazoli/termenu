package io.github.augustoravazoli.termenu.core;

import java.util.List;
import java.util.stream.IntStream;
import static java.util.stream.Collectors.joining;

import io.github.augustoravazoli.termenu.io.Terminal;

/**
 * The base class for all menus.
 *
 * @author Augusto Ravazoli
 * @since 3.0.0
 */
public abstract class Menu {

  private final Terminal terminal = Terminal.getInstance();
  private final String title = Reflection.getTitle(getClass());
  private final List<Choice> options = Reflection.getOptions(getClass());
  private boolean exited;

  /**
   * Constructs a menu.
   */
  protected Menu() {}

  /**
   * Starts the menu and waits for keyboard input to execute an option.
   */
  public final void run() {
    exited = false;
    while (!exited) {
      listOptions();
      var option = chooseOption();
      executeOption(option);
    }
  }

  private void listOptions() {
    terminal.writeLine("---- %s ----", title);
    options.stream().forEachOrdered(option ->
      terminal.writeLine("%d  %s", option.number(), option.name())
    );
    var separator = IntStream.range(0, title.length()).mapToObj(i -> "-").collect(joining());
    terminal.writeLine("-----%s-----", separator);
  }

  private Choice chooseOption() {
    while (true) {
      terminal.write("> ");
      var choice = terminal.readInteger(); 
      var selected = options.stream()
        .filter(option -> option.number() == choice)
        .findFirst()
        .orElse(null);
      if (selected != null) {
        return selected;
      }
      terminal.writeLine("Invalid option, try again:");
    }
  }

  private void executeOption(Choice option) {
    var cleared = terminal.clear();
    Reflection.executeAction(option.action(), this);
    if (!exited && cleared) {
      terminal.pause();
      terminal.clear();
    }
  }

  /**
   * Exit this menu.
   */
  protected void exit() {
    exited = true;
  }

}
