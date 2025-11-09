package br.com.fiap.exception;

public class MensageErro {
    private String mensagem;
    private int statusCode;

    public MensageErro(String mensagem, int statusCode) {
        this.mensagem = mensagem;
        this.statusCode = statusCode;
    }

    public String getMensagem() { return mensagem; }
    public void setMensagem(String mensagem) { this.mensagem = mensagem; }
    public int getStatusCode() { return statusCode; }
    public void setStatusCode(int statusCode) { this.statusCode = statusCode; }
}