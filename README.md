# Termenu

A simple library to develop interactive menus on terminal using Java.

## Getting Started

### Prerequisites

* Java 17+
* Maven

### Installing

Clone the project

```bash
  git clone https://github.com/AugustoRavazoli/termenu.git
```

Go to the project directory

```bash
  cd termenu
```

Install the package in the local maven repository
```bash
  mvn install -DperformRelease=true
```

Add the dependency to the `pom.xml` file of your project
```xml
  <dependency>
    <groupId>io.github.augustoravazoli</groupId>
    <artifactId>termenu</artifactId>
    <version>1.2.0</version>
  </dependency>
```

### Usage

Create a class exteding `AbstractMenu` and mark it with `Header` and `Option` annotations
```java
  @Header(title = "Menu", clearTerminalAutomatically = false) // Set the menu's title
  class Menu extends AbstractMenu {
 
    @Option(number = 1, name = "Greetings") // Add an option
    private void greetings() {
      var name = askForWord("What's your name?\n");
      printf("Greetings %s\n", name);
    }

    @Option(number = 2, name = "Exit") // Use the inherited exit method to exit this menu
    @Override
    protected void exit() {
      super.exit();
      print("Exiting...\n");
    }

  }
```

In the main class
```java
  public class Main {

    public static void main(String[] args) {
      new Menu().run();
    }

  }
```

The terminal output
```console
  Menu
  1 - Greetings
  2 - Exit
  1
  What's your name?
  Joe
  Greetings Joe
  Menu
  1 - Greetings
  2 - Exit
  2
  Exiting...
```

### Documentation

You can check the API documentation [here](https://augustoravazoli.github.io/termenu/apidocs).

## License

This project is licensed under the MIT License - see the LICENSE file for details
