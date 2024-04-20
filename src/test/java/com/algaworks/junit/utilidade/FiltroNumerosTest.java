package com.algaworks.junit.utilidade;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class FiltroNumerosTest {

  @Test
  public void deve_Retornar_Numeros_Pares(){
    List<Integer> numeros = Arrays.asList(1,2,3,4);
    List<Integer> numerosParesEsperados = Arrays.asList(2,4);
    List<Integer> resultado = FiltroNumeros.numerosPares(numeros);

    assertIterableEquals(numerosParesEsperados, resultado);
  }
}
