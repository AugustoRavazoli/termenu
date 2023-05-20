# Termenu

A simple library to develop interactive menus in terminal.

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

Package the project

```bash
  mvn package
```

Install the package in the local maven repository
```bash
  mvn install
```

Add the dependency to your `pom.xml` file
```xml
  <dependency>
    <groupId>io.github.augustoravazoli.termenu</groupId>
    <artifactId>termenu</artifactId>
    <version>1.0.0</version>
  </dependency>
```

### Usage

Create a class exteding `AbstractMenu` and mark it with `Header` and `Option` annotations
```java
  @Header(title = "Menu") // Set the menu's title
  class Menu extends AbstractMenu {
 
    @Option(number = 1, name = "Greetings") // Add an option
    private void greetings() {
      var name = askForWord("What's your name?\n");
      printf("Greetings %s\n", name);
    }

    @Option(number = 2, name = "Exit") // Use the inherited exit action to exit this menu
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

### Documentation

You can check the API documentation [here](https://augustoravazoli.github.io/termenu/apidocs).

## License

This project is licensed under the MIT License - see the LICENSE file for details
