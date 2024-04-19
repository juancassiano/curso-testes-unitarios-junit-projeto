package com.algaworks.junit.utilidade;

import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

import java.time.Duration;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class SimuladorEsperaTest {
  @Test
  @Disabled("Nao é mais aplicável")
  void deveEsperarEnaoDarTimeOut() {
    assertTimeoutPreemptively(Duration.ofSeconds(1), () -> SimuladorEspera.esperar(Duration.ofMillis(10)));
  }
}
