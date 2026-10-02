package br.com.senai.teste.repository;
import br.com.senai.teste.model.Emprestimo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

    public interface EmprestimoRepository
        extends JpaRepository<Emprestimo, Integer> {
              boolean existsByAlunoIdAndLivroIdAndDataDevolucaoIsNull(Integer alunoId, Integer livroId);
              List<Emprestimo> findByDataDevolucaoIsNull();
              List<Emprestimo>findByAlunoId (Integer alunoId);
              List<Emprestimo>findByLivroId (Integer LivroId);
              List<Emprestimo>findByDataPrevistaDevolucaoBeforeAndDataDevolucaoIsNull(LocalDate dataAtual);
              List<Emprestimo> findByAlunoIdAndDataPrevistaDevolucaoBeforeAndDataDevolucaoIsNull(Integer alunoId, LocalDate dataAtual);
              
        }

    