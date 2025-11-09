package br.com.fiap.model;

import java.time.LocalDateTime;

public class Consulta {

    private Long id;
    private Paciente paciente;
    private Medico medico;
    private LocalDateTime dataHora;
    private String motivo;

    public Consulta() {
    }

    public Consulta(Long id, Paciente paciente, Medico medico, LocalDateTime dataHora, String motivo) {
        this.id = id;
        this.paciente = paciente;
        this.medico = medico;
        this.dataHora = dataHora;
        this.motivo = motivo;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
}