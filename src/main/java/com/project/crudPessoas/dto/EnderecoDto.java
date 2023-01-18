package com.project.crudPessoas.dto;

import com.project.crudPessoas.models.Endereco;
import com.project.crudPessoas.models.Pessoa;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class EnderecoDto {
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
}
