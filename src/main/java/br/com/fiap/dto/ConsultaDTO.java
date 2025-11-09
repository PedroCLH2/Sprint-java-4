package br.com.fiap.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class ConsultaDTO {

    @NotNull(message = "A data e hora da consulta são obrigatórias")
    @Future(message = "A data da consulta deve ser no futuro")
    private LocalDateTime dataHora;

    private String motivo;

    @NotNull(message = "O ID do paciente é obrigatório")
    private Long idPaciente;

    @NotNull(message = "O ID do médico é obrigatório")
    private Long idMedico;

    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }
    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }
    public Long getIdPaciente() { return idPaciente; }
    public void setIdPaciente(Long idPaciente) { this.idPaciente = idPaciente; }
    public Long getIdMedico() { return idMedico; }
    public void setIdMedico(Long idMedico) { this.idMedico = idMedico; }
}