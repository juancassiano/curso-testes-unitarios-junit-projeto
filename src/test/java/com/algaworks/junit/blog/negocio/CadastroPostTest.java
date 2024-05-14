package com.algaworks.junit.blog.negocio;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.algaworks.junit.blog.armazenamento.ArmazenamentoPost;
import com.algaworks.junit.blog.modelo.Editor;
import com.algaworks.junit.blog.modelo.Ganhos;
import com.algaworks.junit.blog.modelo.Notificacao;
import com.algaworks.junit.blog.modelo.Post;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;
import java.math.BigDecimal;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ExtendWith(MockitoExtension.class)
public class CadastroPostTest {

  @Mock
  ArmazenamentoPost armazenamentoPost;
  @Mock
  CalculadoraGanhos calculadoraGanhos;
  @Mock
  GerenciadorNotificacao gerenciadorNotificacao;

  @InjectMocks
  CadastroPost cadastroPost;

  @Captor
  ArgumentCaptor<Notificacao> notifiArgumentCaptor;

  @Spy
  Editor editor =  new Editor(1L, "Juan", "juan@email.com", BigDecimal.TEN, true);

  @Nested
  class Cadastro{
    @Spy
    Post post = new Post("Olá Mundo", "Olá mundo com sout", editor, true, true);
    
    @Test
    void dado_um_post_valido_Quando_cadastrar_Entao_deve_salvar(){
      Mockito.when(armazenamentoPost.salvar(Mockito.any(Post.class)))
        .then(invocation -> {
          Post postEnviado = invocation.getArgument(0, Post.class);
          postEnviado.setId(1L);
          return postEnviado;
        });

        cadastroPost.criar(post);
        Mockito.verify(armazenamentoPost, Mockito.times(1)).salvar(Mockito.any(Post.class));
    }

    @Test
    void dado_um_post_valido_Quando_cadastrar_Entao_deve_retornar_id_valido(){
      Mockito.when(armazenamentoPost.salvar(Mockito.any(Post.class)))
        .then(invocation -> {
          Post postEnviado = invocation.getArgument(0, Post.class);
          postEnviado.setId(1L);
          return postEnviado;
        });

        Post postSalvo = cadastroPost.criar(post);
        assertEquals(1L, postSalvo.getId());
      }

      @Test
      void dado_um_post_valido_Quando_cadastrar_Entao_deve_retornar_post_com_ganhos(){
        Mockito.when(armazenamentoPost.salvar(Mockito.any(Post.class)))
        .then(invocation -> {
          Post postEnviado = invocation.getArgument(0, Post.class);
          postEnviado.setId(1L);
          return postEnviado;
        });

        Mockito.when(calculadoraGanhos.calcular(post))
          .thenReturn(new Ganhos(BigDecimal.TEN, 4, BigDecimal.valueOf(40)));

        Post postSalvo = cadastroPost.criar(post);

        Mockito.verify(post, Mockito.times(1)).setGanhos(Mockito.any(Ganhos.class));
        assertNotNull(postSalvo.getGanhos());
      }

      @Test
      void dado_um_post_valido_Quando_cadastrar_Entao_deve_retornar_post_com_slug(){
        Mockito.when(armazenamentoPost.salvar(Mockito.any(Post.class)))
        .then(invocacao -> {
            Post postEnviado = invocacao.getArgument(0, Post.class);
            postEnviado.setId(1L);
            return postEnviado;
        });

        Post postSalvo = cadastroPost.criar(post);

        Mockito.verify(post, Mockito.times(1)).setSlug(Mockito.anyString());
        assertNotNull(postSalvo.getSlug());
      }

      @Test
      void dado_um_post_null_Quando_cadastrar_Entao_deve_lancar_exception_e_nao_deve_savar(){
        assertThrows(NullPointerException.class, () -> cadastroPost.criar(null));
        Mockito.verify(armazenamentoPost, Mockito.never()).salvar(Mockito.any(Post.class));
      }

      @Test
      void dado_um_post_valido_Quando_cadastrar_Entao_deve_calcular_ganhos_antes_de_salvar(){
        Mockito.when(armazenamentoPost.salvar(Mockito.any(Post.class)))
        .then(invocacao -> {
            Post postEnviado = invocacao.getArgument(0, Post.class);
            postEnviado.setId(1L);
            return postEnviado;
        });

        cadastroPost.criar(post);

        InOrder inOrder = Mockito.inOrder(calculadoraGanhos, armazenamentoPost);
        inOrder.verify(calculadoraGanhos, Mockito.times(1)).calcular(post);
        inOrder.verify(armazenamentoPost, Mockito.times(1)).salvar(post);
      }

      @Test
      void dado_um_post_valido_Quando_cadastrar_Entao_deve_enviar_notificacao_apos_salvar() {
        Mockito.when(armazenamentoPost.salvar(Mockito.any(Post.class)))
                .then(invocacao -> {
                    Post postEnviado = invocacao.getArgument(0, Post.class);
                    postEnviado.setId(1L);
                    return postEnviado;
                });
        
        cadastroPost.criar(post);
        InOrder inOrder = Mockito.inOrder(gerenciadorNotificacao,armazenamentoPost);
        inOrder.verify(armazenamentoPost, Mockito.times(1)).salvar(post);
        inOrder.verify(gerenciadorNotificacao, Mockito.times(1)).enviar(Mockito.any(Notificacao.class));

      }

