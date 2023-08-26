package io.github.augustoravazoli.termenu.util;

import java.util.stream.Stream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import org.mockito.MockedStatic;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import io.github.augustoravazoli.termenu.io.Terminal;

class AnswerTest {

  private static MockedStatic<Terminal> mockedTerminal;
  private Terminal terminal;

  @BeforeAll
  static void init() {
    mockedTerminal = mockStatic(Terminal.class);
  }

  @AfterAll
  static void destroy() {
    mockedTerminal.close();
  }

  @BeforeEach
  void setUp() {
    // Since mockito actually can't mock sealed classes we need to create a real instance first
    mockedTerminal.when(Terminal::getInstance).thenCallRealMethod();
    // After a real instance exists, we mock the runtime class of that instance
    terminal = mock(Terminal.getInstance().getClass());
    // With a mocked instance in hands, we can return that instead of the real one
    mockedTerminal.when(Terminal::getInstance).thenReturn(terminal);
  }

  private static Stream<Arguments> parseInputToSupportedTypes() {
    return Stream.of(
      Arguments.of(1, Integer.class),
      Arguments.of(1.0, Double.class),
      Arguments.of("string", String.class),
      Arguments.of("string".toCharArray(), char[].class)
    );
  }

  @ParameterizedTest
  @MethodSource
  @DisplayName("Parse input to supported types")
  void parseInputToSupportedTypes(Object output, Class<?> clazz) {
    // given
    when(terminal.readInteger()).thenReturn(1);
    when(terminal.readDecimal()).thenReturn(1.0);
    when(terminal.readLine()).thenReturn("string");
    when(terminal.readPassword()).thenReturn("string".toCharArray());
    // when
    var result = new Answer<>(clazz, n -> true);
    // then
    assertThat(result.value()).isEqualTo(output);
    verify(terminal).write("> ");
  }

  @Test
  @DisplayName("Do not parse input to unsupported type")
  void doNotParseInputToUnsupportedType() {
    // given
    var clazz = Boolean.class;
    // then
    assertThatThrownBy(() -> new Answer<>(clazz, object -> true).value())
      .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  @DisplayName("Parse input validated")
  void parseInputValidated() {
    // given
    var validator = (Validator<Integer>) n -> n > 2;
    when(terminal.readInteger()).thenReturn(1, 3);
    // when
    var answer = new Answer<>(Integer.class, validator);
    // then
    assertThat(answer.value()).isEqualTo(3);
    // and
    var inOrder = inOrder(terminal);
    inOrder.verify(terminal).writeLine(validator.errorMessage());
    inOrder.verify(terminal).write("> ");
  }

}
