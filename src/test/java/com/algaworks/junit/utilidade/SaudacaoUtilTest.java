package com.algaworks.junit.utilidade;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class SaudacaoUtilTest {

  @Test
  public void saudarTeste(){
    String saudacao = SaudacaoUtil.saudar(9);
    assertEquals("Bom dia", saudacao);
  }
}
