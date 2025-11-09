package br.com.fiap.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class MedicoDTO {

    @NotBlank(message = "O nome do médico é obrigatório")
    private String nome;

    @NotBlank(message = "O CRM é obrigatório")
    @Pattern(regexp = "CRM/[A-Z]{2} \\d{6}", message = "O CRM deve estar no formato 'CRM/UF 123456'")
    private String crm;

    @NotBlank(message = "A especialidade é obrigatória")
    @Size(min = 3, message = "A especialidade deve ter ao menos 3 caracteres")
    private String especialidade;

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getCrm() { return crm; }
    public void setCrm(String crm) { this.crm = crm; }
    public String getEspecialidade() { return especialidade; }
    public void setEspecialidade(String especialidade) { this.especialidade = especialidade; }
}