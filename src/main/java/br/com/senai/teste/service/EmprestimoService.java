package br.com.senai.teste.service;

import java.util.Optional;
import java.lang.Integer;
import org.springframework.stereotype.Service;
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
    public Optional<Emprestimo> cadastar(Integer idAluno, Integer idLivro) 
    
    {
            Optional<Aluno> aluno = alunoRepository.findById(idAluno);
            Optional<Livro> livro = livroRepository.findById(idLivro);
            if(aluno.isEmpty() || livro.isEmpty()){
                return Optional.empty();
            }
    
}
}