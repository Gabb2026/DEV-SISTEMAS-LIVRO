package br.com.senai.teste.controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.senai.teste.service.EmprestimoService;
import br.com.senai.teste.model.Emprestimo;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import br.com.senai.teste.dto.EmprestimoRequest;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController 
@RequestMapping("/emprestimos")
public class EmprestimoController {
    private final EmprestimoService emprestimoService;

    public EmprestimoController(EmprestimoService emprestimoService) {
        this.emprestimoService = emprestimoService;
    }

    @PostMapping
    public ResponseEntity<Emprestimo> cadastrar(
            @RequestBody EmprestimoRequest dados) {
                Integer idAluno = dados.getIdAluno();
                Integer idLivro = dados.getIdLivro();
            if (idAluno == null || idLivro == null) {
                return ResponseEntity.badRequest().build();
            }


            Optional<Emprestimo> emprestimo = emprestimoService.cadastrar(
            idAluno, idLivro);
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

}

