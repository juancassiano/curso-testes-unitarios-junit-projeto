package com.algaworks.junit.blog.negocio;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import com.algaworks.junit.blog.modelo.Editor;
import com.algaworks.junit.blog.modelo.Ganhos;
import com.algaworks.junit.blog.modelo.Post;
import com.algaworks.junit.blog.utilidade.ProcessadorTextoSimples;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class CalculadoraGanhosTest {

  static CalculadoraGanhos calculadora;
  Editor autor;
  Post post;

  @BeforeAll
  static void beforeAll(){
    calculadora = new CalculadoraGanhos(new ProcessadorTextoSimples(), BigDecimal.TEN);
  }

  @BeforeEach
  void beforeEach(){
    autor = new Editor(1L, "Juan", "juan@gmail.com",BigDecimal.valueOf(5), true);
    post = new Post(1L, "Ecossistema Java", "O ecossistema do Java é muito maduro",autor, "ecossistema-java-123",null, false, false);

  }

  @Test
  void given_post_e_autor_premium_when_calcular_ganhos_them_deve_retornar_valor_com_bonus() {
    Ganhos ganhos = calculadora.calcular(post);
    assertEquals(new BigDecimal(45), ganhos.getTotalGanho());
  }

  @Test
  void given_post_e_autor_when_calcular_ganhos_them_deve_retornar_quantidade_de_palavras_no_post() {
    Ganhos ganhos = calculadora.calcular(post);
    assertEquals(7, ganhos.getQuantidadePalavras());
  }

  @Test
  void given_post_e_autor_when_calcular_ganhos_them_deve_retornar_valor_pago_por_palavra_do_autor() {
    Ganhos ganhos = calculadora.calcular(post);
    assertEquals(autor.getValorPagoPorPalavra(), ganhos.getValorPagoPorPalavra());
  }

  @Test
  void given_post_e_autor_sem_premium_when_calcular_ganhos_them_deve_retornar_valor_com_bonus() {
    autor.setPremium(false);
    Ganhos ganhos = calculadora.calcular(post);
    assertEquals(new BigDecimal(35), ganhos.getTotalGanho());
  }
}
