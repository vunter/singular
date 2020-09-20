package com.mybusiness.myrequirementmodule.model.dto;

public class CEPDto {

    private String logradouro;
    private String complemento;
    private String localidade;
    private String uf;
    private String bairro;

    public CEPDto() {
    }

    public CEPDto(String logradouro, String complemento, String localidade, String uf, String bairro) {
        this.logradouro = logradouro;
        this.complemento = complemento;
        this.localidade = localidade;
        this.uf = uf;
        this.bairro = bairro;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getLocalidade() {
        return localidade;
    }

    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }
}
