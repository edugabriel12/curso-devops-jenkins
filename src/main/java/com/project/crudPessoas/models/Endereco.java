package com.project.crudPessoas.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "ENDERECO")
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long enderecoId;

    private String logradouro;

    private String cep;

    private String numero;

    private String cidade;

    private Boolean ehEnderecoPrincipal;

    @ManyToOne
    @JoinColumn(name = "PESSOA_ID", nullable = false)
    @JsonIgnore
    private Pessoa pessoa;

}
