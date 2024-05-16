package com.algaworks.junit.blog.negocio;

import java.math.BigDecimal;

import com.algaworks.junit.blog.modelo.Ganhos;
import com.algaworks.junit.blog.modelo.Post;

public class PostTestData {

  private PostTestData(){}

  public static Post.Builder umPostNovo(){
    return Post.builder().titulo("Olá Mundo").conteudo("Olá mundo com sout").autor(EditorTestData.umEditorExistente().build()).pago(true).publicado(true);
  }

  public static Post.Builder umPostExistente(){
    return umPostNovo().id(1L).slug("ola-mundo-java").ganhos(new Ganhos(BigDecimal.TEN, 4, BigDecimal.valueOf(10)));
  }

  public static Post.Builder umPostComIdInexistente(){
    return umPostNovo().id(99L);
  }
  
}
