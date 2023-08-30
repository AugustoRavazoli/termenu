# Termenu

A simple framework to develop interactive menus on terminal using Java.

## Getting Started

### Prerequisites

* Java 17
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
  mvn install
```

Add the dependency to the `pom.xml` file of your project
```xml
  <dependency>
    <groupId>io.github.augustoravazoli</groupId>
    <artifactId>termenu</artifactId>
    <version>3.0.2</version>
  </dependency>
```

### Usage

Create a class exteding `Menu` and mark it with `Title` and `Option` annotations
```java
  @Title("Greetings Menu") // Sets the menu's title
  public class GreetingsMenu extends Menu { // Extends the base menu
 
    @Option(number = 1, name = "Greetings") // Adds an option
    protected void greetings() {
      var name = ask("What's your name?", String.class); // Displays a message and return a string
      say("Greetings %s", name); // Displays a message
    }

    @Option(number = 2, name = "Exit")
    @Override
    protected void exit() {
      super.exit(); // Use the inherited exit method to exit this menu
      say("Exiting...");
    }

  }
```
`ask` and `say` are static imports from `Comunicator` class.

In the main class
```java
  public class Main {

    public static void main(String[] args) {
      new GreetingsMenu().run(); // Calls the run method to start the menu
    }

  }
```

The terminal output
```console
  ---- Greetings Menu ----
  1  Greetings
  2  Exit
  ------------------------
  > 1
  What's your name?
  > Joe
  Greetings Joe
  ---- Greetings Menu ----
  1  Greetings
  2  Exit
  ------------------------
  > 2
  Exiting...
```

### Documentation

You can check the API documentation [here](https://augustoravazoli.github.io/termenu/apidocs).

## Contributing

To contribute to Termenu, open a issue or a pull-request. 

## License

This project is licensed under the MIT License - see the LICENSE file for details

## Warning

This framework is a small project of mine for learning purposes, this is why it isn't available on 
maven central repository. It's a bit unstable and can suffer weird changes at random time.
