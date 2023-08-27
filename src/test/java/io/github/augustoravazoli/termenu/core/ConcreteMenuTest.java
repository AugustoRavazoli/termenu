package io.github.augustoravazoli.termenu.core;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.mockito.MockedStatic;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;

import io.github.augustoravazoli.termenu.io.Terminal;

class ConcreteMenuTest {

  private static MockedStatic<Terminal> mockedTerminal;
  private Terminal terminal;
  private ConcreteMenu menu;

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
    menu = spy(ConcreteMenu.class);
  }

  @Test
  @DisplayName("Display options")
  void displayOptions() {
    // given
    when(terminal.readInteger()).thenReturn(3);
    // when
    menu.run();
    // then
    var inOrder = inOrder(terminal); 
    inOrder.verify(terminal).writeLine("---- %s ----", "Concrete Menu");
    inOrder.verify(terminal).writeLine("%d  %s", 1, "Hello");
    inOrder.verify(terminal).writeLine("%d  %s", 2, "World");
    inOrder.verify(terminal).writeLine("%d  %s", 3, "Exit");
    inOrder.verify(terminal).writeLine("-----%s-----", "-------------");
    inOrder.verify(terminal).write("> ");
  }

  @Test
  @DisplayName("Execute selected option")
  void executeSelectedOption() {
    // given
    when(terminal.readInteger()).thenReturn(2, 3);
    // when
    menu.run();
    // then
    verify(menu, times(1)).world();
    verify(terminal, times(2)).clear();
  }

  @Test
  @DisplayName("Exit")
  void exit() {
    // given
    when(terminal.readInteger()).thenReturn(3);
    // when
    menu.run();
    // then
    verify(menu, times(1)).exit();
    verify(terminal, times(1)).clear();
  }

  @Test
  @DisplayName("Don't execute invalid option")
  void doNotExecuteInvalidOption() {
    // given
    when(terminal.readInteger()).thenReturn(4, 3);
    // when
    menu.run();
    // then
    var inOrder = inOrder(terminal); 
    inOrder.verify(terminal).writeLine("Invalid option, try again:");
    inOrder.verify(terminal).write("> ");
    verify(menu, times(1)).exit();
  }

}
