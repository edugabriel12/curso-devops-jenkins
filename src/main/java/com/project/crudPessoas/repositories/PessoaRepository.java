package com.project.crudPessoas.repositories;

import com.project.crudPessoas.models.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
}
