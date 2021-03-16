package com.example.ganu.domain;

public class Article {
    private String version = "2.0";
    private String resultCode = "OK";
    public Output output;

    public Article(Output output) {
        this.output = output;
    }
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }
}
