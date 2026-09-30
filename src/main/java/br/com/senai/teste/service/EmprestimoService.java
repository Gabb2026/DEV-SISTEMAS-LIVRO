package br.com.senai.teste.service;
import java.util.List;
import java.util.Optional;
import java.lang.Integer;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.com.senai.teste.model.Aluno;
import br.com.senai.teste.model.Emprestimo;
import br.com.senai.teste.model.Livro;
import br.com.senai.teste.repository.AlunoRepository;
import br.com.senai.teste.repository.EmprestimoRepository;
import br.com.senai.teste.repository.LivroRepository;

@Service
public class EmprestimoService {
    private final EmprestimoRepository emprestimoRepository;
    private final AlunoRepository alunoRepository;
    private final LivroRepository livroRepository;

    public EmprestimoService(EmprestimoRepository emprestimoRepository, AlunoRepository alunoRepository, LivroRepository livroRepository) {
        this.emprestimoRepository = emprestimoRepository;
        this.alunoRepository = alunoRepository;
        this.livroRepository = livroRepository;
    }
    public Optional<Emprestimo> cadastrar(Integer idAluno, Integer idLivro) 
    
    {
            Optional<Aluno> aluno = alunoRepository.findById(idAluno);
            Optional<Livro> livro = livroRepository.findById(idLivro);
            if(aluno.isEmpty() || livro.isEmpty()){
                return Optional.empty();
            }
            boolean livroEmprestado = emprestimoRepository.existsByAlunoIdAndLivroIdAndDataDevolucaoIsNull(idAluno, idLivro);
            if (livroEmprestado) {
                throw new ResponseStatusException(HttpStatus.CONFLICT , "O livro já está emprestado para este aluno.");
            }
            Emprestimo emprestimo = new Emprestimo();
            emprestimo.setAluno(aluno.get());
            emprestimo.setLivro(livro.get());
            emprestimo.setDataEmprestimo(java.time.LocalDate.now());

            return Optional.of(emprestimoRepository.save(emprestimo));
    
}
public List<Emprestimo> listarTodos() {
        return emprestimoRepository.findAll();
    }
    public Optional<Emprestimo> buscarPorId(Integer id) {
        return emprestimoRepository.findById(id);
    }
    public Optional<Emprestimo> devolver(Integer id) {
        Optional<Emprestimo> encontrado = emprestimoRepository.findById(id);
    if (encontrado.isEmpty()) {
        return Optional.empty();
    }
    Emprestimo emprestimo = encontrado.get();   
    if(emprestimo.getDataDevolucao() == null) {
        emprestimo.setDataDevolucao(java.time.LocalDate.now());
        emprestimoRepository.save(emprestimo);
    }
    return Optional.of(emprestimo);
}
}