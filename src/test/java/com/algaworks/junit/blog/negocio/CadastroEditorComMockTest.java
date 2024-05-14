package com.algaworks.junit.blog.negocio;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
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
import java.util.Optional;

import com.algaworks.junit.blog.armazenamento.ArmazenamentoEditor;
import com.algaworks.junit.blog.exception.EditorNaoEncontradoException;
import com.algaworks.junit.blog.exception.RegraNegocioException;
import com.algaworks.junit.blog.modelo.Editor;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ExtendWith(MockitoExtension.class)
public class CadastroEditorComMockTest {

  @Captor
  ArgumentCaptor<Mensagem> mensagemArgumentCaptor;

  @Mock
  ArmazenamentoEditor armazenamentoEditor;
  @Mock
  GerenciadorEnvioEmail gerenciadorEnvioEmail;

  @InjectMocks
  CadastroEditor cadastroEditor;
  

  @Nested
  class CadastroComEditorValido{
    
    @Spy
    Editor editor = new Editor(null, "Juan", "juan@email.com", BigDecimal.TEN, true);

    @BeforeEach
    void init(){
      Mockito.when(armazenamentoEditor.salvar(Mockito.any(Editor.class))).thenAnswer(invocacao -> {
        Editor editorPassado = invocacao.getArgument(0, Editor.class);;
        editorPassado.setId(1L);
        return editorPassado;
      });
    }
  
    @Test
    public void dado_um_editor_valido_Quando_criar_Entao_deve_retornar_um_id_de_cadastro(){
      Editor editorSalvo = cadastroEditor.criar(editor);
      assertEquals(1L, editorSalvo.getId());
    }
  
    @Test
    void dado_um_editor_valido_Quando_criar_Entao_deve_chamar_metodo_salvar_do_armazenamento(){
      cadastroEditor.criar(editor);
      Mockito.verify(armazenamentoEditor, atLeastOnce()).salvar(Mockito.eq(editor));
      // Mockito.verify(armazenamentoEditor, atLeastOnce()).salvar(Mockito.any(Mockito.any(Editor.class)));
    }
  
    @Test
    void dado_um_editor_valido_Quando_criar_e_lancar_exception_ao_salvar_Entao_nao_deve_criar_email(){
      when(armazenamentoEditor.salvar(editor)).thenThrow(new RuntimeException());
      assertAll("Não deve enviar email quando lançar exception do armazenamento", 
        () -> assertThrows(RuntimeException.class, () -> cadastroEditor.criar(editor)),
        () -> verify(gerenciadorEnvioEmail, never()).enviarEmail(any())
      );
    }
  
    @Test
    void dado_um_editor_valido_Quando_cadastrar_Entao_deve_enviar_email_com_desitno_ao_editor(){
      // ArgumentCaptor<Mensagem> mensagemArgumentCaptor = ArgumentCaptor.forClass(Mensagem.class);
  
      Editor editorSalvo = cadastroEditor.criar(editor);
  
      Mockito.verify(gerenciadorEnvioEmail).enviarEmail(mensagemArgumentCaptor.capture());
  
      Mensagem mensagem = mensagemArgumentCaptor.getValue();
  
      assertEquals(editorSalvo.getEmail(), mensagem.getDestinatario());
    }
    
    @Test
    void dado_um_editor_valido_Quando_cadastrar_Entao_deve_verificar_email(){
      // Editor editorSpy = Mockito.spy(editor);
      cadastroEditor.criar(editor);
      Mockito.verify(editor, Mockito.atLeastOnce()).getEmail();
    }
  
    @Test
    void dado_um_editor_com_email_existente_Quando_cadastrar_Entao_deve_lancar_exception(){
      Mockito.when(armazenamentoEditor.encontrarPorEmail("juan@email.com"))
        .thenReturn(Optional.empty())
        .thenReturn(Optional.of(editor));
  
        Editor editorComEmailExistente = new Editor(null, "Juan", "juan@email.com", BigDecimal.TEN, true);
  
        cadastroEditor.criar(editor);
        assertThrows(RegraNegocioException.class, () -> cadastroEditor.criar(editorComEmailExistente));
    }
  
    @Test
    void dado_um_editor_valido_Quando_cadastrar_Entao_deve_enviar_email_apos_salvar(){
      cadastroEditor.criar(editor);
  
      InOrder inOrder = Mockito.inOrder(armazenamentoEditor,gerenciadorEnvioEmail);
      inOrder.verify(armazenamentoEditor,Mockito.times(1)).salvar(editor);
      inOrder.verify(gerenciadorEnvioEmail,Mockito.times(1)).enviarEmail(Mockito.any(Mensagem.class));
    }
  }

  @Nested
  class CadastroComEditorNull {

    @Test
    void dado_um_editor_null_Quando_cadastrar_Entao_deve_lancar_exception(){
      assertThrows(NullPointerException.class,  () -> cadastroEditor.criar(null));
      Mockito.verify(armazenamentoEditor, Mockito.never()).salvar(Mockito.any());
      Mockito.verify(gerenciadorEnvioEmail, Mockito.never()).enviarEmail(Mockito.any());
    }
  }

  @Nested
  class EdicaoComEditorValido{

    @Spy
    Editor editor = new Editor(1L, "Juan", "juan@email.com", BigDecimal.TEN, true);

    @BeforeEach
    void init(){
      Mockito.when(armazenamentoEditor.salvar(editor)).thenAnswer(invocation ->invocation.getArgument(0, Editor.class));
      Mockito.when(armazenamentoEditor.encontrarPorId(1L)).thenReturn(Optional.of(editor));
    }

    @Test
    void dado_um_editor_valido_Quando_editar_Entao_deve_alterar_editor_salvo(){
      Editor editorAtualizado = new Editor(1L, "Cassiano", "cassiano@email.com", BigDecimal.ZERO, false);

      cadastroEditor.editar(editorAtualizado);
      Mockito.verify(editor, Mockito.times(1)).atualizarComDados(editorAtualizado);

      InOrder inOrder = Mockito.inOrder(editor, armazenamentoEditor);
      inOrder.verify(editor).atualizarComDados(editorAtualizado);
      inOrder.verify(armazenamentoEditor).salvar(editor);

    }

  }

  @Nested
  class EdicaoComEditorInexistente{
    Editor editor = new Editor(99L, "Juan", "juan@email.com", BigDecimal.TEN, true);

    @BeforeEach
    void init(){
      Mockito.when(armazenamentoEditor.encontrarPorId(99L)).thenReturn(Optional.empty());
    }

    @Test
    void dado_um_editor_que_nao_exista_Quando_editar_Entao_deve_lancar_exception(){
      assertThrows(EditorNaoEncontradoException.class, () -> cadastroEditor.editar(editor));

      Mockito.verify(armazenamentoEditor, never()).salvar(Mockito.any());
    }
  }
}
