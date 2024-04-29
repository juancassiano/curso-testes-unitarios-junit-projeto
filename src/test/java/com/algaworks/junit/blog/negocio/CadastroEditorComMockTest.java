package com.algaworks.junit.blog.negocio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.atLeastOnce;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.algaworks.junit.blog.armazenamento.ArmazenamentoEditor;
import com.algaworks.junit.blog.modelo.Editor;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ExtendWith(MockitoExtension.class)
public class CadastroEditorComMockTest {

  Editor editor;

  @Mock
  ArmazenamentoEditor armazenamentoEditor;
  @Mock
  GerenciadorEnvioEmail gerenciadorEnvioEmail;

  @InjectMocks
  CadastroEditor cadastroEditor;
  
  @BeforeEach
  void init(){
    editor = new Editor(null, "Juan", "juan@email.com", BigDecimal.TEN, true);
    
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
  public void dado_um_editor_valido_Quando_criar_Entao_deve_chamar_metodo_salvar_do_armazenamento(){
    cadastroEditor.criar(editor);
    Mockito.verify(armazenamentoEditor, atLeastOnce()).salvar(Mockito.eq(editor));
    // Mockito.verify(armazenamentoEditor, atLeastOnce()).salvar(Mockito.any(Mockito.any(Editor.class)));

  }
}
