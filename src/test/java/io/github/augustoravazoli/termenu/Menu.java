package io.github.augustoravazoli.termenu;

import java.io.PrintStream;
import java.util.Scanner;

@Header(title = "Menu")
class Menu extends AbstractMenu {

  Menu(Scanner input, PrintStream output) {
    super(input, output);
  }
  
  @Option(number = 1, name = "Greetings")
  void greetings() {
    var name = askForWord("What's your name?\n");
    printf("Greetings %s\n", name);
  }

  @Option(number = 2, name = "Exit") 
  @Override
  protected void exit() {
    super.exit();
    print("Exiting...\n");
  }

}
