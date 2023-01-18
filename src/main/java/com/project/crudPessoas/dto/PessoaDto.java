package com.project.crudPessoas.dto;

import com.project.crudPessoas.models.Endereco;
import com.project.crudPessoas.models.Pessoa;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.Date;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class PessoaDto {


    private String nome;

    private String dataNascimento;

    private String logradouro;

    private String cep;

    private String numero;

    private String cidade;

    public Endereco geraModeloEndereco(Pessoa pessoa) {
        return new Endereco(
                null,
                this.logradouro,
                this.cep,
                this.numero,
                this.cidade,
                false,
                pessoa);
    }

    public Pessoa geraModeloPessoa() {
        Pessoa pessoa = new Pessoa(null, this.nome, new Date(this.dataNascimento), new ArrayList<Endereco>());
        Endereco endereco = geraModeloEndereco(pessoa);
        pessoa.getEnderecos().add(endereco);
        return pessoa;
    }
}
