package com.project.crudPessoas.dto.response;

import com.project.crudPessoas.models.Endereco;
import lombok.Getter;

@Getter
public class EnderecoResponseDto {

    private String logradouro;

    private String cep;

    private String numero;

    private String cidade;
    public EnderecoResponseDto(Endereco endereco) {
        this.logradouro = endereco.getLogradouro();
        this.cep = endereco.getCep();
        this.numero = endereco.getNumero();
        this.cidade = endereco.getCidade();
    }
}
