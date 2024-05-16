package com.algaworks.junit.blog.negocio;

import java.math.BigDecimal;

import com.algaworks.junit.blog.modelo.Editor;

public class EditorTestData {
  private EditorTestData(){}

  public static Editor.Builder umEditorNovo(){
    return Editor.builder().nome("Juan").email("juan@email.com").valorPagoPorPalavra(BigDecimal.TEN).premium(true);
  }

  public static Editor.Builder umEditorExistente(){
    return umEditorNovo().id(1L);
  }

  public static Editor.Builder umEditorComIdInexistente(){
    return umEditorNovo().id(99L);
  }
  
}
