package com.algaworks.junit.blog.negocio;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.algaworks.junit.blog.armazenamento.ArmazenamentoEditor;
import com.algaworks.junit.blog.modelo.Editor;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class CadastroEditorComMockTest {

  CadastroEditor cadastroEditor;
  Editor editor;
  
  @BeforeEach
  void init(){
    editor = new Editor(null, "Juan", "juan@email.com", BigDecimal.TEN, true);
    
    ArmazenamentoEditor armazenamentoEditor = Mockito.mock(ArmazenamentoEditor.class);
    Mockito.when(armazenamentoEditor.salvar(editor)).thenReturn(new Editor(1L, "Juan", "juan@email.com", BigDecimal.TEN, true));
    
    GerenciadorEnvioEmail gerenciadorEnvioEmail = Mockito.mock(GerenciadorEnvioEmail.class);

    cadastroEditor = new CadastroEditor(armazenamentoEditor, gerenciadorEnvioEmail);
  }

  @Test
  public void data_um_editor_valido_Quando_criar_Entao_deve_retornar_um_id_de_cadastro(){
    Editor editorSalvo = cadastroEditor.criar(editor);
    assertEquals(1L, editorSalvo.getId());
  }
}