      @Test
      public void dado_um_post_valido_Quando_cadastrar_Entao_deve_gerar_notificacao_com_titulo_do_post() {
        Mockito.when(armazenamentoPost.salvar(Mockito.any(Post.class)))
                .then(invocacao -> {
                    Post postEnviado = invocacao.getArgument(0, Post.class);
                    postEnviado.setId(1L);
                    return postEnviado;
                });
                
        cadastroPost.criar(post);
        Mockito.verify(gerenciadorNotificacao)
                .enviar(notifiArgumentCaptor.capture());
        
        Notificacao notificacao = notifiArgumentCaptor.getValue();
        assertEquals("Olá Mundo", post.getTitulo(), notificacao.getConteudo());
      }
  }

  @Nested
  class Edicao{
    
    @Spy
    Post post = new Post(1L, "Olá mundo Java", "Olá Java com sout", editor, "ola-mundo-java", new Ganhos(BigDecimal.TEN, 4, BigDecimal.valueOf(10)), true, true);
  
    @Test
    void dado_um_post_valido_Quando_editar_Entao_deve_salvar(){
      Mockito.when(armazenamentoPost.salvar(Mockito.any(Post.class)))
        .then(invocation -> invocation.getArgument(0, Post.class));

      Mockito.when(armazenamentoPost.encontrarPorId(1L)).thenReturn(Optional.ofNullable(post));

      cadastroPost.editar(post);
      Mockito.verify(armazenamentoPost, Mockito.times(1)).salvar(Mockito.any(Post.class));
    }
  

    @Test
    void Dado_um_post_valido__Quando_editar__Entao_deve_retornar_mesmo_id() {
      Mockito.when(armazenamentoPost.salvar(Mockito.any(Post.class)))
              .then(invocacao -> invocacao.getArgument(0, Post.class));
      Mockito.when(armazenamentoPost.encontrarPorId(1L)).thenReturn(Optional.ofNullable(post));

      Post postSalvo = cadastroPost.editar(post);

      assertEquals(1L, postSalvo.getId());
    }

    @Test
    void dado_um_post_pago__Quando_editar__Entao_deve_retornar_post_com_os_mesmos_ganhos_sem_recalcular() {
      post.setConteudo("Conteúdo editado");
      Mockito.when(armazenamentoPost.salvar(Mockito.any(Post.class)))
              .then(invocacao -> invocacao.getArgument(0, Post.class));
      Mockito.when(armazenamentoPost.encontrarPorId(1L)).thenReturn(Optional.ofNullable(post));

      Post postSalvo = cadastroPost.editar(post);

      Mockito.verify(post, Mockito.never()).setGanhos(Mockito.any(Ganhos.class));
      Mockito.verify(post, Mockito.times(1)).isPago();

      assertNotNull(postSalvo.getGanhos());
    }

    @Test
    void dado_um_post_nao_pago_Quando_editar_Entao_deve_retornar_recalcular_ganhos_antes_de_salvar() {
      post.setConteudo("Conteúdo editado");
      post.setPago(false);
      Ganhos novoGanho = new Ganhos(BigDecimal.TEN, 2, BigDecimal.valueOf(20));

      Mockito.when(armazenamentoPost.salvar(Mockito.any(Post.class))).then(invocacao -> invocacao.getArgument(0, Post.class));
      Mockito.when(armazenamentoPost.encontrarPorId(1L)).thenReturn(Optional.ofNullable(post));
      Mockito.when(calculadoraGanhos.calcular(post)).thenReturn(novoGanho);

      Post postSalvo = cadastroPost.editar(post);
      Mockito.verify(post, Mockito.times(1)).setGanhos(novoGanho);
      assertNotNull(postSalvo.getGanhos());
      assertEquals(novoGanho, postSalvo.getGanhos());

      InOrder inOrder = Mockito.inOrder(calculadoraGanhos, armazenamentoPost);
      inOrder.verify(calculadoraGanhos, Mockito.times(1)).calcular(post);
      inOrder.verify(armazenamentoPost, Mockito.times(1)).salvar(post);
    }

    @Test
    void dado_um_post_com_titulo_alterado_Quando_editar_Entao_deve_retornar_post_com_a_mesma_slug_sem_alterar() {
        post.setTitulo("Ola Teste");
        Mockito.when(armazenamentoPost.salvar(Mockito.any(Post.class))).then(invocacao -> invocacao.getArgument(0, Post.class));
        Mockito.when(armazenamentoPost.encontrarPorId(1L)).thenReturn(Optional.ofNullable(post));

        Post postSalvo = cadastroPost.editar(post);

        Mockito.verify(post, Mockito.never()).setSlug(Mockito.anyString());
        assertEquals("ola-mundo-java", postSalvo.getSlug());
    }

    @Test
    void dado_um_post_null_Quando_editar_Entao_deve_lancar_exception_e_nao_salvar(){
      assertThrows(NullPointerException.class, () -> cadastroPost.editar(null));
      Mockito.verify(armazenamentoPost, Mockito.never()).salvar(Mockito.any(Post.class));
    }

    @Test
    void dado_um_post_valido_Quando_editar_Entao_deve_deve_alterar_post_salvo() {
        Post postAlterado = new Post(1L, "Olá Java", "Olá Java", editor, "ola-mundo-java", new Ganhos(BigDecimal.TEN, 4, BigDecimal.valueOf(10)), true, true);

        Mockito.when(armazenamentoPost.salvar(Mockito.any(Post.class)))
                .then(invocacao -> invocacao.getArgument(0, Post.class));
        Mockito.when(armazenamentoPost.encontrarPorId(1L)).thenReturn(Optional.ofNullable(post));

        cadastroPost.editar(postAlterado);

        Mockito.verify(post).atualizarComDados(postAlterado);

        InOrder inOrder = Mockito.inOrder(armazenamentoPost, post);
        inOrder.verify(post).atualizarComDados(postAlterado);
        inOrder.verify(armazenamentoPost).salvar(post);
    }
  }

}
