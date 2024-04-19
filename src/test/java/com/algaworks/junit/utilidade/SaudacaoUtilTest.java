package com.algaworks.junit.utilidade;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class SaudacaoUtilTest {

  @Test
  public void deveSaudarBomdiaTest(){
    String saudacao = SaudacaoUtil.saudar(9);
    assertEquals("Bom dia", saudacao);
  }

  @Test
  public void deveSaudarBoaTardeTest(){
    String saudacao = SaudacaoUtil.saudar(16);
    assertEquals("Boa tarde", saudacao);
  }

  @Test
  public void deveSaudarBoaNoiteTest(){
    String saudacao = SaudacaoUtil.saudar(21);
    assertEquals("Boa noite", saudacao);
  }
  @Test
  public void deveLancarException(){
    assertThrows(IllegalArgumentException.class, () -> SaudacaoUtil.saudar(-10));
  }

  @Test
  public void naoDeveLancarException(){
    assertDoesNotThrow(() -> SaudacaoUtil.saudar(0));
  }
}
