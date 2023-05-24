package io.github.augustoravazoli.termenu;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class MenuTest {

  @Test
  void whenChooseOptions_thenExecuteTheSelectedOptions() {
    // given
    var input = new ByteArrayInputStream("1\nJoe\n2\n".getBytes());
    var output = new ByteArrayOutputStream();
    var menu = new Menu(new Scanner(input), new PrintStream(output));
    // when
    menu.run();
    // then
    assertThat(output.toString()).isEqualTo(
      """
      Menu
      1 - Greetings
      2 - Exit
      What's your name?
      Greetings Joe
      Menu
      1 - Greetings
      2 - Exit
      Exiting...
      """
    );
  }

  @Test
  void givenInvalidOption_whenChooseOptions_thenPrintInvalidOptionMessage() {
    // given
    var input = new ByteArrayInputStream("3\n2\n".getBytes());
    var output = new ByteArrayOutputStream();
    var menu = new Menu(new Scanner(input), new PrintStream(output));
    // when
    menu.run();
    // then
    assertThat(output.toString()).isEqualTo(
      """
      Menu
      1 - Greetings
      2 - Exit
      Invalid option
      Exiting...
      """
    );
  }

  @Test
  void givenNonIntegerValue_whenAskForInteger_thenPrintErrorMessageAndAskAgain() {
    // given
    var input = new ByteArrayInputStream("f\n2\n".getBytes());
    var output = new ByteArrayOutputStream();
    var menu = new Menu(new Scanner(input), new PrintStream(output));
    // when
    var number = menu.askForInt("Enter an integer:\n");
    // then
    assertThat(output.toString()).isEqualTo(
      """
      Enter an integer:
      That's not a valid integer
      """
    );
    assertThat(number).isEqualTo(2);
  }

  @Test
  void givenNonDoubleValue_whenAskForDouble_thenPrintErrorMessageAndAskAgain() {
    // given
    var input = new ByteArrayInputStream("f\n2\n".getBytes());
    var output = new ByteArrayOutputStream();
    var menu = new Menu(new Scanner(input), new PrintStream(output));
    // when
    var number = menu.askForDouble("Enter a double:\n");
    // then
    assertThat(output.toString()).isEqualTo(
      """
      Enter a double:
      That's not a valid double
      """
    );
    assertThat(number).isEqualTo(2);
  }

}
