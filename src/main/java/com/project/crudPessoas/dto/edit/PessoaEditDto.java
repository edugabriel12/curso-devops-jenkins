package com.project.crudPessoas.dto.edit;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PessoaEditDto {

    private String nome;
    private String dataNascimento;
}
