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
import java.time.LocalDate;
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
    public Optional<Emprestimo> cadastrar(Integer idAluno, Integer idLivro, LocalDate dataPrevistaDevolucao) 
    
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
            emprestimo.setDataPrevistaDevolucao(dataPrevistaDevolucao);

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
public List<Emprestimo> listarEmprestimosAtivos() {
        return emprestimoRepository.findByDataDevolucaoIsNull();
    }
public List<Emprestimo> listarPorAluno(Integer alunoId){
return emprestimoRepository.findByAlunoId(alunoId);
}


public List<Emprestimo> listarPorLivro(Integer livroId)
{
    return emprestimoRepository.findByLivroId(livroId);
}
public List <Emprestimo> ListarEmprestimosAtrasados(){
    return emprestimoRepository.findByDataPrevistaDevolucaoBeforeAndDataDevolucaoIsNull(LocalDate.now());


}
public List<Emprestimo> ListarAtrasadosPorAluno(Integer alunoId){
    return emprestimoRepository.findByAlunoIdAndDataPrevistaDevolucaoBeforeAndDataDevolucaoIsNull(alunoId, LocalDate.now());

}
public Optional<Emprestimo> renovar(Integer id, LocalDate novaDataPrevista) {
    Optional<Emprestimo> emprestimoOptional = emprestimoRepository.findById(id);
    if (emprestimoOptional.isEmpty()) {
        return Optional.empty();
    }
    Emprestimo emprestimo = emprestimoOptional.get();
    if (emprestimo.getDataDevolucao() != null) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O empréstimo já foi devolvido e não pode ser renovado.");
    }
    if(!novaDataPrevista.isAfter(emprestimo.getDataPrevistaDevolucao())) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A nova data prevista de devolução deve ser posterior à data atual.");
    }
    emprestimo.setDataPrevistaDevolucao(novaDataPrevista);
    return Optional.of(emprestimoRepository.save(emprestimo));
}
}