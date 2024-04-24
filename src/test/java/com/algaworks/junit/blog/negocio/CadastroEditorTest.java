package com.algaworks.junit.blog.negocio;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import com.algaworks.junit.blog.exception.RegraNegocioException;
import com.algaworks.junit.blog.modelo.Editor;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class CadastroEditorTest {

  CadastroEditor cadastroEditor;
  ArmazenamentoEditorFixoEmMemoria armazenamentoEditor;
  Editor editor;

  @BeforeEach
  void BeforeEach(){
    editor = new Editor(null, "Juan", "juan@email.com", BigDecimal.TEN, true);
    
    armazenamentoEditor = new ArmazenamentoEditorFixoEmMemoria();

    cadastroEditor = new CadastroEditor(armazenamentoEditor, 
    new GerenciadorEnvioEmail(){
        @Override
        public void enviarEmail(Mensagem mensagem) {
          System.out.println("Enviando mensagem para: " + mensagem.getDestinatario());
        }
      }
    );
  }

  @Test
  public void Dado_editor_valido_Quando_criar_Entao_deve_retornar_um_id_de_cadastro(){
    Editor editorSalvo = cadastroEditor.criar(editor);
    Assertions.assertEquals(1L, editorSalvo.getId());
    assertTrue(armazenamentoEditor.chamouSalvar);
  }

  @Test
  public void Dado_editor_null_Quando_criar_Entao_deve_lancar_Exception(){
    assertThrows(NullPointerException.class, () -> cadastroEditor.criar(null));
    assertFalse(armazenamentoEditor.chamouSalvar);
  }

  @Test
  public void Dado_editor_com_email_existente_Quando_criar_Entao_deve_lancar_exception(){
    editor.setEmail("juan.existe@email.com");
    assertThrows(RegraNegocioException.class, () -> cadastroEditor.criar(editor));
  }

  @Test
  public void Dado_editor_com_email_existente_Quando_criar_Entao_nao_deve_salavar(){
    editor.setEmail("juan.existe@email.com");
    try{
      cadastroEditor.criar(editor);
    }catch(RegraNegocioException e){}
  }
}
