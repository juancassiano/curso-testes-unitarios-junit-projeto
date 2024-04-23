package com.algaworks.junit.utilidade;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Conta bancária")
public class ContaBancariaBDDTest {
  @Nested
  @DisplayName("Dado uma conta bancaria com saldo de R$10,00")
  class ContaBancariaComSaldo{
      private ContaBancaria conta;

      @BeforeEach
      void beforeEach(){
        conta = new ContaBancaria(BigDecimal.TEN);
      }

      @Nested
      @DisplayName("Quando efetuar o saque com valor menor")
      class SaqueValorMenor{
        private BigDecimal valorSaque = new BigDecimal(9.0);

        @Test
        @DisplayName("Então não deve lançar exception")
        void deveLancarSaqueSemException(){
          assertDoesNotThrow(() -> conta.saque(valorSaque));
        }

        @Test
        @DisplayName("E subtrair do saldo")
        void deveSubtrairDoSaldo(){
          conta.saque(valorSaque);
          assertEquals(BigDecimal.ONE, conta.saldo());
        }
      }

      @Nested
      @DisplayName("Quando efetuar o saque com valor maior")
      class SaqueComValorMaior{
        private BigDecimal valorSaque = new BigDecimal(20.0);
      
        @Test
        @DisplayName("Então deve lançar exception")
        void deveFalhar(){
          assertThrows(RuntimeException.class, () -> conta.saque(valorSaque));
        }

        @Test
        @DisplayName("E não deve alterar saldo")
        void naoDeveAlterarSaldo(){
          try{
            conta.saque(valorSaque);
            assertEquals(BigDecimal.TEN, conta.saldo());
          }catch(Exception e){}
        }      
      }
  }
  

  @Nested
  @DisplayName("Dado uma conta bancaria com saldo de R$00,00")
  class ContaBancariaSemSaldo{
    private ContaBancaria conta;

    @BeforeEach
    void beforeEach(){
      conta = new ContaBancaria(BigDecimal.ZERO);
    }
    
    @Nested
    @DisplayName("Dado uma conta bancaria com saldo de R$00,00")
    class SaqueComValorMaior{

      private BigDecimal valorSaque = BigDecimal.ONE;

      @Test
      @DisplayName("Então deve lançar exception")
      void deveFalhar(){
        assertThrows(RuntimeException.class, () -> conta.saque(valorSaque));
      }
    }

    @Nested
    @DisplayName("Quando efetuar um depósito de R$10,00")
    class DepositoComDezReais{
      private BigDecimal valorDeposito = BigDecimal.TEN;

      @Test
      @DisplayName("Então deve somar ao saldo")
      void deveSomarAoSaldo(){
        conta.deposito(valorDeposito);
        assertEquals(BigDecimal.TEN, conta.saldo());
      }
    }


  }
}
