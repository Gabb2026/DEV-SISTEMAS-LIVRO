
package br.com.senai.teste.model;
import java.time.LocalDate;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType; 
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;
@Entity 
@Table (name = "emprestimos")
public class Emprestimo {
    @Id
    @GeneratedValue ( strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDate dataEmprestimo;
    @ManyToOne
    @JoinColumn (name = "aluno_id", nullable = false)
    private Aluno aluno; 
@ManyToOne 
@JoinColumn (name = "livro_id", nullable = false)
    private Livro livro;

    public Emprestimo() {
    }

    public Emprestimo(LocalDate dataEmprestimo, Aluno aluno, Livro livro) {
        this.dataEmprestimo = dataEmprestimo;
        this.aluno = aluno;
        this.livro = livro;
    }

    public Integer getId() {
        return id;
    }

    public LocalDate getDataEmprestimo() {
        return dataEmprestimo;
    }

    public void setDataEmprestimo(LocalDate dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }
    
}
