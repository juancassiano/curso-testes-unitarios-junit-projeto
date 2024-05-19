package com.algaworks.junit.utilidade;

import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import net.bytebuddy.asm.Advice.OffsetMapping.Factory.Illegal;


@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class SaudacaoUtilTest {

  @Test
  @DisplayName("Deve saudar com bom dia de 5 as 11")
  public void deveSaudarBomdiaTest(){
    String saudacao = SaudacaoUtil.saudar(9);
    // assertEquals("Bom dia", saudacao); Junit

    //AssertJ
    Assertions.assertThat(saudacao).isEqualTo("Bom dia");
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
    assertDoesNotThrow(() -> SaudacaoUtil.saudar(-10));

  }

  @Test
  public void dado_uma_hora_invalida_Quando_saudar_Entao_deve_lancar_excecao(){
    // Assertions.catchThrowableOfType(() -> SaudacaoUtil.saudar(-10), IllegalArgumentException.class);
    // IllegalArgumentException e = Assertions.catchThrowableOfType(() -> SaudacaoUtil.saudar(-10), IllegalArgumentException.class);
    // Assertions.assertThat(e).hasMessage("Hora inválida");

    Assertions.assertThatThrownBy(() -> SaudacaoUtil.saudar(-10))
      .isInstanceOf(IllegalArgumentException.class)
      .hasMessage("Hora inválida");
  }

  @ParameterizedTest
  @ValueSource(ints = {5, 6, 7, 8, 9, 10, 11})
  public void dado_horario_matinal_Quando_saudar_Entao_deve_retornar_bom_dia(int hora){
    String saudacao = SaudacaoUtil.saudar(hora);
    assertEquals("Bom dia", saudacao);
  }

  @ParameterizedTest
  @ValueSource(ints = {12, 13, 14, 15, 16, 17})
  public void dado_horario_vespertino_Quando_saudar_Entao_deve_retornar_boa_tarde(int hora){
    String saudacao = SaudacaoUtil.saudar(hora);
    Assertions.assertThat(saudacao).isEqualTo("Boa tarde");
  }

  @ParameterizedTest
  @ValueSource(ints = {18, 19, 20, 21, 22, 23, 0, 1, 2, 3, 4})
  public void dado_horario_noturno_Quando_saudar_Entao_deve_retornar_boa_noite(int hora){
    String saudacao = SaudacaoUtil.saudar(hora);
    Assertions.assertThat(saudacao).isEqualTo("Boa noite");
  }
}
