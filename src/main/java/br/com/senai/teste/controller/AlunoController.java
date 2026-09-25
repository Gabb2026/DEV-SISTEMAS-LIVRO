package br.com.senai.teste.controller;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.senai.teste.model.Aluno;
import br.com.senai.teste.service.AlunoService;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
@RestController
@RequestMapping ("/alunos")
public class AlunoController {
 private final AlunoService alunoService;
 public AlunoController (AlunoService alunoService) {
    this.alunoService = alunoService;   
}


@PostMapping
public ResponseEntity<Aluno> cadastrar (
   @Valid @RequestBody Aluno aluno){
        Aluno alunoCadastrado = alunoService.cadastrar(aluno);
        return ResponseEntity
                 .status(HttpStatus.CREATED)
                 .body(alunoCadastrado);
    }



@GetMapping
public ResponseEntity<List<Aluno>> listar(){
    List<Aluno> alunos = alunoService.Listar();
    return ResponseEntity.ok(alunos);
}
@GetMapping("/{id}")
public ResponseEntity<Aluno> buscarPorId(@PathVariable Integer id){
Optional<Aluno> alunoOptional = alunoService.buscarPorId(id);
if (alunoOptional.isPresent()) {
    Aluno aluno = alunoOptional.get();
    return ResponseEntity.ok(aluno);
} else {
    return ResponseEntity.notFound().build();   
}}
@PutMapping("/{id}")
public ResponseEntity<Aluno> atualizar(@PathVariable Integer id, @Valid @RequestBody Aluno novosDados){
    Optional<Aluno> alunoOptional = alunoService.atualizar(id, novosDados);
    if (alunoOptional.isPresent()) {
        return ResponseEntity.ok(alunoOptional.get());
    } else {
        return ResponseEntity.notFound().build();
    }
}

@DeleteMapping("/{id}")
public ResponseEntity<Void> excluir(@PathVariable Integer id){
    boolean excluido = alunoService.excluir(id);
    if (excluido) {
        return ResponseEntity.noContent().build();
    } else {
        return ResponseEntity.notFound().build();
    }
}

}