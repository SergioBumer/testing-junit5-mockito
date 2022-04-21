package guru.springframework;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class InlineMocktest {
  @Test
  void testInlineMock() {
    Map mapMock = mock(Map.class);

    when(mapMock.size()).thenReturn(1);
    assertEquals(1, mapMock.size());
  }
}
