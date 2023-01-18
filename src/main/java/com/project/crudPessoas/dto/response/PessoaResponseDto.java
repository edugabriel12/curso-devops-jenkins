package com.project.crudPessoas.dto.response;

import com.project.crudPessoas.models.Endereco;
import com.project.crudPessoas.models.Pessoa;
import lombok.Getter;

import java.util.List;
@Getter
public class PessoaResponseDto {

    private final String nome;
    private final String dataNascimento;

    private final List<Endereco> enderecos;
    public PessoaResponseDto(Pessoa pessoa) {
        this.nome = pessoa.getNome();
        this.dataNascimento = pessoa.getDataNascimento().toString();
        this.enderecos = pessoa.getEnderecos();
    }
}
