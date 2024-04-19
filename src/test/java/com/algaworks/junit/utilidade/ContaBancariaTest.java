package com.algaworks.junit.utilidade;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

public class ContaBancariaTest {

  @Test
  void deveLancarIllegalArgumentExceptionSeSaldoForNegativo(){
    assertThrows(IllegalArgumentException.class, () -> new ContaBancaria(null));
  }

  @Test
  void deveCriarContaBancaria(){
    ContaBancaria contaBancaria = new ContaBancaria(new BigDecimal(100));
    assertEquals(new BigDecimal(100), contaBancaria.saldo());
  }

  @Test
  void deveLancarIllegalArgumentExceptionSeSaqueValorNegativo(){
    ContaBancaria contaBancaria = new ContaBancaria(new BigDecimal(100.0));
    assertThrows(IllegalArgumentException.class, () -> contaBancaria.saque(new BigDecimal(-50)));
  }
  @Test
  void deveLancarIllegalArgumentExceptionSeSaqueValorZero(){
    ContaBancaria contaBancaria = new ContaBancaria(new BigDecimal(100));
    assertThrows(IllegalArgumentException.class, () -> contaBancaria.saque(BigDecimal.ZERO));
  }

  @Test
  void deveLancarIllegalArgumentExceptionSeSaqueValorNulo(){
    ContaBancaria contaBancaria = new ContaBancaria(new BigDecimal(100));
    assertThrows(IllegalArgumentException.class, () -> contaBancaria.saque(null));
  }

  @Test
  void deveLancarRuntimeExceptionSeSaqueValorMaiorQueSaldo(){
    ContaBancaria contaBancaria = new ContaBancaria(new BigDecimal(100));
    assertThrows(RuntimeException.class, () -> contaBancaria.saque(new BigDecimal(200)));
  }

  @Test
  void deveSacarSeSaldoForIgualAoValorDeSaque(){
    ContaBancaria contaBancaria = new ContaBancaria(new BigDecimal(100));
    contaBancaria.saque(new BigDecimal(100));
    assertEquals(BigDecimal.ZERO, contaBancaria.saldo());
  }

  @Test
  void deveSacarSeSaldoForMaiorAoValorDeSaque(){
    ContaBancaria contaBancaria = new ContaBancaria(new BigDecimal(100));
    contaBancaria.saque(new BigDecimal(20));
    assertEquals(new BigDecimal(80), contaBancaria.saldo());
  }

  @Test
  void deveLancarIllegalArgumentExceptionSeDepositoForNulo(){
    ContaBancaria contaBancaria = new ContaBancaria(new BigDecimal(100));
    assertThrows(IllegalArgumentException.class, () -> contaBancaria.deposito(null));
  }

  @Test
  void deveLancarIllegalArgumentExceptionSeDepositoForNegativo(){
    ContaBancaria contaBancaria = new ContaBancaria(new BigDecimal(100));
    assertThrows(IllegalArgumentException.class, () -> contaBancaria.deposito(new BigDecimal(-20)));
  }

  @Test
  void deveLancarIllegalArgumentExceptionSeDepositoForZero(){
    ContaBancaria contaBancaria = new ContaBancaria(new BigDecimal(100));
    assertThrows(IllegalArgumentException.class, () -> contaBancaria.deposito(BigDecimal.ZERO));
  }

  @Test
  void deveDepositarSeDepositoMaiorQueZero(){
    ContaBancaria contaBancaria = new ContaBancaria(new BigDecimal(100));
    contaBancaria.deposito(new BigDecimal(50));
    assertEquals(new BigDecimal(150),contaBancaria.saldo());
  }
}
