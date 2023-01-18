package com.project.crudPessoas.services;

import com.project.crudPessoas.dto.edit.PessoaEditDto;
import com.project.crudPessoas.exceptions.EnderecoException;
import com.project.crudPessoas.exceptions.EnderecoJaPrincipalException;
import com.project.crudPessoas.exceptions.PessoaException;
import com.project.crudPessoas.exceptions.PessoaInputInvalidoException;
import com.project.crudPessoas.models.Endereco;
import com.project.crudPessoas.models.Pessoa;
import com.project.crudPessoas.repositories.EnderecoRepository;
import com.project.crudPessoas.repositories.PessoaRepository;
import javax.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class PessoaService {

    private final PessoaRepository pessoaRepository;

    private final EnderecoRepository enderecoRepository;

    PessoaService(PessoaRepository pessoaRepository, EnderecoRepository enderecoRepository) {
        Objects.requireNonNull(pessoaRepository);
        Objects.requireNonNull(enderecoRepository);
        this.pessoaRepository = pessoaRepository;
        this.enderecoRepository = enderecoRepository;
    }

    public Pessoa consultarPessoaPorId(Long id) throws PessoaException {
        return pessoaRepository.findById(id).orElseThrow(PessoaException::new);
    }

    public Pessoa criarPessoa(Pessoa pessoa) {
        return pessoaRepository.save(pessoa);
    }

    public Page<Pessoa> listarPessoas(PageRequest pagina) {
        return pessoaRepository.findAll(PageRequest.of(pagina.getPageNumber(), 10));
    }

    public Pessoa editarPessoa(Long pessoaId, PessoaEditDto pessoaEditDto) throws PessoaException, PessoaInputInvalidoException {
        Pessoa pessoa = consultarPessoaPorId(pessoaId);

        if (pessoaEditDto.getNome() != null) {
            if (pessoaEditDto.getNome().isEmpty()) {
                throw new PessoaInputInvalidoException();
            }
            pessoa.setNome(pessoaEditDto.getNome());
        }

        if (pessoaEditDto.getDataNascimento() != null) {
            pessoa.setDataNascimento(new Date(pessoaEditDto.getDataNascimento()));
        } else {
            throw new PessoaInputInvalidoException();
        }

        return criarPessoa(pessoa);
    }

    public Endereco criarEndereco(Pessoa pessoa, Endereco endereco) {
        endereco.setPessoa(pessoa);
        return enderecoRepository.save(endereco);
    }

    public List<Endereco> listarEnderecos(Pessoa pessoa) {
        return pessoa.getEnderecos();
    }

    public void informarEnderecoPrincipal(Endereco endereco) throws PessoaException, EnderecoJaPrincipalException {
        if (endereco.getEhEnderecoPrincipal()) {
            throw new EnderecoJaPrincipalException();
        }
        Pessoa pessoa = endereco.getPessoa();
        if (pessoa == null) {
            throw new PessoaException();
        }
        List<Endereco> enderecosPessoa = listarEnderecos(pessoa);
        enderecosPessoa.forEach(enderecoPessoa -> {
            if (enderecoPessoa.getEhEnderecoPrincipal()) {
                enderecoPessoa.setEhEnderecoPrincipal(false);
            }
        });
        endereco.setEhEnderecoPrincipal(true);
        criarEndereco(pessoa, endereco);
    }

    public Endereco consultarEnderecoPorId(Long enderecoId) throws EnderecoException {
        return enderecoRepository.findById(enderecoId).orElseThrow(EnderecoException::new);
    }
}
