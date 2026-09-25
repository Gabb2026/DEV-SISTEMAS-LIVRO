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
import br.com.senai.teste.model.Livro;
import br.com.senai.teste.service.LivroService;
import org.springframework.web.bind.annotation.DeleteMapping;
@RestController
@RequestMapping ("/Livro")
public class LivroController {
 private final LivroService livroService;
 public LivroController (LivroService livroService) {
    this.livroService = livroService;   
    
}
@PostMapping
public ResponseEntity<Livro> cadastrar (
    @RequestBody Livro livro){
        Livro livroCadastrado = livroService.cadastrar(livro);
        return ResponseEntity
                 .status(HttpStatus.CREATED)
                 .body(livroCadastrado);
    }

    @GetMapping
public ResponseEntity<List<Livro>> listar(){
    List<Livro> livro = livroService.Listar();
    return ResponseEntity.ok(livro);
}


@GetMapping("/{id}")
public ResponseEntity<Livro> buscarPorId(@PathVariable Integer id){
Optional<Livro> livroOptional = livroService.buscarPorId(id);
if (livroOptional.isPresent()) {
    Livro livro = livroOptional.get();
    return ResponseEntity.ok(livro);
} else {
    return ResponseEntity.notFound().build();   
}}
@PutMapping("/{id}")
public ResponseEntity<Livro> atualizar(@PathVariable Integer id, @RequestBody Livro novosDados){
    Optional<Livro> livroOptional = livroService.atualizar(id, novosDados);
    if (livroOptional.isPresent()) {
        return ResponseEntity.ok(livroOptional.get());
    } else {
        return ResponseEntity.notFound().build();
    }
}
@DeleteMapping("/{id}")
public ResponseEntity<Void> excluir(@PathVariable Integer id){
    boolean excluido = livroService.excluir(id);
    if (excluido) {
        return ResponseEntity.noContent().build();
    } else {
        return ResponseEntity.notFound().build();
    }
}


}
