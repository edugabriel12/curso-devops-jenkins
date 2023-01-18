package com.project.crudPessoas.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PESSOA")
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long pessoaId;

    private String nome;

    private Date dataNascimento;

    @OneToMany(mappedBy = "pessoa", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Endereco> enderecos;
}
