package br.edu.unoesc.exemplo_H2.service_impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.edu.unoesc.exemplo_H2.dto.FuncionarioDTO;
import br.edu.unoesc.exemplo_H2.model.Funcionario;
import br.edu.unoesc.exemplo_H2.repository.FuncionarioRepository;
import br.edu.unoesc.exemplo_H2.service.FuncionarioService;

@Service
public class FuncionarioServiceImpl implements FuncionarioService {
	
	@Autowired
	private FuncionarioRepository repositorio;
	
	@Override
	public void popularTabelaInicial() {
		repositorio.saveAll(List.of(
				new Funcionario(null, "Pedro", 1, new BigDecimal(5000), LocalDate.now()),
				new Funcionario(null, "Maria", 1, new BigDecimal(2000), LocalDate.now()),
				new Funcionario(null, "Helena", 1, new BigDecimal(1000), LocalDate.now()),
				new Funcionario(null, "Alex", 1, new BigDecimal(2000), LocalDate.now()),
				new Funcionario(null, "Bruna", 1, new BigDecimal(9000), LocalDate.now()),
				new Funcionario(null, "Vivian", 1, new BigDecimal(6000), LocalDate.now()),
				new Funcionario(null, "Mauro", 1, new BigDecimal(4080), LocalDate.now())
			)
		);
		
		Funcionario l = new Funcionario(null, "Vinicios", 1, new BigDecimal(4080), LocalDate.now());
		l = repositorio.save(l);
	}

	@Override
	public Funcionario incluir(Funcionario livro) {
		livro.setId(null);
		return repositorio.save(livro);
	}

	@Override
	public Funcionario alterar(Long id, Funcionario livro) {
		var l = repositorio.findById(id).orElseThrow(() -> new ObjectNotFoundException("Funcionario não encontrado! Id: "+ id + ", Tipo: " + Funcionario.class.getName(), null));
		
		l.setNome(livro.getNome());
		l.setNum_dep(livro.getNum_dep());
		l.setSalario(livro.getSalario());
		l.setNascimento(livro.getNascimento());
		
		return repositorio.save(l);
	}

	@Override
	public void excluir(Long id) {
		if (repositorio.existsById(id)) {
			repositorio.deleteById(id);
		} else {
			throw new ObjectNotFoundException("Funcionario não encontrado! Id: "
					   						  + id + ", Nome: " + Funcionario.class.getName(), null);
		}
	}

	@Override
	public List<Funcionario> listar() {
		List<Funcionario> funcionarios = new ArrayList<Funcionario>();
		
		// Recupera todos os registros da tabela
		Iterable<Funcionario> itens = repositorio.findAll();
		
		// Cria uma cópia dos dados na lista 'funcionario'
		itens.forEach(funcionarios::add);
		
		return funcionarios;
	}

	@Override
	public Funcionario buscar(Long id) {
		Optional<Funcionario> obj = repositorio.findById(id);
				
		return obj.orElseThrow(
						() -> new ObjectNotFoundException("Funcionario não encontrado! Id: "
					  		+ id + ", Nome: " + Funcionario.class.getName(), null)
					);
	}

	@Override
	public Funcionario buscarPorId(Long id) {
		return repositorio.findById(id).orElse(new Funcionario());					      
	}

	@Override
	public Optional<Funcionario> porId(Long id) {
		return repositorio.findById(id);
	}

	@Override
	public Page<FuncionarioDTO> listarPaginado(Pageable pagina) {
		Page<Funcionario> lista = repositorio.findAll(pagina);
		
		Page<FuncionarioDTO> listaDTO = lista.map(funcionario -> new FuncionarioDTO(funcionario));
		
		return listaDTO;
	}

	@Override
	public List<Funcionario> buscarPorNome(String nome) {
		return repositorio.findByNomeContainingIgnoreCase(nome);
	}

	@Override
	public List<Funcionario> buscarPorNumDependentes(Integer num) {
		return repositorio.possuiDependentes();
	}

	@Override
	public List<Funcionario> buscarPorFaixaSalarial(BigDecimal valorUm, BigDecimal valorDois) {
		return repositorio.porSalario(valorUm, valorDois);
	}
	
}