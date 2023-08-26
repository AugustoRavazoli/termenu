package io.github.augustoravazoli.termenu.core;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.groups.Tuple.tuple;

class ReflectionTest {

  @Test
  @DisplayName("Get title")
  void getTitle() {
    // when
    var title = Reflection.getTitle(ConcreteMenu.class);
    // then
    assertThat(title).isEqualTo("Concrete Menu");
  }

  @Test
  @DisplayName("Get error if title is missing")
  void getErrorIfTitleIsMissing() {
    // given
    class MissingTitleMenu extends Menu {}
    // then
    assertThatThrownBy(() -> Reflection.getTitle(MissingTitleMenu.class))
      .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  @DisplayName("Get options")
  void getOptions() {
    // when
    var options = Reflection.getOptions(ConcreteMenu.class);
    // then
    assertThat(options).size().isEqualTo(3)
      .returnToIterable()
      .extracting("number", "name", "action.name")
      .contains(
        tuple(1, "Hello", "hello"),
        tuple(2, "World", "world"),
        tuple(3, "Exit", "exit")
      );
  }

  @Test
  @DisplayName("Get error option with parameters")
  void getErrorForOptionWithParameters() {
    // given
    class InvalidOptionsMenu extends Menu {
      
      @Option(number = 1, name = "invalid")
      void invalid(int arg) {}

    };
    // then
    assertThatThrownBy(() -> Reflection.getOptions(InvalidOptionsMenu.class))
      .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  @DisplayName("Get error for option with return type different from void")
  void getErrorForOptionWithReturnTypeDifferentFromVoid() {
    // given
    class InvalidOptionsMenu extends Menu {
      
      @Option(number = 1, name = "invalid")
      int invalid() {
        return 1;
      }

    };
    // then
    assertThatThrownBy(() -> Reflection.getOptions(InvalidOptionsMenu.class))
      .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  @DisplayName("Get error for options with same number")
  void getErrorForOptionsWithSameNumber() {
    // given
    class InvalidOptionsMenu extends Menu {
      
      @Option(number = 1, name = "Hello")
      void hello() {}

      @Option(number = 1, name = "World")
      void world() {}

    };
    // then
    assertThatThrownBy(() -> Reflection.getOptions(InvalidOptionsMenu.class))
      .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  @DisplayName("Execute action")
  void executeAction() {
    // given
    var menu = mock(ConcreteMenu.class);
    var options = Reflection.getOptions(ConcreteMenu.class);
    // when
    Reflection.executeAction(options.get(1).action(), menu);
    // then
    verify(menu, times(1)).world();
  }

}
