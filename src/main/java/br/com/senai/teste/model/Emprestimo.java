
package br.com.senai.teste.model;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

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
    private LocalDate dataPrevistaDevolucao;
    private LocalDate dataDevolucao;
    private LocalDate dataEmprestimo;
    private static  BigDecimal ValorMulta = new BigDecimal("2.00"); 
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
    public LocalDate getDataDevolucao() {
        return dataDevolucao;
    }
    public void setDataDevolucao(LocalDate dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }
    public LocalDate getDataPrevistaDevolucao() {
        return dataPrevistaDevolucao;
    }
    public void setDataPrevistaDevolucao(LocalDate dataPrevistaDevolucao) {
        this.dataPrevistaDevolucao = dataPrevistaDevolucao;
    }


    public String getSituacao(){
        if (dataDevolucao != null) {
            return "Devolvido";
        }
        if (dataPrevistaDevolucao == null){
            return "Sem Previsão";
        }
        if(dataPrevistaDevolucao.isBefore(LocalDate.now())){
        
        return "Atrasado";
    }
    return "Ativo";
}
public long getDiasAtraso() {
    if (dataDevolucao != null 
        || dataPrevistaDevolucao == null 
        || !dataPrevistaDevolucao.isBefore(LocalDate.now())) {
          return 0;}
        return ChronoUnit.DAYS.between(dataPrevistaDevolucao, LocalDate.now());
    }
  public BigDecimal getValorMulta() {

    return ValorMulta.multiply(BigDecimal.valueOf(getDiasAtraso()));
  }
}
