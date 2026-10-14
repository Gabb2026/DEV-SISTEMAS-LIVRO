package br.com.senai.teste.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

public class EmprestimoRequest {
    @NotNull(message = "O ID do aluno não pode ser nulo")
    private Integer idAluno;
    @NotNull (message = "O ID do livro não pode ser nulo")
    private Integer idLivro;
    @NotNull (message = "A data prevista de devolução não pode ser nula")
    @Future (message = "A data prevista deve ester no futuro")
    private LocalDate dataPrevistaDevolucao;

    public Integer getIdAluno() {
        return idAluno;
    }
    public void setIdAluno(Integer idAluno) {
        this.idAluno = idAluno;
    }
    public Integer getIdLivro() {
        return idLivro;
    }
    public void setIdLivro(Integer idLivro) {
        this.idLivro = idLivro;
    }

    public LocalDate getDataPrevistaDevolucao() {
        return dataPrevistaDevolucao;
    }

    public void setDataPrevistaDevolucao(LocalDate dataPrevistaDevolucao) {
        this.dataPrevistaDevolucao = dataPrevistaDevolucao;
    }

}
