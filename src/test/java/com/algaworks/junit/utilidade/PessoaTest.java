package com.algaworks.junit.utilidade;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class PessoaTest {

  @Test
  void assercaoAgrupada(){
    Pessoa pessoa = new Pessoa("Juan","Cassiano");
    assertAll("Asserções de pessoa",
    () -> assertEquals("Juan", pessoa.getNome()),
    () -> assertEquals("Cassiano", pessoa.getSobrenome())
    );
  }
}
