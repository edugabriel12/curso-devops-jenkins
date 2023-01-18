package com.project.crudPessoas.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class PessoaError {

    static final String PESSOA_NAO_EXISTE = "A pessoa especificada não está cadastrada no sistema!";

    static final String PESSOA_JA_EXISTE = "A pessoa especificada já está cadastrada no sistema!";

    static final String INPUT_INVALIDO = "Por favor, preencha o campo corretamente!";

    public static ResponseEntity<CustomTypeError> errorPessoaNaoExiste() {
        return new ResponseEntity<>(new CustomTypeError(PessoaError.PESSOA_NAO_EXISTE),
                HttpStatus.NOT_FOUND);
    }

    public static ResponseEntity<CustomTypeError> errorPessoaJaExiste() {
        return new ResponseEntity<>(new CustomTypeError(PessoaError.PESSOA_JA_EXISTE),
                HttpStatus.CONFLICT);
    }

    public static ResponseEntity<CustomTypeError> errorInputInvalido() {
        return new ResponseEntity<>(new CustomTypeError(PessoaError.INPUT_INVALIDO),
                HttpStatus.BAD_REQUEST);
    }
}
