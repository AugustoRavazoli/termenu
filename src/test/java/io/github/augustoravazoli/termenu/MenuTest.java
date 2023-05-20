package io.github.augustoravazoli.termenu;

import java.util.Scanner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

class MenuTest {

  private Scanner input;
  private Menu menu;

  @BeforeEach
  void setUp() {
    input = mock(Scanner.class);
    menu = spy(new Menu(input, System.out));
  }

  @Test
  void whenChooseAnOption_thenExecuteTheSelectedOption() {
    // given
    when(input.nextInt()).thenReturn(1, 2);
    when(input.next()).thenReturn("Joe");
    doCallRealMethod().when(menu).greetings();
    doCallRealMethod().when(menu).exit();
    // when
    menu.run();
    // then
    verify(menu, times(1)).greetings();
    verify(menu, times(1)).exit();
  }

}
