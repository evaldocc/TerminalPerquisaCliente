package com.evaldo.terminalperquisacliente.classes;

public class DispositivoCliente {
    String key, idDispositivo, nomeDispositivo, status, questionarioAtual;

    public DispositivoCliente(String idDispositivo, String nomeDispositivo, String status, String questionarioAtual) {
        this.idDispositivo = idDispositivo;
        this.nomeDispositivo = nomeDispositivo;
        this.status = status;
        this.questionarioAtual = questionarioAtual;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getIdDispositivo() {
        return idDispositivo;
    }

    public void setIdDispositivo(String idDispositivo) {
        this.idDispositivo = idDispositivo;
    }

    public String getNomeDispositivo() {
        return nomeDispositivo;
    }

    public void setNomeDispositivo(String nomeDispositivo) {
        this.nomeDispositivo = nomeDispositivo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getQuestionarioAtual() {
        return questionarioAtual;
    }

    public void setQuestionarioAtual(String questionarioAtual) {
        this.questionarioAtual = questionarioAtual;
    }

    @Override
    public String toString() {
        return "DispositivoCliente{" +
                "key='" + key + '\'' +
                ", idDispositivo='" + idDispositivo + '\'' +
                ", nomeDispositivo='" + nomeDispositivo + '\'' +
                ", status='" + status + '\'' +
                ", questionarioAtual='" + questionarioAtual + '\'' +
                '}';
    }
}

