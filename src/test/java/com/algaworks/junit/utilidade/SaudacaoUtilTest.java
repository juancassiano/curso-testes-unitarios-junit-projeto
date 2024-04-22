package com.algaworks.junit.utilidade;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class SaudacaoUtilTest {

  @Test
  @DisplayName("Deve saudar com bom dia de 5 as 11")
  public void deveSaudarBomdiaTest(){
    String saudacao = SaudacaoUtil.saudar(9);
    assertEquals("Bom dia", saudacao);
  }

  @Test
  @DisplayName("Deve saudar com boa tarde de 12 as 17")
  public void deveSaudarBoaTardeTest(){
    String saudacao = SaudacaoUtil.saudar(16);
    assertEquals("Boa tarde", saudacao);
  }

  @Test
  @DisplayName("Deve saudar com boa tarde de 18 as 4")
  public void deveSaudarBoaNoiteTest(){
    String saudacao = SaudacaoUtil.saudar(3);
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
