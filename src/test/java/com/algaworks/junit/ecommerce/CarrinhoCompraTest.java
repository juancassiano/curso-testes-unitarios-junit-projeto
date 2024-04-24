package com.algaworks.junit.ecommerce;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;


@DisplayName("Carrinho de Compra")
public class CarrinhoCompraTest {

  private CarrinhoCompra carrinhoCompra;
  private Cliente cliente;
  private List<ItemCarrinhoCompra> itens;
  private Produto notebook;
  private Produto desktop;
  private Produto tablet;

  @Nested
  @DisplayName("Dado um carrinho de dois itens")
  class DadoUmCarrinhoComDoisItens{
    @BeforeEach
    void beforeEach(){
      cliente = new Cliente(1L, "Juan");
      notebook = new Produto(1L,"Notebook", "Notebook",BigDecimal.TEN);
      desktop = new Produto(2L,"Desktop", "desktop",new BigDecimal(20.50));
      tablet = new Produto(3L,"Tablet", "tablet",new BigDecimal(30.50));
      itens = new ArrayList<>();
      itens.add(new ItemCarrinhoCompra(notebook, 2));
      itens.add(new ItemCarrinhoCompra(desktop, 1));

      carrinhoCompra = new CarrinhoCompra(cliente, itens);
    }

    @Nested
    @DisplayName("Quando retornar itens")
    class QuandoRetornarItens{

      @Test
      @DisplayName("Então deve retornar dois itens")
      void entaoDeveRetornarDoisItens(){
        assertEquals(2, carrinhoCompra.getItens().size());
      }

      @Test
      @DisplayName("E deve retornar uma nova instância da lista de itens")
      void eDeveRetornarUmaNovaLista(){
        carrinhoCompra.getItens().clear();
        assertEquals(2, carrinhoCompra.getItens().size());
      }
    }

    @Nested
    @DisplayName("Quando remover um notebook")
    class QuandoRemoverUmItem{

      @BeforeEach
      void beforeEach(){
        carrinhoCompra.removerProduto(notebook);
      }

      @Test
      @DisplayName("Então deve diminuir a quantidade total de itens")
      void entaoDeveDiminuirQuantidadeTotalDeItens(){
        assertEquals(1, carrinhoCompra.getItens().size());
      }

      @Test
      @DisplayName("E não deve remover demais itens")
      void eNaoDeveRemoverDemaisItens(){
        assertEquals(desktop, carrinhoCompra.getItens().get(0).getProduto());
      }
    }


    @Nested
    @DisplayName("Quando aumentar quantidade de um notebook")
    class QuandoAumentarQuantidade{

      @BeforeEach
      void beforeEach(){
        carrinhoCompra.aumentarQuantidadeProduto(notebook);
      }

      @Test
      @DisplayName("Então deve somar na quantidade dos itens iguais")
      void deveSomarQuantidadeItensIguais(){
        assertEquals(3, carrinhoCompra.getItens().get(0).getQuantidade());
        assertEquals(1, carrinhoCompra.getItens().get(1).getQuantidade());
      }

      @Test
      @DisplayName("Então deve retornar quatro de quantidade total de itens")
      void deveRetornarQuantidadeTotalDeItens(){
        assertEquals(4, carrinhoCompra.getQuantidadeTotalDeProdutos());
      }
    }

    @Nested
    @DisplayName("Quando diminuir quantidade de um notebook")
    class QuandoDiminuirQuantidade{

      @BeforeEach
      void beforeEach(){
        carrinhoCompra.diminuirQuantidadeProduto(notebook);
      }

      @Test
      @DisplayName("Então deve diminuir na quantidade dos itens iguais")
      void deveSomarQuantidadeItensIguais(){
        assertEquals(1, carrinhoCompra.getItens().get(0).getQuantidade());
        assertEquals(1, carrinhoCompra.getItens().get(1).getQuantidade());
      }

      @Test
      @DisplayName("Então deve retornar dois de quantidade total de itens")
      void deveRetornarQuantidadeTotalDeItens(){
        assertEquals(2, carrinhoCompra.getQuantidadeTotalDeProdutos());
      }

      @Test
      @DisplayName("Então deve retornar valor total correto de itens")
      void deveRetornarValorTotalCorretoDeItens(){
        assertEquals(new BigDecimal(30.5), carrinhoCompra.getValorTotal());
      }
    }

    @Nested
    @DisplayName("Quando diminuir quantidade de um item com apenas um de quantidade")
    class QuandoDiminuirQuantidadeDeItemUnico{

      @BeforeEach
      void beforeEach(){
        carrinhoCompra.diminuirQuantidadeProduto(desktop);
      }

      @Test
      @DisplayName("Então deve remover item")
      void entaoDeveRemoverItem(){
        assertNotEquals(carrinhoCompra.getItens().get(0).getProduto(), desktop);
      }
    }

    @Nested
    @DisplayName("Quando adicionar item com quantidade inválida")
    class QuandoAdicionarItemComQuantidadeInvalida{

      @Test
      @DisplayName("Então deve lançar exception")
      void entaoDeveLancarException(){
        assertThrows(RuntimeException.class, () -> carrinhoCompra.adicionarProduto(desktop, -1));
      }
    }
  }
}
