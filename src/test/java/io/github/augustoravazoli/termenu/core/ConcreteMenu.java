package io.github.augustoravazoli.termenu.core;

@Title("Concrete Menu")
public class ConcreteMenu extends Menu {

  @Option(number = 1, name = "Hello")
  protected void hello() {}

  @Option(number = 2, name = "World")
  protected void world() {}

  @Option(number = 3, name = "Exit") 
  @Override
  protected void exit() {
    super.exit();
  }

}
