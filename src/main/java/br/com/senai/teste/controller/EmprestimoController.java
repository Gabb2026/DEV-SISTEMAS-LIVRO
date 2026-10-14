package br.com.senai.teste.controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.senai.teste.service.EmprestimoService;
import jakarta.validation.Valid;
import br.com.senai.teste.model.Emprestimo;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import br.com.senai.teste.dto.EmprestimoRequest;
import br.com.senai.teste.dto.RenovacaoRequest;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.time.LocalDate;
@RestController 
@RequestMapping("/emprestimos")
public class EmprestimoController {
    private final EmprestimoService emprestimoService;

    public EmprestimoController(EmprestimoService emprestimoService) {
        this.emprestimoService = emprestimoService;
    }

    @PostMapping
    public ResponseEntity<Emprestimo> cadastrar(
            @Valid @RequestBody EmprestimoRequest dados) {
                Integer idAluno = dados.getIdAluno();
                Integer idLivro = dados.getIdLivro();
                LocalDate dataPrevistaDevolucao = dados.getDataPrevistaDevolucao();
            if (idAluno == null || idLivro == null || dataPrevistaDevolucao == null) {
                return ResponseEntity.badRequest().build();
            }


            Optional<Emprestimo> emprestimo = emprestimoService.cadastrar(
            idAluno, idLivro, dataPrevistaDevolucao);
             if (emprestimo.isEmpty()) {
                return ResponseEntity.notFound().build();
             }
             return ResponseEntity
             .status(HttpStatus.CREATED)
             .body(emprestimo.get());
        }
        @GetMapping
        public ResponseEntity<List<Emprestimo>> listarTodos() {
            List<Emprestimo> emprestimos = emprestimoService.listarTodos();
            return ResponseEntity.ok(emprestimos);
        }
        @GetMapping ("/{id}")
        public ResponseEntity<Emprestimo> buscarPorId(@PathVariable Integer id) {
            Optional<Emprestimo> emprestimo = emprestimoService.buscarPorId(id);
            if (emprestimo.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(emprestimo.get());
        }
        @PatchMapping ("/{id}/devolucao")
        public ResponseEntity<Emprestimo> devolver(@PathVariable Integer id){
            Optional<Emprestimo> emprestimo = emprestimoService.devolver(id);
            if (emprestimo.isPresent()) {
                return ResponseEntity.ok(emprestimo.get());
        }
            return ResponseEntity.notFound().build();
        }
@GetMapping ("/ativos")
    public ResponseEntity<List<Emprestimo>> listarEmprestimosAtivos() {
        List<Emprestimo> emprestimosAtivos = emprestimoService.listarEmprestimosAtivos();
        return ResponseEntity.ok(emprestimosAtivos);
    }
    @GetMapping ("/aluno/{alunoId}")
        public ResponseEntity<List<Emprestimo>> listarPorAluno(@PathVariable Integer alunoId){
            List<Emprestimo>  emprestimos = emprestimoService.listarPorAluno(alunoId);
            return ResponseEntity.ok(emprestimos);

        }
         @GetMapping ("/livro/{livroId}")
        public ResponseEntity<List<Emprestimo>> listarPorLivro(@PathVariable Integer livroId){
            List<Emprestimo>  emprestimos = emprestimoService.listarPorLivro(livroId);
            return ResponseEntity.ok(emprestimos);

        }
        @GetMapping("/atrasados")
        public ResponseEntity<List<Emprestimo>> ListarEmprestimosAtrasados(){
            List<Emprestimo> emprestimosAtrasados = emprestimoService.ListarEmprestimosAtrasados();
            return ResponseEntity.ok(emprestimosAtrasados);


        }
        @GetMapping("/aluno/{alunoId}/atrasados")
        public ResponseEntity<List<Emprestimo>> ListarAtrasadosPorAluno(@PathVariable Integer alunoId){
            List<Emprestimo> emprestimosAtrasadosPorAluno = emprestimoService.ListarAtrasadosPorAluno(alunoId);
            return ResponseEntity.ok(emprestimosAtrasadosPorAluno);
        }
        @PatchMapping("/{id}/renovacao")
        public ResponseEntity<Emprestimo> renovar(@PathVariable Integer id, @Valid @RequestBody RenovacaoRequest dados) {
            Optional<Emprestimo> emprestimo = emprestimoService.renovar(id, dados.getNovaDataPrevista());
            if (emprestimo.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
    
            return ResponseEntity.ok(emprestimo.get());
        }
       
    }
