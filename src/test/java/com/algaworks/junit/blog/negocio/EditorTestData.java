package com.algaworks.junit.blog.negocio;

import java.math.BigDecimal;

import com.algaworks.junit.blog.modelo.Editor;

public class EditorTestData {
  private EditorTestData(){}

  public static Editor umEditorNovo(){
    return new Editor(null, "Juan", "juan@email.com", BigDecimal.TEN, true);
  }

  public static Editor umEditorExistente(){
    return new Editor(1L, "Juan", "juan@email.com", BigDecimal.TEN, true);
  }

  public static Editor umEditorComIdInexistente(){
    return new Editor(99L, "Juan", "juan@email.com", BigDecimal.TEN, true);
  }
  
}
