package com.project.crudPessoas.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class EnderecoError {

    static final String ENDERECO_JA_PRINCIPAL = "O endereço especificado já é o principal!";

    static final String ENDERECO_NAO_EXISTE = "O endereco não está cadastrado no sistema!";

    public static ResponseEntity<CustomTypeError> errorEnderecoJaPrincipal() {
        return new ResponseEntity<>(new CustomTypeError(EnderecoError.ENDERECO_JA_PRINCIPAL),
                HttpStatus.CONFLICT);
    }

    public static ResponseEntity<CustomTypeError> errorEnderecoNaoExiste() {
        return new ResponseEntity<>(new CustomTypeError(EnderecoError.ENDERECO_NAO_EXISTE),
                HttpStatus.NOT_FOUND);
    }
}
