package br.com.zumbolovsky.fateapp.web;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonagensVO implements Serializable {

  private String nome;
}
