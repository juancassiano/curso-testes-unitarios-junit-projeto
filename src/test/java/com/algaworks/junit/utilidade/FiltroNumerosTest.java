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
  public void given_lista_de_numeros_when_filtrar_por_pares_Then_deve_retornar_apenas_numeros_pares(){
    List<Integer> numeros = Arrays.asList(1,2,3,4);
    List<Integer> numerosParesEsperados = Arrays.asList(2,4);
    List<Integer> resultado = FiltroNumeros.numerosPares(numeros);

    assertIterableEquals(numerosParesEsperados, resultado);
  }

  @Test
  public void given_lista_de_numeros_when_filtrar_por_impares_Then_deve_retornar_apenas_numeros_impares(){
    List<Integer> numeros = Arrays.asList(1,2,3,4);
    List<Integer> numerosImparesEsperados = Arrays.asList(1,3);
    List<Integer> resultado = FiltroNumeros.numerosImpares(numeros);

    assertIterableEquals(numerosImparesEsperados, resultado);
  }
}
