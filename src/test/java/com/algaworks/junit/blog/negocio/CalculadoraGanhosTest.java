package com.algaworks.junit.blog.negocio;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.algaworks.junit.blog.modelo.Editor;
import com.algaworks.junit.blog.modelo.Ganhos;
import com.algaworks.junit.blog.modelo.Post;
import com.algaworks.junit.blog.utilidade.ProcessadorTextoSimples;

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
  void deveCalcularGanhos() {
    Ganhos ganhos = calculadora.calcular(post);
    assertEquals(new BigDecimal(45), ganhos.getTotalGanho());
  }

  @Test
  void deveCalcularGanhosSemPremium() {
    autor.setPremium(false);
    Ganhos ganhos = calculadora.calcular(post);
    assertEquals(new BigDecimal(35), ganhos.getTotalGanho());
  }
}
