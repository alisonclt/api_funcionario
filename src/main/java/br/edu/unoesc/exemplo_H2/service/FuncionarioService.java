package br.edu.unoesc.exemplo_H2.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.edu.unoesc.exemplo_H2.dto.FuncionarioDTO;
import br.edu.unoesc.exemplo_H2.model.Funcionario;

public interface FuncionarioService {
	
	void popularTabelaInicial();

	Funcionario incluir(Funcionario funcionario);
	Funcionario alterar(Long id, Funcionario funcionario);
	void excluir(Long id);

	List<Funcionario> listar();
	Page<FuncionarioDTO> listarPaginado(Pageable pagina);
	
	Funcionario buscar(Long id);		
	Funcionario buscarPorId(Long id); 
	Optional<Funcionario> porId(Long id);
	
	List<Funcionario> buscarPorNome(String nome);
	List<Funcionario> buscarPorNumDependentes(Integer num);

	List<Funcionario> buscarPorFaixaSalarial(BigDecimal valorUm, BigDecimal valorDois);
	
}