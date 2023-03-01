package br.edu.unoesc.exemplo_H2;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.EmptyResultDataAccessException;

import br.edu.unoesc.exemplo_H2.model.Funcionario;
import br.edu.unoesc.exemplo_H2.service.FuncionarioService;

@SpringBootApplication
public class ApiFuncionarioApplication {
	
	@Value("${mensagem}")
	private String mensagem;
	
	@Value("${ambiente}")
	private String ambiente;
	
	public static void main(String[] args) {
		SpringApplication.run(ApiFuncionarioApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(FuncionarioService funcionario) {
		return args -> {
			System.out.println(mensagem + " " + ambiente);
			
			funcionario.popularTabelaInicial();

			try {
				funcionario.excluir(2L);			
			} catch (EmptyResultDataAccessException e) {
				System.out.println("\n>>> Erro! Registro não encontrado! <<<\n");
			} catch (RuntimeException e) {
				System.out.println("\n>>> Erro de execução! <<<\n" + e.getMessage());
			}
			
			Optional<Funcionario> p = funcionario.porId(3L);
			if (p.isEmpty()) {
				System.out.println("\n>>> Registro não encontrado! <<<\n");
			} else {
				System.out.println(p);				
				System.out.println(p.get());				
				System.out.println(p.get().getNome());				
			}
			
			Funcionario a = funcionario.buscarPorId(5L);
			a.setNome("Pedro");
			a.setNum_dep(3);
			a.setSalario(new BigDecimal(300));
			a.setNascimento(LocalDate.now());
			
			if (a.getId() == null) {
				funcionario.incluir(a);
			} else {
				funcionario.alterar(a.getId(), a);
			}
			
			System.out.println(funcionario.listar());
			
			for (var f: funcionario.buscarPorNome("Pedro")) {
				System.out.println(f);
			}
			
			for (var f: funcionario.buscarPorNumDependentes(2)) {
				System.out.println(f);
			}
			
			for (var f: funcionario.buscarPorNome("Alex")) {
				System.out.println(f);
			}
		};
	}
}