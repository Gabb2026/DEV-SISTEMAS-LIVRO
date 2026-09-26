package br.com.senai.teste.repository;
import br.com.senai.teste.model.Emprestimo;
import org.springframework.data.jpa.repository.JpaRepository;


    public interface EmprestimoRepository
        extends JpaRepository<Emprestimo, Integer> {

        }

    