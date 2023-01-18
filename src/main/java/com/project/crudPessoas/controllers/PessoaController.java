package com.project.crudPessoas.controllers;

import com.project.crudPessoas.dto.EnderecoDto;
import com.project.crudPessoas.dto.PessoaDto;
import com.project.crudPessoas.dto.edit.PessoaEditDto;
import com.project.crudPessoas.dto.response.EnderecoResponseDto;
import com.project.crudPessoas.dto.response.PessoaResponseDto;
import com.project.crudPessoas.exceptions.EnderecoException;
import com.project.crudPessoas.exceptions.EnderecoJaPrincipalException;
import com.project.crudPessoas.exceptions.PessoaException;
import com.project.crudPessoas.exceptions.PessoaInputInvalidoException;
import com.project.crudPessoas.models.Endereco;
import com.project.crudPessoas.models.Pessoa;
import com.project.crudPessoas.services.PessoaService;
import com.project.crudPessoas.utils.EnderecoError;
import com.project.crudPessoas.utils.PessoaError;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value="/pessoas")
@CrossOrigin
public class PessoaController {

    private final PessoaService pessoaService;

    PessoaController(PessoaService pessoaService) {
        Objects.requireNonNull(pessoaService);
        this.pessoaService = pessoaService;
    }

    @RequestMapping(value = "/{pessoaId}", method = RequestMethod.GET)
    public ResponseEntity<?> consultarPessoaPorId(@PathVariable Long pessoaId) {
        try {
            Pessoa pessoa = pessoaService.consultarPessoaPorId(pessoaId);
            return new ResponseEntity<>(new PessoaResponseDto(pessoa), HttpStatus.OK);
        } catch (PessoaException e) {
            return PessoaError.errorPessoaNaoExiste();
        }
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> criarPessoa(@RequestBody PessoaDto pessoaDto) {
        Pessoa pessoaCriada = pessoaService.criarPessoa(pessoaDto.geraModeloPessoa());
        pessoaService.criarEndereco(pessoaCriada, pessoaCriada.getEnderecos().get(0));
        PessoaResponseDto response = new PessoaResponseDto(pessoaCriada);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> listarPessoas(@RequestParam int pagina) {
        Page<Pessoa> listaPessoas = pessoaService.listarPessoas(PageRequest.of(pagina, 10));
        List<PessoaResponseDto> response = listaPessoas.stream().map(PessoaResponseDto::new).collect(Collectors.toList());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping(value = "/{pessoaId}")
    public ResponseEntity<?> editarPessoa(@PathVariable Long pessoaId, @RequestBody PessoaEditDto pessoaEditDto) {
        Pessoa pessoaEditada = new Pessoa();
        try {
            pessoaEditada = pessoaService.editarPessoa(pessoaId, pessoaEditDto);
        } catch (PessoaException e) {
            return PessoaError.errorPessoaNaoExiste();
        } catch (PessoaInputInvalidoException e) {
            PessoaError.errorInputInvalido();
        }

        PessoaResponseDto response = new PessoaResponseDto(pessoaEditada);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/{pessoaId}/enderecos")
    public ResponseEntity<?> criarEndereco(@PathVariable Long pessoaId, @RequestBody EnderecoDto enderecoDto) {
        Endereco enderecoCriado;
        try {
            Pessoa pessoa = pessoaService.consultarPessoaPorId(pessoaId);
            enderecoCriado = pessoaService.criarEndereco(pessoa, enderecoDto.geraModeloEndereco(pessoa));
        } catch (PessoaException e) {
            return PessoaError.errorPessoaNaoExiste();
        }

        EnderecoResponseDto response = new EnderecoResponseDto(enderecoCriado);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{pessoaId}/enderecos")
    public ResponseEntity<?> listarEnderecos(@PathVariable Long pessoaId) {
        try {
            Pessoa pessoa = pessoaService.consultarPessoaPorId(pessoaId);
            List<Endereco> listaEnderecos = pessoaService.listarEnderecos(pessoa);
            List<EnderecoResponseDto> response = listaEnderecos.stream().map(EnderecoResponseDto::new).collect(Collectors.toList());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (PessoaException e) {
            return PessoaError.errorPessoaNaoExiste();
        }
    }

    @PatchMapping(value = "/enderecos/{enderecoId}")
    public ResponseEntity<?> informarEnderecoPrincipal(@PathVariable Long enderecoId) {
        try {
            Endereco endereco = pessoaService.consultarEnderecoPorId(enderecoId);
            pessoaService.informarEnderecoPrincipal(endereco);
        } catch (EnderecoException e) {
            EnderecoError.errorEnderecoNaoExiste();
        } catch (EnderecoJaPrincipalException e) {
            EnderecoError.errorEnderecoJaPrincipal();
        } catch (PessoaException e) {
            return PessoaError.errorPessoaNaoExiste();
        }
        return new ResponseEntity<>("Endereço principal atualizado!", HttpStatus.OK);
    }
}
