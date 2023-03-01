package br.edu.unoesc.exemplo_H2.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.edu.unoesc.exemplo_H2.model.Funcionario;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
	
	public List<Funcionario> findByNomeContainingIgnoreCase(String nome);
	
	@Query("Select f from Funcionario f where f.salario >= :salarioUm and f.salario <= :salarioDois")
	public List<Funcionario> porSalario(@Param("salarioUm") BigDecimal salarioUm, @Param("salarioDois") BigDecimal salarioDois);
	
	@Query("Select f from Funcionario f where f.num_dep >= 1")
	public List<Funcionario> possuiDependentes();
	
}